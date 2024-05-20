package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.Function;
import gus.game5.core.point.point0.Point0;

public abstract class GraphFunction extends GraphObject {
	
	public GraphFunction() {
		super();
	}
	
	public GraphFunction(Color color, String name) {
		super(color, name);
	}
	
	public GraphFunction(String name) {
		super(name);
	}
	
	private double dx = 0.1;
	
	public void setDx(double dx) {
		this.dx = dx;
	}
	
	public double getDx() {
		return dx;
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Function h = function();
		
		double xMin = graph.getXMin();
		double xMax = graph.getXMax();
		double yMin = graph.getYMin();
		double yMax = graph.getYMax();
		
		double x0 = xMin;
		double y0 = h.h(xMin);
		
		for(double x=xMin; x<=xMax; x+=dx) {
			double y = h.h(x);
			
			if(y>=yMin && y<=yMax && y0>=yMin && y0<=yMax) {
				Point0 p0 = graph.pMult(x0, y0);
				Point0 p = graph.pMult(x, y);
				graph.drawLine(c, p0, p);
			}
			
			x0 = x;
			y0 = y;
		}
	}

	/*
	 * FUNCTION
	 */
	
	public abstract Function function();
}
