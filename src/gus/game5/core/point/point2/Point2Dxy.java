package gus.game5.core.point.point2;

import gus.game5.core.features.g.GDouble;
import gus.game5.core.point.point1.Point1;

public class Point2Dxy extends Point2 {

	private Point1 point1;
	private GDouble gX;
	private GDouble gY;
	
	public Point2Dxy(GDouble gX, GDouble gY) {
		this.point1 = new Point1();
		this.gX = gX;
		this.gY = gY;
	}
	
	public Point2Dxy(Point1 point1, GDouble gX, GDouble gY) {
		this.point1 = point1;
		this.gX = gX;
		this.gY = gY;
	}
	
	public Point2Dxy(Point1 point1, GDouble gX) {
		this(point1, gX, gX);
	}
	
	public Point2Dxy(double x, double y, GDouble gX, GDouble gY) {
		this(new Point1(x,y), gX, gY);
	}
	
	public Point2Dxy(double x, double y, GDouble gX) {
		this(new Point1(x,y), gX);
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
