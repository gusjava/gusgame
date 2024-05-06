package gus.game5.main.game.chess;

import static gus.game5.main.game.chess.UtilChess.*;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.JMenuBar1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.image.ImageLoader;

public class GameChess extends Game1 {

	public static final String BASE_IMG = "/gus/game5/main/game/chess/icon/";
	public static final String TITLE = "Chess";
	public static final int CELL_SIZE = 50;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static final Color DARK = new Color(153,204,255);
	public static final Color LIGHT = new Color(255,255,240);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	
	private BufferedImage valueToImg(int value) {
		switch(value) {
			case WP:return imgLoader.get("CHESS1_pawn1.gif");
			case WR:return imgLoader.get("CHESS1_rook1.gif");
			case WB:return imgLoader.get("CHESS1_bishop1.gif");
			case WK:return imgLoader.get("CHESS1_knight1.gif");
			case WQ:return imgLoader.get("CHESS1_queen1.gif");
			case WKI:return imgLoader.get("CHESS1_king1.gif");
			
			case BP:return imgLoader.get("CHESS1_pawn2.gif");
			case BR:return imgLoader.get("CHESS1_rook2.gif");
			case BB:return imgLoader.get("CHESS1_bishop2.gif");
			case BK:return imgLoader.get("CHESS1_knight2.gif");
			case BQ:return imgLoader.get("CHESS1_queen2.gif");
			case BKI:return imgLoader.get("CHESS1_king2.gif");
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
	private JLabel labelInfo;
	private GameOver gameOver;
	private Player player;
	private ShapeBoard<Cell> board;
	private Cell dragged;
	
	private List<Snapshot> snapshots;
	private boolean[][] moved;
	private boolean whiteKingChecked;
	private boolean blackKingChecked;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		imgLoader = new ImageLoader(BASE_IMG);
		changePlayer(Player.WHITE);
		gameOver = null;
		
		board = newShapeBoard(CELL_SIZE, 8, this::buildCell);
		snapshots = UtilList.asList(new Snapshot(INIT_STATE));
		moved = UtilArray.boolArray2(8, false);
		whiteKingChecked = false;
		blackKingChecked = false;
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
		
		addDraw(new Drag());
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

		if(mouse().button1().justPressed()) {
			handlePressed();
		}
		else if(mouse().button1().justReleased()) {
			handleReleased();
		}
	}
	
	private void handlePressed() {
		Cell pressedCell = board.cellAt(mouse().pointCurrent());
		if(pressedCell!=null && pressedCell.isPlayer(player)) {
			dragged = pressedCell;
		}
	}
	
	private void handleReleased() {
		if(dragged==null) return;
		
		boolean played = attemptToPlay();
		dragged = null;
		if(!played) return;
		
		changePlayer(player.opposite());
//		if(!isBoardPlayable()) finishGame();
	}
	
	/*
	 * PLAY
	 */
	
	private boolean attemptToPlay() {
		Cell startCell = dragged;
		Cell endCell = board.cellAt(mouse().pointCurrent());
		if(endCell==null) return false;
		
		final int[] start = startCell.getIJ();
		final int[] end = endCell.getIJ();
		final int[][] state = snapshotDataAt(0);
		final int[][] state0 = snapshotDataAt(1);
		
		Move move = computeMove(state, state0, start, end);
		if(move==null) return false;
		
		boolean done = performMove(move, startCell, endCell);
		if(!done) return false;

		final int[][] state1 = board.asInt(Cell::getValue);
		if(player.isWhite()) {
			if(whiteKingIsChecked(state1)) {
				restoreBoard(state);
				return false;
			}
			whiteKingChecked = false;
			blackKingChecked = blackKingIsChecked(state1);
		}
		else {
			if(blackKingIsChecked(state1)) {
				restoreBoard(state);
				return false;
			}
			blackKingChecked = false;
			whiteKingChecked = whiteKingIsChecked(state1);
		}

		checkMoved(state, state1);
		snapshots.add(new Snapshot(state1));
		return true;
	}
	
	private boolean performMove(Move move, Cell startCell, Cell endCell) {
		switch(move) {
		case EAT:
			endCell.setValue(startCell.getValue());
			startCell.setValue(0);
			return true;
			
		case EN_PASSANT:
			endCell.setValue(startCell.getValue());
			startCell.setValue(0);
			board.cellAt(endCell.getJ(),startCell.getI()).setValue(0);
			return true;
			
		case CASTLING:
			if(endCell.isIJ(7,6)) {
				if(moved[7][7] || moved[7][4]) return false;
				board.cellAt(7,7).setValue(0);
				board.cellAt(7,5).setValue(WR);
				endCell.setValue(startCell.getValue());
				startCell.setValue(0);
				return true;
			}
			if(endCell.isIJ(7,2)) {
				if(moved[7][0] || moved[7][4]) return false;
				board.cellAt(7,0).setValue(0);
				board.cellAt(7,3).setValue(WR);
				endCell.setValue(startCell.getValue());
				startCell.setValue(0);
				return true;
			}
			if(endCell.isIJ(0,6)) {
				if(moved[0][7] || moved[0][4]) return false;
				board.cellAt(0,7).setValue(0);
				board.cellAt(0,5).setValue(BR);
				endCell.setValue(startCell.getValue());
				startCell.setValue(0);
				return true;
			}
			if(endCell.isIJ(0,2)) {
				if(moved[0][0] || moved[0][4]) return false;
				board.cellAt(0,0).setValue(0);
				board.cellAt(0,3).setValue(BR);
				endCell.setValue(startCell.getValue());
				startCell.setValue(0);
				return true;
			}
			return false;
		case PROMOTION:
			endCell.setValue(player.isWhite() ? WQ : BQ);
			startCell.setValue(0);
			return true;
		}
		return false;
	}
	
	private void restoreBoard(int[][] state) {
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			board.cellAt(i, j).setValue(state[i][j]);
		}
	}
	
	private void checkMoved(int[][] state0, int[][] state) {
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			if(state[i][j]!=state0[i][j]) moved[i][j] = true;
		}
	}
	
	/*
	 * PLAYER
	 */
	
	private void changePlayer(Player player) {
		this.player = player;
		StringBuffer b = new StringBuffer(" "+player.getLabel()+" is playing");
		if(isPlayerChecked()) b.append(" (check)");
		labelInfo.setText(b.toString());
	}
	
	/*
	 * GAME OVER
	 */
	
	private boolean isPlayerChecked() {
		return player.isWhite() ? whiteKingChecked : blackKingChecked;
	}
	
	private boolean isGameOver() {
		return gameOver!=null;
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
		
		private Color buildColor() {
			if(value==WKI && whiteKingChecked) return Color.ORANGE;
			if(value==BKI && blackKingChecked) return  Color.ORANGE;
			return (i+j)%2==0 ? LIGHT : DARK;
		}
		
		protected void drawShape() {
			fillSquareC(buildColor(), CELL_SIZE);
			BufferedImage img = getImage();
			if(img!=null) {
				Composite composite = g2_getComposite();
				if(this==dragged) g2_setComposite(ALPHA);
				paintRenderedImageC(CELL_SIZE-8, CELL_SIZE-8, img);
				if(this==dragged) g2_setComposite(composite);
			}
		}
		
		public BufferedImage getImage() {
			return valueToImg(value);
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public boolean isPlayer(Player player) {
			if(value>0) return player.isWhite();
			if(value<0) return player.isBlack();
			return false;
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
				paintRenderedImageC(CELL_SIZE-8, CELL_SIZE-8, dragged.getImage());
			}
		}
	}
	
	/*
	 * SNAPSHOT
	 */
	
	private int[][] snapshotDataAt(int p) {
		int size = snapshots.size();
		return p<size ? snapshots.get(size-p-1).getData() : null;
	}
}
