package gus.game5.main.game.p1.c.drmario;

public class Capsule {

	private static final int[] KICKS = {0, -1, 1};

	private Bottle bottle;
	private GColor colorA;
	private GColor colorB;
	private int orientation;
	private int x;
	private int y;

	public Capsule(Bottle bottle, GColor colorA, GColor colorB, int x, int y) {
		this.bottle = bottle;
		this.colorA = colorA;
		this.colorB = colorB;
		this.orientation = 0;
		this.x = x;
		this.y = y;
	}

	public GColor getColorA() {
		return colorA;
	}

	public GColor getColorB() {
		return colorB;
	}

	private int[] otherOffset(int o) {
		switch(o) {
			case 0: return new int[] {1, 0};
			case 1: return new int[] {0, -1};
			case 2: return new int[] {-1, 0};
			default: return new int[] {0, 1};
		}
	}

	public int getPivotCol() {
		return x;
	}

	public int getPivotRow() {
		return y;
	}

	public int getOtherCol() {
		return x + otherOffset(orientation)[0];
	}

	public int getOtherRow() {
		return y + otherOffset(orientation)[1];
	}

	public Link pivotLink() {
		switch(orientation) {
			case 0: return Link.RIGHT;
			case 1: return Link.UP;
			case 2: return Link.LEFT;
			default: return Link.DOWN;
		}
	}

	public Link otherLink() {
		switch(orientation) {
			case 0: return Link.LEFT;
			case 1: return Link.DOWN;
			case 2: return Link.RIGHT;
			default: return Link.UP;
		}
	}

	public boolean fits(int orientation, int x, int y) {
		int[] off = otherOffset(orientation);
		return bottle.isFree(x, y) && bottle.isFree(x+off[0], y+off[1]);
	}

	public boolean isBlocked() {
		return !fits(orientation, x, y);
	}

	public boolean isLanded() {
		return !fits(orientation, x, y+1);
	}

	public boolean move(int dx, int dy) {
		if(!fits(orientation, x+dx, y+dy)) return false;
		x += dx;
		y += dy;
		return true;
	}

	public boolean rotate(boolean cw) {
		int target = (orientation + (cw ? 1 : 3))%4;
		for(int dx : KICKS) {
			if(!fits(target, x+dx, y)) continue;
			orientation = target;
			x += dx;
			return true;
		}
		return false;
	}
}
