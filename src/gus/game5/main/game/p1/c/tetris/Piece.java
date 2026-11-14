package gus.game5.main.game.p1.c.tetris;

public class Piece {

	private Board board;
	private Tetromino type;
	private int rotation;
	private int x;
	private int y;

	public Piece(Board board, Tetromino type) {
		this.board = board;
		this.type = type;
		this.rotation = 0;
		this.x = type.getSpawnX();
		this.y = Board.HIDDEN;
	}

	public Tetromino getType() {
		return type;
	}

	public int[][] cells() {
		return cellsAt(rotation, x, y);
	}

	public int[][] cellsAt(int rotation, int x, int y) {
		int[][] src = type.getCells(rotation);
		int[][] res = new int[src.length][2];
		for(int i=0;i<src.length;i++) {
			res[i][0] = x + src[i][0];
			res[i][1] = y + src[i][1];
		}
		return res;
	}

	public int[][] ghostCells() {
		return cellsAt(rotation, x, y+dropDistance());
	}

	public boolean fits(int rotation, int x, int y) {
		for(int[] c : cellsAt(rotation, x, y))
			if(!board.isFree(c[0], c[1])) return false;
		return true;
	}

	public boolean isBlocked() {
		return !fits(rotation, x, y);
	}

	public boolean isLanded() {
		return !fits(rotation, x, y+1);
	}

	public int dropDistance() {
		int distance = 0;
		while(fits(rotation, x, y+distance+1)) distance++;
		return distance;
	}

	public boolean move(int dx, int dy) {
		if(!fits(rotation, x+dx, y+dy)) return false;
		x += dx;
		y += dy;
		return true;
	}

	public boolean rotate(boolean cw) {
		if(type==Tetromino.O) return false;
		int target = (rotation + (cw ? 1 : 3))%4;
		for(int[] kick : Kick.offsets(type, rotation, cw)) {
			if(!fits(target, x+kick[0], y+kick[1])) continue;
			rotation = target;
			x += kick[0];
			y += kick[1];
			return true;
		}
		return false;
	}
}
