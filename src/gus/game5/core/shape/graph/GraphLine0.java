package gus.game5.core.shape.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.function.Function;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public abstract class GraphLine0 extends GraphFunction {
	
	public GraphLine0(Color color) {
		super(color);
	}
	
	public GraphLine0() {
		super();
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		FunctionPolynom polynom = getPolynom();
		double[] aa = polynom.getCoef();
		if(aa.length!=2) throw new RuntimeException("Invalid polynom degree for straight line: "+aa.length);
		double a1 = aa[1];
		double a0 = aa[0];
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		double yForXMin = a1 * xMin + a0;
		double yForXMax = a1 * xMax + a0;
		
		double xForYMin = (yMin - a0) / a1;
		double xForYMax = (yMax - a0) / a1;
		
		List<Point0> pp = new ArrayList<>();
		
		if(yForXMin >= yMin && yForXMin < yMax) {
			pp.add(new Point1(xMin, yForXMin));
		}
		if(yForXMax >= yMin && yForXMax < yMax) {
			pp.add(new Point1(xMax, yForXMax));
		}
		
		if(xForYMin > xMin && xForYMin <= xMax) {
			pp.add(new Point1(xForYMin, yMin));
		}
		if(xForYMax > xMin && xForYMax <= xMax) {
			pp.add(new Point1(xForYMax, yMax));
		}
		
		if(pp.size()==2) {
			Point0 p1 = graph.pMult(pp.get(0));
			Point0 p2 = graph.pMult(pp.get(1));
			graph.drawLine(c, p1, p2);
		}
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

	public double getA0() {
		return getPolynom().coefAt(0);
	}

	public double getA1() {
		return getPolynom().coefAt(1);
	}
}
