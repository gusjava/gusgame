package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;

public class GraphLine1 extends GraphLine0 {
	
	private FunctionPolynom polynom;
	
	public GraphLine1(Color color, Point0 p) {
		super(color);
		setPolynom(p);
	}
	
	public GraphLine1(Color color, Point0 p1, Point0 p2) {
		super(color);
		setPolynom(p1, p2);
	}
	
	public GraphLine1(Color color, Point0 p, double slope) {
		super(color);
		setPolynom(p, slope);
	}
	
	public GraphLine1(Color color, double y0, double slope) {
		super(color);
		setPolynom(y0, slope);
	}
	
	public GraphLine1(Color color, double slope) {
		super(color);
		setPolynom(slope);
	}
	
	public GraphLine1(Point0 p) {
		super();
		setPolynom(p);
	}
	
	public GraphLine1(Point0 p1, Point0 p2) {
		super();
		setPolynom(p1, p2);
	}
	
	public GraphLine1(Point0 p, double slope) {
		super();
		setPolynom(p, slope);
	}
	
	public GraphLine1(double y0, double slope) {
		super();
		setPolynom(y0, slope);
	}
	
	public GraphLine1(double slope) {
		super();
		setPolynom(slope);
	}
	
	/*
	 * POLYNOM
	 */
	
	public FunctionPolynom getPolynom() {
		return polynom;
	}
	
	public void setPolynom(Point0 p) {
		polynom = new FunctionPolynom(0, p.slope());
	}
	
	public void setPolynom(Point0 p1, Point0 p2) {
		double slope = p1.pSub(p2).slope();
		double y0 = p1.getY() - slope * p1.getX();
		polynom = new FunctionPolynom(y0, slope);
	}
	
	public void setPolynom(Point0 p, double slope) {
		double y0 = p.getY() - slope * p.getX();
		polynom = new FunctionPolynom(y0, slope);
	}
	
	public void setPolynom(double y0, double slope) {
		polynom = new FunctionPolynom(y0, slope);
	}
	
	public void setPolynom(double slope) {
		polynom = new FunctionPolynom(0, slope);
	}
}
