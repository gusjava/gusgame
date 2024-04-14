package gus.game5.core.point.point0;

import gus.game5.core.features.g.GDouble;

public class Point0Dxy extends Point0 {
	
	private GDouble gX;
	private GDouble gY;
	
	public Point0Dxy(GDouble gX, GDouble gY) {
		this.gX = gX;
		this.gY = gY;
	}
	
	public Point0Dxy(double x, double y) {
		this.gX = ()->x;
		this.gY = ()->y;
	}

	public double getX() {
		return gX.gDouble();
	}

	public double getY() {
		return gY.gDouble();
	}
}
