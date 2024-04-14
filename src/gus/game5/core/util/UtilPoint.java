package gus.game5.core.util;

import java.util.List;

import gus.game5.core.point.point0.Point0;

public class UtilPoint {
	
	/*
	 * SUM X
	 */

	public static double sumX(Point0... points) {
		double x = 0;
		for(Point0 point : points)
			x += point.getX();
		return x;
	}
	
	public static double sumX(List<Point0> points) {
		double x = 0;
		for(Point0 point : points)
			x += point.getX();
		return x;
	}
	
	/*
	 * SUM Y
	 */
	
	public static double sumY(Point0... points) {
		double y = 0;
		for(Point0 point : points)
			y += point.getY();
		return y;
	}
	
	public static double sumY(List<Point0> points) {
		double y = 0;
		for(Point0 point : points)
			y += point.getY();
		return y;
	}
	
	/*
	 * AVG X
	 */
	
	public static double avgX(Point0... points) {
		return sumX(points) / points.length;
	}
	
	public static double avgX(List<Point0> points) {
		return sumX(points) / points.size();
	}
	
	/*
	 * AVG Y
	 */
	
	public static double avgY(Point0... points) {
		return sumY(points) / points.length;
	}
	
	public static double avgY(List<Point0> points) {
		return sumY(points) / points.size();
	}
}
