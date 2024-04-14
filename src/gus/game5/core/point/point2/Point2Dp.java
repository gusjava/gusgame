package gus.game5.core.point.point2;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Point2Dp extends Point2 {

	private Point1 point1;
	private G<Point0> gPoint;
	
	public Point2Dp(Point1 point1, G<Point0> gPoint) {
		this.point1 = point1;
		this.gPoint = gPoint;
	}
	
	public Point2Dp(double x, double y, G<Point0> gPoint) {
		this(new Point1(x,y), gPoint);
	}
	
	/*
	 * DIFF
	 */
	
	public double dx() {
		return gPoint.g().getX();
	}
	
	public double dy() {
		return gPoint.g().getY();
	}
	
	/*
	 * GET X Y
	 */
	
	public double getX() {
		return dx() + point1.getX();
	}
	
	public double getY() {
		return dy() + point1.getY();
	}
	
	/*
	 * SET X Y
	 */
	
	public void setX(double x) {
		point1.setX(x - dx());
	}
	
	public void setY(double y) {
		point1.setY(y - dy());
	}
}
