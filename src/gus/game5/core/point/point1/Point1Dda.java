package gus.game5.core.point.point1;

import gus.game5.core.angle.Angle;
import gus.game5.core.features.g.GAngle;
import gus.game5.core.features.g.GDouble;

public class Point1Dda extends Point1 {

	private Point1 point1;
	private GDouble gDist;
	private GAngle gAngle;
	
	public Point1Dda(Point1 point1, GDouble gDist, GAngle gAngle) {
		this.point1 = point1;
		this.gDist = gDist;
		this.gAngle = gAngle;
	}
	
	public Point1Dda(Point1 point1, double dist, Angle angle) {
		this(point1, ()->dist, ()->angle);
	}
	
	/*
	 * DIFF
	 */
	
	public double dx() {
		return gDist.gDouble()*gAngle.g().cos();
	}
	
	public double dy() {
		return gDist.gDouble()*gAngle.g().sin();
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
