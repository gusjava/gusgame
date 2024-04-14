package gus.game5.core.alter;

import gus.game5.core.features.g.GAngle;
import gus.game5.core.point.point0.Point0;

public class AlterRotate implements Alter {
	
	private Point0 origin;
	private GAngle gAngle;
	
	public AlterRotate(GAngle gAngle) {
		this.gAngle = gAngle;
	}
	
	public AlterRotate(Point0 origin, GAngle gAngle) {
		this.origin = origin;
		this.gAngle = gAngle;
	}

	public Point0 alterPoint(Point0 p) {
		if(p==null) return null;
		return p.pRotate(origin, gAngle.g());
	}
	
	public double alterDistance(double dist) {
		return dist;
	}

	public Point0 revPoint(Point0 p) {
		if(p==null) return null;
		return p.pRotate(origin, gAngle.g().inv());
	}

	public double revDistance(double dist) {
		return dist;
	}
}
