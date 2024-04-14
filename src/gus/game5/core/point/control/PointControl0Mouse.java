package gus.game5.core.point.control;

import gus.game5.core.game.Game;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class PointControl0Mouse implements PointControl {

	private Game game;
	
	public PointControl0Mouse(Game game) {
		this.game = game;
	}

	public void handle(Point2 point) {
		Point0 pm = game.mouse().pointCurrent();
		
		if(pm!=null) point.setXY(pm);
		point.setDerived(null);
	}
}
