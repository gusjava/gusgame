package gus.game5.main.game.p1.c.taquin;

import java.awt.Color;
import java.util.Random;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;

public class GameTaquin extends Game1 {

	public static final int SIZE = 4;

	public static final int CELL = 100;
	public static final int GAP = 6;
	public static final int MARGIN = 16;
	public static final int TOP = 104;
	public static final int BOTTOM = 56;

	public static final int BOARD = SIZE*CELL + (SIZE+1)*GAP;

	public static final int BOX_W = 130;
	public static final int BOX_H = 50;

	private Board board;
	private Random random;

	private long startTime;
	private long wonTime;
	private boolean won;


	public static void main(String[] args) {
		GameTaquin main = new GameTaquin();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Taquin");
		s.setWidth(BOARD + 2*MARGIN);
		s.setHeight(TOP + BOARD + BOTTOM);
		s.setSleep(16);
		s.setBackground(Palette.WINDOW);
	}

	protected void initialize1() {
		random = new Random();
		board = new Board(SIZE);
		shuffle();

		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) {
			restart();
			return;
		}
		if(k.in().F2()) exit();
		if(k.in().r()) {
			shuffle();
			return;
		}
		if(handleMouse()) return;
		if(won) return;
		int dir = direction(k);
		if(dir>=0) move(dir);
	}

	private int direction(Keyboard k) {
		if(k.in().left()) return Board.LEFT;
		if(k.in().right()) return Board.RIGHT;
		if(k.in().up()) return Board.UP;
		if(k.in().down()) return Board.DOWN;
		return -1;
	}

	private void move(int dir) {
		if(board.moveBlank(dir)) checkWin();
	}

	private boolean handleMouse() {
		if(won) return false;
		Mouse mouse = mouse();
		if(!mouse.button1().justPressed()) return false;

		Point0 p = mouse.button1().justPressedPoint();
		int col = (int) Math.floor((p.getX()-MARGIN-GAP)/(CELL+GAP));
		int row = (int) Math.floor((p.getY()-TOP-GAP)/(CELL+GAP));
		if(col<0 || col>=SIZE || row<0 || row>=SIZE) return false;

		if(!board.slideTo(col, row)) return false;
		checkWin();
		return true;
	}

	private void checkWin() {
		if(!board.isSolved()) return;
		won = true;
		wonTime = System.currentTimeMillis();
	}

	private void shuffle() {
		board.shuffle(random);
		won = false;
		startTime = System.currentTimeMillis();
	}

	private long elapsedSeconds() {
		long end = won ? wonTime : System.currentTimeMillis();
		return (end-startTime)/1000;
	}

	private static String formatTime(long seconds) {
		long m = seconds/60;
		long s = seconds%60;
		return (m<10?"0":"")+m+":"+(s<10?"0":"")+s;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		public GameDrawing() {
			super();
			initOrigin().setXY(MARGIN, TOP);
		}

		protected void draw() {
			drawHeader();
			drawBoard();
			drawHelp();
			drawMessage();
		}

		private void drawHeader() {
			drawString(Palette.DARK_TEXT, fontBold(42), p(2, -32), "TAQUIN");
			drawBox("COUPS", ""+board.getMoveCount(), BOARD-2*BOX_W-GAP);
			drawBox("TEMPS", formatTime(elapsedSeconds()), BOARD-BOX_W);
		}

		private void drawBox(String label, String value, double x) {
			fillRect(Palette.BOARD, p(x, -82), BOX_W, BOX_H);
			drawStringC(Palette.LABEL, fontBold(11), p(x+BOX_W*0.5, -66), label);
			drawStringC(Color.WHITE, fontBold(20), p(x+BOX_W*0.5, -46), value);
		}

		private void drawBoard() {
			fillRect(Palette.BOARD, p(0, 0), BOARD, BOARD);
			for(int row=0;row<SIZE;row++)
				for(int col=0;col<SIZE;col++) drawTile(col, row);
		}

		private void drawTile(int col, int row) {
			double x = GAP + col*(CELL+GAP);
			double y = GAP + row*(CELL+GAP);
			int value = board.at(col, row);
			if(value==0) {
				fillRect(Palette.EMPTY, p(x, y), CELL, CELL);
				return;
			}
			boolean correct = value==row*SIZE+col+1;
			fillRect(correct ? Palette.TILE_OK : Palette.TILE, p(x, y), CELL, CELL);
			drawStringC(Color.WHITE, fontBold(36), p(x+CELL*0.5, y+CELL*0.5), ""+value);
		}

		private void drawHelp() {
			drawString(Palette.DARK_TEXT, fontPlain(11), p(0, BOARD+22),
					"Fleches ou clic sur une tuile alignee : jouer   R remelanger");
			drawString(Palette.DARK_TEXT, fontPlain(11), p(0, BOARD+36),
					"F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!won) return;
			fillRect(Palette.VEIL, p(0, 0), BOARD, BOARD);
			drawStringC(Palette.HIGHLIGHT, fontBold(38), p(BOARD*0.5, BOARD*0.5-18), "GAGNE !");
			drawStringC(Palette.DARK_TEXT, fontPlain(14), p(BOARD*0.5, BOARD*0.5+22), "R ou F1 pour rejouer");
		}
	}
}
