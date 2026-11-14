package gus.game5.main.game.p1.o.asteroids;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Field {

	public static final double W = 820;
	public static final double H = 640;


	/*
	 * WRAPPING
	 */

	public static void wrap(Point1 p) {
		p.setXY(mod(p.getX(), W), mod(p.getY(), H));
	}

	public static double deltaX(Point0 from, Point0 to) {
		return shortest(to.getX()-from.getX(), W);
	}

	public static double deltaY(Point0 from, Point0 to) {
		return shortest(to.getY()-from.getY(), H);
	}


	/*
	 * DISTANCE
	 */

	public static double dist(Point0 a, Point0 b) {
		double dx = deltaX(a, b);
		double dy = deltaY(a, b);
		return Math.sqrt(dx*dx + dy*dy);
	}

	public static boolean hit(Point0 a, double ra, Point0 b, double rb) {
		double dx = deltaX(a, b);
		double dy = deltaY(a, b);
		double r = ra + rb;
		return dx*dx + dy*dy <= r*r;
	}


	/*
	 * TOOLS
	 */

	private static double mod(double value, double size) {
		double v = value % size;
		return v<0 ? v+size : v;
	}

	private static double shortest(double delta, double size) {
		double d = mod(delta + size/2, size) - size/2;
		return d;
	}
}
