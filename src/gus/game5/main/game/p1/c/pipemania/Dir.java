package gus.game5.main.game.p1.c.pipemania;

public enum Dir {
	N(0,-1), E(1,0), S(0,1), W(-1,0);

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
			case N: return S;
			case S: return N;
			case E: return W;
			default: return E;
		}
	}
}
