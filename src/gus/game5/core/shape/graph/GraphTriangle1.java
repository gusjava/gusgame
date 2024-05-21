package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphTriangle1 extends GraphTriangle0 {

	private Point0 point1;
	private Point0 point2;
	private Point0 point3;
	
	public GraphTriangle1(Color color, Point1 point1, Point1 point2, Point1 point3) {
		super(color);
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
	}
	
	public GraphTriangle1(Point1 point1, Point1 point2, Point1 point3) {
		super();
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
	}
	
	/*
	 * POINTS
	 */
	
	public Point0 getPoint1() {
		return point1;
	}
	
	public Point0 getPoint2() {
		return point2;
	}
	
	public Point0 getPoint3() {
		return point3;
	}
}
