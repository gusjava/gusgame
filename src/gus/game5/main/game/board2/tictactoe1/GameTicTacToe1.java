package gus.game5.main.game.board2.tictactoe1;

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

import static gus.game5.core.util.UtilGui.*;

public class GameTicTacToe1 extends Game1 {

	public static final String TITLE = "Tic-tac-toe";
	public static final int CELL_SIZE = 100;
	public static final int BOARD_SIZE = CELL_SIZE*3;
	
	public static void main(String[] args) {
		GameTicTacToe1 main = new GameTicTacToe1();
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
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		winner = null;
		player = Side.CIRCLE;
		
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
		
		if(mouse().button1().justPressed())
			handlePressed();
	}
	
	private void handlePressed() {
		boolean played = attemptToPlay();
		if(!played) return;
		
		player = player.opposite();
		winner = searchWinner();
		updateLabelInfo();
	}
	
	private boolean attemptToPlay() {
		Cell c = board.cellAt(mouse().pointCurrent());
		if(c==null || !c.getSide().isEmpty()) return false;
		c.setSide(player);
		return true;
	}
	
	private boolean isGameOver() {
		return winner!=null;
	}
	
	private void updateLabelInfo() {
		if(winner!=null) labelInfo.setText(" "+winner.getWinDescription());
		else labelInfo.setText(" "+player.getLabel()+" is playing");
	}
	
	private Side searchWinner() {
		Side side11 = sideAt(1, 1);
		if(!side11.isEmpty()) {
			if(sideAt(0, 0)==side11 && sideAt(2, 2)==side11) return side11;
			if(sideAt(0, 1)==side11 && sideAt(2, 1)==side11) return side11;
			if(sideAt(0, 2)==side11 && sideAt(2, 0)==side11) return side11;
			if(sideAt(1, 0)==side11 && sideAt(1, 2)==side11) return side11;
		}
		Side side00 = sideAt(0, 0);
		if(!side00.isEmpty()) {
			if(sideAt(0, 1)==side00 && sideAt(0, 2)==side00) return side00;
			if(sideAt(1, 0)==side00 && sideAt(2, 0)==side00) return side00;
		}
		Side side22 = sideAt(2, 2);
		if(!side22.isEmpty()) {
			if(sideAt(2, 0)==side22 && sideAt(2, 1)==side22) return side22;
			if(sideAt(0, 2)==side22 && sideAt(1, 2)==side22) return side22;
		}
		if(board.none(c->c.getSide().isEmpty())) return Side.EMPTY;
		return null;
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
	 * SIDE AT
	 */
	
	private Side sideAt(int i, int j) {
		return board.cellAt(i, j).getSide();
	}
}
