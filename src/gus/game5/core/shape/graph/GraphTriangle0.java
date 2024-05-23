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
	
	/*
	 * DISPLAY CENTER
	 */
	
	protected boolean displayCenter = false;
	
	public void setDisplayCenter(boolean displayCenter) {
		this.displayCenter = displayCenter;
	}
	
	public boolean isDisplayCenter() {
		return displayCenter;
	}

	/*
	 * DRAW OBJECT
	 */

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
			graph.drawLine(c, p1, p2);
			graph.drawLine(c, p2, p3);
			graph.drawLine(c, p3, p1);

			if(displayCenter) {
				graph.drawCross(c, getPoint123());
			}
		}
	}
	
	/*
	 * POINTS
	 */
	
	public abstract Point0 getPoint1();
	public abstract Point0 getPoint2();
	public abstract Point0 getPoint3();
	
	public Point0 getPoint12() {
		return avg(getPoint1(), getPoint2());
	}
	
	public Point0 getPoint13() {
		return avg(getPoint1(), getPoint3());
	}
	
	public Point0 getPoint23() {
		return avg(getPoint2(), getPoint3());
	}
	
	public Point0 getPoint123() {
		return avg(getPoint1(), getPoint2(), getPoint3());
	}
}
