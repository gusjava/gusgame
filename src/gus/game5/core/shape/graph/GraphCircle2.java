package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.point.point0.Point0;

public class GraphCircle2 extends GraphCircle0 {
	
	private G<Point0> gPoint;
	private GDouble gRadius;
	
	public GraphCircle2(Color color, G<Point0> gPoint, GDouble gRadius) {
		super(color);
		this.gPoint = gPoint;
		this.gRadius = gRadius;
	}
	
	public GraphCircle2(G<Point0> gPoint, GDouble gRadius) {
		super();
		this.gPoint = gPoint;
		this.gRadius = gRadius;
	}
	
	public Point0 getPoint() {
		return gPoint.g();
	}
	public double getRadius() {
		return gRadius.gDouble();
	}
}
