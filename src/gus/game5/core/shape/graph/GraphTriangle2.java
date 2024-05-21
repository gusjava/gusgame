package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point0.Point0;

public class GraphTriangle2 extends GraphTriangle0 {

	private G<Point0> gPoint1;
	private G<Point0> gPoint2;
	private G<Point0> gPoint3;
	
	public GraphTriangle2(Color color, G<Point0> gPoint1, G<Point0> gPoint2, G<Point0> gPoint3) {
		super(color);
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
		this.gPoint3 = gPoint3;
	}
	
	public GraphTriangle2(G<Point0> gPoint1, G<Point0> gPoint2, G<Point0> gPoint3) {
		super();
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
		this.gPoint3 = gPoint3;
	}
	
	/*
	 * POINTS
	 */
	
	public Point0 getPoint1() {
		return gPoint1.g();
	}
	
	public Point0 getPoint2() {
		return gPoint2.g();
	}
	
	public Point0 getPoint3() {
		return gPoint3.g();
	}
}
