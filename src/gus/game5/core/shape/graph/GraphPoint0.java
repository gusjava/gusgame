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
	
	public GraphPoint0(Color color) {
		super(color);
	}
	
	public GraphPoint0(Color color, String name) {
		super(color);
		setName(name);
	}
	
	public GraphPoint0(String name) {
		super();
		setName(name);
	}
	
	/*
	 * DISPLAY MODE
	 */
	
	public static final int DISPLAY_MODE_CROSS = 0;
	public static final int DISPLAY_MODE_ARROW = 1;
	
	protected int displayMode = DISPLAY_MODE_CROSS;
	
	public void setDisplayMode(int displayMode) {
		this.displayMode = displayMode;
	}
	
	public int getDisplayMode() {
		return displayMode;
	}
	
	/*
	 * DRAW OBJECT
	 */

	protected void drawObject(ShapeGraph graph) {
		Point0 p = getPoint();
		if(!graph.includes(p)) return;
		
		Color c = getColor();
		if(c==null) c = graph.getColor();
		Point0 p_ = graph.pMult(p);
		
		if(displayMode==DISPLAY_MODE_CROSS) {
			graph.drawLine(c, p_.pAdd(5, 0), p_.pAdd(-5, 0));
			graph.drawLine(c, p_.pAdd(0, 5), p_.pAdd(0, -5));
		}
		else if(displayMode==DISPLAY_MODE_ARROW) {
			graph.drawArrow(c, p_, 5);
		}
		
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
