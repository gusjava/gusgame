package gus.game5.main.l1.bloon.type;

import java.awt.Color;

import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilList;
import gus.game5.main.l1.bloon.BloonConst;
import gus.game5.main.l1.bloon.GameBloon;

public abstract class Bloon extends ShapeRound {
	private GameBloon game;
	private int targetIndex;
	private Point1 target;
	private double speed;
	private double bloonLife;
	
	public Bloon(GameBloon game, double speed, double bloonLife) {
		super(game.getPath().get(0), BloonConst.BLOON_RADIUS);
		this.speed = speed;
		this.bloonLife = bloonLife;
		this.game = game;
		setTarget(1);
	}
	
	public void goNext() {
		super.goNext();
		if(getAnchor().near(target, speed)) {
			setTarget(targetIndex+1);
		}
		
		if(isOver()) {
			game.setLife(game.getLife() - bloonLife);
			if(game.getLife()<0) game.handleGameOver();
		}
	}
	
	protected void drawShape() {
		super.drawShape();
		drawRoundC(Color.BLACK, getRadius());
	}
	
	public boolean isOver() {
		return target==null || bloonLife<=0;
	}
	
	private void setTarget(int targetIndex) {
		this.targetIndex = targetIndex;
		target = UtilList.get(game.getPath(), targetIndex);
		getAnchor().setDerived(target, speed);
	}
	
	public void damage(int damage) {
		bloonLife -= damage;
	}
}
