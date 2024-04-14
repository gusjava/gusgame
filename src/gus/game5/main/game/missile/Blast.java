package gus.game5.main.game.missile;

import gus.game5.core.shape.ShapeRound;

public class Blast extends ShapeRound {
	private double radiusLimit;
	private double radiusIncr;
	private int starNb;

	public Blast(Element element, double radiusIncr, int starNb) {
		super(element.getAnchor(), 2, element.getColor(), AnchorType.CENTER);
		this.radiusLimit = element.getBlastSize();
		this.radiusIncr = radiusIncr;
		this.starNb = starNb;
	}
	public void goNext() {
		if(!over())
		setRadius(getRadius()+radiusIncr);
	}
	public boolean over() {
		return getRadius()>=radiusLimit;
	}
	public void draw() {
		drawStarC(getRadius(), starNb);
	}
}