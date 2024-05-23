package gus.game5.core.point.point2;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.point.point0.Point0;

public class Point2D0 extends Point2 {

	private GPoint0 gPoint;
	
	public Point2D0(GPoint0 gPoint) {
		this.gPoint = gPoint;
	}
	
	public Point2D0(Point0 point) {
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
	}
	
	public void setY(double y) {
	}
}
