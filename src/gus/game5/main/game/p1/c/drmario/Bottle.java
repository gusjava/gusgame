package gus.game5.main.game.p1.c.drmario;

import java.util.Random;

public class Bottle {

	public static final int COLS = 8;
	public static final int ROWS = 16;
	public static final int HIDDEN = 2;
	public static final int TOTAL = ROWS + HIDDEN;

	public static final int MIN_RUN = 4;
	private static final int[][] DIRECTIONS = {{1,0},{0,1}};

	private static final Random RANDOM = new Random();

	private Cell[][] cells = new Cell[TOTAL][COLS];

	public Cell at(int col, int row) {
		return cells[row][col];
	}

	public boolean isFree(int col, int row) {
		if(col<0 || col>=COLS || row>=TOTAL) return false;
		if(row<0) return true;
		return cells[row][col]==null;
	}

	public void lockCapsule(Capsule capsule) {
		put(capsule.getPivotCol(), capsule.getPivotRow(), capsule.getColorA(), capsule.pivotLink());
		put(capsule.getOtherCol(), capsule.getOtherRow(), capsule.getColorB(), capsule.otherLink());
	}

	private void put(int col, int row, GColor color, Link link) {
		if(col<0 || col>=COLS || row<0 || row>=TOTAL) return;
		cells[row][col] = new Cell(false, color, link);
	}

	public void placeViruses(int level) {
		int lowerStart = HIDDEN + ROWS/2;
		int capacity = (TOTAL-lowerStart)*COLS*3/4;
		int count = Math.min(4 + 4*level, capacity);
		int placed = 0;
		int guard = count*200;
		while(placed<count && guard-->0) {
			int col = RANDOM.nextInt(COLS);
			int row = lowerStart + RANDOM.nextInt(TOTAL-lowerStart);
			if(cells[row][col]!=null) continue;
			GColor color = GColor.random();
			if(wouldExtendRun(col, row, color)) continue;
			cells[row][col] = new Cell(true, color, Link.NONE);
			placed++;
		}
	}

	private boolean wouldExtendRun(int col, int row, GColor color) {
		for(int[] d : DIRECTIONS) {
			int total = 1 + runLength(col, row, d[0], d[1], color) + runLength(col, row, -d[0], -d[1], color);
			if(total>=MIN_RUN) return true;
		}
		return false;
	}

	private int runLength(int col, int row, int dx, int dy, GColor color) {
		int length = 0;
		int c = col+dx;
		int r = row+dy;
		while(holds(c, r, color)) {
			length++;
			c += dx;
			r += dy;
		}
		return length;
	}

	public int virusCount() {
		int count = 0;
		for(int row=0;row<TOTAL;row++)
			for(int col=0;col<COLS;col++)
				if(cells[row][col]!=null && cells[row][col].isVirus()) count++;
		return count;
	}

	public Match findMatch() {
		Match match = new Match();
		for(int[] d : DIRECTIONS)
			for(int row=0;row<TOTAL;row++)
				for(int col=0;col<COLS;col++)
					scan(match, col, row, d[0], d[1]);
		return match;
	}

	private void scan(Match match, int col, int row, int dx, int dy) {
		Cell cell = cells[row][col];
		if(cell==null) return;
		GColor color = cell.getColor();
		if(holds(col-dx, row-dy, color)) return;
		int length = 1;
		while(holds(col+dx*length, row+dy*length, color)) length++;
		if(length<MIN_RUN) return;
		for(int i=0;i<length;i++) match.add(col+dx*i, row+dy*i);
		match.addRun(length);
	}

	private boolean holds(int col, int row, GColor color) {
		if(col<0 || col>=COLS || row<0 || row>=TOTAL) return false;
		Cell cell = cells[row][col];
		return cell!=null && cell.getColor()==color;
	}

	public void clear(Match match) {
		for(int row=0;row<TOTAL;row++)
			for(int col=0;col<COLS;col++) {
				if(!match.contains(col, row)) continue;
				unlinkPartner(col, row);
				cells[row][col] = null;
			}
	}

	private void unlinkPartner(int col, int row) {
		Cell cell = cells[row][col];
		int pc = col;
		int pr = row;
		switch(cell.getLink()) {
			case LEFT: pc--; break;
			case RIGHT: pc++; break;
			case UP: pr--; break;
			case DOWN: pr++; break;
			default: return;
		}
		if(pr<0 || pr>=TOTAL || pc<0 || pc>=COLS) return;
		Cell partner = cells[pr][pc];
		if(partner!=null) partner.setLink(Link.NONE);
	}

	public boolean settleStep() {
		boolean moved = false;
		for(int row=TOTAL-2;row>=0;row--)
			for(int col=0;col<COLS;col++) {
				Cell cell = cells[row][col];
				if(cell==null || cell.isVirus()) continue;
				if(cell.getLink()==Link.NONE) {
					if(isFree(col, row+1)) {
						dropSingle(col, row);
						moved = true;
					}
				} else if(cell.getLink()==Link.RIGHT) {
					if(canDropHorizontal(col, row)) {
						dropHorizontal(col, row);
						moved = true;
					}
				} else if(cell.getLink()==Link.DOWN) {
					if(canDropVertical(col, row)) {
						dropVertical(col, row);
						moved = true;
					}
				}
			}
		return moved;
	}

	private boolean canDropHorizontal(int col, int row) {
		return isFree(col, row+1) && isFree(col+1, row+1);
	}

	private void dropHorizontal(int col, int row) {
		cells[row+1][col] = cells[row][col];
		cells[row][col] = null;
		cells[row+1][col+1] = cells[row][col+1];
		cells[row][col+1] = null;
	}

	private boolean canDropVertical(int col, int row) {
		return isFree(col, row+2);
	}

	private void dropVertical(int col, int row) {
		cells[row+2][col] = cells[row+1][col];
		cells[row+1][col] = cells[row][col];
		cells[row][col] = null;
	}

	private void dropSingle(int col, int row) {
		cells[row+1][col] = cells[row][col];
		cells[row][col] = null;
	}
}
