package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.GDouble;
import gus.game5.core.point.point0.Point0;

public class GraphYLine2 extends GraphYLine0 {
	
	private GDouble gX0;
	
	public GraphYLine2(Color color, Point0 p) {
		super(color);
		gX0 = ()->p.getX();
	}
	
	public GraphYLine2(Color color, GDouble gX0) {
		super(color);
		this.gX0 = gX0;
	}

	public double getX0() {
		return gX0.gDouble();
	}
}
