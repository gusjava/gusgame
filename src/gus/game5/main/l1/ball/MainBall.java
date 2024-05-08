package gus.game5.main.l1.ball;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControlBorderReboundR;
import gus.game5.core.util.UtilRandom;

public class MainBall extends Game1 {
	
	public static void main(String[] args) {
		MainBall main = new MainBall();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Ball");
		s.setWidth(800);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}
	
	private ShapeList<Ball> balls;
	private ShapeControlBorderReboundR control;
	
	
	protected void initialize1() {
		balls = newShapeList();
		control = new ShapeControlBorderReboundR(this, 0.01);
		
		for(int i=0; i<15; i++) {
			balls.add(new Ball(UtilRandom.randomPoint1(this)));
		}
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();	
		
		goNext();
		balls.control(control);
	}
	
	private class Ball extends ShapeRound {
		public Ball(Point1 p) {
			super(p, UtilRandom.randomInt(5, 30));
			setColor(UtilRandom.randomColor());
			
			getAnchor().initDerived().setXY(UtilRandom.randomInt(-5, 5), UtilRandom.randomInt(-5, 5));
			getAnchor().getDerived().initDerived().setXY(UtilRandom.randomDouble(-0.2, 0.2), UtilRandom.randomDouble(-0.2, 0.2));
			
			setColor(Color.WHITE);
		}
	}
	
}
