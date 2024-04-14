package gus.game5.core.point.point1;

import gus.game5.core.features.g.G;

public class Point1D extends Point1 {

	private G<Point1> gPoint;
	
	public Point1D(G<Point1> gPoint) {
		this.gPoint = gPoint;
	}
	
	public Point1D(Point1 point) {
		this(()->point);
	}
	
	/*
	 * GET X Y
	 */
	
	public double getX() {
		return gPoint.g().getX();
	}
	
	public double getY() {
		return gPoint.g().getY();
	}
	
	/*
	 * SET X Y
	 */
	
	public void setX(double x) {
		gPoint.g().setX(x);
	}
	
	public void setY(double y) {
		gPoint.g().setY(y);
	}
}
