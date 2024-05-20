package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point2.Point2;

public class GraphPoint2 extends GraphPoint1 {
	
	private Point2 point2;
	
	public GraphPoint2(Color color, String name, double x, double y) {
		super(color, name, x, y);
		this.point2 = new Point2(x, y);
	}
	
	public GraphPoint2(Color color, String name, Point2 point2) {
		super(color, name, point2);
		this.point2 = point2;
	}
	
	public GraphPoint2(String name, double x, double y) {
		super(name, x, y);
		this.point2 = new Point2(x, y);
	}
	
	public GraphPoint2(String name, Point2 point2) {
		super(name, point2);
		this.point2 = point2;
	}
	
	public GraphPoint2(Point2 point2) {
		super(point2);
		this.point2 = point2;
	}
	
	/*
	 * DYN
	 */

	public void goNext() {
		point2.goNext();
	}

	public void goBack() {
		point2.goBack();
	}
}
