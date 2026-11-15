package gus.game5.main.game.p1.c.columns;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GameColumns extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 32;
	public static final int GAP = 12;
	public static final int TOP = 40;
	public static final int BOTTOM = 34;
	public static final int PANEL_W = 150;
	public static final int PREVIEW_CELL = 26;

	public static final int FIELD_W = Field.COLS*CELL;
	public static final int FIELD_H = Field.ROWS*CELL;
	public static final int PANEL_X = FIELD_W + GAP;

	public static final int[] GRAVITY = {
		44,40,36,32,28,25,22,19,16,14,12,10,9,8,7,6,5,4,3,2};

	public static final int SOFT_INTERVAL = 3;
	public static final int LOCK_DELAY = 18;
	public static final int LOCK_RESETS = 10;
	public static final int DAS_DELAY = 16;
	public static final int DAS_REPEAT = 6;
	public static final int FLASH_DELAY = 30;
	public static final int FLASH_PERIOD = 6;
	public static final int GEMS_PER_LEVEL = 20;
	public static final int SPAWN_COL = 2;

	public static final int[] CHAIN_FACTORS = {1,2,4,8,16,32};
	public static final int SOFT_DROP_POINTS = 1;
	public static final int HARD_DROP_POINTS = 2;

	public static final Color COLOR_FIELD = new Color(16,16,30);
	public static final Color COLOR_GRID = new Color(38,38,60);
	public static final Color COLOR_BORDER = new Color(110,110,150);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_FLASH = new Color(255,255,255);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);


	private Field field;
	private Column column;
	private Gem[] next;
	private Match match;

	private int score;
	private int level;
	private int gems;
	private int chain;

	private int gravityTick;
	private int lockTick;
	private int lockResets;
	private int dasDir;
	private int dasTick;
	private int flashTick;

	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GameColumns main = new GameColumns();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Columns");
		s.setWidth(GAP*3 + FIELD_W + PANEL_W);
		s.setHeight(TOP + FIELD_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(new Color(10,10,18));
	}

	protected void initialize1() {
		field = new Field();

		score = 0;
		level = 0;
		gems = 0;
		chain = 0;
		match = null;
		paused = false;
		over = false;
		dasDir = 0;
		dasTick = 0;

		next = Gem.randomColumn();
		spawn();
		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;
		if(!over && k.in().p()) paused = !paused;
		if(paused || over) return;

		if(match!=null) {
			flash();
			return;
		}

		shift(k);
		if(k.in().up() || k.in().x()) cycle();
		if(k.in().space()) {
			hardDrop();
			return;
		}
		fall(k);
	}

	private void cycle() {
		column.cycle();
		delayLock();
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
		if(column.move(dir, 0)) delayLock();
	}

	private void delayLock() {
		if(!column.isLanded()) {
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

		if(!column.move(0, 1)) {
			countLock();
			return;
		}
		if(soft) score += SOFT_DROP_POINTS;
		lockTick = 0;
		lockResets = 0;
	}

	private void countLock() {
		if(!column.isLanded()) return;
		lockTick++;
		if(lockTick>=LOCK_DELAY) lockColumn();
	}

	private void hardDrop() {
		int distance = column.dropDistance();
		column.move(0, distance);
		score += distance*HARD_DROP_POINTS;
		lockColumn();
	}

	private void lockColumn() {
		column.lock();
		chain = 0;
		resolve();
	}

	private void resolve() {
		Match found = field.findMatch();
		if(found.isEmpty()) {
			match = null;
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
		gems += match.getCount();
		level = gems/GEMS_PER_LEVEL;
		field.clear(match);
		field.collapse();
		resolve();
	}

	private int chainFactor() {
		return CHAIN_FACTORS[Math.min(chain-1, CHAIN_FACTORS.length-1)];
	}

	private void spawn() {
		column = new Column(field, next, SPAWN_COL);
		next = Gem.randomColumn();
		gravityTick = 0;
		lockTick = 0;
		lockResets = 0;
		if(column.isBlocked()) over = true;
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
			if(match==null && !over) drawColumn();
			drawFrame();
			drawPanel();
			drawHelp();
			drawMessage();
		}

		private void drawField() {
			fillRect(COLOR_FIELD, p(0, 0), FIELD_W, FIELD_H);
			for(int col=1;col<Field.COLS;col++)
				drawLine(COLOR_GRID, p(col*CELL, 0), p(col*CELL, FIELD_H));
			for(int row=1;row<Field.ROWS;row++)
				drawLine(COLOR_GRID, p(0, row*CELL), p(FIELD_W, row*CELL));
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), FIELD_W, FIELD_H);
		}

		private void drawStack() {
			for(int row=0;row<Field.ROWS;row++)
				for(int col=0;col<Field.COLS;col++) {
					Gem gem = field.at(col, row);
					if(gem==null) continue;
					drawGem(gemColor(gem, col, row), col*CELL, row*CELL, CELL);
				}
		}

		private Color gemColor(Gem gem, int col, int row) {
			if(match==null || !match.contains(col, row)) return gem.getColor();
			return (flashTick/FLASH_PERIOD)%2==0 ? COLOR_FLASH : gem.getColor();
		}

		private void drawColumn() {
			for(int i=0;i<Column.SIZE;i++) {
				int row = column.getRow()+i;
				if(row<0) continue;
				drawGem(column.gemAt(i).getColor(), column.getCol()*CELL, row*CELL, CELL);
			}
		}

		private void drawGem(Color color, double x, double y, double size) {
			fillRect(color, p(x+1, y+1), size-2, size-2);
			fillRect(color.brighter(), p(x+1, y+1), size-2, size*0.12);
			fillRect(color.darker(), p(x+1, y+size-1-size*0.12), size-2, size*0.12);
			fillPoly(color.brighter(),
					p(x+size*0.5, y+size*0.2),
					p(x+size*0.8, y+size*0.5),
					p(x+size*0.5, y+size*0.8),
					p(x+size*0.2, y+size*0.5));
			fillPoly(color.darker(),
					p(x+size*0.5, y+size*0.5),
					p(x+size*0.8, y+size*0.5),
					p(x+size*0.5, y+size*0.8));
		}

		private void drawPanel() {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, 12), "SUIVANT");
			fillRect(COLOR_FIELD, p(PANEL_X, 20), PANEL_W, 94);
			drawRect(COLOR_BORDER, p(PANEL_X, 20), PANEL_W, 94);
			double x = PANEL_X + (PANEL_W - PREVIEW_CELL)*0.5;
			for(int i=0;i<Column.SIZE;i++)
				drawGem(next[i].getColor(), x, 28+i*PREVIEW_CELL, PREVIEW_CELL);

			drawValue("SCORE", ""+score, 148);
			drawValue("NIVEAU", ""+level, 210);
			drawValue("GEMMES", ""+gems, 272);
			if(match!=null) drawValue("CHAINE", "x"+chainFactor(), 334);
		}

		private void drawValue(String label, String value, double y) {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, y), label);
			drawString(COLOR_TEXT, fontBold(22), p(PANEL_X, y+28), value);
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, FIELD_H+16),
					"Fleches deplacer   BAS accelerer   HAUT/X permuter");
			drawString(COLOR_LABEL, fontPlain(11), p(0, FIELD_H+29),
					"ESPACE chute   P pause   F1 rejouer   F2 quitter");
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
