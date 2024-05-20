package gus.game5.main.l1.bloon.missile;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeRound;
import gus.game5.main.l1.bloon.BloonConst;

public class Missile extends ShapeRound {
	private int carburant = 1000;

	public Missile(Point0 anchor, Point0 target) {
		super(anchor, BloonConst.MISSILE_RADIUS);
		setColor(Color.BLACK);
		getAnchor().setDerived(target, BloonConst.MISSILE_SPEED);
	}
	
	public void explode() {
		if(carburant==0) return;
		carburant = 0;
	}
	
	public void goNext() {
		super.goNext();
		carburant--;
	}
	
	public boolean isOver() {
		return carburant<=0;
	}
}