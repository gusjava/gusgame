package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphTangent1 extends GraphLine0 {
	
	private Function parentFunction;
	private Point0 point;
	private FunctionPolynom polynom;
	
	public GraphTangent1(Color color, Function parentFunction, double x) {
		super(color);
		this.parentFunction = parentFunction;
		double y = parentFunction.h(x);
		double slope = parentFunction.getDerived().h(x);
		double y0 = y - slope * x;
		
		polynom = new FunctionPolynom(y0, slope);
		point = new Point1(x, y);
	}
	
	public GraphTangent1(Function parentFunction, double x) {
		super();
		this.parentFunction = parentFunction;
		double y = parentFunction.h(x);
		double slope = parentFunction.getDerived().h(x);
		double y0 = y - slope * x;
		
		polynom = new FunctionPolynom(y0, slope);
		point = new Point1(x, y);
	}
	
	/*
	 * PARENT FUNCTION
	 */
	
	public Function getParentFunction() {
		return parentFunction;
	}
	
	/*
	 * POINT
	 */
	
	public Point0 getPoint() {
		return point;
	}
	
	/*
	 * POLYNOM
	 */
	
	public FunctionPolynom getPolynom() {
		return polynom;
	}
}
