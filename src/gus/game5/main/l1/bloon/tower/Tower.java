package gus.game5.main.l1.bloon.tower;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeSquare;
import gus.game5.main.l1.bloon.BloonConst;
import gus.game5.main.l1.bloon.GameBloon;
import gus.game5.main.l1.bloon.missile.Missile;
import gus.game5.main.l1.bloon.type.Bloon;

public abstract class Tower extends ShapeSquare {

	private GameBloon game;
	protected double range;
	private long countLastMissile = -1;
	private int shotCount = 0;
	
	public Tower(GameBloon game, Point0 anchor) {
		super(anchor, BloonConst.TOWER_LENGTH);
		this.game = game;
	}

	protected void drawShape() {
		super.drawShape();
		drawRoundC(range);
		drawString(p(15,10), ""+shotCount);
	}
	
	public double getRange() {
		return range;
	}
	
	public void targetBloon(Bloon bloon) {
		if(getCount()<countLastMissile + 10) return;
		Missile missile = new Missile(getAnchor(), bloon.getAnchor());
		game.getMissiles().add(missile);
		countLastMissile = getCount();
		shotCount++;
	}
}
