package gus.game5.main.game.p1.c.sokoban;

public enum Dir {
	NORTH(0,-1), SOUTH(0,1), WEST(-1,0), EAST(1,0);

	private final int dx;
	private final int dy;

	private Dir(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}
}
