package gus.game5.core.point.point0;

import gus.game5.core.angle.Angle;
import gus.game5.core.features.g.GAngle;
import gus.game5.core.features.g.GDouble;

public class Point0Dda extends Point0 {
	
	private GDouble gDist;
	private GAngle gAngle;
	
	public Point0Dda(GDouble gDist, GAngle gAngle) {
		this.gDist = gDist;
		this.gAngle = gAngle;
	}
	
	public Point0Dda(double dist, GAngle gAngle) {
		this.gDist = ()->dist;
		this.gAngle = gAngle;
	}
	
	public Point0Dda(GDouble gDist, Angle angle) {
		this.gDist = gDist;
		this.gAngle = ()->angle;
	}
	
	public Angle angle() {
		return gAngle.g();
	}
	
	public double dist() {
		return gDist.gDouble();
	}

	public double getX() {
		return dist()*angle().cos();
	}

	public double getY() {
		return dist()*angle().sin();
	}
}
