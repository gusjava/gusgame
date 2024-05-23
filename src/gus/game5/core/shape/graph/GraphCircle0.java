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
		
		Point0 p = getPoint();
		double r = getRadius();
		double px = p.getX();
		double py = p.getY();
		
		double xMin = graph.getXMin();
		double xMax = graph.getXMax();
		
		double yMin = graph.getYMin();
		double yMax = graph.getYMax();
		
		
		if(px>=xMin+r && px<=xMax-r && py>=yMin+r && py<=yMax-r) {
			double width = r*2;
			double height = r*2;
			graph.drawOvalC(c, p, width, height);

			if(displayCenter) {
				graph.drawCross(c, p);
			}
		}
	}
	
	public abstract Point0 getPoint();
	public abstract double getRadius();
}
