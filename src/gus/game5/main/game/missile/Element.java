package gus.game5.main.game.missile;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;

public abstract class Element extends ShapeRound {
	
	protected Point0 start;
	protected Point0 target;
	protected double blastSize;
	protected double speed;
	
	public Element(Point0 start, Point0 target, Color color, int size, double speed, double blastSize) {
		super(new Point2(start), size, color, AnchorType.CENTER);
		
		this.start = start;
		this.target = target;
		this.speed = speed;
		this.blastSize = blastSize;
		
		Point1 pv = target.pSub(start).pDistSet(speed);
		getAnchor().setDerived(pv);
	}
	public boolean targetReached() {
		return target.dist(getAnchor())<=speed;
	}
	
	public Point0 getStart() {
		return start;
	}
	
	public Point0 getTarget() {
		return target;
	}
	
	public double getBlastSize() {
		return blastSize;
	}
	
	public double getSpeed() {
		return speed;
	}
}
