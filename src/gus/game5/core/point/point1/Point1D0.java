package gus.game5.core.point.point1;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.point.point0.Point0;

public class Point1D0 extends Point1 {

	private GPoint0 gPoint;
	
	public Point1D0(GPoint0 gPoint) {
		this.gPoint = gPoint;
	}
	
	public Point1D0(Point0 point) {
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
