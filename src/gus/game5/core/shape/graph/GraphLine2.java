package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.point.point0.Point0;

public class GraphLine2 extends GraphLine0 {
	
	private G<FunctionPolynom> gPolynom;
	
	public GraphLine2(Color color, G<Point0> gP) {
		super(color);
		setGPolynom(gP);
	}
	
	public GraphLine2(Color color, G<Point0> gP1, G<Point0> gP2) {
		super(color);
		setGPolynom(gP1, gP2);
	}
	
	public GraphLine2(Color color, G<Point0> gP, GDouble gSlope) {
		super(color);
		setGPolynom(gP, gSlope);
	}
	
	public GraphLine2(Color color, GDouble gY0, GDouble gSlope) {
		super(color);
		setPolynom(gY0, gSlope);
	}
	
	public GraphLine2(Color color, GDouble gSlope) {
		super(color);
		setPolynom(gSlope);
	}
	
	public GraphLine2(G<Point0> gP) {
		super();
		setGPolynom(gP);
	}
	
	public GraphLine2(G<Point0> gP1, G<Point0> gP2) {
		super();
		setGPolynom(gP1, gP2);
	}
	
	public GraphLine2(G<Point0> gP, GDouble gSlope) {
		super();
		setGPolynom(gP, gSlope);
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
	
	public void setGPolynom(G<Point0> gP) {
		gPolynom = ()->new FunctionPolynom(0, gP.g().slope());
	}
	
	public void setGPolynom(G<Point0> gP1, G<Point0> gP2) {
		gPolynom = ()->{
			Point0 p1 = gP1.g();
			Point0 p2 = gP2.g();
			double slope = p1.pSub(p2).slope();
			double y0 = p1.getY() - slope * p1.getX();
			return new FunctionPolynom(y0, slope);
		};
	}
	
	public void setGPolynom(G<Point0> gP, GDouble gSlope) {
		gPolynom = ()->{
			Point0 p = gP.g();
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
