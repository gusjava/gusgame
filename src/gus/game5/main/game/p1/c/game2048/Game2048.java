package gus.game5.main.game.p1.c.game2048;

import java.awt.Color;
import java.util.Random;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class Game2048 extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 90;
	public static final int GAP = 12;
	public static final int MARGIN = 16;
	public static final int TOP = 104;
	public static final int BOTTOM = 46;

	public static final int BOARD = Grid.SIZE*CELL + (Grid.SIZE+1)*GAP;

	public static final int BOX_W = 92;
	public static final int BOX_H = 50;

	private Grid grid;
	private Random random;

	private int best;

	private int[][] undoCells;
	private int undoScore;
	private boolean undoWin;
	private boolean undoKept;
	private boolean undoReady;

	private boolean paused;
	private boolean over;
	private boolean kept;


	public static void main(String[] args) {
		Game2048 main = new Game2048();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("2048");
		s.setWidth(BOARD + 2*MARGIN);
		s.setHeight(TOP + BOARD + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(Palette.WINDOW);
	}

	protected void initialize1() {
		random = new Random();
		grid = new Grid(random);
		grid.spawn();
		grid.spawn();

		undoReady = false;
		paused = false;
		over = false;
		kept = false;

		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) {
			restart();
			return;
		}
		if(k.F2()) exit();
		if(!over && k.in().p()) paused = !paused;
		if(paused) return;
		if(isWinPending()) {
			if(k.in().space()) kept = true;
			return;
		}
		if(k.in().u()) {
			undo();
			return;
		}
		if(over) return;
		int dir = direction(k);
		if(dir<0) return;
		play(dir);
	}

	private boolean isWinPending() {
		return grid.isWin() && !kept;
	}

	private int direction(Keyboard k) {
		if(k.in().left()) return Grid.LEFT;
		if(k.in().right()) return Grid.RIGHT;
		if(k.in().up()) return Grid.UP;
		if(k.in().down()) return Grid.DOWN;
		return -1;
	}

	private void play(int dir) {
		int[][] before = grid.copyCells();
		int scoreBefore = grid.getScore();
		boolean winBefore = grid.isWin();
		boolean keptBefore = kept;
		if(!grid.move(dir)) return;

		undoCells = before;
		undoScore = scoreBefore;
		undoWin = winBefore;
		undoKept = keptBefore;
		undoReady = true;

		grid.spawn();
		best = Math.max(best, grid.getScore());
		if(!grid.canMove()) over = true;
	}

	private void undo() {
		if(!undoReady) return;
		grid.restore(undoCells, undoScore, undoWin);
		kept = undoKept;
		undoReady = false;
		over = false;
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
			drawString(Palette.DARK_TEXT, fontBold(46), p(2, -34), "2048");
			drawBox("SCORE", ""+grid.getScore(), BOARD-2*BOX_W-GAP);
			drawBox("MEILLEUR", ""+best, BOARD-BOX_W);
		}

		private void drawBox(String label, String value, double x) {
			fillRect(Palette.BOARD, p(x, -86), BOX_W, BOX_H);
			drawStringC(Palette.LABEL, fontBold(11), p(x+BOX_W*0.5, -70), label);
			drawStringC(Color.WHITE, fontBold(20), p(x+BOX_W*0.5, -50), value);
		}

		private void drawBoard() {
			fillRect(Palette.BOARD, p(0, 0), BOARD, BOARD);
			for(int row=0;row<Grid.SIZE;row++)
				for(int col=0;col<Grid.SIZE;col++) drawTile(col, row);
		}

		private void drawTile(int col, int row) {
			double x = GAP + col*(CELL+GAP);
			double y = GAP + row*(CELL+GAP);
			int value = grid.at(col, row);
			if(value==0) {
				fillRect(Palette.EMPTY, p(x, y), CELL, CELL);
				return;
			}
			fillRect(Palette.tile(value), p(x, y), CELL, CELL);
			drawStringC(Palette.text(value), fontBold(Palette.fontSize(value)), p(x+CELL*0.5, y+CELL*0.5), ""+value);
		}

		private void drawHelp() {
			drawString(Palette.DARK_TEXT, fontPlain(11), p(0, BOARD+18),
					"Fleches jouer   U annuler le dernier coup   P pause");
			drawString(Palette.DARK_TEXT, fontPlain(11), p(0, BOARD+32),
					"F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(paused) {
				drawVeil();
				drawStringC(Palette.DARK_TEXT, fontBold(34), p(BOARD*0.5, BOARD*0.5), "PAUSE");
				return;
			}
			if(isWinPending()) {
				drawVeil();
				drawStringC(Palette.HIGHLIGHT, fontBold(38), p(BOARD*0.5, BOARD*0.5-18), "GAGNE !");
				drawStringC(Palette.DARK_TEXT, fontPlain(14), p(BOARD*0.5, BOARD*0.5+22), "ESPACE pour continuer");
				return;
			}
			if(!over) return;
			drawVeil();
			drawStringC(Palette.DARK_TEXT, fontBold(38), p(BOARD*0.5, BOARD*0.5-18), "PERDU");
			drawStringC(Palette.DARK_TEXT, fontPlain(14), p(BOARD*0.5, BOARD*0.5+22), "F1 pour rejouer");
		}

		private void drawVeil() {
			fillRect(Palette.VEIL, p(0, 0), BOARD, BOARD);
		}
	}
}
