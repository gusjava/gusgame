package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.FunctionPolynom;

public class GraphLine2 extends GraphLine0 {
	
	private G<FunctionPolynom> gFunction;
	
	public GraphLine2(Color color, String name, GraphPoint2 p1, GraphPoint2 p2) {
		super(color, name);
		gFunction = ()->{
			double a1 = p1.getPoint().pSub(p2.getPoint()).slope();
			double a0 = p1.getPoint().getY() - a1 * p1.getPoint().getX();
			return new FunctionPolynom(a0, a1);
		};
	}
	
	public GraphLine2(Color color, String name, GraphPoint2 p) {
		super(color, name);
		gFunction = ()->new FunctionPolynom(0, p.getPoint().slope());
	}
	
	public GraphLine2(Color color, String name, GDouble gA0, GDouble gA1) {
		super(color, name);
		gFunction = ()->new FunctionPolynom(gA0.gDouble(), gA1.gDouble());
	}
	
	public GraphLine2(Color color, String name, GDouble gA1) {
		super(color, name);
		gFunction = ()->new FunctionPolynom(0, gA1.gDouble());
	}

	public FunctionPolynom polynom() {
		return gFunction.g();
	}
}
