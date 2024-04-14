package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeOval extends Shape0 {

	public ShapeOval(Point0 anchor, double width, double height, Color color, AnchorType type) {
		super(anchor, width, height, type);
		this.color = color;
	}

	public ShapeOval(Point0 anchor, double width, double height, AnchorType type) {
		this(anchor, width, height, Color.BLACK, type);
	}

	public ShapeOval(Point2 anchor, double width, double height, Color color, AnchorType type) {
		super(anchor, width, height, type);
		this.color = color;
	}

	public ShapeOval(Point2 anchor, double width, double height, AnchorType type) {
		this(anchor, width, height, Color.BLACK, type);
	}

	protected void drawShape() {
		fillOvalC(getWidth(), getHeight());
	}
}
