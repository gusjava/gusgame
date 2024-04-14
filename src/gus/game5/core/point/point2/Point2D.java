package gus.game5.core.point.point2;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point1.Point1;

public class Point2D extends Point2 {

	private G<Point1> gPoint;
	
	public Point2D(G<Point1> gPoint) {
		this.gPoint = gPoint;
	}
	
	public Point2D(Point1 point) {
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
