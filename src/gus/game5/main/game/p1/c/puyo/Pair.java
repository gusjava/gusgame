package gus.game5.main.game.p1.c.puyo;

public class Pair {

	public static final int[] DX = {0,1,0,-1};
	public static final int[] DY = {-1,0,1,0};

	private Board board;
	private Puyo pivot;
	private Puyo child;
	private int col;
	private int row;
	private int orientation;

	public Pair(Board board, Puyo[] puyos, int col, int row) {
		this.board = board;
		this.pivot = puyos[0];
		this.child = puyos[1];
		this.col = col;
		this.row = row;
		this.orientation = 0;
	}

	public Puyo getPivot() {
		return pivot;
	}

	public Puyo getChild() {
		return child;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getChildCol() {
		return col+DX[orientation];
	}

	public int getChildRow() {
		return row+DY[orientation];
	}

	public boolean fits(int col, int row, int orientation) {
		if(!board.isFree(col, row)) return false;
		return board.isFree(col+DX[orientation], row+DY[orientation]);
	}

	public boolean isBlocked() {
		return !fits(col, row, orientation);
	}

	public boolean isLanded() {
		return !fits(col, row+1, orientation);
	}

	public int dropDistance() {
		int distance = 0;
		while(fits(col, row+distance+1, orientation)) distance++;
		return distance;
	}

	public boolean move(int dx, int dy) {
		if(!fits(col+dx, row+dy, orientation)) return false;
		col += dx;
		row += dy;
		return true;
	}

	public boolean rotate(int way) {
		int target = (orientation+way+4)%4;
		if(fits(col, row, target)) {
			orientation = target;
			return true;
		}
		int kick = -DX[target];
		if(kick!=0 && fits(col+kick, row, target)) {
			col += kick;
			orientation = target;
			return true;
		}
		if(DY[target]>0 && row-1>=0 && fits(col, row-1, target)) {
			row -= 1;
			orientation = target;
			return true;
		}
		return false;
	}

	public void lock() {
		board.put(col, row, pivot);
		board.put(getChildCol(), getChildRow(), child);
	}
}
