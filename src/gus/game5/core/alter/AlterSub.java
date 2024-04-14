package gus.game5.core.alter;

import gus.game5.core.point.point0.Point0;

public class AlterSub implements Alter {
	
	private Point0 point;
	
	public AlterSub(Point0 point) {
		this.point = point;
	}

	public Point0 alterPoint(Point0 p) {
		if(p==null) return null;
		return p.pSub(point);
	}
	
	public double alterDistance(double dist) {
		return dist;
	}

	public Point0 revPoint(Point0 p) {
		if(p==null) return null;
		return p.pAdd(point);
	}

	public double revDistance(double dist) {
		return dist;
	}
}
