package gus.game5.main.game.chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gus.game5.core.drawing.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.JMenuBar1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.image.ImageLoader;

public class GameChess extends Game1 {
	
	/********************************/
	/* 1:pawn, 2:tower, 3:bishop    */
	/* 4:knight, 5:queen, 6:king    */
	/* -:black +:white              */
	/********************************/

	public static final String BASE_IMG = "/gus/game5/main/game/chess/icon/";
	public static final String TITLE = "Chess";
	public static final int CELL_SIZE = 50;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static final Color DARK = new Color(153,204,255);
	public static final Color LIGHT = new Color(255,255,240);
	
	public static int[][] INIT_STATE = {
			{-2,-3,-4,-5,-6,-4,-3,-2},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1},
			{2,3,4,5,6,4,3,2}
	};
	
	private BufferedImage valueToImg(int value) {
		switch(value) {
			case 1:return imgLoader.get("CHESS1_pawn1.gif");
			case 2:return imgLoader.get("CHESS1_rook1.gif");
			case 3:return imgLoader.get("CHESS1_bishop1.gif");
			case 4:return imgLoader.get("CHESS1_knight1.gif");
			case 5:return imgLoader.get("CHESS1_queen1.gif");
			case 6:return imgLoader.get("CHESS1_king1.gif");
			
			case -1:return imgLoader.get("CHESS1_pawn2.gif");
			case -2:return imgLoader.get("CHESS1_rook2.gif");
			case -3:return imgLoader.get("CHESS1_bishop2.gif");
			case -4:return imgLoader.get("CHESS1_knight2.gif");
			case -5:return imgLoader.get("CHESS1_queen2.gif");
			case -6:return imgLoader.get("CHESS1_king2.gif");
		}
		return null;
	}
	
	public static void main(String[] args) {
		GameChess main = new GameChess();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */
	
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
		s.setIcon(BASE_IMG+"app.gif");
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

	private ImageLoader imgLoader;
	private GameOver gameOver;
	private Side player;
	private ShapeBoard<Cell> board;
	private JLabel labelInfo;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		imgLoader = new ImageLoader(BASE_IMG);
		changePlayer(Side.WHITE);
		gameOver = null;
		
		board = newShapeBoard(CELL_SIZE, 8, this::buildCell);
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
	}
	
	private Cell buildCell(int i, int j) {
		return new Cell(i, j, INIT_STATE[i][j]);
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();	
	}
	
	private void changePlayer(Side player) {
		this.player = player;
		labelInfo.setText(" "+player.getLabel()+" is playing");
	}
	
	private boolean isGameOver() {
		return gameOver!=null;
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
		private int value;

		public Cell(int i, int j, int value) {
			super(i, j);
			this.value = value;
		}
		
		protected void drawShape() {
			Color color = (i+j)%2==0 ? LIGHT : DARK;
			fillSquareC(color, CELL_SIZE);
			
			BufferedImage img = valueToImg(value);
			if(img!=null) paintRenderedImageC(CELL_SIZE-8, CELL_SIZE-8, img);
		}
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
	}
	
	/*
	 * GAME OVER
	 */
	
	private class GameOver {
		private Side winner;
		
		public GameOver(Side winner) {
			this.winner = winner;
		}
		
		public String getDescription() {
			if(winner.isEmpty()) return "Draw";
			return winner.getLabel()+" won the game";
		}
	}
	
}
