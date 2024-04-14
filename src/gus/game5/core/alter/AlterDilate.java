package gus.game5.core.alter;

import gus.game5.core.features.g.GDouble;
import gus.game5.core.point.point0.Point0;

public class AlterDilate implements Alter {
	
	private Point0 origin;
	private GDouble factor;
	
	public AlterDilate(GDouble factor) {
		this.factor = factor;
	}
	
	public AlterDilate(Point0 origin, GDouble factor) {
		this.origin = origin;
		this.factor = factor;
	}

	public Point0 alterPoint(Point0 p) {
		if(p==null) return null;
		return p.pDilate(origin, factor.gDouble());
	}
	
	public double alterDistance(double dist) {
		return dist * factor.gDouble();
	}

	public Point0 revPoint(Point0 p) {
		if(p==null) return null;
		return p.pDilate(origin, 1/factor.gDouble());
	}

	public double revDistance(double dist) {
		return dist / factor.gDouble();
	}
}
