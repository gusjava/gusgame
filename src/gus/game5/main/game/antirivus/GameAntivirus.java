package gus.game5.main.game.antirivus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JDialog1;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.persister.Persister1;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilRandom;

public class GameAntivirus extends Game1 {

	public static final String TITLE = "Antivirus";
	public static final int GAME_HEIGHT = 500;
	public static final int GAME_WIDTH = 500;
	public static final double CELL_SIZE = 50;
	
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameAntivirus main = new GameAntivirus();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * GUI
	 */
	
	private JDialog1 dialog = new JDialog1();
	private JTextPaneAbout paneAbout = new JTextPaneAbout();
	private JPanelLevel panelLevel = new JPanelLevel();
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit),
			action("About (F3)", this::displayAbout),
			null,
			action("Choose level (SHIFT)", this::chooseLevel),
			action("Next level (\u2192)", this::levelUp),
			action("Previous level (\u2190)", this::levelDown),
			action("First level (\u2191)", this::levelFirst),
			action("Last level (\u2193)", this::levelLast)
		);
		menuBar.add("Profile", 
			action("Create new profile", this::createNewProfile),
			action("Load profile", this::loadProfile),
			action("Close profile", this::closeProfile));
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Calibri", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */

	private ShapeBoard<Cell> board;
	private DrawingText levelTitle;
	private DrawingText profileDisplay;
	
	private Cell draggedCell;
	private List<Cell> draggedPiece;
	private int draggedValue;
	
	private int[][] data;
	private int level;
	private Profile profile;
	private Persister1 persister;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		board = newShapeBoard(CELL_SIZE, 8, 7, Cell::new);
		draggedCell = null;
		draggedPiece = null;
		draggedValue = UtilAntivirus.EMPTY;
		
		addDraw(new Drag());
		addDraw(new LevelChip());
		
		levelTitle = newDrawingTextP(p1(gameWidth()-20, 20), "", 1, 1);
		levelTitle.setFontBold(26);
		
		profileDisplay = newDrawingTextP(p1(gameWidth()-20, 50), ()->profile.getDisplay(), 1, 1);
		profileDisplay.setDrawable(()->profile!=null);
		profileDisplay.setFontBold(14);
		
		persister = new Persister1(this);
		
		String currentProfileId = persister.get("CURRENT_PROFILE_ID");
		if(currentProfileId!=null) loadProfile(currentProfileId);
		else changeLevel(1);
	}
	
	/*
	 * ABOUT
	 */
	
	private void displayAbout() {
		dialog.showContent(paneAbout, 1000, 700);
	}
	
	/*
	 * LEVEL	
	 */
	
	private void levelWon() {
		if(profile!=null && level<UtilAntivirus.LEVEL_NUMBER) 
			profile.accessLevel(level+1);
		levelUp(); 
	}
	
	private void changeLevel(int level) {
		if(level<1) return;
		if(level>getLastLevel()) return;
		
		this.level = level;
		data = UtilAntivirus.dataForLevel(level);
		
		levelTitle.setString(UtilAntivirus.getLevelTitle(level));
		levelTitle.setColor(UtilAntivirus.getLevelColor(level));
	}
	
	private void levelUp() {
		changeLevel(level+1);
	}
	
	private void levelDown() {
		changeLevel(level-1);
	}
	
	private void levelFirst() {
		changeLevel(1);
	}
	
	private void levelLast() {
		changeLevel(getLastLevel());
	}
	
	private void chooseLevel() {
		panelLevel.setLevel(level);
		panelLevel.setMaxLevel(getLastLevel());
		dialog.showContent(panelLevel, 1000, 700);
	}
	
	private int getLastLevel() {
		if(profile!=null) return profile.getLevel();
		return UtilAntivirus.LEVEL_NUMBER;
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		if(k.in().F3())	displayAbout();

		if(k.in().shift())	chooseLevel();
		if(k.in().right()) levelUp();
		if(k.in().left()) levelDown();
		if(k.in().up()) levelFirst();
		if(k.in().down()) levelLast();
		
		if(mouse().button1().justPressed()) {
			Cell pressedCell = board.cellAt(mouse().pointCurrent());
			if(pressedCell!=null && pressedCell.isDraggable()) {
				draggedCell = pressedCell;
				draggedValue = pressedCell.getValue();
				draggedPiece = board.findAll(c->c.getValue()==draggedValue);
			}
		}
		else if(mouse().button1().justReleased()) {
			Cell releasedCell = board.cellAt(mouse().pointCurrent());
			if(releasedCell!=null && draggedCell!=null) {
				int[] releasedPos = releasedCell.getIJ();
				int[] draggedPos = draggedCell.getIJ();
				handleMoves(draggedPos, releasedPos);
			}
			draggedCell = null;
			draggedPiece = null;
			draggedValue = UtilAntivirus.EMPTY;
			
			if(getOutputCell().hasVirus()) {
				levelWon();
			}
		}
	}
	
	private void handleMoves(int[] startPos, int[] endPos) {
		int di = endPos[0]-startPos[0];
		int dj = endPos[1]-startPos[1];
		
		if(di==0 && dj==0) return;
		if(di==0) {
			int dj1 = Math.abs(dj);
			int dj0 = dj<0 ? -1 : 1;
			
			for(int n=0;n<dj1;n++) {
				List<Cell[]> moves = findMoves(0, dj0);
				if(moves==null) return;
				
				int[][] data1 = UtilArray.clone(data);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[0].getIJ(), UtilAntivirus.EMPTY);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[1].getIJ(), move[0].getValue());
				data = data1;
			}
		}
		if(dj==0) {
			int di1 = Math.abs(di);
			int di0 = di<0 ? -1 : 1;
			
			for(int n=0;n<di1;n++) {
				List<Cell[]> moves = findMoves(di0, 0);
				if(moves==null) return;
				
				int[][] data1 = UtilArray.clone(data);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[0].getIJ(), UtilAntivirus.EMPTY);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[1].getIJ(), move[0].getValue());
				data = data1;
			}
		}
	}
	
	private List<Cell[]> findMoves(int di, int dj) {
		List<Cell[]> target = new ArrayList<>();
		List<Integer> values1 = UtilList.asList(draggedValue);
		List<Integer> values2 = UtilList.asList(draggedValue);
		
		while(!values1.isEmpty()) {
			int value1 = values1.get(0);
			values1.remove(0);
			List<Cell> piece = board.findAll(f->f.getValue()==value1);
			for(Cell c : piece) {
				Cell c1 = board.cellAt(c.getI()+di, c.getJ()+dj);
				if(c1==null || c1.isBlocked()) return null;
				
				target.add(new Cell[] {c, c1});
				if(c1.isPiece()) {
					int newValue = c1.getValue();
					if(!values2.contains(newValue)) {
						values1.add(newValue);
						values2.add(newValue);
					}
				}
			}
		}
		return target;
	}
	
	private Cell getOutputCell() {
		return board.cellAt(UtilAntivirus.OUTPUT_I, UtilAntivirus.OUTPUT_J);
	}
	
	
	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
			setColor(Color.GRAY);
		}
		
		protected void initAnchor() {
			int k = i+j;
			double px = gameHeight()*0.5 + CELL_SIZE*1.4*0.5*(k - 0.9*x);
			double py = gameWidth()*0.5 - 50 - CELL_SIZE*1.4*(j - 0.5*k);
			setAnchor(new Point2(px, py));
		}
		
		public Color getColor() {
			return isEmpty() ? UtilAntivirusDraw.COLOR_EMPTY : UtilAntivirus.COLORS[getValue()];
		}
		public int getValue() {
			return data[i][j];
		}
		public boolean isPiece() {
			return data[i][j]>=0 && data[i][j]<=8;
		}
		public boolean isEmpty() {
			return data[i][j]==UtilAntivirus.EMPTY;
		}
		public boolean isBlocked() {
			return data[i][j]==UtilAntivirus.BLOCKED;
		}
		public boolean isExternal() {
			return UtilAntivirus.isExternal(i,j);
		}
		public boolean isDraggable() {
			return isPiece();
		}
		public boolean isDragged() {
			return draggedCell!=null && draggedCell.getValue()==getValue();
		}
		public boolean isOutput() {
			return UtilAntivirus.isOutput(i,j);
		}
		public boolean hasVirus() {
			return data[i][j]==UtilAntivirus.PIECE0;
		}
		
		/*
		 * DRAW
		 */
		
		protected void drawShape() {
			drawBoardEdges();
			
			if(!isExternal()) {
				if(isBlocked()) drawBlocked();
				else if(isEmpty()) drawEmpty();
				else if(isPiece()) drawPiece();
			}
		}
		
		private void drawPiece() {
			Composite composite = g2_getComposite();
			boolean isDragged = isDragged();
			
			if(isDragged) g2_setComposite(ALPHA);
			UtilAntivirusDraw.drawPiece(this, p(0,0), getRadius(), data, i, j);
			if(isDragged) g2_setComposite(composite);
		}
		
		private void drawEmpty() {
			UtilAntivirusDraw.drawEmpty(this, getRadius(), data, i, j);
		}
		
		private void drawBlocked() {
			UtilAntivirusDraw.drawBlocked(this, getRadius());
		}
		
		private void drawBoardEdges() {
			UtilAntivirusDraw.drawBoardEdges(this, getRadius(), getIJ());
		}
	}
	
	/*
	 * DRAG
	 */
	
	private class Drag extends Drawing1 {
		public Drag() {
			super();
			setOrigin(new Point1D0(mouse()::point));
			setColor(Color.GRAY);
		}
		protected void draw() {
			if(draggedCell==null) return;
			
			double r = CELL_SIZE*0.5;
			Point1 draggedCellAnchor = draggedCell.getAnchor();
			for(Cell c : draggedPiece) {
				Point1 m = c.getAnchor().pSub(draggedCellAnchor);
				UtilAntivirusDraw.drawPiece(this, m, r, data, c.getI(), c.getJ());
			}
		}
	}
	
	/*
	 * LEVEL CHIP
	 */
	
	private class LevelChip extends Drawing1 {
		public LevelChip() {
			super();
			setOrigin(p1(243, 80));
		}
		protected void draw() {
				Color color = UtilAntivirus.getLevelColor(level);
				fillRoundC(color, 20);
				drawStringC(Color.WHITE, fontBold(23), p(0,-2), ""+level);
		}
	}
	
	/*
	 * PROFILE
	 */
	
	private void createNewProfile() {
		String profileName = JOptionPane.showInputDialog("Please, enter profile's name:");
		if(profileName==null || profileName.equals("")) return;
		
		String profileId = ""+UtilRandom.randomInt(1000);
		persister.put("profile_"+profileId, "name", profileName);
		persister.put("profile_"+profileId, "level", "1");
		
		profile = new Profile(profileId, profileName, 1);
		persister.put("CURRENT_PROFILE_ID", profileId);
		changeLevel(1);
	}
	
	private void loadProfile() {
		List<String> names = persister.names(name->name.startsWith("profile_"));
		List<String> profileIds = UtilList.collect(names, name->name.substring(8));
		int profileNb = profileIds.size();
		if(profileNb==0) return;
		if(profileNb==1) loadProfile(profileIds.get(0));
		else {
			//CHOOSE ONE PROFILE
		}
	}
	
	private void loadProfile(String profileId) {
		String profileName = persister.get("profile_"+profileId, "name");
		int profileLevel = persister.getInt("profile_"+profileId, "level");
		
		profile = new Profile(profileId, profileName, profileLevel);
		changeLevel(profileLevel);
	}
	
	private void closeProfile() {
		profile = null;
		changeLevel(1);
	}
	
	private class Profile {
		private String id;
		private String name;
		private int level;
		
		public Profile(String id, String name, int level) {
			this.id = id;
			this.name = name;
			this.level = level;
		}
		
		public int getLevel() {
			return level;
		}
		
		public String getDisplay() {
			return name+" "+level;
		}
		
		public void accessLevel(int newLevel) {
			if(newLevel>level) level = newLevel;
			persister.put("profile_"+id, "level", ""+level);
		}
	}
}
