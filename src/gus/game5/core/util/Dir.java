package gus.game5.core.util;

public enum Dir {
	NORTH(0,-1), EAST(1,0), SOUTH(0,1), WEST(-1,0);

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

	public Dir opposite() {
		switch(this) {
			case NORTH: return SOUTH;
			case SOUTH: return NORTH;
			case WEST: return EAST;
			default: return WEST;
		}
	}

	public boolean isNorth() {
		return this==NORTH;
	}

	public boolean isSouth() {
		return this==SOUTH;
	}

	public boolean isEast() {
		return this==EAST;
	}

	public boolean isWest() {
		return this==WEST;
	}
}
