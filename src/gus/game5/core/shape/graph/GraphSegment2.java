package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.point.point0.Point0;

public class GraphSegment2 extends GraphSegment0 {

	private GPoint0 gPoint1;
	private GPoint0 gPoint2;
	
	public GraphSegment2(Color color, GPoint0 gPoint1, GPoint0 gPoint2) {
		super(color);
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
	}
	
	public GraphSegment2(GPoint0 gPoint1, GPoint0 gPoint2) {
		super();
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
	}
	
	/*
	 * POINTS
	 */
	
	public Point0 getPoint1() {
		return gPoint1.g();
	}
	
	public Point0 getPoint2() {
		return gPoint2.g();
	}
}
