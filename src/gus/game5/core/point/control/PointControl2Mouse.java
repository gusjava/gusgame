package gus.game5.core.point.control;

import gus.game5.core.game.Game;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;

public class PointControl2Mouse implements PointControl {

	private Game game;
	private double acc;
	
	public PointControl2Mouse(Game game) {
		this.game = game;
	}
	
	public PointControl2Mouse(Game game, double acc) {
		this.game = game;
		this.acc = acc;
	}
	
	public void setAcc(double acc) {
		this.acc = acc;
	}
	
	public double getAcc() {
		return acc;
	}

	public void handle(Point2 point) {
		if(point==null) return;
		
		Point0 pm = game.mouse().pointCurrent();
		Point1 pa = pm!=null ? pm.pSub(point).pDistSet(acc) : null;
		
		point.initDerived().setDerived(pa);
	}
}
