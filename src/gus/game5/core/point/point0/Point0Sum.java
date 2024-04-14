package gus.game5.core.point.point0;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilPoint;

public class Point0Sum extends Point0 {

	private List<Point0> points;
	
	public Point0Sum(List<Point0> points) {
		this.points = points;
	}
	
	public Point0Sum(Point0... points) {
		this.points = UtilList.asList(points);
	}

	public double getX() {
		return UtilPoint.sumX(points);
	}

	public double getY() {
		return UtilPoint.sumY(points);
	}
}
