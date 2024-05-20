package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;

public class GraphLine2 extends GraphLine0 {
	
	private G<FunctionPolynom> gPolynom;
	
	public GraphLine2(Color color, Point0 p) {
		super(color);
		setPolynom(p);
	}
	
	public GraphLine2(Color color, Point0 p1, Point0 p2) {
		super(color);
		setPolynom(p1, p2);
	}
	
	public GraphLine2(Color color, Point0 p, GDouble gSlope) {
		super(color);
		setPolynom(p, gSlope);
	}
	
	public GraphLine2(Color color, GDouble gY0, GDouble gSlope) {
		super(color);
		setPolynom(gY0, gSlope);
	}
	
	public GraphLine2(Color color, GDouble gSlope) {
		super(color);
		setPolynom(gSlope);
	}
	
	public GraphLine2(Point0 p) {
		super();
		setPolynom(p);
	}
	
	public GraphLine2(Point0 p1, Point0 p2) {
		super();
		setPolynom(p1, p2);
	}
	
	public GraphLine2(Point0 p, GDouble gSlope) {
		super();
		setPolynom(p, gSlope);
	}
	
	public GraphLine2(GDouble gY0, GDouble gSlope) {
		super();
		setPolynom(gY0, gSlope);
	}
	
	public GraphLine2(GDouble gSlope) {
		super();
		setPolynom(gSlope);
	}
	
	/*
	 * POLYNOM
	 */

	public FunctionPolynom getPolynom() {
		return gPolynom.g();
	}
	
	public void setPolynom(Point0 p) {
		gPolynom = ()->new FunctionPolynom(0, p.slope());
	}
	
	public void setPolynom(Point0 p1, Point0 p2) {
		gPolynom = ()->{
			double slope = p1.pSub(p2).slope();
			double y0 = p1.getY() - slope * p1.getX();
			return new FunctionPolynom(y0, slope);
		};
	}
	
	public void setPolynom(Point0 p, GDouble gSlope) {
		gPolynom = ()->{
			double y0 = p.getY() - gSlope.gDouble() * p.getX();
			return new FunctionPolynom(y0, gSlope.gDouble());
		};
	}
	
	public void setPolynom(GDouble gY0, GDouble gSlope) {
		gPolynom = ()->new FunctionPolynom(gY0.gDouble(), gSlope.gDouble());
	}
	
	public void setPolynom(GDouble gSlope) {
		gPolynom = ()->new FunctionPolynom(0, gSlope.gDouble());
	}
}
