package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.GDouble;

public class GraphYLine2 extends GraphYLine0 {
	
	private GDouble gX0;
	
	public GraphYLine2(Color color, String name, GraphPoint2 p) {
		super(color, name);
		gX0 = ()->p.getPoint().getX();
	}
	
	public GraphYLine2(Color color, String name, GDouble gX0) {
		super(color, name);
		this.gX0 = gX0;
	}

	public double getX0() {
		return gX0.gDouble();
	}
}
