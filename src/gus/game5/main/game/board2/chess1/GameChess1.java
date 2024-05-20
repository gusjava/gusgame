package gus.game5.main.game.board2.chess1;

import static gus.game5.main.game.board2.chess1.UtilChess.BKI;
import static gus.game5.main.game.board2.chess1.UtilChess.INIT_STATE;
import static gus.game5.main.game.board2.chess1.UtilChess.WKI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameChess1 extends Game1 {

	public static final String TITLE = "Chess";
	public static final int CELL_SIZE = 50;
	public static final int BOARD_SIZE = CELL_SIZE*8;
	
	public static final Color DARK = new Color(153,204,255);
	public static final Color LIGHT = new Color(255,255,240);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameChess1 main = new GameChess1();
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
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		engine = new Engine();
		imgLoader = new ImageLoader1(this);
		board = newShapeBoard(CELL_SIZE, 8, this::buildCell);
		dragged = null;
		
		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);
		
		addDraw(new Drag());
		updateLabelInfo();
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
		
		if(isGameOver()) return;

		if(mouse().button1().justPressed()) {
			handlePressed();
		}
		else if(mouse().button1().justReleased()) {
			handleReleased();
		}
	}
	
	private void handlePressed() {
		Cell pressedCell = board.cellAt(mouse().pointCurrent());
		if(pressedCell!=null && pressedCell.isPlayer(engine.getPlayer())) {
			dragged = pressedCell;
		}
	}
	
	private void handleReleased() {
		if(dragged==null) return;
		
		boolean played = attemptToPlay();
		dragged = null;
		if(!played) return;
		
		if(!isGameOver()) engine.shiftPlayer();
		updateBoard();
		updateLabelInfo();
	}
	
	/*
	 * PLAY
	 */
	
	private boolean attemptToPlay() {
		Cell startCell = dragged;
		Cell endCell = board.cellAt(mouse().pointCurrent());
		if(endCell==null) return false;
		
		int[] start = startCell.getIJ();
		int[] end = endCell.getIJ();
		return engine.attemptToPlay(start, end);
	}
	
	/*
	 * UPDATE BOARD
	 */
	
	private void updateBoard() {
		int[][] data = engine.getData();
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			board.cellAt(i, j).setValue(data[i][j]);
		}
	}
	
	/*
	 * UPDATE LABELINFO
	 */
	
	private void updateLabelInfo() {
		if(isGameOver()) {
			labelInfo.setText(engine.getGameOver().getDescription());
		}
		else {
			EPlayer player = engine.getPlayer();
			String info = " "+player.getLabel()+" is playing";
			if(engine.isPlayerChecked()) info += " (check)";
			labelInfo.setText(info);
		}
	}
	
	/*
	 * GAME OVER
	 */
	
	public boolean isGameOver() {
		return engine!=null && engine.getGameOver()!=null;
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
				paintRenderedImageC(CELL_SIZE-8, CELL_SIZE-8, img);
				if(this==dragged) g2_setComposite(composite);
			}
		}
		
		public BufferedImage getImage() {
			return imgLoader.valueToImg(value);
		}
		public void setValue(int value) {
			this.value = value;
		}
		public boolean isPlayer(EPlayer player) {
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
}
