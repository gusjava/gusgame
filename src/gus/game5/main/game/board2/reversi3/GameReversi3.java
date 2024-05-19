package gus.game5.main.game.board2.reversi3;

import static gus.game5.main.game.board2.reversi3.UtilReversi3.BLACK;
import static gus.game5.main.game.board2.reversi3.UtilReversi3.INIT_STATE;
import static gus.game5.main.game.board2.reversi3.UtilReversi3.WHITE;
import static gus.game5.main.game.board2.reversi3.UtilReversi3.searchWinner;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;

import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameReversi3 extends Play1 {
	
	public static final String TITLE = "Reversi";
	public static final int CELL_SIZE = 50;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static void main(String[] args) {
		GameReversi3 main = new GameReversi3();
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
			menu("Player 1 (white)",
				radioMenuItem("Human", ()->changeMode1(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM)),
				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (black)",
				radioMenuItem("Human", ()->changeMode2(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode2(Mode.RANDOM)),
				radioMenuItem("Computer (Min-Max)", ()->changeMode2(Mode.MINMAX))
			)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(BOARD_SIZE);
		s.setHeight(BOARD_SIZE);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */

	private ShapeBoard<Cell> board;
	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize2() {
		addPlayer(buildPlayer(mode1));
		addPlayer(buildPlayer(mode2));
		
		board = newShapeBoard(CELL_SIZE, 8, (i,j)->new Cell(i, j, INIT_STATE[i][j]));
		
		updateLabelInfo1();
		updateLabelInfo2();
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
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
		HUMAN, RANDOM, MINMAX
	}
	
	private void changeMode1(Mode mode) {
		mode1 = mode;
	}
	
	private void changeMode2(Mode mode) {
		mode2 = mode;
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
		
		protected void drawShape() {
			int r = CELL_SIZE/2;
			drawSquareC(Color.GRAY, CELL_SIZE);
			if(value==WHITE) drawRoundC(r-5);
			else if(value==BLACK) fillRoundC(r-5);
		}
		
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
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

	public void setValues(List<int[]> play, int value) {
		for(int[] p : play) setValueAt(p[0], p[1], value);
	}
	
	/*
	 * GAME OVER
	 */
	
	private void handleGameOver() throws Exception {
		int value = searchWinner(oppositePlayerValue(), boardData());
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
		if(value==WHITE) return firstPlayer();
		if(value==BLACK) return secondPlayer();
		return null;
	}
	
	private Player1 buildPlayer(Mode mode) {
		switch(mode) {
		case HUMAN: return new PlayerHuman(this);
		case RANDOM: return new PlayerComputerRandom(this);
		case MINMAX: return new PlayerComputerMinmax(this);
		}
		return null;
	}
	
	private int oppositePlayerValue() {
		return playIndex()==1 ? WHITE : BLACK;
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
			labelInfo2.setText("White: "+type1+" , Black: "+type2);
		}
		else labelInfo2.setText(" ");
	}
}
