package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;

public class GraphULine1 extends GraphULine0 {
	
	private double val0;
	private Double slope;
	
	public GraphULine1(Color color, Double slope) {
		super(color);
		this.slope = slope;
		val0 = 0;
	}
	
	public GraphULine1(Color color, Point0 p) {
		super(color);
		slope = p.getSlope();
		val0 = 0;
	}
	
	public GraphULine1(Color color, Point0 p, Double slope) {
		super(color);
		this.slope = slope;
		if(slope==null) val0 = p.getX();
		else val0 = p.getY() - slope * p.getX();
	}
	
	public GraphULine1(Color color, Point0 p1, Point0 p2) {
		super(color);
		this.slope = p1.pSub(p2).getSlope();
		if(slope==null) val0 = p1.getX();
		else val0 = p1.getY() - slope * p1.getX();
	}
	
	public GraphULine1(Double slope) {
		super();
		this.slope = slope;
		val0 = 0;
	}
	
	public GraphULine1(Point0 p) {
		super();
		slope = p.getSlope();
		val0 = 0;
	}
	
	public GraphULine1(Point0 p, Double slope) {
		super();
		this.slope = slope;
		if(slope==null) val0 = p.getX();
		else val0 = p.getY() - slope * p.getX();
	}
	
	public GraphULine1(Point0 p1, Point0 p2) {
		super();
		this.slope = p1.pSub(p2).getSlope();
		if(slope==null) val0 = p1.getX();
		else val0 = p1.getY() - slope * p1.getX();
	}
	
	public double getVal0() {
		return val0;
	}

	public Double getSlope() {
		return slope;
	}

	public Double getSlopeInv() {
		if(slope==null) return 0.0;
		return -1/slope;
	}
}
