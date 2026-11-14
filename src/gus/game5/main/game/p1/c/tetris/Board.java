package gus.game5.main.game.p1.c.tetris;

public class Board {

	public static final int COLS = 10;
	public static final int ROWS = 20;
	public static final int HIDDEN = 2;
	public static final int TOTAL = ROWS + HIDDEN;

	private Tetromino[][] cells = new Tetromino[TOTAL][COLS];

	public Tetromino at(int col, int row) {
		return cells[row][col];
	}

	public boolean isFree(int col, int row) {
		if(col<0 || col>=COLS || row>=TOTAL) return false;
		if(row<0) return true;
		return cells[row][col]==null;
	}

	public void lock(Piece piece) {
		for(int[] c : piece.cells()) {
			if(c[1]<0 || c[1]>=TOTAL || c[0]<0 || c[0]>=COLS) continue;
			cells[c[1]][c[0]] = piece.getType();
		}
	}

	public int clearLines() {
		int cleared = 0;
		for(int row=TOTAL-1;row>=0;row--) {
			if(!isFull(row)) continue;
			collapse(row);
			cleared++;
			row++;
		}
		return cleared;
	}

	private boolean isFull(int row) {
		for(int col=0;col<COLS;col++) if(cells[row][col]==null) return false;
		return true;
	}

	private void collapse(int row) {
		for(int r=row;r>0;r--) cells[r] = cells[r-1];
		cells[0] = new Tetromino[COLS];
	}
}
