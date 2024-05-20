package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.function.FunctionPolynom;
import gus.game5.core.util.UtilArray;

public class GraphPolynom2 extends GraphPolynom0 {
	
	private G<FunctionPolynom> gPolynom;
	
	public GraphPolynom2(Color color, G<FunctionPolynom> gPolynom) {
		super(color);
		this.gPolynom = gPolynom;
	}
	
	public GraphPolynom2(Color color, GDouble... gA) {
		super(color);
		gPolynom = ()->new FunctionPolynom(UtilArray.collectDouble2(gA));
	}

	public FunctionPolynom getPolynom() {
		return gPolynom.g();
	}
}
