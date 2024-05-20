package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.dyn.Dyn;

public abstract class GraphObject implements Dyn {

	private String name;
	private Color color;
	
	private boolean displayName = true;
	private boolean displayInfo = true;
	
	public GraphObject() {
	}
	
	public GraphObject(Color color, String name) {
		this.color = color;
		this.name = name;
	}
	
	public GraphObject(String name) {
		this.name = name;
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
	
	public boolean displayName() {
		return displayName;
	}
	
	/*
	 * DISPLAY INFO
	 */
	
	public boolean displayInfo() {
		return displayInfo;
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
}
