package gus.game5.main.game.p1.c.pipemania;

import gus.game5.core.util.Dir;

public enum PipeShape {
	STRAIGHT_H(Dir.WEST, Dir.EAST),
	STRAIGHT_V(Dir.NORTH, Dir.SOUTH),
	ELBOW_NE(Dir.NORTH, Dir.EAST),
	ELBOW_NW(Dir.NORTH, Dir.WEST),
	ELBOW_SE(Dir.SOUTH, Dir.EAST),
	ELBOW_SW(Dir.SOUTH, Dir.WEST);

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
