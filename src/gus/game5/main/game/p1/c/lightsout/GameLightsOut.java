package gus.game5.main.game.p1.c.lightsout;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;

public class GameLightsOut extends Game1 {

	/*
	 * CONSTANTS
	 */

	public static final int GRID = 5;
	public static final int CELL = 70;
	public static final int GAP = 20;
	public static final int TOP = 46;
	public static final int BOTTOM = 42;
	public static final int BOARD = GRID*CELL;

	public static final int SHUFFLE_MIN = GRID*GRID;
	public static final int SHUFFLE_MAX = GRID*GRID*2;

	public static final Color COLOR_BACKGROUND = new Color(18,18,28);
	public static final Color COLOR_OFF = new Color(40,40,54);
	public static final Color COLOR_ON = new Color(255,200,60);
	public static final Color COLOR_ON_GLOW = new Color(255,230,140);
	public static final Color COLOR_BORDER = new Color(110,110,150);
	public static final Color COLOR_CURSOR = new Color(90,170,255);
	public static final Color COLOR_HINT = new Color(255,70,70);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);


	/*
	 * VARIABLES
	 */

	private boolean[][] lit;
	private boolean[][] pending;

	private int cursorI;
	private int cursorJ;
	private int moves;
	private boolean won;
	private int hintI;
	private int hintJ;

	private Random random = new Random();


	public static void main(String[] args) {
		GameLightsOut main = new GameLightsOut();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Lights Out");
		s.setWidth(GAP*2 + BOARD);
		s.setHeight(TOP + BOARD + BOTTOM);
		s.setSleep(30);
		s.setBackground(COLOR_BACKGROUND);
	}

	protected void initialize1() {
		lit = new boolean[GRID][GRID];
		pending = new boolean[GRID][GRID];
		cursorI = GRID/2;
		cursorJ = GRID/2;
		hintI = -1;
		hintJ = -1;
		shuffle();
		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		Mouse mse = mouse();

		if(k.in().F1()) {restart(); return;}
		if(k.in().F2()) exit();
		if(k.in().r()) {restart(); return;}
		if(won) return;

		if(k.in().left())  cursorI = Math.max(0, cursorI-1);
		if(k.in().right()) cursorI = Math.min(GRID-1, cursorI+1);
		if(k.in().up())    cursorJ = Math.max(0, cursorJ-1);
		if(k.in().down())  cursorJ = Math.min(GRID-1, cursorJ+1);

		if(k.in().space()) press(cursorI, cursorJ);
		if(k.in().h()) computeHint();

		if(mse.button1().justPressed()) {
			Point0 p = mse.button1().justPressedPoint();
			int i = cellI(p);
			int j = cellJ(p);
			if(inGrid(i, j)) {
				cursorI = i;
				cursorJ = j;
				press(i, j);
			}
		}
	}

	private int cellI(Point0 p) {
		return (int) Math.floor((p.getX()-GAP)/CELL);
	}

	private int cellJ(Point0 p) {
		return (int) Math.floor((p.getY()-TOP)/CELL);
	}


	/*
	 * LOGIC
	 */

	private void shuffle() {
		for(boolean[] row : lit) Arrays.fill(row, false);
		for(boolean[] row : pending) Arrays.fill(row, false);
		moves = 0;
		won = false;
		hintI = -1;
		hintJ = -1;

		int presses = SHUFFLE_MIN + random.nextInt(SHUFFLE_MAX-SHUFFLE_MIN+1);
		for(int n=0;n<presses;n++) toggleCross(random.nextInt(GRID), random.nextInt(GRID));

		if(allOff()) shuffle();
	}

	private void press(int i, int j) {
		toggleCross(i, j);
		moves++;
		hintI = -1;
		hintJ = -1;
		if(allOff()) won = true;
	}

	private void toggleCross(int i, int j) {
		if(!inGrid(i, j)) return;
		toggle(i, j);
		toggle(i-1, j);
		toggle(i+1, j);
		toggle(i, j-1);
		toggle(i, j+1);
		pending[i][j] = !pending[i][j];
	}

	private void toggle(int i, int j) {
		if(!inGrid(i, j)) return;
		lit[i][j] = !lit[i][j];
	}

	private boolean inGrid(int i, int j) {
		return i>=0 && i<GRID && j>=0 && j<GRID;
	}

	private boolean allOff() {
		for(boolean[] row : lit)
			for(boolean v : row)
				if(v) return false;
		return true;
	}

	private void computeHint() {
		for(int i=0;i<GRID;i++)
			for(int j=0;j<GRID;j++)
				if(pending[i][j]) {
					hintI = i;
					hintJ = j;
					return;
				}
		hintI = -1;
		hintJ = -1;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		public GameDrawing() {
			super();
			initOrigin().setXY(GAP, TOP);
		}

		protected void draw() {
			drawGrid();
			drawCursor();
			drawHint();
			drawFrame();
			drawHeader();
			drawHelp();
			drawMessage();
		}

		private void drawGrid() {
			for(int i=0;i<GRID;i++)
			for(int j=0;j<GRID;j++) {
				boolean on = lit[i][j];
				fillSquare(on ? COLOR_ON : COLOR_OFF, p(i*CELL+3, j*CELL+3), CELL-6);
				if(on) drawSquare(COLOR_ON_GLOW, p(i*CELL+3, j*CELL+3), CELL-6);
				drawSquare(COLOR_BORDER, p(i*CELL, j*CELL), CELL);
			}
		}

		private void drawCursor() {
			if(won) return;
			drawSquare(COLOR_CURSOR, p(cursorI*CELL+1, cursorJ*CELL+1), CELL-2);
		}

		private void drawHint() {
			if(hintI<0) return;
			drawRoundC(COLOR_HINT, p((hintI+0.5)*CELL, (hintJ+0.5)*CELL), 8);
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), BOARD, BOARD);
		}

		private void drawHeader() {
			drawString(COLOR_LABEL, fontPlain(13), p(0, -16), "COUPS : "+moves);
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD+16),
					"Fleches deplacer   ESPACE/clic basculer   H indice   R nouveau tirage");
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD+29),
					"F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!won) return;
			fillRect(COLOR_VEIL, p(0, 0), BOARD, BOARD);
			drawStringC(COLOR_TEXT, fontBold(26), p(BOARD/2, BOARD/2-10), "GAGNE !");
			drawStringC(COLOR_TEXT, fontPlain(14), p(BOARD/2, BOARD/2+18), "en "+moves+" coups - F1 pour rejouer");
		}
	}
}
