package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public class GraphYLine1 extends GraphYLine0 {
	
	private double x0;
	
	public GraphYLine1(Color color, Point0 p) {
		super(color);
		x0 = p.getX();
	}
	
	public GraphYLine1(Color color, double x0) {
		super(color);
		this.x0 = x0;
	}
	
	public GraphYLine1(Point0 p) {
		super();
		x0 = p.getX();
	}
	
	public GraphYLine1(double x0) {
		super();
		this.x0 = x0;
	}
	
	public double getX0() {
		return x0;
	}
}
