package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.GPoint0;
import gus.game5.core.point.point0.Point0;

public class GraphTriangle2 extends GraphTriangle0 {

	private GPoint0 gPoint1;
	private GPoint0 gPoint2;
	private GPoint0 gPoint3;
	
	public GraphTriangle2(Color color, GPoint0 gPoint1, GPoint0 gPoint2, GPoint0 gPoint3) {
		super(color);
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
		this.gPoint3 = gPoint3;
	}
	
	public GraphTriangle2(GPoint0 gPoint1, GPoint0 gPoint2, GPoint0 gPoint3) {
		super();
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
		this.gPoint3 = gPoint3;
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
	
	public Point0 getPoint3() {
		return gPoint3.g();
	}
}
