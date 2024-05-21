package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public abstract class GraphCircle0 extends GraphObject {
	
	public GraphCircle0(Color color) {
		super(color);
	}
	
	public GraphCircle0() {
		super();
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Point0 p = getPoint();
		double r = getRadius();
		double px = p.getX();
		double py = p.getY();
		
		double xMin = graph.getXMin();
		double xMax = graph.getXMax();
		double xStep = graph.getXStep();
		
		double yMin = graph.getYMin();
		double yMax = graph.getYMax();
		double yStep = graph.getYStep();
		
		
		if(px>=xMin+r && px<=xMax-r && py>=yMin+r && py<=yMax-r) {
			Point0 p_ = graph.pMult(p);
			double width = r*2*xStep;
			double height = r*2*yStep;
			graph.drawOvalC(c, p_, width, height);
		}
	}
	
	public abstract Point0 getPoint();
	public abstract double getRadius();
}
