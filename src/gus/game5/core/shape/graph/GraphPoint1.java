package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphPoint1 extends GraphPoint0 {
	
	private Point0 point;
	
	public GraphPoint1(Color color, String name, double x, double y) {
		super(color, name);
		this.point = new Point1(x,y);
	}
	
	public GraphPoint1(Color color, String name, Point0 point) {
		super(color, name);
		this.point = point;
	}
	
	public GraphPoint1(Color color, Point0 point) {
		super(color);
		this.point = point;
	}
	
	public GraphPoint1(String name, double x, double y) {
		this(name, new Point1(x,y));
	}
	
	public GraphPoint1(double x, double y) {
		this(new Point1(x,y));
	}
	
	public GraphPoint1(String name, Point0 point) {
		super(name);
		this.point = point;
	}
	
	public GraphPoint1(Point0 point) {
		super();
		this.point = point;
	}
	
	/*
	 * POINT
	 */
	
	public Point0 getPoint() {
		return point;
	}
}
