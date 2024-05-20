package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.util.UtilArray;

public class GraphPolynom2 extends GraphPolynom0 {
	
	private G<FunctionPolynom> gFunction;
	
	public GraphPolynom2(Color color, String name, G<FunctionPolynom> gFunction) {
		super(color, name);
		this.gFunction = gFunction;
	}
	
	public GraphPolynom2(Color color, String name, GDouble... gA) {
		super(color, name);
		gFunction = ()->new FunctionPolynom(UtilArray.collectDouble2(gA));
	}

	public FunctionPolynom polynom() {
		return gFunction.g();
	}
}
