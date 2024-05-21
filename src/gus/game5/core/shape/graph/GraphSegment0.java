package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public abstract class GraphSegment0 extends GraphObject {
	
	public GraphSegment0(Color color) {
		super(color);
	}
	
	public GraphSegment0() {
		super();
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Point0 p1 = getPoint1();
		Point0 p2 = getPoint2();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		boolean p1Inside = p1.xBetween(xMin, xMax) && p1.yBetween(yMin, yMax);
		boolean p2Inside = p2.xBetween(xMin, xMax) && p2.yBetween(yMin, yMax);
		
		if(p1Inside && p2Inside) {
			Point0 p1_ = graph.pMult(p1);
			Point0 p2_ = graph.pMult(p2);
			graph.drawLine(c, p1_, p2_);
		}
	}
	
	/*
	 * POINTS
	 */
	
	public abstract Point0 getPoint1();
	public abstract Point0 getPoint2();
}
