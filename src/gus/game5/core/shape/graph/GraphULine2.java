package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.features.g.GPoint0;
import gus.game5.core.point.point0.Point0;

public class GraphULine2 extends GraphULine0 {
	
	private GDouble gVal0;
	private G<Double> gSlope;
	
	public GraphULine2(Color color, G<Double> gSlope) {
		super(color);
		this.gSlope = gSlope;
		gVal0 = ()->0;
	}
	
	public GraphULine2(Color color, GPoint0 gP) {
		super(color);
		gSlope = ()->gP.g().getSlope();
		gVal0 = ()->0;
	}
	
	public GraphULine2(Color color, GPoint0 gP, G<Double> gSlope) {
		super(color);
		this.gSlope = gSlope;
		gVal0 = ()->{
			Double slope = getSlope();
			Point0 p = gP.g();
			if(slope==null) return p.getX();
			return p.getY() - slope * p.getX();
		};
	}
	
	public GraphULine2(Color color, GPoint0 gP1, GPoint0 gP2) {
		super(color);
		gSlope = ()->{
				Point0 p1 = gP1.g();
				Point0 p2 = gP1.g();
				return p1.pSub(p2).getSlope();
		};
		gVal0 = ()->{
			Double slope = getSlope();
			Point0 p1 = gP1.g();
			if(slope==null) return p1.getX();
			return p1.getY() - slope * p1.getX();
		};
	}
	
	public GraphULine2(G<Double> gSlope) {
		super();
		this.gSlope = gSlope;
		gVal0 = ()->0;
	}
	
	public GraphULine2(GPoint0 gP) {
		super();
		gSlope = ()->gP.g().getSlope();
		gVal0 = ()->0;
	}
	
	public GraphULine2(GPoint0 gP, G<Double> gSlope) {
		super();
		this.gSlope = gSlope;
		gVal0 = ()->{
			Double slope = getSlope();
			Point0 p = gP.g();
			if(slope==null) return p.getX();
			return p.getY() - slope * p.getX();
		};
	}
	
	public GraphULine2(GPoint0 gP1, GPoint0 gP2) {
		super();
		gSlope = ()->{
			Point0 p1 = gP1.g();
			Point0 p2 = gP1.g();
			return p1.pSub(p2).getSlope();
		};
		gVal0 = ()->{
			Double slope = getSlope();
			Point0 p1 = gP1.g();
			if(slope==null) return p1.getX();
			return p1.getY() - slope * p1.getX();
		};
	}
	
	public double getVal0() {
		return gVal0.gDouble();
	}

	public Double getSlope() {
		return gSlope.g();
	}

	public Double getSlopeInv() {
		Double slope = getSlope();
		if(slope==null) return 0.0;
		return -1/slope;
	}
}
