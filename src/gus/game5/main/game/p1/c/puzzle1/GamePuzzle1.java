package gus.game5.main.game.p1.c.puzzle1;

import static gus.game5.core.util.UtilGui.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.image.ImageLoader;

public class GamePuzzle1 extends Game1 {
	
	public static final String TITLE = "Puzzle";

	public static final int GAME_HEIGHT = 1300;
	public static final int GAME_WIDTH = 700;
	
	public static final int PUZZLE_MAX_WIDTH = 1200;
	public static final int PUZZLE_MAX_HEIGHT = 600;
	
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	
	
	public static void main(String[] args) {
		GamePuzzle1 main = new GamePuzzle1();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_HEIGHT);
		s.setHeight(GAME_WIDTH);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
		s.setFont(FONT);
	}
	
	/*
	 * DATA
	 */
	
	private DynTimer completeTimer;
	
	private ShapeBoard<Cell> board;
	private ImageLoader imageLoader;
	private BufferedImage image;
	private List<BufferedImage> imgList;
	private Cell dragged;

	private int level = 0;
	private int imageW;
	private int imageH;
	private double subW;
	private double subH;
	private int x;
	private int y;
	
	
	protected void initialize1() {
		imageLoader = new ImageLoader(this);
		imgList = new ArrayList<>();
		level = 0;
		startPuzzle();
	}
	
	private void startPuzzle() {
		clearAll();
		loadPuzzleData(level);
		splitImage();
		initBoard();
		shufflePuzzle();

		completeTimer = newDynTimer();
		addDraw(new Drag());
	}
	
	private void loadPuzzleData(int index) {
		String imageName = UtilPuzzle.NAMES[index];
		int[] ratio = UtilPuzzle.RATIOS[index];
		
		image = imageLoader.get(imageName + ".jpg");
		imageW = image.getWidth();
		imageH = image.getHeight();
		
		x = ratio[0];
		y = ratio[1];

		subW = imageW/y;
		subH = imageH/x;
	}
	
	private void splitImage() {
		imgList.clear();
		for(int i=0;i<x;i++) {
			double y0 = i*subH;
			for(int j=0;j<y;j++) {
				double x0 = j*subW;
				BufferedImage img = image.getSubimage((int) x0, (int) y0, (int) subW, (int) subH);
				imgList.add(img);
			}
		}
	}
	
	private void initBoard() {
		double cellMaxWidth = PUZZLE_MAX_WIDTH/y;
		double cellMaxHeight = PUZZLE_MAX_HEIGHT/x;
		double cellMaxRatio = cellMaxWidth/cellMaxHeight;
		
		double subRatio = subW/subH;
		double cellWidth = cellMaxRatio>subRatio ? cellMaxHeight * subRatio : cellMaxWidth;
		double cellHeight = cellMaxRatio>subRatio ? cellMaxHeight : cellMaxWidth/subRatio;
		
		board = newShapeBoard(cellWidth, cellHeight, x, y, Cell::new);
	}
	
	private void shufflePuzzle() {
		List<Integer> n = new ArrayList<>();
		for(int i=0;i<board.getNb();i++) n.add(i);
		Collections.shuffle(n);
		
		for(int i=0;i<board.getNb();i++) {
			board.cellAt(i).setValue(n.get(i));
		}
	}
	
	private boolean puzzleSolved() {
		return board.all(Cell::atRightPosition);
	}
	
	private void nextLevel() {
		level++;
		if(level==UtilPuzzle.NAMES.length) level = 0;
		startPuzzle();
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		
		goNext();
		if(completeTimer.gBool()) return;

		if(mouse().button1().justPressed()) {
			Cell pressed = board.cellAt(mouse().pointCurrent());
			if(pressed!=null) {
				dragged = pressed;
			}
		}
		else if(mouse().button1().justReleased()) {
			Cell released = board.cellAt(mouse().pointCurrent());
			if(released!=null && dragged!=null) {
				int value1 = dragged.getValue();
				int value2 = released.getValue();
				
				dragged.setValue(value2);
				released.setValue(value1);
				dragged = null;
				
				if(puzzleSolved()) {
					completeTimer.start(60, this::nextLevel);
				}
			}
		}
	}
	
	
	public class Cell extends ShapeCell {
		private int value;

		public Cell(int i, int j) {
			super(i, j);
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public boolean atRightPosition() {
			return value==index;
		}
		
		protected void drawShape() {
			if(this!=dragged)
				paintRenderedImageC(getWidth(), getHeight(), imgList.get(value));
			else drawRectC(Color.BLACK, getWidth(), getHeight());
			
//			if(atRightPosition()) drawStringC(fontBold(20), "OK"); 
		}

		//HACK pour remettre le comportement contains comme il faut (à revoir)
		public boolean contains(Point0 p) {
			if(p==null) return false;
			
			if(p.getX() < getP0().getX()) return false;
			if(p.getX() > getP1().getX()) return false;
			
			if(p.getY() < getP0().getY()) return false;
			if(p.getY() > getP1().getY()) return false;
			
			return true;
		}
		
		protected void initAnchor() {
			double h = getHeight();
			double w = getWidth();
			
			double px = (gameWidth()-w*y)*0.5 + w*(j+0.5);
			double py = (gameHeight()-h*x)*0.5 + h*(i+0.5);
			setAnchor(new Point2(px, py));
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
				paintImageC(dragged.getWidth(), dragged.getHeight(), imgList.get(dragged.getValue()));
			}
		}
	}
}
