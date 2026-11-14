package gus.game5.main.game.p1.c.tetris;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class GameTetris extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 26;
	public static final int GAP = 12;
	public static final int TOP = 40;
	public static final int BOTTOM = 34;
	public static final int PANEL_W = 150;
	public static final int PREVIEW_CELL = 20;

	public static final int BOARD_W = Board.COLS*CELL;
	public static final int BOARD_H = Board.ROWS*CELL;
	public static final int PANEL_X = BOARD_W + GAP;

	public static final int[] GRAVITY = {
		48,43,38,33,28,23,18,13,8,6,5,5,5,4,4,4,3,3,3,2,2,2,2,2,2,2,2,2,2,1};

	public static final int SOFT_INTERVAL = 2;
	public static final int LOCK_DELAY = 30;
	public static final int LOCK_RESETS = 15;
	public static final int DAS_DELAY = 16;
	public static final int DAS_REPEAT = 6;
	public static final int LINES_PER_LEVEL = 10;

	public static final boolean GHOST_DEFAULT = true;

	public static final int[] LINE_SCORES = {0, 40, 100, 300, 1200};
	public static final int SOFT_DROP_POINTS = 1;
	public static final int HARD_DROP_POINTS = 2;

	public static final Color COLOR_FIELD = new Color(18,18,28);
	public static final Color COLOR_GRID = new Color(40,40,56);
	public static final Color COLOR_BORDER = new Color(110,110,150);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_GHOST = new Color(150,150,170);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);


	private Board board;
	private Bag bag;
	private Piece piece;
	private Tetromino next;

	private int score;
	private int level;
	private int lines;

	private int gravityTick;
	private int lockTick;
	private int lockResets;
	private int dasDir;
	private int dasTick;

	private boolean ghost = GHOST_DEFAULT;
	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GameTetris main = new GameTetris();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Tetris");
		s.setWidth(GAP*3 + BOARD_W + PANEL_W);
		s.setHeight(TOP + BOARD_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(new Color(10,10,16));
	}

	protected void initialize1() {
		board = new Board();
		bag = new Bag();

		score = 0;
		level = 0;
		lines = 0;
		paused = false;
		over = false;
		dasDir = 0;
		dasTick = 0;

		next = bag.next();
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
		if(k.in().g()) ghost = !ghost;
		if(k.in().p()) paused = !paused;
		if(paused || over) return;

		shift(k);
		turnPiece(k);
		if(k.in().space()) hardDrop();
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
		if(piece.move(dir, 0)) delayLock();
	}

	private void turnPiece(Keyboard k) {
		if(k.in().up() || k.in().x()) rotate(true);
		else if(k.in().z()) rotate(false);
	}

	private void rotate(boolean cw) {
		if(piece.rotate(cw)) delayLock();
	}

	private void delayLock() {
		if(!piece.isLanded()) {
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

		if(!piece.move(0, 1)) {
			countLock();
			return;
		}
		if(soft) score += SOFT_DROP_POINTS;
		lockTick = 0;
		lockResets = 0;
	}

	private void countLock() {
		if(!piece.isLanded()) return;
		lockTick++;
		if(lockTick>=LOCK_DELAY) lockPiece();
	}

	private void hardDrop() {
		int distance = piece.dropDistance();
		piece.move(0, distance);
		score += distance*HARD_DROP_POINTS;
		lockPiece();
	}

	private void lockPiece() {
		board.lock(piece);
		int cleared = board.clearLines();
		if(cleared>0) {
			lines += cleared;
			score += LINE_SCORES[cleared]*(level+1);
			level = lines/LINES_PER_LEVEL;
		}
		spawn();
	}

	private void spawn() {
		piece = new Piece(board, next);
		next = bag.next();
		gravityTick = 0;
		lockTick = 0;
		lockResets = 0;
		if(piece.isBlocked()) over = true;
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
			if(!over) {
				if(ghost) drawGhost();
				drawPiece();
			}
			drawFrame();
			drawPanel();
			drawHelp();
			drawMessage();
		}

		private void drawField() {
			fillRect(COLOR_FIELD, p(0, 0), BOARD_W, BOARD_H);
			for(int col=1;col<Board.COLS;col++)
				drawLine(COLOR_GRID, p(col*CELL, 0), p(col*CELL, BOARD_H));
			for(int row=1;row<Board.ROWS;row++)
				drawLine(COLOR_GRID, p(0, row*CELL), p(BOARD_W, row*CELL));
		}

		private void drawFrame() {
			drawRect(COLOR_BORDER, p(0, 0), BOARD_W, BOARD_H);
		}

		private void drawStack() {
			for(int row=Board.HIDDEN;row<Board.TOTAL;row++)
				for(int col=0;col<Board.COLS;col++) {
					Tetromino type = board.at(col, row);
					if(type!=null) drawBlock(type.getColor(), col*CELL, (row-Board.HIDDEN)*CELL, CELL);
				}
		}

		private void drawPiece() {
			for(int[] c : piece.cells()) {
				if(c[1]<Board.HIDDEN) continue;
				drawBlock(piece.getType().getColor(), c[0]*CELL, (c[1]-Board.HIDDEN)*CELL, CELL);
			}
		}

		private void drawGhost() {
			for(int[] c : piece.ghostCells()) {
				if(c[1]<Board.HIDDEN) continue;
				double x = c[0]*CELL;
				double y = (c[1]-Board.HIDDEN)*CELL;
				drawRect(COLOR_GHOST, p(x+2, y+2), CELL-4, CELL-4);
			}
		}

		private void drawBlock(Color color, double x, double y, double size) {
			fillRect(color, p(x+1, y+1), size-2, size-2);
			fillRect(color.brighter(), p(x+1, y+1), size-2, size*0.12);
			fillRect(color.darker(), p(x+1, y+size-1-size*0.12), size-2, size*0.12);
		}

		private void drawPanel() {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, 12), "SUIVANT");
			fillRect(COLOR_FIELD, p(PANEL_X, 20), PANEL_W, 92);
			drawRect(COLOR_BORDER, p(PANEL_X, 20), PANEL_W, 92);
			drawPreview(next, PANEL_X + PANEL_W/2, 66);

			drawValue("SCORE", ""+score, 150);
			drawValue("NIVEAU", ""+level, 212);
			drawValue("LIGNES", ""+lines, 274);
		}

		private void drawValue(String label, String value, double y) {
			drawString(COLOR_LABEL, fontPlain(12), p(PANEL_X, y), label);
			drawString(COLOR_TEXT, fontBold(22), p(PANEL_X, y+28), value);
		}

		private void drawPreview(Tetromino type, double cx, double cy) {
			int[][] cells = type.getCells(0);
			int minCol = cells[0][0];
			int maxCol = cells[0][0];
			int minRow = cells[0][1];
			int maxRow = cells[0][1];
			for(int[] c : cells) {
				minCol = Math.min(minCol, c[0]);
				maxCol = Math.max(maxCol, c[0]);
				minRow = Math.min(minRow, c[1]);
				maxRow = Math.max(maxRow, c[1]);
			}
			double x0 = cx - (maxCol-minCol+1)*PREVIEW_CELL*0.5 - minCol*PREVIEW_CELL;
			double y0 = cy - (maxRow-minRow+1)*PREVIEW_CELL*0.5 - minRow*PREVIEW_CELL;
			for(int[] c : cells)
				drawBlock(type.getColor(), x0+c[0]*PREVIEW_CELL, y0+c[1]*PREVIEW_CELL, PREVIEW_CELL);
		}

		private void drawHelp() {
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+16),
					"Fleches deplacer   HAUT/X rotation   Z anti-horaire   ESPACE chute");
			drawString(COLOR_LABEL, fontPlain(11), p(0, BOARD_H+29),
					"P pause   G reperage "+(ghost ? "ON" : "OFF")+"   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!over && !paused) return;
			fillRect(COLOR_VEIL, p(0, 0), BOARD_W, BOARD_H);
			if(paused) {
				drawStringC(COLOR_TEXT, fontBold(28), p(BOARD_W/2, BOARD_H/2), "PAUSE");
				return;
			}
			drawStringC(Color.RED, fontBold(28), p(BOARD_W/2, BOARD_H/2-16), "GAME OVER");
			drawStringC(COLOR_TEXT, fontPlain(14), p(BOARD_W/2, BOARD_H/2+20), "F1 pour rejouer");
		}
	}
}
