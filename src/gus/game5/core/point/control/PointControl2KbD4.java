package gus.game5.core.point.control;

import gus.game5.core.game.Game;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2;

public class PointControl2KbD4 implements PointControl {

	private Game game;
	private double acc;
	
	public PointControl2KbD4(Game game) {
		this.game = game;
	}
	
	public PointControl2KbD4(Game game, double acc) {
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
		
		Keyboard kb = game.keyboard();
		
		int dx = 0;
		if(kb.left()) dx -= 1;
		if(kb.right()) dx += 1;
		
		int dy = 0;
		if(kb.up()) dy -= 1;
		if(kb.down()) dy += 1;
		
		point.initDerived().setDerived(dx*acc, dy*acc);
	}
}
