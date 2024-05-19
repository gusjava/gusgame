package gus.game5.main.game.board2.reversi1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeSquare;

public class GameReversi1 extends Game1 {
	
	public static final String TITLE = "Reversi";
	public static final int CELL_SIZE = 50;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static int[][] INIT_STATE = {
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,1,2,0,0,0},
		{0,0,0,2,1,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0}
	};
	
	public static Side sideOf(int n) {
		switch(n) {
		case 1: return Side.WHITE;
		case 2: return Side.BLACK;
		default: return Side.EMPTY;
		}
	}
	
	public static void main(String[] args) {
		GameReversi1 main = new GameReversi1();
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
		s.setWidth(BOARD_SIZE);
		s.setHeight(BOARD_SIZE);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */

	private GameOver gameOver;
	private Side player;
	private Cell[][] board;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		gameOver = null;
		changePlayer(Side.WHITE);
		
		board = new Cell[8][8];
		for(int i=0;i<8;i++)
		for(int j=0;j<8;j++) {
			board[i][j] = new Cell(i, j, sideOf(INIT_STATE[i][j]));
			addDraw(board[i][j]);
		}
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
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
			handleClick();
	}
	
	private void handleClick() {
		boolean played = attemptToPlay();
		if(!played) return;
		
		changePlayer(player.opposite());
		if(!isBoardPlayable()) finishGame();
	}
	
	private void changePlayer(Side player) {
		this.player = player;
		labelInfo.setText(" "+player.getLabel()+" is playing");
	}
	
	private void finishGame() {
		gameOver = new GameOver();
		labelInfo.setText(" " + gameOver.getDescription());
	}
	
	private boolean isGameOver() {
		return gameOver!=null;
	}
	
	private boolean isBoardPlayable() {
		for(int i=0;i<8;i++)
		for(int j=0;j<8;j++) {
			Cell c = board[i][j];
			if(c.getSide().isEmpty() && !scan(c).isEmpty()) return true;
		}
		return false;
	}
	
	private boolean attemptToPlay() {
		Cell c = findClickedCell();
		if(c==null || !c.getSide().isEmpty()) return false;
		
		List<Cell> list = scan(c);
		if(list.isEmpty()) return false;
		
		for(Cell cell : list) cell.setSide(player);
		c.setSide(player);
		return true;
	}
	
	private Cell findClickedCell() {
		Point0 pm = mouse().pointCurrent();
		for(int i=0;i<8;i++)
		for(int j=0;j<8;j++) {
			if(board[i][j].contains(pm)) return board[i][j];
		}
		return null;
	}
	
	/*
	 * SCAN
	 */
	
	private List<Cell> scan(Cell c) {
		List<Cell> list = new ArrayList<>();
		list.addAll(scan(c,0,1));
		list.addAll(scan(c,0,-1));
		list.addAll(scan(c,1,0));
		list.addAll(scan(c,1,1));
		list.addAll(scan(c,1,-1));
		list.addAll(scan(c,-1,0));
		list.addAll(scan(c,-1,1));
		list.addAll(scan(c,-1,-1));
		return list;
	}
	
	private List<Cell> scan(Cell c0, int di, int dj) {
		Cell c = c0.neighbor(di, dj);
		List<Cell> list = new ArrayList<>();
		while(c!=null && c.getSide()==player.opposite()) {
			list.add(c);
			c = c.neighbor(di, dj);
		}
		if(c==null || c.getSide()!=player) list.clear();
		return list;
	}
	
	/*
	 * SIDE
	 */
	
	private enum Side {
		WHITE, BLACK, EMPTY;
		
		public boolean isWhite() {return this==WHITE;}
		public boolean isBlack() {return this==BLACK;}
		public boolean isEmpty() {return this==EMPTY;}
		
		public Side opposite() {
			if(isWhite()) return BLACK;
			if(isBlack()) return WHITE;
			return EMPTY;
		}
		
		public String getLabel() {
			if(isWhite()) return "White";
			if(isBlack()) return "Black";
			return null;
		}
	}
	
	/*
	 * CELL
	 */
	
	private class Cell extends ShapeSquare {
		private int i;
		private int j;
		private Side side;

		public Cell(int i, int j, Side side) {
			super(p1(CELL_SIZE*(i+0.5), CELL_SIZE*(j+0.5)), CELL_SIZE);
			this.i = i;
			this.j = j;
			this.side = side;
		}
		
		protected void drawShape() {
			drawSquareC(Color.GRAY, CELL_SIZE);
			if(side.isWhite()) drawRoundC(CELL_SIZE/2-5);
			else if(side.isBlack()) fillRoundC(CELL_SIZE/2-5);
		}
		
		public Side getSide() {
			return side;
		}
		
		public void setSide(Side side) {
			this.side = side;
		}
		
		public Cell neighbor(int di, int dj) {
			int i1 = i+di;
			int j1 = j+dj;
			if(i1<0 || i1>7) return null;
			if(j1<0 || j1>7) return null;
			return board[i1][j1];
		}
	}
	
	/*
	 * GAME OVER
	 */
	
	private class GameOver {
		private Side winner;
		private int scoreWinner;
		private int scoreLoser;
		
		public GameOver() {
			int scoreWhite = 0;
			int scoreBlack = 0;
			for(int i=0;i<8;i++)
			for(int j=0;j<8;j++) {
				switch(board[i][j].getSide()) {
				case WHITE: scoreWhite++;break;
				case BLACK: scoreBlack++;break;
				case EMPTY: break;
				};
			}
			scoreWinner = Math.max(scoreWhite, scoreBlack);
			scoreLoser = Math.min(scoreWhite, scoreBlack);
			winner = scoreWhite > scoreBlack ? Side.WHITE : scoreBlack > scoreWhite ? Side.BLACK : Side.EMPTY;
		}
		
		public String getDescription() {
			if(winner.isEmpty()) return "Draw";
			return winner.getLabel()+" won the game (" + scoreWinner + "/" + scoreLoser + ")";
		}
	}
}
