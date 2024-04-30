package gus.game5.core.drawing;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point1.Point1;

public class DrawingTextC extends DrawingText {

	
	public DrawingTextC(Color color, Point1 origin, G<String> gString) {
		super(color, origin, gString);
	}
	
	public DrawingTextC(Color color, Point1 origin, String text) {
		super(color, origin, text);
	}
	
	public DrawingTextC(Point1 origin, G<String> gString) {
		super(origin, gString);
	}
	
	public DrawingTextC(Point1 origin, String text) {
		super(origin, text);
	}
	
	protected void draw() {
		drawStringC(getString());
	}
}
