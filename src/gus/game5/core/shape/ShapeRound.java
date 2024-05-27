package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeRound extends Shape0 {
	
	public ShapeRound(Point0 anchor, double radius, Color color, AnchorType type) {
		super(anchor, 2*radius, 2*radius, type);
		setColor(color);
	}

	public ShapeRound(Point0 anchor, double radius, AnchorType type) {
		this(anchor, radius, Color.BLACK, type);
	}
	
	public ShapeRound(Point2 anchor, double radius, Color color, AnchorType type) {
		super(anchor, 2*radius, 2*radius, type);
		setColor(color);
	}

	public ShapeRound(Point2 anchor, double radius, AnchorType type) {
		this(anchor, radius, Color.BLACK, type);
	}
	
	public ShapeRound(Point0 anchor, double radius, Color color) {
		super(anchor, 2*radius, 2*radius);
		setColor(color);
	}

	public ShapeRound(Point0 anchor, double radius) {
		this(anchor, radius, Color.BLACK);
	}
	
	public ShapeRound(Point2 anchor, double radius, Color color) {
		super(anchor, 2*radius, 2*radius);
		setColor(color);
	}

	public ShapeRound(Point2 anchor, double radius) {
		this(anchor, radius, Color.BLACK);
	}

	protected void drawShape() {
		fillRoundC(getRadius());
	}
	
	public double getRadius() {
		return getWidth2();
	}
	
	public void setRadius(double radius) {
		setWidth2(radius);
		setHeight2(radius);
	}
	
	public void incrRadius(double incr) {
		setRadius(getRadius()+incr);
	}

	
	public boolean contains(Point0 p) {
		if(p==null) return false;
		return getPc().dist(p)<getRadius();
	}
	
	public boolean includes(Shape p) {
		if(p==null) return false;
		if(p instanceof ShapeRound) {
			ShapeRound sr = (ShapeRound) p;
			double d = getPc().dist(sr.getAnchor());
			return d<getRadius()-sr.getRadius();
		}
		return super.includes(p);
	}

	public boolean intersects(Shape p) {
		if(p==null) return false;
		if(p instanceof ShapeRound) {
			ShapeRound sr = (ShapeRound) p;
			double d = getPc().dist(sr.getAnchor());
			return d<getRadius()+sr.getRadius();
		}
		return super.intersects(p);
	}
}
