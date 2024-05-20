package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;

public class GraphFunction1 extends GraphFunction {
	
	private Function function;
	
	public GraphFunction1(Color color, Function function) {
		super(color);
		this.function = function;
	}
	
	public GraphFunction1(Function function) {
		super();
		this.function = function;
	}

	/*
	 * FUNCTION
	 */
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
}
