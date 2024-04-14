package gus.game5.core.point.control;

import gus.game5.core.game.Game;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2;

public class PointControl1KbD4 implements PointControl {

	private Game game;
	private double speed;
	
	public PointControl1KbD4(Game game) {
		this.game = game;
	}
	
	public PointControl1KbD4(Game game, double speed) {
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
		
		Keyboard kb = game.keyboard();
		
		int dx = 0;
		if(kb.left()) dx -= 1;
		if(kb.right()) dx += 1;
		
		int dy = 0;
		if(kb.up()) dy -= 1;
		if(kb.down()) dy += 1;
		
		point.setDerived(dx*speed, dy*speed);
	}
}
