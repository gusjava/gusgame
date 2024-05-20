package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.FunctionPolynom;


public class GraphPolynom1 extends GraphPolynom0 {
	
	private FunctionPolynom polynom;
	
	public GraphPolynom1(Color color, FunctionPolynom polynom) {
		super(color);
		this.polynom = polynom;
	}
	
	public GraphPolynom1(Color color, double... a) {
		super(color);
		polynom = new FunctionPolynom(a);
	}
	
	public GraphPolynom1(FunctionPolynom polynom) {
		super();
		this.polynom = polynom;
	}
	
	public GraphPolynom1(double... a) {
		super();
		polynom = new FunctionPolynom(a);
	}
	
	/*
	 * POLYNOM
	 */
	
	public FunctionPolynom getPolynom() {
		return polynom;
	}
}
