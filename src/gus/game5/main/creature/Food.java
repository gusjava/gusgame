package gus.game5.main.creature;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeRound;

public class Food extends ShapeRound {

	public Food(Point0 anchor) {
		super(anchor, CreatureConst.FOOD_RADIUS, CreatureConst.FOOD_COLOR);
	}

	/*
	 * EATEN
	 */

	private boolean eaten = false;

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

	public boolean isOver() {
		return eaten;
	}
}
