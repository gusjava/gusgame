package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeSquare extends Shape0 {
	
	// ANCHOR P0
	
	public ShapeSquare(Point0 anchor, double length, Color color, AnchorType type) {
		super(anchor, length, length, type);
		setColor(color);
	}

	public ShapeSquare(Point0 anchor, double length, AnchorType type) {
		this(anchor, length, Color.BLACK, type);
	}
	
	public ShapeSquare(Point0 anchor, double length, Color color) {
		super(anchor, length, length);
		setColor(color);
	}

	public ShapeSquare(Point0 anchor, double length) {
		this(anchor, length, Color.BLACK);
	}
	
	// ANCHOR P2
	
	public ShapeSquare(Point2 anchor, double length, Color color, AnchorType type) {
		super(anchor, length, length, type);
		setColor(color);
	}

	public ShapeSquare(Point2 anchor, double length, AnchorType type) {
		this(anchor, length, Color.BLACK, type);
	}
	
	public ShapeSquare(Point2 anchor, double length, Color color) {
		super(anchor, length, length);
		setColor(color);
	}

	public ShapeSquare(Point2 anchor, double length) {
		this(anchor, length, Color.BLACK);
	}
	
	/*
	 * DRAW
	 */

	protected void drawShape() {
		fillSquareC(getLength());
	}
	
	public double getLength() {
		return getWidth();
	}
	
	/*
	 * LENGTH
	 */
	
	public void setLength(double length) {
		setWidth(length);
		setHeight(length);
	}
}
