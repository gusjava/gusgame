package gus.game5.main.game.p2.board.chess2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

import static gus.game5.core.util.UtilGui.*;
import static gus.game5.main.game.p2.board.chess2.UtilChess.BKI;
import static gus.game5.main.game.p2.board.chess2.UtilChess.BLACK;
import static gus.game5.main.game.p2.board.chess2.UtilChess.INIT_STATE;
import static gus.game5.main.game.p2.board.chess2.UtilChess.WHITE;
import static gus.game5.main.game.p2.board.chess2.UtilChess.WKI;

public class GameChess2 extends Play1 {

	public static final String TITLE = "Chess";
	public static final int CELL_SIZE = 50;
	public static final int IMG_SIZE = CELL_SIZE-8;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static final Color DARK = new Color(153,204,255);
	public static final Color LIGHT = new Color(255,255,240);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameChess2 main = new GameChess2();
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
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (black)",
				radioMenuItem("Human", ()->changeMode2(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode2(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode2(Mode.MINMAX))
			)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setIcon(resourceAt("app.gif"));
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

	private Engine engine;
	private ImageLoader1 imgLoader;
	private ShapeBoard<Cell> board;
	private Cell dragged;
	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize2() {
		addPlayer(buildPlayer(mode1));
		addPlayer(buildPlayer(mode2));
		
		engine = new Engine();
		imgLoader = new ImageLoader1(this);
		board = newShapeBoard(CELL_SIZE, 8, (i,j)->new Cell(i, j, INIT_STATE[i][j]));
		dragged = null;
		
		updateLabelInfo1();
		updateLabelInfo2();
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
		
		addDraw(new Drag());
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
		updateBoardData();
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
//		, MINMAX
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
		
		private Color buildColor() {
			if(value==WKI) {
				if(engine.whiteChecked()) return Color.ORANGE;
				if(engine.whiteMate()) return Color.RED;
			}
			else if(value==BKI) {
				if(engine.blackChecked()) return  Color.ORANGE;
				if(engine.blackMate()) return  Color.RED;
			}
			return (i+j)%2==0 ? LIGHT : DARK;
		}
		
		protected void drawShape() {
			fillSquareC(buildColor(), CELL_SIZE);
			BufferedImage img = getImage();
			if(img!=null) {
				Composite composite = g2_getComposite();
				if(this==dragged) g2_setComposite(ALPHA);
				paintRenderedImageC(IMG_SIZE, img);
				if(this==dragged) g2_setComposite(composite);
			}
		}
		
		public BufferedImage getImage() {
			return imgLoader.valueToImg(value);
		}
		public boolean isPlayer(int player) {
			if(value>0) return player==WHITE;
			if(value<0) return player==BLACK;
			return false;
		}
		
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
	}
	
	/*
	 * GAME DATA
	 */
	
	public int[][] boardData() {
		return board.asInt2(Cell::getValue);
	}
	
	public Cell getPressedCell() {
		if(!mouse().button1().justPressed()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public Cell getReleasedCell() {
		if(!mouse().button1().justReleased()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public Cell getDragged() {
		return dragged;
	}
	
	/*
	 * GAME ACTIONS
	 */
	
	public void setValueAt(int i, int j, int value) {
		board.cellAt(i, j).setValue(value);
	}

	public void setValueAt(int[] p, int value) {
		setValueAt(p[0], p[1], value);
	}
	
	public void setDragged(Cell dragged) {
		this.dragged = dragged;
	}
	
	public boolean attemptToPlay(int player, int[] start, int[] end) {
		return engine.attemptToPlay(player, start, end);
	}
	
	public void updateBoardData() {
		board.updateCells(engine.getData(), Cell::setValue);
	}
	
	/*
	 * GAME OVER
	 */
	
	private void handleGameOver() throws Exception {
		int winner = engine.getWinner();
		if(winner!=-1) System.out.println("winner: "+winner);
		if(winner!=-1) setGameOver(playerForValue(winner));
	}
	
	private String getGameOverDisplay() {
		Player1 winner = getGameOver().getWinner();
		return winner!=null ? "Chess Mate. "+winner.getDisplay()+" won the game" : "Draw";
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
		case HUMAN: return new PlayerChessHuman(this);
		case RANDOM: return new PlayerChessRandom(this);
//		case MINMAX: return new PlayerComputerMinmax(this);
		}
		return null;
	}
	
	/*
	 * LABEL INFO
	 */
	
	private void updateLabelInfo1() {
		if(isGameOver()) {
			labelInfo1.setText(" "+getGameOverDisplay());
		}
		else {
			String info = " "+currentPlayer().getDisplay()+" is playing";
			if(engine.isPlayerChecked()) info += " (check)";
			labelInfo1.setText(info);
		}
	}
	
	private void updateLabelInfo2() {
		if(playerNumber()>0) {
			String type1 = firstPlayer().getType();
			String type2 = secondPlayer().getType();
			labelInfo2.setText("White: "+type1+" , Black: "+type2);
		}
		else labelInfo2.setText(" ");
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
				paintRenderedImageC(IMG_SIZE, dragged.getImage());
			}
		}
	}
}
