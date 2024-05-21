package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public abstract class GraphTriangle0 extends GraphObject {
	
	public GraphTriangle0(Color color) {
		super(color);
	}
	
	public GraphTriangle0() {
		super();
	}

	protected void drawObject(ShapeGraph graph) {
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Point0 p1 = getPoint1();
		Point0 p2 = getPoint2();
		Point0 p3 = getPoint3();
		
		double xMin = graph.getXMin();
		double yMin = graph.getYMin();
		double xMax = graph.getXMax();
		double yMax = graph.getYMax();
		
		boolean p1Inside = p1.xBetween(xMin, xMax) && p1.yBetween(yMin, yMax);
		boolean p2Inside = p2.xBetween(xMin, xMax) && p2.yBetween(yMin, yMax);
		boolean p3Inside = p3.xBetween(xMin, xMax) && p3.yBetween(yMin, yMax);
		
		if(p1Inside && p2Inside && p3Inside) {
			Point0 p1_ = graph.pMult(p1);
			Point0 p2_ = graph.pMult(p2);
			Point0 p3_ = graph.pMult(p3);
			
			graph.drawLine(c, p1_, p2_);
			graph.drawLine(c, p2_, p3_);
			graph.drawLine(c, p3_, p1_);
		}
	}
	
	/*
	 * POINTS
	 */
	
	public abstract Point0 getPoint1();
	public abstract Point0 getPoint2();
	public abstract Point0 getPoint3();
}
