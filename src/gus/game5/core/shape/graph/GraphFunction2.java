package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.function.Function;

public class GraphFunction2 extends GraphFunction {
	
	private G<Function> gFunction;
	
	public GraphFunction2(Color color, G<Function> gFunction) {
		super(color);
		this.gFunction = gFunction;
	}
	
	public GraphFunction2(G<Function> gFunction) {
		super();
		this.gFunction = gFunction;
	}

	/*
	 * FUNCTION
	 */
	
	public Function getFunction() {
		return gFunction.g();
	}
	
	public void setFunction(Function function) {
		gFunction = ()->function;
	}
	
	public void setFunction(G<Function> gFunction) {
		this.gFunction = gFunction;
	}
}
