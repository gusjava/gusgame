package gus.game5.core.point.point0;

import java.util.List;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilPoint;

public class Point0DAvg extends Point0 {

	private List<GPoint0> gPoints;
	
	public Point0DAvg(List<GPoint0> gPoints) {
		this.gPoints = gPoints;
	}
	
	public Point0DAvg(GPoint0... gPoints) {
		this.gPoints = UtilList.asList(gPoints);
	}

	public double getX() {
		List<Point0> points = UtilList.collect(gPoints, g->g.g());
		return UtilPoint.avgX(points);
	}

	public double getY() {
		List<Point0> points = UtilList.collect(gPoints, g->g.g());
		return UtilPoint.avgY(points);
	}
}
