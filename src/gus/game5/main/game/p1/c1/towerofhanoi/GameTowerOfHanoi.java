package gus.game5.main.game.p1.c1.towerofhanoi;

import static gus.game5.core.util.UtilGui.action;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.math.BigInteger;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRect;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilDurationS;

public class GameTowerOfHanoi extends Game1 {
	
	public static final String TITLE = "Tower of Hanoi";
	public static final int GAME_WIDTH = 600;
	public static final int GAME_HEIGHT = 600;

	public static final int X1 = 100;
	public static final int X2 = 300;
	public static final int X3 = 500;
	
	public static final int Y_BOTTOM = 530;
	public static final int Y_TOP = 100;
	public static final int DISK_MAX_HEIGHT = 70;
	public static final int DISK_MIN_WIDTH = 30;
	public static final int DISK_MAX_WIDTH = 180;
	
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	
	public static void main(String[] args) {
		GameTowerOfHanoi main = new GameTowerOfHanoi();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit),
			action("About (F3)", this::displayAbout),
			null,
			action("Next level (\u2192)", this::nextLevel),
			action("Previous level (\u2190)", this::previousLevel)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(new Color(204,255,255));
		s.setFont(new Font("Calibri", Font.PLAIN, 12));
	}
	
	/*
	 * ABOUT
	 */
	
	private JTextPaneAbout paneAbout;
	
	private void displayAbout() {
		paneAbout.display();
	}
	
	/*
	 * DATA
	 */

	private DrawingText completeDisplay;
	private DrawingText estimatedTimeDisplay;
	private DrawingText moveNbDisplay;
	private DrawingText diskNbDisplay;
	
	private DynTimer completeTimer;
	private ShapeList<Disk> disks;
	private ShapeList<Disk> tower1; //LEFT
	private ShapeList<Disk> tower2; //CENTER
	private ShapeList<Disk> tower3; //RIGHT
	private Disk dragged;
	
	private int diskNumber = 3;
	private int moveNumber = 0;
	private Place startPlace = Place.LEFT;
	private Place endPlace = Place.RIGHT;
	
	
	protected void initialize1() {
		paneAbout = new JTextPaneAbout();
		
		cloud(50,35);
		cloud(70,35);
		cloud(90,35);
		cloud(110,40);

		cloud(60,55);
		cloud(80,55);
		cloud(90,55);
		cloud(422,55);
		cloud(466,51);
		cloud(453,33);
		cloud(439,58);
		
		cloud(260,210);
		cloud(270,230);
		cloud(270,205);
		cloud(300,197);
		cloud(255,198);
		cloud(244,205);
		cloud(289,200);
		
		cloud(320,250);
		cloud(340,245);
		cloud(305,248);
		
		cloud(520,150);
		cloud(540,145);
		cloud(505,140);
		cloud(560,140);
		
		disks = newShapeList();
		tower1 = newShapeList();
		tower2 = newShapeList();
		tower3 = newShapeList();
		
		completeTimer = newDynTimer();
		moveNumber = 0;
		initTowers();
		
		addDraw(new GroundDraw());
		addDraw(new Drag());
		
		completeDisplay = newDrawingTextC(Color.BLUE, p1(gameCenter().getX(), 50), "Level Complete");
		completeDisplay.setDrawable(completeTimer);
		completeDisplay.setFont(FONT);
		completeDisplay.setFontBold(25);
		
		estimatedTimeDisplay = newDrawingTextP(p1(gameWidth()-30, 30), ()->"Estimated time: "+estimatedTime(), 1, 0);
		estimatedTimeDisplay.setFont(FONT);
		estimatedTimeDisplay.setFont(18);
		
		moveNbDisplay = newDrawingText(p1(30, 30), ()->"Move nb: "+moveNumber);
		moveNbDisplay.setFont(FONT);
		moveNbDisplay.setFont(18);
		
		diskNbDisplay = newDrawingTextC(Color.WHITE, p1(gameCenter().getX(), Y_BOTTOM+30), ()->"Tower with "+diskNumber+" disks");
		diskNbDisplay.setFont(FONT);
		diskNbDisplay.setFont(18);
	}

	
	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) restart();
		if(k.in().F2()) exit();
		if(k.in().F3())	displayAbout();
		
		goNext();
		if(completeTimer.gBool()) return;
		
		if(k.in().right()) nextLevel();
		if(k.in().left()) previousLevel();
		
		if(mouse().button1().justPressed()) {
			Disk selected = disks.findCo(mouse().pointCurrent());
			if(selected!=null && selected.isTop()) {
				dragged = selected;
			}
		}
		else if(mouse().button1().justReleased()) {
			Place place = findPlaceAtMouse();
			moveDisk(dragged, place);
			dragged = null;
			
			if(isLevelComplete()) {
				completeTimer.start(60, this::nextLevel);
			}
		}
	}
	
	private void initTowers() {
		disks.clear();
		tower1.clear();
		tower2.clear();
		tower3.clear();
		
		for(int i=0;i<diskNumber;i++) {
			double width = DISK_MIN_WIDTH + (DISK_MAX_WIDTH-DISK_MIN_WIDTH)*(diskNumber-i)/diskNumber;
			double height = Math.min(DISK_MAX_HEIGHT, (Y_BOTTOM-Y_TOP)/diskNumber);
			Disk disk = new Disk(width, height, i);
			disk.initPlace(startPlace);
			disks.add(disk);
		}
	}
	
	private void nextLevel() {
		if(diskNumber>=100) return;
		diskNumber++;
		moveNumber = 0;
		initTowers();
	}
	
	private void previousLevel() {
		if(diskNumber<=1) return;
		diskNumber--;
		moveNumber = 0;
		initTowers();
	}
	
	private Place findPlaceAtMouse() {
		Point0 p = mouse().pointCurrent();
		if(p==null) return null;
		double px = p.getX();
		if(px<200) return Place.LEFT;
		if(px<400) return Place.CENTER;
		if(px<600) return Place.RIGHT;
		return null;
	}
	
	private double findCenterXForPlace(Place place) {
		if(place==Place.LEFT) return X1;
		if(place==Place.CENTER) return X2;
		return X3;
	}
	
	private ShapeList<Disk> findTowerForPlace(Place place) {
		if(place==Place.LEFT) return tower1;
		if(place==Place.CENTER) return tower2;
		return tower3;
	}
	
	private void moveDisk(Disk disk, Place place) {
		if(place==null || disk==null) return;
		if(!disk.isAllowedOnTop(place)) return;
		
		disk.initPlace(place);
		moveNumber++;
	}
	
	private boolean isLevelComplete() {
		return findTowerForPlace(endPlace).size()==diskNumber;
	}
	
	private String estimatedTime() {
		BigInteger d1 = new BigInteger("1");
		BigInteger d2 = new BigInteger("2");

		BigInteger d = new BigInteger("1");
		for(int i=0;i<diskNumber;i++) d = d.multiply(d2);
		d = d.subtract(d1);
		return UtilDurationS.getDisplay(d);
	}
	
	
	private enum Place {
		LEFT, CENTER, RIGHT
	}
	
	private class Disk extends ShapeRect {
		private ShapeList<Disk> tower;
		private int fullIndex;
		private int towerIndex;

		public Disk(double width, double height, int fullIndex) {
			super(p(0,0), width, height);
			this.fullIndex = fullIndex;
			setColor(Color.BLACK);
		}
		
		protected void drawShape() {
			Composite composite = g2_getComposite();
			boolean isDragged = this==dragged;
			
			if(isDragged) g2_setComposite(ALPHA);
			fillRectC(new Color(0,204,102), getWidth(), getHeight());
			drawRectC(getWidth(), getHeight());
			if(isDragged) g2_setComposite(composite);
		}
		
		public boolean isTop() {
			if(tower==null) return false;
			return towerIndex==tower.size()-1;
		}
		
		public void initPlace(Place place) {
			if(tower!=null) tower.remove(towerIndex);
			tower = findTowerForPlace(place);
			tower.add(this);
			towerIndex = tower.size()-1;
			
			double x = findCenterXForPlace(place);
			double y = Y_BOTTOM-height*towerIndex-height*0.5;
			getAnchor().setXY(x,y);
		}
		
		public boolean isAllowedOnTop(Place place) {
			ShapeList<Disk> t = findTowerForPlace(place);
			if(t.isEmpty()) return true;
			Disk last = t.get(t.size()-1);
			return last.fullIndex<fullIndex;
		}
	}
	
	private class GroundDraw extends Drawing1 {
		
		public GroundDraw() {
			
		}
		protected void draw() {
			fillRect(p1(0, Y_BOTTOM + 10),  gameWidth(), gameHeight() - Y_BOTTOM - 10);
			fillRect(p1(X1 - DISK_MAX_WIDTH*0.5 - 1, Y_BOTTOM),  DISK_MAX_WIDTH + 2, 12);
			fillRect(p1(X2 - DISK_MAX_WIDTH*0.5 - 1, Y_BOTTOM),  DISK_MAX_WIDTH + 2, 12);
			fillRect(p1(X3 - DISK_MAX_WIDTH*0.5 - 1, Y_BOTTOM),  DISK_MAX_WIDTH + 2, 12);
		}
	}
	
	/*
	 * DRAG
	 */
	
	private class Drag extends Drawing1 {
		public Drag() {
			super();
			setOrigin(new Point1D0(mouse()::point));
		}
		protected void draw() {
			if(dragged!=null) {
				fillRectC(new Color(0,204,102), dragged.getWidth(), dragged.getHeight());
				drawRectC(dragged.getWidth(), dragged.getHeight());
			}
		}
	}
	
	/*
	 * CLOUD
	 */
	
	private void cloud(double xc, double yc) {
		newShape(new Cloud(p2(xc + 20, yc + 20)));
	}
	
	private class Cloud extends ShapeRound {
		public Cloud(Point2 anchor) {
			super(anchor, 15);
			setColor(Color.WHITE);
			anchor.setDerived(2, 0);
		}
		
		public void goNext() {
			super.goNext();
			if(getP0().getX()>gameWidth()) 
				getAnchor().addX(-gameWidth()-100);
		}
	}
}
