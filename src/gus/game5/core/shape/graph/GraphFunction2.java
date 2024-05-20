package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.function.Function;

public abstract class GraphFunction2 extends GraphFunction {
	
	private G<Function> gFunction;
	
	public GraphFunction2(Color color, String name, G<Function> gFunction) {
		super(color, name);
		this.gFunction = gFunction;
	}
	
	public GraphFunction2(String name, G<Function> gFunction) {
		super(name);
		this.gFunction = gFunction;
	}
	
	public GraphFunction2(G<Function> gFunction) {
		super();
		this.gFunction = gFunction;
	}

	/*
	 * FUNCTION
	 */
	
	public Function function() {
		return gFunction.g();
	}
}
