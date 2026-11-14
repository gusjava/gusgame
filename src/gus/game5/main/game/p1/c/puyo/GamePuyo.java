package gus.game5.main.game.p1.c.puyo;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GamePuyo extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 32;
	public static final int GAP = 12;
	public static final int TOP = 40;
	public static final int BOTTOM = 34;
	public static final int PANEL_W = 150;
	public static final int PREVIEW_CELL = 26;

	public static final int FIELD_W = Board.COLS*CELL;
	public static final int FIELD_H = Board.ROWS*CELL;
	public static final int PANEL_X = FIELD_W + GAP;

	public static final int[] GRAVITY = {
		46,42,38,34,30,27,24,21,18,16,14,12,10,9,8,7,6,5,4,3};

	public static final int SOFT_INTERVAL = 3;
	public static final int LOCK_DELAY = 20;
	public static final int LOCK_RESETS = 10;
	public static final int DAS_DELAY = 16;
	public static final int DAS_REPEAT = 6;
	public static final int FLASH_DELAY = 30;
	public static final int FLASH_PERIOD = 6;
	public static final int PAIRS_PER_LEVEL = 20;
	public static final int SPAWN_COL = 2;
	public static final int SPAWN_ROW = 1;

	public static final int SOFT_DROP_POINTS = 1;

	public static final Color COLOR_FIELD = new Color(18,20,42);
	public static final Color COLOR_GRID = new Color(40,44,76);
	public static final Color COLOR_BORDER = new Color(120,124,170);
	public static final Color COLOR_TEXT = new Color(238,238,248);
	public static final Color COLOR_LABEL = new Color(152,156,190);
	public static final Color COLOR_FLASH = new Color(255,255,255);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);
	public static final Color COLOR_EYE = new Color(252,252,252);
	public static final Color COLOR_PUPIL = new Color(24,24,32);


	private Board board;
	private Pair pair;
	private Puyo[] next;
	private Group group;

	private int score;
	private int level;
	private int pairs;
	private int puyos;
	private int chain;
	private int bestChain;

	private int gravityTick;
	private int lockTick;
	private int lockResets;
	private int dasDir;
	private int dasTick;
	private int flashTick;

	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GamePuyo main = new GamePuyo();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Puyo Puyo");
		s.setWidth(GAP*3 + FIELD_W + PANEL_W);
		s.setHeight(TOP + FIELD_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(new Color(12,13,26));
	}

	protected void initialize1() {
		board = new Board();

		score = 0;
		level = 0;
		pairs = 0;
		puyos = 0;
		chain = 0;
		bestChain = 0;
		group = null;
		paused = false;
		over = false;
		dasDir = 0;
		dasTick = 0;

		next = Puyo.randomPair();
		spawn();
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
		if(paused || over) return;

		if(group!=null) {
			flash();
			return;
		}

		shift(k);
		if(k.in().up() || k.in().x()) rotate(1);
		else if(k.in().z()) rotate(-1);
		fall(k);
	}

	private void rotate(int way) {
		if(pair.rotate(way)) delayLock();
	}

	private void shift(Keyboard k) {
		int dir = k.left() ? -1 : (k.right() ? 1 : 0);
		if(dir==0) {
			dasDir = 0;
			dasTick = 0;
			return;
		}
		if(dir!=dasDir) {
			dasDir = dir;
			dasTick = 0;
			slide(dir);
			return;
		}
		dasTick++;
		if(dasTick<DAS_DELAY) return;
		if((dasTick-DAS_DELAY)%DAS_REPEAT==0) slide(dir);
	}

	private void slide(int dir) {
		if(pair.move(dir, 0)) delayLock();
	}

	private void delayLock() {
		if(!pair.isLanded()) {
			lockTick = 0;
			return;
		}
		if(lockResets>=LOCK_RESETS) return;
		lockResets++;
		lockTick = 0;
	}

	private void fall(Keyboard k) {
		boolean soft = k.down();
		int interval = GRAVITY[Math.min(level, GRAVITY.length-1)];
		if(soft) interval = Math.min(interval, SOFT_INTERVAL);

		gravityTick++;
		if(gravityTick<interval) {
			countLock();
			return;
		}
		gravityTick = 0;

		if(!pair.move(0, 1)) {
			countLock();
			return;
		}
		if(soft) score += SOFT_DROP_POINTS;
		lockTick = 0;
		lockResets = 0;
	}

	private void countLock() {
		if(!pair.isLanded()) return;
		lockTick++;
		if(lockTick>=LOCK_DELAY) lockPair();
	}

	private void lockPair() {
		pair.lock();
		pairs++;
		level = pairs/PAIRS_PER_LEVEL;
		board.collapse();
		chain = 0;
		resolve();
	}

	private void resolve() {
		Group found = board.findGroups();
		if(found.isEmpty()) {
			group = null;
			spawn();
			return;
		}
		group = found;
		chain++;
		if(chain>bestChain) bestChain = chain;
		flashTick = 0;
	}

	private void flash() {
		flashTick++;
		if(flashTick<FLASH_DELAY) return;
		score += group.getScore(chain);
		puyos += group.getCount();
		board.clear(group);
		board.collapse();
		resolve();
	}

	private void spawn() {
		pair = new Pair(board, next, SPAWN_COL, SPAWN_ROW);
		next = Puyo.randomPair();
		gravityTick = 0;
		lockTick = 0;
		lockResets = 0;
		if(pair.isBlocked()) over = true;
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
			drawField();
			drawStack();
			if(group==null && !over) drawPair();
			drawFrame();
			drawPanel();
			drawHelp();
			drawMessage();
		}

		private void drawField() {
			fillRect(COLOR_FIELD, p(0, 0), FIELD_W, FIELD_H);
			for(int col=1;col<Board.COLS;col++)
				drawLine(COLOR_GRID, p(col*CELL, 0), p(col*CELL, FIELD_H));
			for(int row=1;row<Board.ROWS;row++)
				drawLine(COLOR_GRID, p(0, row*CELL), p(FIELD_W, row*CELL));
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), FIELD_W, FIELD_H);
		}

		private void drawStack() {
			for(int row=0;row<Board.ROWS;row++)
				for(int col=0;col<Board.COLS;col++) {
					Puyo puyo = board.at(col, row);
					if(puyo==null) continue;
					drawPuyo(puyoColor(puyo, col, row), col*CELL, row*CELL, CELL);
				}
		}

		private Color puyoColor(Puyo puyo, int col, int row) {
			if(group==null || !group.contains(col, row)) return puyo.getColor();
			return (flashTick/FLASH_PERIOD)%2==0 ? COLOR_FLASH : puyo.getColor();
		}

		private void drawPair() {
			drawCell(pair.getPivot(), pair.getCol(), pair.getRow());
			drawCell(pair.getChild(), pair.getChildCol(), pair.getChildRow());
		}

		private void drawCell(Puyo puyo, int col, int row) {
			if(row<0) return;
			drawPuyo(puyo.getColor(), col*CELL, row*CELL, CELL);
		}

		private void drawPuyo(Color color, double x, double y, double size) {
			double radius = size*0.46;
			double cx = x + size*0.5;
			double cy = y + size*0.5;
			fillRoundC(color, p(cx, cy), radius);
			fillRoundC(color.darker(), p(cx, cy+radius*0.30), radius*0.62);
			fillRoundC(color, p(cx, cy), radius*0.86);
			fillRoundC(color.brighter(), p(cx-radius*0.36, cy-radius*0.40), radius*0.22);
			double eye = radius*0.30;
			fillRoundC(COLOR_EYE, p(cx-radius*0.32, cy-radius*0.04), eye);
			fillRoundC(COLOR_EYE, p(cx+radius*0.32, cy-radius*0.04), eye);
			fillRoundC(COLOR_PUPIL, p(cx-radius*0.28, cy+radius*0.04), eye*0.46);
			fillRoundC(COLOR_PUPIL, p(cx+radius*0.36, cy+radius*0.04), eye*0.46);
		}

		private void drawPanel() {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, 12), "SUIVANT");
			fillRect(COLOR_FIELD, p(PANEL_X, 20), PANEL_W, 72);
			drawRect(COLOR_BORDER, p(PANEL_X, 20), PANEL_W, 72);
			double x = PANEL_X + (PANEL_W - PREVIEW_CELL)*0.5;
			drawPuyo(next[1].getColor(), x, 28, PREVIEW_CELL);
			drawPuyo(next[0].getColor(), x, 28+PREVIEW_CELL, PREVIEW_CELL);

			drawValue("SCORE", ""+score, 116);
			drawValue("NIVEAU", ""+level, 178);
			drawValue("PUYOS", ""+puyos, 240);
			drawValue("CHAINE MAX", ""+bestChain, 302);
			if(group!=null) drawString(COLOR_TEXT, fontBold(18), p(PANEL_X, 364), "CHAINE x"+chain);
		}

		private void drawValue(String label, String value, double y) {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, y), label);
			drawString(COLOR_TEXT, fontBold(22), p(PANEL_X, y+28), value);
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, FIELD_H+16),
					"Fleches deplacer   BAS accelerer   HAUT/X rotation");
			drawString(COLOR_LABEL, fontPlain(11), p(0, FIELD_H+29),
					"Z rotation inverse   P pause   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!over && !paused) return;
			fillRect(COLOR_VEIL, p(0, 0), FIELD_W, FIELD_H);
			if(paused) {
				drawStringC(COLOR_TEXT, fontBold(26), p(FIELD_W/2, FIELD_H/2), "PAUSE");
				return;
			}
			drawStringC(Color.RED, fontBold(24), p(FIELD_W/2, FIELD_H/2-16), "GAME OVER");
			drawStringC(COLOR_TEXT, fontPlain(13), p(FIELD_W/2, FIELD_H/2+20), "F1 pour rejouer");
		}
	}
}
