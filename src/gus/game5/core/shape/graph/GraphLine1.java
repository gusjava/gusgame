package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.FunctionPolynom;

public class GraphLine1 extends GraphLine0 {
	
	private FunctionPolynom function;
	
	public GraphLine1(Color color, String name, GraphPoint1 p) {
		super(color, name);
		double a1 = p.getPoint().slope();
		double a0 = 0;
		function = new FunctionPolynom(a0, a1);
	}
	
	public GraphLine1(Color color, String name, GraphPoint1 p1, GraphPoint1 p2) {
		super(color, name);
		double a1 = p1.getPoint().pSub(p2.getPoint()).slope();
		double a0 = p1.getPoint().getY() - a1 * p1.getPoint().getX();
		function = new FunctionPolynom(a0, a1);
	}
	
	public GraphLine1(Color color, String name, double a1, double a0) {
		super(color, name);
		function = new FunctionPolynom(a0, a1);
	}
	
	public GraphLine1(String name, double a1, double a0) {
		super(name);
		function = new FunctionPolynom(a0, a1);
	}
	
	public GraphLine1(double a1, double a0) {
		super();
		function = new FunctionPolynom(a0, a1);
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
	
	/*
	 * FUNCTION
	 */
	
	public FunctionPolynom polynom() {
		return function;
	}
}
