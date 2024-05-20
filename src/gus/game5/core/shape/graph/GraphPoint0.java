package gus.game5.core.shape.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilString;

public abstract class GraphPoint0 extends GraphObject {
	
	public GraphPoint0() {
		super();
	}
	
	public GraphPoint0(Color color, String name) {
		super(color, name);
	}
	
	public GraphPoint0(String name) {
		super(name);
	}
	
	public GraphPoint0(Color color) {
		super(color);
	}

	protected void drawObject(ShapeGraph graph) {
		Point0 p = getPoint();
		if(!graph.includes(p)) return;
		
		Color c = getColor();
		if(c==null) c = graph.getColor();
		
		Point0 p_ = graph.pMult(p);
		graph.drawLine(c, p_.pAdd(5, 0), p_.pAdd(-5, 0));
		graph.drawLine(c, p_.pAdd(0, 5), p_.pAdd(0, -5));
		
		List<String> desc = buildDescription();
		if(!desc.isEmpty()) {
			graph.drawString(c, p_.pAdd(10,5), UtilString.join(desc, " "));
		}
	}
	
	private List<String> buildDescription() {
		List<String> infos = new ArrayList<>();
		if(displayName()) infos.add(getName());
		if(displayInfo()) infos.add("("+UtilDisplay.dec2s(getX())+","+UtilDisplay.dec2s(getY())+")");
		return infos;
	}
	
	/*
	 * X Y
	 */
	
	public double getX() {
		return getPoint().getX();
	}
	
	public double getY() {
		return getPoint().getY();
	}
	
	/*
	 * POINT
	 */
	
	public abstract Point0 getPoint();
}
