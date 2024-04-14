package gus.game5.core.point.point0;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilPoint;

public class Point0Avg extends Point0 {
	
	private List<Point0> points;
	
	public Point0Avg(List<Point0> points) {
		this.points = points;
	}
	
	public Point0Avg(Point0... points) {
		this.points = UtilList.asList(points);
	}

	public double getX() {
		return UtilPoint.avgX(points);
	}

	public double getY() {
		return UtilPoint.avgY(points);
	}

}
