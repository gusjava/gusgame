package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.dyn.Dyn;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point0.Point0Avg;
import gus.game5.core.point.point0.Point0Sum;
import gus.game5.core.point.point1.Point1;

public abstract class GraphObject implements Dyn {

	private String name;
	private Color color;
	
	private boolean displayName = true;
	private boolean displayInfo = true;
	
	public GraphObject() {
	}
	
	public GraphObject(Color color) {
		this.color = color;
	}
	
	/*
	 * COLOR
	 */
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/*
	 * NAME
	 */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * DISPLAY NAME
	 */
	
	public boolean isDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(boolean displayName) {
		this.displayName = displayName;
	}
	
	/*
	 * DISPLAY INFO
	 */
	
	public boolean isDisplayInfo() {
		return displayInfo;
	}
	
	public void setDisplayInfo(boolean displayInfo) {
		this.displayInfo = displayInfo;
	}
	
	/*
	 * DYN
	 */

	public void goNext() {}

	public void goBack() {}
	
	/*
	 * DRAW OBJECT
	 */
	
	protected abstract void drawObject(ShapeGraph graph);
	
	/*
	 * POINT
	 */
	
	public static Point1 p(double x, double y) {
		return new Point1(x, y);
	}
	
	public static Point0 avg(Point0... points) {
		return new Point0Avg(points);
	}
	
	public static Point0 sum(Point0... points) {
		return new Point0Sum(points);
	}
}
