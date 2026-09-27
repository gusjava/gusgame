package gus.game5.main.game.p1.c.solitaire8;

import static gus.game5.core.util.UtilGui.action;

import java.awt.Color;
import java.awt.Font;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArrayInt;

public class GameSolitaire8 extends Game1 {
	
	public static final String TITLE = "The 8 enemy pawns";
	
	public static final double CELL_SIZE = 80;
	public static final double CELL_RADIUS  = CELL_SIZE*0.5;
	public static final int X = 5;
	
	
	public static void main(String[] args) {
		GameSolitaire8 main = new GameSolitaire8();
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
			action("About (F3)", this::displayAbout)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(600);
		s.setHeight(500);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Calibri", Font.PLAIN, 12));
	}
	
	/*
	 * ABOUT
	 */
	
	private void displayAbout() {
		paneAbout.display();
	}
	
	/*
	 * DATA
	 */

	private JTextPaneAbout paneAbout;
	private ShapeBoard<Cell> board;
	private DrawingText completeDisplay;
	private Cell dragged;
	private int[][] data;
	
	protected void initialize1() {
		paneAbout = new JTextPaneAbout();
		addDraw(new BackgroundDraw());
		
		board = newShapeBoard(CELL_SIZE, X, Cell::new);
		data = UtilArrayInt.clone2(UtilSolitaire8.STATE1);
		addDraw(new Drag());
		
		completeDisplay = newDrawingTextC(p1(240, 30), "Game Complete");
		completeDisplay.setDrawable(this::isGameWon);
		completeDisplay.setFontBold(25);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();	
		if(mouse().button1().justPressed()) {
			Cell pressedCell = board.cellAt(mouse().pointCurrent());
			if(pressedCell!=null && !pressedCell.isOutside()) {
				dragged = pressedCell;
			}
		}
		else if(mouse().button1().justReleased()) {
			Cell releasedCell = board.cellAt(mouse().pointCurrent());
			if(releasedCell!=null) {
				handleMove(releasedCell);
			}
			dragged = null;
		}
	}
	
	/*
	 * GAME WON
	 */
	
	private boolean isGameWon() {
		return UtilArrayInt.eq2(data, UtilSolitaire8.STATE2);
	}
	
	/*
	 * CELL
	 */
	
	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
		}
		
		public int getValue() {
			return data[i][j];
		}
		public boolean isOutside() {
			return getValue()==-1;
		}
		public boolean isEmpty() {
			return getValue()==0;
		}
		public boolean isRed() {
			return getValue()==1;
		}
		public boolean isBlack() {
			return getValue()==2;
		}
		public boolean isDragged() {
			return this==dragged;
		}
		
		protected void drawShape() {
			if(!isOutside()) 
				fillRoundC(Color.WHITE, CELL_RADIUS-2);

			if(!isDragged()) {
				if(isRed()) 
					fillRoundC(Color.RED, CELL_RADIUS-10);
				else if(isBlack()) 
					fillRoundC(Color.BLACK, CELL_RADIUS-10);
			}
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
	 * BACKGROUND DRAW
	 */
	
	private class BackgroundDraw extends Drawing1 {
		protected void draw() {
			fillSquareC(Color.LIGHT_GRAY, gameCenter(), CELL_SIZE*X+20);
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
				int i = dragged.getI();
				int j = dragged.getJ();
				int value = data[i][j];
				
				if(value==1) 
					fillRoundC(Color.RED, CELL_RADIUS-10);
				else if(value==2) 
					fillRoundC(Color.BLACK, CELL_RADIUS-10);
			}
		}
	}
	
	/*
	 * HANDLE MOVE
	 */
	
	private void handleMove(Cell target) {
		if(dragged==null) return;
		if(!target.isEmpty()) return;
		if(dragged.isEmpty()) return;
		
		int[] d = UtilArrayInt.sub1(target.getIJ(), dragged.getIJ());
		if(d[0]!=0 && d[1]!=0) return;
		
		if(dragged.isRed()) {
			if(d[0]==0) {
				if(d[1]!=-1 && d[1]!=-2) return;
			}
			else if(d[1]==0) {
				if(d[0]!=1 && d[0]!=2) return;
			}
		}
		
		if(dragged.isBlack()) {
			if(d[0]==0) {
				if(d[1]!=1 && d[1]!=2) return;
			}
			else if(d[1]==0) {
				if(d[0]!=-1 && d[0]!=-2) return;
			}
		}
		
		UtilArrayInt.swap2(data, target.getIJ(), dragged.getIJ());
		
	}
}
