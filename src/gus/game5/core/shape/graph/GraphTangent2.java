package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphTangent2 extends GraphLine0 {
	
	private G<Function> gParentFunction;
	private GDouble gX;
	
	public GraphTangent2(Color color, G<Function> gParentFunction, GDouble gX) {
		super(color);
		this.gParentFunction = gParentFunction;
		this.gX = gX;
	}
	
	public GraphTangent2(Color color, Function parentFunction, GDouble gX) {
		super(color);
		this.gParentFunction = ()->parentFunction;
		this.gX = gX;
	}
	
	public GraphTangent2(G<Function> gParentFunction, GDouble gX) {
		super();
		this.gParentFunction = gParentFunction;
		this.gX = gX;
	}
	
	public GraphTangent2(Function parentFunction, GDouble gX) {
		super();
		this.gParentFunction = ()->parentFunction;
		this.gX = gX;
	}
	
	/*
	 * PARENT FUNCTION
	 */
	
	public Function getParentFunction() {
		return gParentFunction.g();
	}
	
	/*
	 * POINT
	 */
	
	public Point0 getPoint() {
		Function parentFunction = getParentFunction();
		double x = gX.gDouble();
		
		double y = parentFunction.h(x);
		return new Point1(x,y);
	}
	
	/*
	 * POLYNOM
	 */
	
	public FunctionPolynom getPolynom() {
		Function parentFunction = getParentFunction();
		double x = gX.gDouble();
		
		double y = parentFunction.h(x);
		double slope = parentFunction.getDerived().h(x);
		double y0 = y - slope * x;
		
		return new FunctionPolynom(y0, slope);
	}
}
