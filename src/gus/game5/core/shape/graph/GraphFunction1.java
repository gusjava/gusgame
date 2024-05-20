package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;

public abstract class GraphFunction1 extends GraphFunction {
	
	private Function function;
	
	public GraphFunction1(Color color, String name, Function function) {
		super(color, name);
		this.function = function;
	}
	
	public GraphFunction1(String name, Function function) {
		super(name);
		this.function = function;
	}
	
	public GraphFunction1(Function function) {
		super();
		this.function = function;
	}

	/*
	 * FUNCTION
	 */
	
	public Function function() {
		return function;
	}
}
