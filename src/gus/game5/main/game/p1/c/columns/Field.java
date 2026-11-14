package gus.game5.main.game.p1.c.columns;

public class Field {

	public static final int COLS = 6;
	public static final int ROWS = 13;

	private static final int[][] DIRECTIONS = {{1,0},{0,1},{1,1},{1,-1}};

	private Gem[][] cells = new Gem[ROWS][COLS];

	public Gem at(int col, int row) {
		return cells[row][col];
	}

	public boolean isFree(int col, int row) {
		if(col<0 || col>=COLS || row>=ROWS) return false;
		if(row<0) return true;
		return cells[row][col]==null;
	}

	public void put(int col, int row, Gem gem) {
		if(col<0 || col>=COLS || row<0 || row>=ROWS) return;
		cells[row][col] = gem;
	}

	public Match findMatch() {
		Match match = new Match();
		for(int[] direction : DIRECTIONS)
			for(int row=0;row<ROWS;row++)
				for(int col=0;col<COLS;col++)
					scan(match, col, row, direction[0], direction[1]);
		return match;
	}

	private void scan(Match match, int col, int row, int dx, int dy) {
		Gem gem = cells[row][col];
		if(gem==null) return;
		if(holds(col-dx, row-dy, gem)) return;
		int length = 1;
		while(holds(col+dx*length, row+dy*length, gem)) length++;
		if(length<3) return;
		for(int i=0;i<length;i++) match.add(col+dx*i, row+dy*i);
		match.addRun(length);
	}

	private boolean holds(int col, int row, Gem gem) {
		if(col<0 || col>=COLS || row<0 || row>=ROWS) return false;
		return cells[row][col]==gem;
	}

	public void clear(Match match) {
		for(int row=0;row<ROWS;row++)
			for(int col=0;col<COLS;col++)
				if(match.contains(col, row)) cells[row][col] = null;
	}

	public void collapse() {
		for(int col=0;col<COLS;col++) {
			int target = ROWS-1;
			for(int row=ROWS-1;row>=0;row--) {
				if(cells[row][col]==null) continue;
				if(target!=row) {
					cells[target][col] = cells[row][col];
					cells[row][col] = null;
				}
				target--;
			}
		}
	}
}
