package gus.game5.main.game.board2.ragus1;

import static gus.game5.main.game.board2.ragus1.UtilRagus.INIT_STATE;
import static gus.game5.main.game.board2.ragus1.UtilRagus.PLAYER_ANUBIS;
import static gus.game5.main.game.board2.ragus1.UtilRagus.PLAYER_DINO;
import static gus.game5.main.game.board2.ragus1.UtilRagus.searchWinner;

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
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

import static gus.game5.core.util.UtilGui.*;

public class GameRagus1 extends Play1 {
	
	public static final String TITLE = "Ragus";
	
	public static final int CELL_SIZE = 50;
	public static final int IMG_SIZE = 30;
	public static final int BOARD_X = 13;
	public static final int BOARD_Y = 8;

	public static final int GAME_HEIGHT = CELL_SIZE * BOARD_X + 15;
	public static final int GAME_WIDTH = CELL_SIZE * BOARD_Y;
	
	public static final Color COLOR_GROUND = new Color(238,238,191);
	public static final Color COLOR_HOME = new Color(207,207,151);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameRagus1 main = new GameRagus1();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */

	private JLabel labelInfo1;
	private JLabel labelInfo2;

	private PlayerScoreBar scoreBar1;
	private PlayerScoreBar scoreBar2;
	
	protected Container buildContentPane() {
		labelInfo1 = new JLabel(" ");
		labelInfo2 = new JLabel(" ");
		
		scoreBar1 = new PlayerScoreBar();
		scoreBar2 = new PlayerScoreBar();
		
		return panelCNS(panel(), 
				raised(panelCN(panelWE(scoreBar1, scoreBar2),labelInfo1)), 
				raised(labelInfo2));
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit),
			action("About (F3)", this::displayAbout)
		);
		menuBar.add("Players", 
			menu("Player 1 (Dino)",
				radioMenuItem("Human", ()->changeMode1(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM)),
				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (Anubis)",
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
		s.setIcon(iconAt("RAGUS_playerA_s.gif"));
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	/*
	 * ABOUT
	 */
	
	private JTextPaneAbout paneAbout;
	
	private void displayAbout() {
		paneAbout.display();
	}
	
	/*
	 * DATA
	 */

	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;
	private ImageLoader1 imgLoader;
	
	private PlayerRagus player1;
	private PlayerRagus player2;
	private ShapeBoard<Cell> board;
	private Cell dragged;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize2() {
		imgLoader = new ImageLoader1(this);
		paneAbout = new JTextPaneAbout();
		
		player1 = buildPlayer(mode1, PLAYER_DINO);
		player2 = buildPlayer(mode2, PLAYER_ANUBIS);
		
		scoreBar1.setPlayer(player1);
		scoreBar2.setPlayer(player2);
		
		addPlayers(player1, player2);
		
		board = newShapeBoard(CELL_SIZE, BOARD_X, BOARD_Y, (i,j)->new Cell(i, j, INIT_STATE[i][j]));
		dragged = null;
		
		updateLabels();
		
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
		if(k.in().F3())	displayAbout();
	}
	
	protected void played() throws Exception {
		handleGameOver();
	}

	protected void turnEnd() {
		updateLabels();
	}
	
	/*
	 * MODE
	 */
	
	private enum Mode {
		HUMAN, RANDOM, MINMAX
	}
	
	private void changeMode1(Mode mode1) {
		this.mode1 = mode1;
	}
	
	private void changeMode2(Mode mode2) {
		this.mode2 = mode2;
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
		public boolean isPlayer(int player) {
			if(value>0) return player==PLAYER_DINO;
			if(value<0) return player==PLAYER_ANUBIS;
			return false;
		}
		
		public BufferedImage getImage(boolean blocked) {
			return imgLoader.valueToImg(value, blocked);
		}
		
		protected void drawShape() {
			Color cellColor = i==0 || i==12 ? COLOR_HOME : COLOR_GROUND;
			fillSquareC(cellColor, CELL_SIZE);
			drawSquareC(Color.WHITE, CELL_SIZE);

			boolean blocked = UtilRagus.isBlocked(boardData(), getIJ());
			BufferedImage img = getImage(blocked);
			if(img!=null) {
				Composite composite = g2_getComposite();
				if(this==dragged) g2_setComposite(ALPHA);
				paintRenderedImageC(IMG_SIZE, img);
				if(this==dragged) g2_setComposite(composite);
			}
			
			int strengh = Math.abs(value);
			if(strengh>1) drawString(blocked ? Color.gray : Color.BLACK, p(11,-8), ""+strengh);
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
	
	public void updateBoard(int[][] data) {
		board.updateCells(data, Cell::setValue);
	}
	
	/*
	 * GAME OVER
	 */
	
	private void handleGameOver() throws Exception {
		PlayerRagus winner = searchWinner(boardData(), player1, player2);
		if(winner!=null) setGameOver(winner);
	}
	
	private String getGameOverDisplay() {
		Player1 winner = getGameOver().getWinner();
		return winner!=null ? winner.getDisplay()+" won the game" : "Draw";
	}
	
	/*
	 * PLAYER
	 */
	
	private PlayerRagus buildPlayer(Mode mode, int value) {
		switch(mode) {
		case HUMAN: return new PlayerRagusHuman(this, value);
		case RANDOM: return new PlayerRagusRandom(this, value);
		case MINMAX: return new PlayerRagusMinmax(this, value);
		}
		return null;
	}
	
	private PlayerRagus currentRagusPlayer() {
		return (PlayerRagus) currentPlayer();
	}
	
	/*
	 * LABEL INFO
	 */
	
	private void updateLabels() {
		if(playerNumber()==0) return;
		
		if(isGameOver()) {
			labelInfo1.setText(" "+getGameOverDisplay());
			labelInfo1.setIcon(null);
		}
		else {
			labelInfo1.setText(" "+currentRagusPlayer().getDisplay()+" is playing");
			labelInfo1.setIcon(currentRagusPlayer().getIconS());
		}
		
		String type1 = player1.getType();
		String type2 = player2.getType();
		
		labelInfo2.setText("Dino: "+type1+" , Anubis: "+type2);
		
		scoreBar1.refresh();
		scoreBar2.refresh();
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
				paintRenderedImageC(IMG_SIZE, dragged.getImage(false));
			}
		}
	}
}
