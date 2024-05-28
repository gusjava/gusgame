package gus.game5.main.game.board2.hex1;

import static gus.game5.main.game.board2.hex1.UtilHex.BLUE;
import static gus.game5.main.game.board2.hex1.UtilHex.EMPTY;
import static gus.game5.main.game.board2.hex1.UtilHex.RED;
import static gus.game5.main.game.board2.hex1.UtilHex.searchWinner;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

import static gus.game5.core.util.UtilGui.*;

public class GameHex1 extends Play1 {
	
	public static final String TITLE = "Hex";
	public static final int GAME_WIDTH = 650;
	public static final int GAME_HEIGHT = 500;
	
	public static final Color COLOR_RED = Color.RED;
	public static final Color COLOR_BLUE = Color.BLUE;
	
	public static void main(String[] args) {
		GameHex1 main = new GameHex1();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */

	private JLabel labelInfo1;
	private JLabel labelInfo2;
	
	protected Container buildContentPane() {
		labelInfo1 = new JLabel(" ");
		labelInfo2 = new JLabel(" ");
		return panelCNS(panel(), labelInfo1, labelInfo2);
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
		menuBar.add("Players", 
			menu("Player 1 (red)",
				radioMenuItem("Human", ()->changeMode1(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (blue)",
				radioMenuItem("Human", ()->changeMode2(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode2(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode2(Mode.MINMAX))
			)
		);
		menuBar.add("Board size", 
			radioMenuItem("4 x 4", ()->boardSize = 4),
			radioMenuItem("5 x 5", ()->boardSize = 5),
			radioMenuItem("6 x 6", ()->boardSize = 6),
			radioMenuItem("7 x 7", ()->boardSize = 7),
			radioMenuItem("8 x 8", ()->boardSize = 8),
			radioMenuItem("9 x 9", ()->boardSize = 9),
			radioMenuItem("10 x 10", ()->boardSize = 10),
			radioMenuItem("11 x 11", ()->boardSize = 11)
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
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */

	private int boardSize = 4;
	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;
	
	private ShapeBoard<Cell> board;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize2() {
		addPlayer(buildPlayer(mode1));
		addPlayer(buildPlayer(mode2));
		
		double cellSize = 320/boardSize;
		board = newShapeBoard(cellSize, boardSize, (i,j)->new Cell(i, j, EMPTY));
		
		updateLabelInfo1();
		updateLabelInfo2();
		
		DrawingText gameOverText = newDrawingTextC(gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
	}
	
	/*
	 * TURN
	 */

	protected void turnStart() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}
	
	protected void played() throws Exception {
		handleGameOver();
	}

	protected void turnEnd() {
		updateLabelInfo1();
	}
	
	/*
	 * MODE
	 */
	
	private enum Mode {
		HUMAN, RANDOM
	}
	
	private void changeMode1(Mode mode) {
		mode1 = mode;
		updateLabelInfo2();
	}
	
	private void changeMode2(Mode mode) {
		mode2 = mode;
		updateLabelInfo2();
	}
	
	/*
	 * CELL
	 */
	
	public class Cell extends ShapeCell {
		private int value;

		public Cell(int i, int j, int value) {
			super(i, j);
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
		protected void drawShape() {
			
			double r = cellSize*0.5;
			double c = cellSize/Math.sqrt(3);
			
			Point0 p1 = p(c, 0);
			Point0 p2 = p1.pRotate(Angle.ANGLE60);
			Point0 p3 = p2.pRotate(Angle.ANGLE60);
			Point0 p4 = p(-c, 0);
			Point0 p5 = p4.pRotate(Angle.ANGLE60);
			Point0 p6 = p5.pRotate(Angle.ANGLE60);
					
			if(value==RED) fillRoundC(COLOR_RED, r*0.9);
			else if(value==BLUE) fillRoundC(COLOR_BLUE, r*0.9);
			drawPoly(p1,p2,p3,p4,p5,p6);
			
			if(i==0) {
				drawThickLine(COLOR_BLUE, j==0 ? avg(p5, p6) : p5, p6, 3);
				drawThickLine(COLOR_BLUE, p6, p1, 3);
			}
			else if(i==x-1) {
				drawThickLine(COLOR_BLUE, j==x-1 ? avg(p2, p3) : p2, p3, 3);
				drawThickLine(COLOR_BLUE, p3, p4, 3);
			}
			if(j==0) {
				drawThickLine(COLOR_RED, i==0 ? avg(p5, p6) : p6, p5, 3);
				drawThickLine(COLOR_RED, p4, p5, 3);
			}
			else if(j==y-1) {
				drawThickLine(COLOR_RED, i==y-1 ? avg(p2, p3) : p3, p2, 3);
				drawThickLine(COLOR_RED, p1, p2, 3);
			}
		}
		
		protected void initAnchor() {
			int k = i+j;
			double px = gameWidth()*0.5 + cellSize*Math.sqrt(3)*(j - 0.5*k);
			double py = gameHeight()*0.5 + cellSize*0.5*(k - x);
			setAnchor(new Point2(px, py));
		}
	}
	
	/*
	 * BOARD
	 */
	
	public int[][] boardData() {
		return board.asInt2(Cell::getValue);
	}
	
	public Cell getPressedCell() {
		if(!mouse().button1().justPressed()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public void setValueAt(int i, int j, int value) {
		board.cellAt(i, j).setValue(value);
	}

	public void setValueAt(int[] p, int value) {
		setValueAt(p[0], p[1], value);
	}
	
	/*
	 * GAME OVER
	 */
	
	private void handleGameOver() throws Exception {
		int value = searchWinner(boardData());
		if(value!=-1) setGameOver(playerForValue(value));
	}
	
	private String getGameOverDisplay() {
		Player1 winner = getGameOver().getWinner();
		return winner!=null ? winner.getDisplay()+" won the game" : "Draw";
	}
	
	/*
	 * PLAYER
	 */
	
	private Player1 playerForValue(int value) {
		if(value==RED) return firstPlayer();
		if(value==BLUE) return secondPlayer();
		return null;
	}
	
	private Player1 buildPlayer(Mode mode) {
		switch(mode) {
		case HUMAN: return new PlayerHexHuman(this);
		case RANDOM: return new PlayerHexRandom(this);
//		case MINMAX: return new PlayerComputerMinmax(this);
		}
		return null;
	}
	
	/*
	 * LABEL INFO
	 */
	
	private void updateLabelInfo1() {
		if(isGameOver()) labelInfo1.setText(" "+getGameOverDisplay());
		else labelInfo1.setText(" "+currentPlayer().getDisplay()+" is playing");
	}
	
	private void updateLabelInfo2() {
		if(playerNumber()>0) {
			String type1 = firstPlayer().getType();
			String type2 = secondPlayer().getType();
			labelInfo2.setText("Red: "+type1+" , Blue: "+type2);
		}
		else labelInfo2.setText(" ");
	}
}
