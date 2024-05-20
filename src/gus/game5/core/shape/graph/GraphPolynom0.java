package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionPolynom;

public abstract class GraphPolynom0 extends GraphFunction {
	
	
	public GraphPolynom0(Color color, String name) {
		super(color, name);
	}
	
	public GraphPolynom0(String name) {
		super(name);
	}
	
	public GraphPolynom0() {
		super();
	}
	
	/*
	 * FUNCTION
	 */

	
	public abstract FunctionPolynom polynom();
	
	public Function function() {
		return polynom();
	}

	public double[] getCoef() {
		return polynom().getCoef();
	}

	public double coefAt(int i) {
		return polynom().coefAt(i);
	}
}
