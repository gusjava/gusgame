package gus.game5.main.game.board2.tictactoe2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameTicTacToe2 extends Game1 {

	public static final String TITLE = "Tic-tac-toe";
	public static final int CELL_SIZE = 100;
	public static final int BOARD_SIZE = CELL_SIZE*3;
	
	public static void main(String[] args) {
		GameTicTacToe2 main = new GameTicTacToe2();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */
	
	private JLabel labelInfo;
	
	protected Container buildContentPane() {
		labelInfo = new JLabel(" ");
		return panelCN(panel(), labelInfo);
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
		menuBar.add("Mode", 
			radioMenuItem("Play against human", this::changeModeHuman),
			radioMenuItem("Play against computer", this::changeModeComputer)
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

	private Side winner;
	private Side player;
	private ShapeBoard<Cell> board;
	
	private Mode mode = Mode.HUMAN;
	private long lastPlayCount;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		winner = null;
		player = Side.CIRCLE;
		lastPlayCount = 0;
		
		board = newShapeBoard(CELL_SIZE, 3, this::buildCell);
		updateLabelInfo();
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
	}
	
	private Cell buildCell(int i, int j) {
		return new Cell(i, j, Side.EMPTY);
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(isGameOver()) return;
		
		boolean played = handlePlay();
		if(played) {
			lastPlayCount = getCount();
			player = player.opposite();
			winner = searchWinner();
			updateLabelInfo();
		}
	}
	
	/*
	 * HANDLE PLAY
	 */
	
	private boolean handlePlay() {
		if(isComputerTurn()) 
			return handleComputerPlay(Side.CROSS);
		return handleHumanPlay();
	}
	
	private boolean handleHumanPlay() {
		if(!mouse().button1().justPressed()) return false;
		Cell c = board.cellAt(mouse().pointCurrent());
		if(c==null || !c.getSide().isEmpty()) return false;
		c.setSide(player);
		return true;
	}
	
	private boolean handleComputerPlay(Side computerSide) {
		if(getCount()<lastPlayCount + 50) return false;
		int[] play = UtilTTT2.randomPlay(boardData());
		board.cellAt(play[0], play[1]).setSide(computerSide);
		return true;
	}
	
	private boolean isComputerTurn() {
		return mode==Mode.COMPUTER && player.isCross();
	}
	
	private boolean isGameOver() {
		return winner!=null;
	}
	
	private void updateLabelInfo() {
		if(winner!=null) labelInfo.setText(" "+winner.getWinDescription());
		else labelInfo.setText(" "+player.getLabel()+" is playing");
	}
	
	private Side searchWinner() {
		return intToSide(UtilTTT2.searchWinner(boardData()));
	}
	
	/*
	 * SIDE
	 */
	
	private enum Side {
		CIRCLE, CROSS, EMPTY;
		
		public boolean isCircle() {return this==CIRCLE;}
		public boolean isCross() {return this==CROSS;}
		public boolean isEmpty() {return this==EMPTY;}
		
		public Side opposite() {
			if(isCircle()) return CROSS;
			if(isCross()) return CIRCLE;
			return EMPTY;
		}
		
		public String getLabel() {
			if(isCircle()) return "Circle";
			if(isCross()) return "Cross";
			return null;
		}
		
		public String getWinDescription() {
			if(isEmpty()) return "Draw";
			return getLabel()+" won the game";
		}
	}
	
	/*
	 * MODE
	 */
	
	private enum Mode {
		HUMAN, COMPUTER
	}
	
	private void changeModeHuman() {
		mode = Mode.HUMAN;
	}
	
	private void changeModeComputer() {
		mode = Mode.COMPUTER;
	}
	
	/*
	 * CELL
	 */
	
	private class Cell extends ShapeCell {
		private Side side;

		public Cell(int i, int j, Side side) {
			super(i, j);
			this.side = side;
		}
		
		protected void drawShape() {
			int r = CELL_SIZE/2;
			
			drawSquareC(Color.GRAY, CELL_SIZE);
			if(side.isCircle()) {
				fillRoundC(r-5);
				fillRoundC(Color.WHITE, r-15);
			}
			else if(side.isCross()) {
				drawThickLine(p(-r+10,-r+10), p(r-10,r-10), 10);
				drawThickLine(p(-r+10,r-10), p(r-10,-r+10), 10);
			}
		}
		
		public Side getSide() {
			return side;
		}
		public void setSide(Side side) {
			this.side = side;
		}
	}
	
	/*
	 * SIDE
	 */
	
	private int sideToInt(Side side) {
		if(side==null) return -1;
		return side.isCircle() ? 1 : side.isCross() ? 2 : 0;
	}
	
	private Side intToSide(int value) {
		if(value==-1) return null;
		return value==1 ? Side.CIRCLE : value==2 ? Side.CROSS : Side.EMPTY;
	}
	
	/*
	 * BOARD DATA
	 */
	
	private int[][] boardData() {
		return board.asInt2(c->sideToInt(c.getSide()));
	}
}
