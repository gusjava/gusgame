package gus.game5.core.util;

import gus.game5.core.point.point0.Point0;

public class UtilDistance {
	
	/*
	 * DIST
	 */
	
	public static double dist(Point0 p1, Point0 p2) {
		return dist(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double dist(double[] p1, double[] p2) {
		return dist(p1[0], p1[1], p2[0], p2[1]);
	}

	public static double dist(double x1, double y1, double x2, double y2) {
		return dist(x2-x1, y2-y1);
	}
	
	public static double dist(double x, double y) {
		return Math.sqrt(x*x + y*y);
	}
	
	/*
	 * 
	 */
	
	public static int dist(int[] p1, int[] p2) {
		return dist(p1[0], p1[1], p2[0], p2[1]);
	}
	
	public static int dist(int x1, int y1, int x2, int y2) {
		return dist(x1-x2, y1-y2);
	}
	
	public static int dist(int x, int y) {
		return Math.abs(x) + Math.abs(y);
	}
	
	/*
	 * DIST 2
	 */
	
	public static double dist2(Point0 p1, Point0 p2) {
		return dist2(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double dist2(double x1, double y1, double x2, double y2) {
		return dist2(x2-x1, y2-y1);
	}
	
	public static double dist2(double x, double y) {
		return x*x + y*y;
	}
}
