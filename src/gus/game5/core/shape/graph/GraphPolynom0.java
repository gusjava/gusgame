package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionPolynom;

public abstract class GraphPolynom0 extends GraphFunction {
	
	public GraphPolynom0(Color color) {
		super(color);
	}
	
	public GraphPolynom0() {
		super();
	}
	
	/*
	 * FUNCTION
	 */
	
	public Function getFunction() {
		return getPolynom();
	}
	
	/*
	 * POLYNOM
	 */
	
	public abstract FunctionPolynom getPolynom();

	public double[] getCoef() {
		return getPolynom().getCoef();
	}

	public double coefAt(int i) {
		return getPolynom().coefAt(i);
	}
	
	public int getDegree() {
		return getCoef().length;
	}
}
