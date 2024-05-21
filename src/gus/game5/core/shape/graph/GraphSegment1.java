package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class GraphSegment1 extends GraphSegment0 {

	private Point0 point1;
	private Point0 point2;
	
	public GraphSegment1(Color color, Point1 point1, Point1 point2) {
		super(color);
		this.point1 = point1;
		this.point2 = point2;
	}
	
	public GraphSegment1(Point1 point1, Point1 point2) {
		super();
		this.point1 = point1;
		this.point2 = point2;
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
}
