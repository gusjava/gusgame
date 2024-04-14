package gus.game5.core.point.control;

import gus.game5.core.game.Game;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;

public class PointControl1Mouse implements PointControl {

	private Game game;
	private double speed;
	
	public PointControl1Mouse(Game game) {
		this.game = game;
	}
	
	public PointControl1Mouse(Game game, double speed) {
		this.game = game;
		this.speed = speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void handle(Point2 point) {
		if(point==null) return;
		
		Point0 pm = game.mouse().pointCurrent();
		Point1 pv = pm!=null ? pm.pSub(point).pDistSet(speed) : null;
		
		point.setDerived(pv);
	}
}
