package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.FunctionPolynom;

public class GraphPolynom1 extends GraphPolynom0 {
	
	private FunctionPolynom function;
	
	public GraphPolynom1(Color color, String name, FunctionPolynom function) {
		super(color, name);
		this.function = function;
	}
	
	public GraphPolynom1(Color color, String name, double... a) {
		super(color, name);
		function = new FunctionPolynom(a);
	}
	
	public GraphPolynom1(String name, double... a) {
		super(name);
		function = new FunctionPolynom(a);
	}
	
	public GraphPolynom1(double... a) {
		super();
		function = new FunctionPolynom(a);
	}
	
	/*
	 * FUNCTION
	 */
	
	public FunctionPolynom polynom() {
		return function;
	}
}
