package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point0.Point0;

public class GraphPoint2 extends GraphPoint0 {
	
	private G<Point0> gPoint;
	
	public GraphPoint2(Color color, G<Point0> gPoint) {
		super(color);
		this.gPoint = gPoint;
	}
	
	public GraphPoint2(G<Point0> gPoint) {
		super();
		this.gPoint = gPoint;
	}
	
	/*
	 * POINT
	 */
	
	public Point0 getPoint() {
		return gPoint.g();
	}
}
