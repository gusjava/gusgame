package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public abstract class GraphYLine0 extends GraphObject {
	
	public abstract double getX0();
	
	public GraphYLine0() {
		super();
	}
	
	public GraphYLine0(Color color) {
		super(color);
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		double x0 = getX0();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		if(x0>=xMin && x0<=xMax) {
			Point0 p1 = graph.pMult(x0, yMin);
			Point0 p2 = graph.pMult(x0, yMax);
			graph.drawLine(c, p1, p2);
		}
	}
}
