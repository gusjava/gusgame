package gus.game5.main.game.p1.c.madvirus;

import static gus.game5.core.util.UtilGui.action;

import java.awt.Color;
import java.awt.Font;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArrayBoolean;
import gus.game5.core.util.UtilArrayInt;

public class GameMadVirus extends Game1 {
	
	public static final String TITLE = "Mad virus";

	public static final int GAME_HEIGHT = 700;
	public static final int GAME_WIDTH = 900;
	
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	
	
	public static void main(String[] args) {
		GameMadVirus main = new GameMadVirus();
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
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(FONT);
	}
	
	/*
	 * DATA
	 */
	
	private DrawingText infoDisplay;
	private DrawingText gameOverDisplay;

	private int[][] data;
	private boolean[][] infected;

	private int[][] data0;
	private boolean[][] infected0;
	
	private ShapeBoard<Cell> board;
	private int cellSize;
	private int turnLeft;
	private int level;
	private boolean gameOver;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		level = 1;
		gameOver = false;
		initLevel();
	}
	
	/*
	 * INIT LEVEL
	 */
	
	private void initLevel() {
		clearAll();
		
		cellSize = UtilMadVirus.CELL_SIZES[level-1];
		turnLeft = UtilMadVirus.TURN_LEFT[level-1];
		data = UtilMadVirus.generateForLevel(level);
		
		infected = UtilArrayBoolean.boolArray2(data.length, data[0].length, false);
		int[] startPos = UtilMadVirus.findRandomStart(data);
		infected[startPos[0]][startPos[1]] = true;
		
		board = newShapeBoard(cellSize, data, Cell::new);
		
		infoDisplay = newDrawingText(Color.BLACK, p1(20, 50), ()->"Level: "+level+"  Turn left: "+turnLeft);
		infoDisplay.setFont(FONT);
		infoDisplay.setFontBold(25);
		
		gameOverDisplay = newDrawingTextC(Color.BLACK, gameCenter(), "GAME OVER");
		gameOverDisplay.setFont(FONT);
		gameOverDisplay.setFontBold(40);
		gameOverDisplay.setDrawable(()->gameOver);
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		
		if(gameOver) return;
		
		if(k.in().F3()) {
			data = data0;
			infected = infected0;
		}

		if(mouse().button1().justPressed()) {
			Cell selected = board.cellAt(mouse().pointCurrent());
			if(selected!=null) {
				data0 = UtilArrayInt.clone(data);
				infected0 = UtilArrayBoolean.clone(infected);
				
				int value = selected.getValue();
				boolean done = UtilMadVirus.attempToInfect(data, infected, value);
				if(done) {
					turnLeft--;
					
					if(UtilMadVirus.isAllInfected(data, infected)) gameWon();
					else if(turnLeft==0) gameLost();
				}
			}
		}
	}
	
	private void gameWon() {
		if(level==UtilMadVirus.TURN_LEFT.length) return;
		
		level++;
		initLevel();
	}
	
	private void gameLost() {
		gameOver = true;
	}
	

	public class Cell extends ShapeCell {

		public Cell(int i, int j) {
			super(i, j);
		}
		
		public int getValue() {
			return data[i][j];
		}
		
		public boolean isInfected() {
			return infected[i][j];
		}
		
		protected void drawShape() {
			Color color = UtilMadVirus.COLORS[getValue()];
			
			double c = cellSize/Math.sqrt(3);
			
			Point0 p1 = p(c, 0);
			Point0 p2 = p1.pRotate(Angle.ANGLE60);
			Point0 p3 = p2.pRotate(Angle.ANGLE60);
			Point0 p4 = p(-c, 0);
			Point0 p5 = p4.pRotate(Angle.ANGLE60);
			Point0 p6 = p5.pRotate(Angle.ANGLE60);

			fillPoly(color, p1,p2,p3,p4,p5,p6);
			drawPoly(Color.DARK_GRAY, p1,p2,p3,p4,p5,p6);
			
			if(isInfected()) {
				fillOvalC(Color.WHITE, p(-6,8), 10, 15);
				fillOvalC(Color.WHITE, p(6,8), 10, 15);
				drawOvalC(Color.BLACK, p(-6,8), 10, 15);
				drawOvalC(Color.BLACK, p(6,8), 10, 15);
				fillRoundC(Color.BLACK, p(-5,8), 3);
				fillRoundC(Color.BLACK, p(8,8), 3);
			}
		}
		
		protected void initAnchor() {
			double h = cellSize*x;
			double w = cellSize*y*0.9;
			
			double px = (gameWidth()-w)*0.5 + cellSize*(j+0.5)*0.9;
			double py = (gameHeight()-h)*0.5 + cellSize*(i+0.5*(j%2));
			setAnchor(new Point2(px, py));
		}
	}
}
