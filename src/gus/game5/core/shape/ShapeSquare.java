package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeSquare extends Shape0 {
	
	public ShapeSquare(Point0 anchor, double length, Color color, AnchorType type) {
		super(anchor, length, length, type);
		this.color = color;
	}

	public ShapeSquare(Point0 anchor, double length, AnchorType type) {
		this(anchor, length, Color.BLACK, type);
	}
	
	public ShapeSquare(Point2 anchor, double length, Color color, AnchorType type) {
		super(anchor, length, length, type);
		this.color = color;
	}

	public ShapeSquare(Point2 anchor, double length, AnchorType type) {
		this(anchor, length, Color.BLACK, type);
	}

	protected void drawShape() {
		fillSquareC(getLength());
	}
	
	public double getLength() {
		return getWidth();
	}
}
