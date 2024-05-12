package gus.game5.main.game.tictactoe3;

import static gus.game5.main.game.tictactoe3.UtilTTT3.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gus.game5.core.drawing.DrawingText;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameTicTacToe3 extends Play1 {

	public static final String TITLE = "Tic-tac-toe";
	public static final int CELL_SIZE = 100;
	public static final int BOARD_SIZE = CELL_SIZE*3;
	
	public static void main(String[] args) {
		GameTicTacToe3 main = new GameTicTacToe3();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */
	
	private JLabel labelInfo;
	
	protected Container buildContentPane() {
		labelInfo = new JLabel(" ");
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(labelInfo, BorderLayout.NORTH);
		p.add(panel(), BorderLayout.CENTER);
		return p;
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
			menu("Player 1 (circle)",
				radioMenuItem("Human", ()->mode1 = Mode.HUMAN),
				radioMenuItem("Computer 1", ()->{mode1 = Mode.COMPUTER1;}),
				radioMenuItem("Computer 2", ()->{mode1 = Mode.COMPUTER2;})
			),
			menu("Player 2 (cross)",
				radioMenuItem("Human", ()->mode2 = Mode.HUMAN),
				radioMenuItem("Computer 1", ()->{mode2 = Mode.COMPUTER1;}),
				radioMenuItem("Computer 2", ()->{mode2 = Mode.COMPUTER2;})
			)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(300);
		s.setHeight(300);
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
		
		board = newShapeBoard(CELL_SIZE, 3, (i,j)->new Cell(i, j, EMPTY));
		updateLabelInfo();
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
	}
	
	/*
	 * TURN
	 */

	protected void turn1() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}
	
	protected void played() {
		handleGameOver();
		updateLabelInfo();
	}
	
	private void updateLabelInfo() {
		if(isGameOver()) labelInfo.setText(" "+getGameOverDisplay());
		else labelInfo.setText(" "+currentPlayer().getDisplay()+" is playing");
	}
	
	/*
	 * MODE
	 */
	
	private enum Mode {
		HUMAN, COMPUTER1, COMPUTER2
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
			if(value==CIRCLE) {
				fillRoundC(r-5);
				fillRoundC(Color.WHITE, r-15);
			}
			else if(value==CROSS) {
				drawThickLine(p(-r+10,-r+10), p(r-10,r-10), 10);
				drawThickLine(p(-r+10,r-10), p(r-10,-r+10), 10);
			}
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
		return board.asInt(Cell::getValue);
	}
	
	public Cell getPressedCell() {
		if(!mouse().button1().justPressed()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public void setValue(int i, int j, int value) {
		board.cellAt(i, j).setValue(value);
	}
	
	/*
	 * GAME OVER
	 */
	
	private void handleGameOver() {
		int value = UtilTTT3.searchWinner(boardData());
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
		if(value==CIRCLE) return firstPlayer();
		if(value==CROSS) return secondPlayer();
		return null;
	}
	
	private Player1 buildPlayer(Mode mode) {
		switch(mode) {
		case HUMAN: return new PlayerHuman(this);
		case COMPUTER1: return new PlayerComputer1(this);
		case COMPUTER2: return new PlayerComputer2(this);
		}
		return null;
	}
}
