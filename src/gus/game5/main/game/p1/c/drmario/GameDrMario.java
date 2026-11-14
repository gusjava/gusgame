package gus.game5.main.game.p1.c.drmario;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GameDrMario extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 28;
	public static final int GAP = 12;
	public static final int TOP = 40;
	public static final int BOTTOM = 34;
	public static final int PANEL_W = 150;
	public static final int PREVIEW_CELL = 22;

	public static final int BOARD_W = Bottle.COLS*CELL;
	public static final int BOARD_H = Bottle.ROWS*CELL;
	public static final int PANEL_X = BOARD_W + GAP;

	public static final int SPAWN_COL = Bottle.COLS/2 - 1;

	public static final int[] GRAVITY = {
		40,36,32,28,24,20,17,14,11,9,7,6,5,4,3};

	public static final int SOFT_INTERVAL = 3;
	public static final int LOCK_DELAY = 20;
	public static final int LOCK_RESETS = 12;
	public static final int DAS_DELAY = 16;
	public static final int DAS_REPEAT = 6;
	public static final int FALL_INTERVAL = 4;
	public static final int FLASH_DELAY = 28;
	public static final int FLASH_PERIOD = 6;
	public static final int LEVEL_DELAY = 70;

	public static final int[] CHAIN_FACTORS = {1,2,4,8,16,32};
	public static final int SOFT_DROP_POINTS = 1;

	public static final Color COLOR_FIELD = new Color(14,20,30);
	public static final Color COLOR_GRID = new Color(34,44,60);
	public static final Color COLOR_BORDER = new Color(110,130,150);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,160,180);
	public static final Color COLOR_FLASH = new Color(255,255,255);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);


	private Bottle bottle;
	private Capsule capsule;
	private GColor[] next;
	private Match match;

	private int score;
	private int level;
	private int chain;

	private int gravityTick;
	private int lockTick;
	private int lockResets;
	private int dasDir;
	private int dasTick;
	private int flashTick;
	private int fallTick;
	private int clearedTick;

	private boolean paused;
	private boolean over;
	private boolean falling;
	private boolean clearedPending;


	public static void main(String[] args) {
		GameDrMario main = new GameDrMario();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Dr. Mario");
		s.setWidth(GAP*3 + BOARD_W + PANEL_W);
		s.setHeight(TOP + BOARD_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(new Color(8,12,18));
	}

	protected void initialize1() {
		bottle = new Bottle();
		score = 0;
		level = 0;
		chain = 0;
		match = null;
		paused = false;
		over = false;
		falling = false;
		clearedPending = false;
		dasDir = 0;
		dasTick = 0;

		bottle.placeViruses(level);
		next = GColor.randomPair();
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

		if(clearedPending) {
			advanceLevel();
			return;
		}
		if(match!=null) {
			flash();
			return;
		}
		if(falling) {
			fallSettle();
			return;
		}

		shift(k);
		turnCapsule(k);
		fall(k);
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
		if(capsule.move(dir, 0)) delayLock();
	}

	private void turnCapsule(Keyboard k) {
		if(k.in().up() || k.in().x()) rotate(true);
		else if(k.in().z()) rotate(false);
	}

	private void rotate(boolean cw) {
		if(capsule.rotate(cw)) delayLock();
	}

	private void delayLock() {
		if(!capsule.isLanded()) {
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

		if(!capsule.move(0, 1)) {
			countLock();
			return;
		}
		if(soft) score += SOFT_DROP_POINTS;
		lockTick = 0;
		lockResets = 0;
	}

	private void countLock() {
		if(!capsule.isLanded()) return;
		lockTick++;
		if(lockTick>=LOCK_DELAY) lockCapsule();
	}

	private void lockCapsule() {
		bottle.lockCapsule(capsule);
		chain = 0;
		resolve();
	}

	private void resolve() {
		Match found = bottle.findMatch();
		if(found.isEmpty()) {
			if(bottle.virusCount()==0) {
				clearedPending = true;
				clearedTick = 0;
				return;
			}
			spawn();
			return;
		}
		match = found;
		chain++;
		flashTick = 0;
	}

	private void flash() {
		flashTick++;
		if(flashTick<FLASH_DELAY) return;
		score += match.getScore()*chainFactor();
		bottle.clear(match);
		match = null;
		falling = true;
		fallTick = 0;
	}

	private int chainFactor() {
		return CHAIN_FACTORS[Math.min(chain-1, CHAIN_FACTORS.length-1)];
	}

	private void fallSettle() {
		fallTick++;
		if(fallTick<FALL_INTERVAL) return;
		fallTick = 0;
		if(bottle.settleStep()) return;
		falling = false;
		resolve();
	}

	private void advanceLevel() {
		clearedTick++;
		if(clearedTick<LEVEL_DELAY) return;
		level++;
		bottle = new Bottle();
		bottle.placeViruses(level);
		clearedPending = false;
		chain = 0;
		spawn();
	}

	private void spawn() {
		capsule = new Capsule(bottle, next[0], next[1], SPAWN_COL, Bottle.HIDDEN+1);
		next = GColor.randomPair();
		gravityTick = 0;
		lockTick = 0;
		lockResets = 0;
		if(capsule.isBlocked()) over = true;
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
			if(!over && match==null && !falling && !clearedPending) drawCapsule();
			drawFrame();
			drawPanel();
			drawHelp();
			drawMessage();
		}

		private void drawField() {
			fillRect(COLOR_FIELD, p(0, 0), BOARD_W, BOARD_H);
			for(int col=1;col<Bottle.COLS;col++)
				drawLine(COLOR_GRID, p(col*CELL, 0), p(col*CELL, BOARD_H));
			for(int row=1;row<Bottle.ROWS;row++)
				drawLine(COLOR_GRID, p(0, row*CELL), p(BOARD_W, row*CELL));
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), BOARD_W, BOARD_H);
		}

		private void drawStack() {
			for(int row=Bottle.HIDDEN;row<Bottle.TOTAL;row++)
				for(int col=0;col<Bottle.COLS;col++) {
					Cell cell = bottle.at(col, row);
					if(cell==null) continue;
					double x = col*CELL;
					double y = (row-Bottle.HIDDEN)*CELL;
					Color color = cellColor(cell, col, row);
					if(cell.isVirus()) drawVirus(color, x, y, CELL);
					else drawHalf(color, x, y, CELL);
				}
		}

		private Color cellColor(Cell cell, int col, int row) {
			if(match==null || !match.contains(col, row)) return cell.getColor().getColor();
			return (flashTick/FLASH_PERIOD)%2==0 ? COLOR_FLASH : cell.getColor().getColor();
		}

		private void drawCapsule() {
			drawCapsuleCell(capsule.getColorA().getColor(), capsule.getPivotCol(), capsule.getPivotRow());
			drawCapsuleCell(capsule.getColorB().getColor(), capsule.getOtherCol(), capsule.getOtherRow());
		}

		private void drawCapsuleCell(Color color, int col, int row) {
			if(row<Bottle.HIDDEN) return;
			drawHalf(color, col*CELL, (row-Bottle.HIDDEN)*CELL, CELL);
		}

		private void drawHalf(Color color, double x, double y, double size) {
			fillRect(color, p(x+1, y+1), size-2, size-2);
			fillRect(color.brighter(), p(x+1, y+1), size-2, size*0.12);
			fillRect(color.darker(), p(x+1, y+size-1-size*0.12), size-2, size*0.12);
		}

		private void drawVirus(Color color, double x, double y, double size) {
			fillOvalC(color, p(x+size*0.5, y+size*0.5), size-4, size-4);
			fillOvalC(Color.WHITE, p(x+size*0.35, y+size*0.42), size*0.22, size*0.22);
			fillOvalC(Color.WHITE, p(x+size*0.65, y+size*0.42), size*0.22, size*0.22);
			fillOvalC(Color.BLACK, p(x+size*0.35, y+size*0.44), size*0.1, size*0.1);
			fillOvalC(Color.BLACK, p(x+size*0.65, y+size*0.44), size*0.1, size*0.1);
		}

		private void drawPanel() {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, 12), "SUIVANT");
			fillRect(COLOR_FIELD, p(PANEL_X, 20), PANEL_W, 60);
			drawRect(COLOR_BORDER, p(PANEL_X, 20), PANEL_W, 60);
			drawPreview(next[0], next[1], PANEL_X + PANEL_W*0.5, 50);

			drawValue("SCORE", ""+score, 118);
			drawValue("NIVEAU", ""+level, 180);
			drawValue("VIRUS", ""+bottle.virusCount(), 242);
			if(match!=null) drawValue("CHAINE", "x"+chainFactor(), 304);
		}

		private void drawValue(String label, String value, double y) {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, y), label);
			drawString(COLOR_TEXT, fontBold(22), p(PANEL_X, y+28), value);
		}

		private void drawPreview(GColor a, GColor b, double cx, double cy) {
			double x0 = cx - PREVIEW_CELL;
			drawHalf(a.getColor(), x0, cy-PREVIEW_CELL*0.5, PREVIEW_CELL);
			drawHalf(b.getColor(), x0+PREVIEW_CELL, cy-PREVIEW_CELL*0.5, PREVIEW_CELL);
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+16),
					"Fleches deplacer   BAS chute rapide   HAUT/X rotation horaire");
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+29),
					"Z anti-horaire   P pause   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!over && !paused && !clearedPending) return;
			fillRect(COLOR_VEIL, p(0, 0), BOARD_W, BOARD_H);
			if(paused) {
				drawStringC(COLOR_TEXT, fontBold(28), p(BOARD_W/2, BOARD_H/2), "PAUSE");
				return;
			}
			if(clearedPending) {
				drawStringC(COLOR_TEXT, fontBold(22), p(BOARD_W/2, BOARD_H/2-16), "NIVEAU "+level+" TERMINE");
				drawStringC(COLOR_LABEL, fontPlain(13), p(BOARD_W/2, BOARD_H/2+16), "Niveau suivant...");
				return;
			}
			drawStringC(Color.RED, fontBold(28), p(BOARD_W/2, BOARD_H/2-16), "GAME OVER");
			drawStringC(COLOR_TEXT, fontPlain(14), p(BOARD_W/2, BOARD_H/2+20), "F1 pour rejouer");
		}
	}
}
