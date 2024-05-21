package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point0.Point0;

public class GraphSegment2 extends GraphSegment0 {

	private G<Point0> gPoint1;
	private G<Point0> gPoint2;
	
	public GraphSegment2(Color color, G<Point0> gPoint1, G<Point0> gPoint2) {
		super(color);
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
	}
	
	public GraphSegment2(G<Point0> gPoint1, G<Point0> gPoint2) {
		super();
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
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
}
