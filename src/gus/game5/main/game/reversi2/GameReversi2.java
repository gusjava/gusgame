package gus.game5.main.game.reversi2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gus.game5.core.drawing.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameReversi2 extends Game1 {
	
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
		GameReversi2 main = new GameReversi2();
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
	private ShapeBoard<Cell> board;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		gameOver = null;
		changePlayer(Side.WHITE);
		
		board = newShapeBoard(CELL_SIZE, 8, this::buildCell);
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
	}
	
	private Cell buildCell(int i, int j) {
		return new Cell(i, j, sideOf(INIT_STATE[i][j]));
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
		for(Cell c : board.cells()) {
			if(c.getSide().isEmpty() && !scan(c).isEmpty()) return true;
		}
		return false;
	}
	
	private boolean attemptToPlay() {
		Cell c = board.cellAt(mouse().pointCurrent());
		if(c==null || !c.getSide().isEmpty()) return false;
		
		List<Cell> list = scan(c);
		if(list.isEmpty()) return false;
		
		for(Cell cell : list) cell.setSide(player);
		c.setSide(player);
		return true;
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
	
	private class Cell extends ShapeCell {
		private Side side;

		public Cell(int i, int j, Side side) {
			super(i, j);
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
			return board.cellAt(i+di, j+dj);
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
			int scoreWhite = board.count(c->c.getSide().isWhite());
			int scoreBlack = board.count(c->c.getSide().isBlack());
			
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
