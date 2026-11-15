package gus.game5.main.game.p1.c.pipemania;

public enum PipeShape {
	STRAIGHT_H(Dir.W, Dir.E),
	STRAIGHT_V(Dir.N, Dir.S),
	ELBOW_NE(Dir.N, Dir.E),
	ELBOW_NW(Dir.N, Dir.W),
	ELBOW_SE(Dir.S, Dir.E),
	ELBOW_SW(Dir.S, Dir.W);

	private final Dir a;
	private final Dir b;

	private PipeShape(Dir a, Dir b) {
		this.a = a;
		this.b = b;
	}

	public boolean hasOpening(Dir d) {
		return a==d || b==d;
	}

	public Dir otherOpening(Dir d) {
		if(a==d) return b;
		if(b==d) return a;
		return null;
	}

	public Dir getA() {
		return a;
	}

	public Dir getB() {
		return b;
	}
}
