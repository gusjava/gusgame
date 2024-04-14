package gus.game5.core.point.point0;

import java.util.List;

import gus.game5.core.features.g.G;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilPoint;

public class Point0DSum extends Point0 {

	private List<G<Point0>> gPoints;
	
	public Point0DSum(List<G<Point0>> gPoints) {
		this.gPoints = gPoints;
	}
	
	@SuppressWarnings("unchecked")
	public Point0DSum(G<Point0>... gPoints) {
		this.gPoints = UtilList.asList(gPoints);
	}

	public double getX() {
		List<Point0> points = UtilList.collect(gPoints, g->g.g());
		return UtilPoint.sumX(points);
	}

	public double getY() {
		List<Point0> points = UtilList.collect(gPoints, g->g.g());
		return UtilPoint.sumY(points);
	}
}
