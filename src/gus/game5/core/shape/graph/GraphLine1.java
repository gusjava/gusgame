package gus.game5.core.shape.graph;

import java.awt.Color;

public class GraphLine1 extends GraphLine0 {
	
	private double a;
	private double b;
	
	public GraphLine1(Color color, String name, GraphPoint1 p1, GraphPoint1 p2) {
		super(color, name);
		a = p1.getPoint().pSub(p2.getPoint()).slope();
		b = p1.getPoint().getY() - a * p1.getPoint().getX();
	}
	
	public GraphLine1(Color color, String name, GraphPoint1 p) {
		super(color, name);
		a = p.getPoint().slope();
		b = 0;
	}
	
	public GraphLine1(Color color, String name, double a, double b) {
		super(color, name);
		this.a = a;
		this.b = b;
	}
	
	public GraphLine1(String name, double a, double b) {
		super(name);
		this.a = a;
		this.b = b;
	}
	
	public GraphLine1(double a, double b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	public GraphLine1(Color color, String name, double a) {
		this(color, name, a, 0);
	}
	
	public GraphLine1(String name, double a) {
		this(name, a, 0);
	}
	
	public GraphLine1(double a) {
		this(a, 0);
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}
}
