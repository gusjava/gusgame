package gus.game5.core.point.point0;

import gus.game5.core.features.g.G;

public class Point0D extends Point0 {
	
	private G<Point0> gPoint;
	
	public Point0D(G<Point0> gPoint) {
		this.gPoint = gPoint;
	}

	public double getX() {
		return gPoint.g().getX();
	}

	public double getY() {
		return gPoint.g().getY();
	}
}
