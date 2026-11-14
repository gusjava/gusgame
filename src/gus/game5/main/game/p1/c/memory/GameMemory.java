package gus.game5.main.game.p1.c.memory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;

public class GameMemory extends Game1 {

	/*
	 * CONSTANTS
	 */

	public static final int COLS = 4;
	public static final int ROWS = 4;
	public static final int NB_SYMBOLS = COLS*ROWS/2;

	public static final int CELL = 90;
	public static final int GAP = 20;
	public static final int TOP = 46;
	public static final int BOTTOM = 42;
	public static final int BOARD_W = COLS*CELL;
	public static final int BOARD_H = ROWS*CELL;

	public static final int FLIP_DELAY = 35;

	public static final Color COLOR_BACKGROUND = new Color(18,18,28);
	public static final Color COLOR_CARD_BACK = new Color(50,60,90);
	public static final Color COLOR_CARD_BACK_BORDER = new Color(90,110,160);
	public static final Color COLOR_CARD_FACE = new Color(235,235,245);
	public static final Color COLOR_CARD_MATCHED = new Color(40,70,50);
	public static final Color COLOR_BORDER = new Color(110,110,150);
	public static final Color COLOR_CURSOR = new Color(90,170,255);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);

	public static final Color[] SYMBOL_COLORS = {
			new Color(230,70,70),
			new Color(70,150,230),
			new Color(230,180,60),
			new Color(120,200,100),
			new Color(190,110,220),
			new Color(240,140,180),
			new Color(220,90,90),
			new Color(210,210,90),
	};


	/*
	 * VARIABLES
	 */

	private Card[][] cards;

	private int cursorI;
	private int cursorJ;
	private int moves;
	private int pairsFound;
	private boolean won;

	private Card firstCard;
	private DynTimer flipTimer;

	private long startTimeMillis;
	private long wonTimeMillis;


	public static void main(String[] args) {
		GameMemory main = new GameMemory();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Memory");
		s.setWidth(GAP*2 + BOARD_W);
		s.setHeight(TOP + BOARD_H + BOTTOM);
		s.setSleep(30);
		s.setBackground(COLOR_BACKGROUND);
	}

	protected void initialize1() {
		flipTimer = newDynTimer();
		cursorI = COLS/2;
		cursorJ = ROWS/2;
		dealCards();
		addDraw(new GameDrawing());
	}

	private void dealCards() {
		List<Integer> symbols = new ArrayList<>();
		for(int s=0;s<NB_SYMBOLS;s++) {
			symbols.add(s);
			symbols.add(s);
		}
		Collections.shuffle(symbols);

		cards = new Card[COLS][ROWS];
		int index = 0;
		for(int j=0;j<ROWS;j++)
		for(int i=0;i<COLS;i++)
			cards[i][j] = new Card(symbols.get(index++));

		moves = 0;
		pairsFound = 0;
		won = false;
		firstCard = null;
		startTimeMillis = System.currentTimeMillis();
		wonTimeMillis = -1;
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();

		if(k.in().F1()) {restart(); return;}
		if(k.in().F2()) exit();

		goNext();

		if(won) return;
		if(flipTimer.gBool()) return;

		if(k.in().left())  cursorI = Math.max(0, cursorI-1);
		if(k.in().right()) cursorI = Math.min(COLS-1, cursorI+1);
		if(k.in().up())    cursorJ = Math.max(0, cursorJ-1);
		if(k.in().down())  cursorJ = Math.min(ROWS-1, cursorJ+1);
		if(k.in().space()) tryFlip(cursorI, cursorJ);
		if(flipTimer.gBool()) return;

		Mouse mse = mouse();
		if(mse.button1().justPressed()) {
			Point0 p = mse.button1().justPressedPoint();
			int i = cellI(p);
			int j = cellJ(p);
			if(inGrid(i, j)) {
				cursorI = i;
				cursorJ = j;
				tryFlip(i, j);
			}
		}
	}

	private int cellI(Point0 p) {
		return (int) Math.floor((p.getX()-GAP)/CELL);
	}

	private int cellJ(Point0 p) {
		return (int) Math.floor((p.getY()-TOP)/CELL);
	}

	private boolean inGrid(int i, int j) {
		return i>=0 && i<COLS && j>=0 && j<ROWS;
	}


	/*
	 * LOGIC
	 */

	private void tryFlip(int i, int j) {
		Card c = cards[i][j];
		if(c.matched || c.revealed) return;

		c.revealed = true;

		if(firstCard==null) {
			firstCard = c;
			return;
		}

		moves++;
		Card first = firstCard;
		firstCard = null;

		if(first.symbol==c.symbol) {
			first.matched = true;
			c.matched = true;
			pairsFound++;
			if(pairsFound==NB_SYMBOLS) {
				won = true;
				wonTimeMillis = System.currentTimeMillis();
			}
		}
		else {
			Card second = c;
			flipTimer.start(FLIP_DELAY, () -> {
				first.revealed = false;
				second.revealed = false;
			});
		}
	}

	private long elapsedSeconds() {
		long end = won ? wonTimeMillis : System.currentTimeMillis();
		return (end-startTimeMillis)/1000;
	}


	/*
	 * CARD
	 */

	private static class Card {
		private final int symbol;
		private boolean revealed;
		private boolean matched;

		private Card(int symbol) {
			this.symbol = symbol;
		}
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
			drawFrame();
			drawHeader();
			drawHelp();
			drawMessage();
		}

		private void drawGrid() {
			for(int i=0;i<COLS;i++)
			for(int j=0;j<ROWS;j++)
				drawCard(cards[i][j], i, j);
		}

		private void drawCard(Card c, int i, int j) {
			Point0 p0 = p(i*CELL+4, j*CELL+4);
			double size = CELL-8;
			Point0 pc = p((i+0.5)*CELL, (j+0.5)*CELL);
			double radius = size*0.3;

			if(c.matched) {
				fillSquare(COLOR_CARD_MATCHED, p0, size);
				drawSymbol(c.symbol, pc, radius, COLOR_CARD_MATCHED);
			}
			else if(c.revealed) {
				fillSquare(COLOR_CARD_FACE, p0, size);
				drawSymbol(c.symbol, pc, radius, COLOR_CARD_FACE);
			}
			else {
				fillSquare(COLOR_CARD_BACK, p0, size);
				drawSquare(COLOR_CARD_BACK_BORDER, p0, size);
			}
			drawSquare(COLOR_BORDER, p(i*CELL, j*CELL), CELL);
		}

		private void drawCursor() {
			if(won) return;
			drawSquare(COLOR_CURSOR, p(cursorI*CELL+1, cursorJ*CELL+1), CELL-2);
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), BOARD_W, BOARD_H);
		}

		private void drawHeader() {
			drawString(COLOR_LABEL, fontPlain(13), p(0, -16),
					"COUPS : "+moves+"   PAIRES : "+pairsFound+"/"+NB_SYMBOLS+"   TEMPS : "+elapsedSeconds()+"s");
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+16),
					"Fleches deplacer   ESPACE/clic retourner");
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+29),
					"F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!won) return;
			fillRect(COLOR_VEIL, p(0, 0), BOARD_W, BOARD_H);
			drawStringC(COLOR_TEXT, fontBold(26), p(BOARD_W/2, BOARD_H/2-10), "GAGNE !");
			drawStringC(COLOR_TEXT, fontPlain(14), p(BOARD_W/2, BOARD_H/2+18),
					"en "+moves+" coups - "+elapsedSeconds()+"s - F1 pour rejouer");
		}

		/*
		 * SYMBOLS
		 */

		private void drawSymbol(int symbol, Point0 pc, double r, Color background) {
			Color color = SYMBOL_COLORS[symbol % SYMBOL_COLORS.length];
			switch(symbol) {
				case 0: drawRond(color, pc, r); break;
				case 1: drawCarre(color, pc, r); break;
				case 2: drawTriangle(color, pc, r); break;
				case 3: drawEtoile(color, pc, r); break;
				case 4: drawCroix(color, pc, r); break;
				case 5: drawLosange(color, pc, r); break;
				case 6: drawCoeur(color, pc, r); break;
				default: drawLune(color, pc, r, background); break;
			}
		}

		private void drawRond(Color color, Point0 pc, double r) {
			fillOvalC(color, pc, r*2, r*2);
		}

		private void drawCarre(Color color, Point0 pc, double r) {
			fillSquareC(color, pc, r*1.8);
		}

		private void drawTriangle(Color color, Point0 pc, double r) {
			double x = pc.getX(), y = pc.getY();
			fillPoly(color,
					p(x, y-r),
					p(x-r*0.87, y+r*0.6),
					p(x+r*0.87, y+r*0.6));
		}

		private void drawLosange(Color color, Point0 pc, double r) {
			double x = pc.getX(), y = pc.getY();
			fillPoly(color,
					p(x, y-r),
					p(x+r, y),
					p(x, y+r),
					p(x-r, y));
		}

		private void drawCroix(Color color, Point0 pc, double r) {
			fillRectC(color, pc, r*2, r*0.55);
			fillRectC(color, pc, r*0.55, r*2);
		}

		private void drawEtoile(Color color, Point0 pc, double r) {
			double x = pc.getX(), y = pc.getY();
			int spikes = 5;
			double innerR = r*0.42;
			double start = -Math.PI/2;
			double step = Math.PI/spikes;
			Point0[] pts = new Point0[spikes*2];
			for(int n=0;n<spikes*2;n++) {
				double rad = (n%2==0) ? r : innerR;
				double a = start + n*step;
				pts[n] = p(x+rad*Math.cos(a), y+rad*Math.sin(a));
			}
			fillPoly(color, pts);
		}

		private void drawCoeur(Color color, Point0 pc, double r) {
			double x = pc.getX(), y = pc.getY();
			fillOvalC(color, p(x-r*0.5, y-r*0.3), r, r);
			fillOvalC(color, p(x+r*0.5, y-r*0.3), r, r);
			fillPoly(color,
					p(x-r, y-r*0.15),
					p(x+r, y-r*0.15),
					p(x, y+r*1.1));
		}

		private void drawLune(Color color, Point0 pc, double r, Color background) {
			double x = pc.getX(), y = pc.getY();
			fillOvalC(color, pc, r*2, r*2);
			fillOvalC(background, p(x+r*0.6, y-r*0.15), r*1.8, r*1.8);
		}
	}
}
