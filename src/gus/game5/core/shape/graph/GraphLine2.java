package gus.game5.core.shape.graph;

import java.awt.Color;

import gus.game5.core.features.g.GDouble;

public class GraphLine2 extends GraphLine0 {
	
	private GDouble gA;
	private GDouble gB;
	
	public GraphLine2(Color color, String name, GraphPoint2 p1, GraphPoint2 p2) {
		super(color, name);
		gA = ()->p1.getPoint().pSub(p2.getPoint()).slope();
		gB = ()->p1.getPoint().getY() - getA() * p1.getPoint().getX();
	}
	
	public GraphLine2(Color color, String name, GraphPoint2 p) {
		super(color, name);
		gA = ()->p.getPoint().slope();
		gB = ()->0;
	}
	
	public GraphLine2(Color color, String name, GDouble gA, GDouble gB) {
		super(color, name);
		this.gA = gA;
		this.gB = gB;
	}
	
	public GraphLine2(Color color, String name, GDouble gA) {
		super(color, name);
		this.gA = gA;
		this.gB = ()->0;
	}

	public double getA() {
		return gA.gDouble();
	}

	public double getB() {
		return gB.gDouble();
	}
}
