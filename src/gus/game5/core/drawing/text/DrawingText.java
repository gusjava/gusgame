package gus.game5.core.drawing.text;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.features.g.G;
import gus.game5.core.point.point1.Point1;

public class DrawingText extends Drawing1 {

	private G<String> gString;
	
	public DrawingText(Color color, Point1 origin, G<String> gString) {
		super();
		setOrigin(origin);
		setColor(color);
		this.gString = gString;
	}
	
	public DrawingText(Color color, Point1 origin, String text) {
		this(color, origin, ()->text);
	}
	
	public DrawingText(Point1 origin, G<String> gString) {
		super();
		setOrigin(origin);
		this.gString = gString;
	}
	
	public DrawingText(Point1 origin, String text) {
		this(origin, ()->text);
	}
	
	public String getString() {
		return gString.g();
	}
	
	protected void draw() {
		drawString(getString());
	}
}
