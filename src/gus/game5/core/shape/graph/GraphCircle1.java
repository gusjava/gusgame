package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphCircle1 extends GraphCircle0 {
	
	private Point0 point;
	private double radius;
	
	public GraphCircle1(Color color, Point1 point, double radius) {
		super(color);
		this.point = point;
		this.radius = radius;
	}
	
	public GraphCircle1(Point1 point, double radius) {
		super();
		this.point = point;
		this.radius = radius;
	}
	
	public Point0 getPoint() {
		return point;
	}
	
	public double getRadius() {
		return radius;
	}
}
