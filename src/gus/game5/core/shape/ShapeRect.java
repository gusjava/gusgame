package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeRect extends Shape0 {
	
	// ANCHOR P0

	public ShapeRect(Point0 anchor, double width, double height, Color color, AnchorType type) {
		super(anchor, width, height, type);
		this.color = color;
	}

	public ShapeRect(Point0 anchor, double width, double height, AnchorType type) {
		this(anchor, width, height, Color.BLACK, type);
	}

	public ShapeRect(Point0 anchor, double width, double height, Color color) {
		super(anchor, width, height);
		this.color = color;
	}

	public ShapeRect(Point0 anchor, double width, double height) {
		this(anchor, width, height, Color.BLACK);
	}
	
	// ANCHOR P2

	public ShapeRect(Point2 anchor, double width, double height, Color color, AnchorType type) {
		super(anchor, width, height, type);
		this.color = color;
	}

	public ShapeRect(Point2 anchor, double width, double height, AnchorType type) {
		this(anchor, width, height, Color.BLACK, type);
	}

	public ShapeRect(Point2 anchor, double width, double height, Color color) {
		super(anchor, width, height);
		this.color = color;
	}

	public ShapeRect(Point2 anchor, double width, double height) {
		this(anchor, width, height, Color.BLACK);
	}
	
	/*
	 * DRAW
	 */

	protected void drawShape() {
		fillRectC(getWidth(), getHeight());
	}
}
