package gus.game5.main.anim.life1;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeRound;

public class Food extends ShapeRound {

	public Food(Point0 anchor) {
		super(anchor, LifeConst.FOOD_RADIUS);
		setColor(LifeConst.FOOD_COLOR);
	}
	
	public double getEnergy() {
		return LifeConst.FOOD_ENERGY;
	}
	
	/*
	 * OVER
	 */
	
	private boolean over = false;
	
	public void setOver(boolean over) {
		this.over = over;
	}
	
	public boolean isOver() {
		return over;
	}
}
