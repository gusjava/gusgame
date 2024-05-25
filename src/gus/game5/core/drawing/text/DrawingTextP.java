package gus.game5.core.drawing.text;

import java.awt.Color;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point1.Point1;

public class DrawingTextP extends DrawingText {

	private double fW;
	private double fH;
	
	public DrawingTextP(Color color, Point1 origin, G<String> gString, double fW, double fH) {
		super(color, origin, gString);
		this.fW = fW;
		this.fH = fH;
	}
	
	public DrawingTextP(Color color, Point1 origin, String text, double fW, double fH) {
		super(color, origin, text);
		this.fW = fW;
		this.fH = fH;
	}
	
	public DrawingTextP(Point1 origin, G<String> gString, double fW, double fH) {
		super(origin, gString);
		this.fW = fW;
		this.fH = fH;
	}
	
	public DrawingTextP(Point1 origin, String text, double fW, double fH) {
		super(origin, text);
		this.fW = fW;
		this.fH = fH;
	}
	
	protected void draw() {
		drawStringP(getString(), fW, fH);
	}
	
	public double getfW() {
		return fW;
	}

	public void setfW(double fW) {
		this.fW = fW;
	}

	public double getfH() {
		return fH;
	}

	public void setfH(double fH) {
		this.fH = fH;
	}
}
