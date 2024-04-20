package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1Dxy;
import gus.game5.core.point.point2.Point2;

public abstract class Shape0 extends Drawing1 implements Shape {
	
	public static enum AnchorType {
		NW, NE, SW, SE, CENTER
	}
	
	protected Point2 anchor;
	protected double width;
	protected double height;
	protected int count = 0;
	
	protected Color debugFrameColor;
	protected Color debugOriginColor;
	protected Color debugAnchorColor;
	
	
	public Shape0(Point0 anchor, double width, double height) {
		this(new Point2(anchor), width, height, AnchorType.CENTER);
	}

	public Shape0(Point2 anchor, double width, double height) {
		this(anchor, width, height, AnchorType.CENTER);
	}
	
	public Shape0(Point0 anchor, double width, double height, AnchorType type) {
		this(new Point2(anchor), width, height, type);
	}

	public Shape0(Point2 anchor, double width, double height, AnchorType type) {
		super();
		this.width = width;
		this.height = height;
		this.anchor = anchor;
		setOrigin(buildOrigin(anchor, type));
	}
	
	private Point1 buildOrigin(Point2 anchor, AnchorType type) {
		switch(type) {
		case NE:return new Point1Dxy(anchor, ()->-getWidth2(), ()->getHeight2());
		case NW:return new Point1Dxy(anchor, ()->getWidth2(), ()->getHeight2());
		case SE:return new Point1Dxy(anchor, ()->-getWidth2(), ()->-getHeight2());
		case SW:return new Point1Dxy(anchor, ()->getWidth2(), ()->-getHeight2());
		case CENTER:return anchor;
		default:return anchor;
		}
	}
	
	/*
	 * ANCHOR
	 */
	
	public Point2 getAnchor() {
		return anchor;
	}
	
	public void setAnchor(Point2 anchor) {
		this.anchor = anchor;
	}
	
	/*
	 * WIDTH
	 */
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	/*
	 * HEIGHT
	 */
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	/*
	 * WIDTH 2
	 */
	
	public double getWidth2() {
		return width/2;
	}
	
	public void setWidth2(double width2) {
		this.width = width2*2;
	}
	
	/*
	 * HEIGHT 2
	 */
	
	public double getHeight2() {
		return height/2;
	}
	
	public void setHeight2(double height2) {
		this.height = height2*2;
	}
	
	/*
	 * Pc P0 P1
	 */
	
	public Point0 getPc() {
		return getOrigin();
	}
	
	public Point0 getP0() {
		return new Point1Dxy(anchor, -width/2, -height/2);
	}
	
	public Point0 getP1() {
		return new Point1Dxy(anchor, width/2, height/2);
	}
	
	/*
	 * GO NEXT / GO BACK
	 */

	public void goNext() {
		count++;
		anchor.goNext();
	}
	
	public void goBack() {
		count--;
		anchor.goBack();
	}

	/*
	 * CONTAINS
	 */

	public boolean contains(Point0 p) {
		if(p==null) return false;
		
		if(p.getX() < getP0().getX()) return false;
		if(p.getX() > getP1().getX()) return false;
		
		if(p.getY() < getP0().getY()) return false;
		if(p.getY() > getP1().getY()) return false;
		
		return true;
	}

	/*
	 * CONTAINS X Y
	 */

	public boolean containsX(double x) {
		return x >= getP0().getX() && x <= getP1().getX();
	}

	public boolean containsY(double y) {
		return y >= getP0().getY() && y <= getP1().getY();
	}
	
	/*
	 * INCLUDES
	 */
	
	public boolean includes(Shape shape) {
		if(shape==null) return false;
		
		Point0 p0 = shape.getP0();
		Point0 p1 = shape.getP1();
		
		return p0.getX() > getP0().getX() 
				&& p0.getY() > getP0().getY()
				&& p1.getX() < getP1().getX()
				&& p1.getY() < getP1().getY();
	}
	
	/*
	 * INTERSECTS
	 */
	
	public boolean intersects(Shape shape) {
		if(shape==null) return false;
		
		Point0 p0 = shape.getP0();
		Point0 p1 = shape.getP1();
		
		if(p1.getX() < getP0().getX()) return false;
		if(p0.getX() > getP1().getX()) return false;
		
		if(p1.getY() < getP0().getY()) return false;
		if(p0.getY() > getP1().getY()) return false;
		
		return true;
	}
	
	/*
	 * OVER
	 */
	
	public boolean isOver() {
		return false;
	}
	
	/*
	 * COUNT
	 */
	
	public int getCount() {
		return count;
	}
	
	/*
	 * DEBUG
	 */
	
	public void debugFrame(Color c) {
		debugFrameColor = c;
	}
	
	public void debugOrigin(Color c) {
		debugOriginColor = c;
	}
	
	public void debugAnchor(Color c) {
		debugAnchorColor = c;
	}
	
	/*
	 * DRAW
	 */
	
	protected void draw() {
		drawShape();
		drawDebug();
	}
	
	private void drawDebug() {
		if(debugFrameColor!=null) drawRectC(debugFrameColor, width, height);
		if(debugAnchorColor!=null) fillRoundC(debugAnchorColor, getAnchor().pSub(getOrigin()), 4);
		if(debugOriginColor!=null) drawSquareC(debugOriginColor, 4);
	}
	
	protected abstract void drawShape();
	
	/*
	 * FIND
	 */
	
	public <E extends Shape> E findNearest(ShapeList<E> shapes) {
		return shapes.findNearest(this);
	}
	
	public <E extends Shape> E findFarest(ShapeList<E> shapes) {
		return shapes.findFarest(this);
	}
}
