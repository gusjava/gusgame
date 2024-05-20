package gus.game5.core.shape.graph;

import java.awt.Color;

public class GraphYLine1 extends GraphYLine0 {
	
	private double x0;
	
	public GraphYLine1(Color color, String name, GraphPoint1 p) {
		super(color, name);
		x0 = p.getX();
	}
	
	public GraphYLine1(Color color, String name, double x0) {
		super(color, name);
		this.x0 = x0;
	}
	
	public GraphYLine1(String name, double x0) {
		super(name);
		this.x0 = x0;
	}
	
	public GraphYLine1(double x0) {
		super();
		this.x0 = x0;
	}
	
	public double getX0() {
		return x0;
	}
}
