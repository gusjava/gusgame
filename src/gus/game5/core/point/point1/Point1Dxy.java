package gus.game5.core.point.point1;

import gus.game5.core.features.g.GDouble;

public class Point1Dxy extends Point1 {

	private Point1 point1;
	private GDouble gX;
	private GDouble gY;
	
	public Point1Dxy(Point1 point1, GDouble gX, GDouble gY) {
		this.point1 = point1;
		this.gX = gX;
		this.gY = gY;
	}
	
	public Point1Dxy(Point1 point1, double dx, double dy) {
		this(point1, ()->dx, ()->dy);
	}
	
	public Point1Dxy(Point1 point1, GDouble gX) {
		this(point1, gX, gX);
	}
	
	public Point1Dxy(Point1 point1, double dx) {
		this(point1, ()->dx);
	}
	
	/*
	 * DIFF
	 */
	
	public double dx() {
		return gX.gDouble();
	}
	
	public double dy() {
		return gY.gDouble();
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
