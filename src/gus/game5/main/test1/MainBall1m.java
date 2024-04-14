package gus.game5.main.test1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.draw.Draw;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.control.PointControl;
import gus.game5.core.point.control.PointControl2Mouse;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControl;
import gus.game5.core.shape.control.ShapeControlBorderRebound;
import gus.game5.core.util.UtilRandom;

public class MainBall1m extends Game {

	public static void main(String[] args) {
		MainBall1m main = new MainBall1m();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Ball");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	

	private ShapeList<ShapeRound> balls;
	
	private ShapeControl borderRebound;
	private PointControl mouseControl;
	
	
	protected void initialize() {
		balls = new ShapeList<>();
		
		borderRebound = new ShapeControlBorderRebound(this);
		mouseControl = new PointControl2Mouse(this, 0.1);
	}

	protected void turn() {
		
		if(getCount()%200==0) {
			Point2 center = p2(200,100);
			center.setDerived(p2(3, Angle.random()));
			Color color = UtilRandom.randomColor();
			double radius = 10+UtilRandom.randomDouble(20);
			
			balls.add(new ShapeRound(center, radius, color));
		}
		
		for(ShapeRound ball : balls) {
			if(mouse().button1().isPressed()) 
				mouseControl.handle(ball.getAnchor());
			else ball.getAnchor().initDerived().setDerived(null);
		}
		
		balls.goNext();
		
		for(ShapeRound ball : balls) {
			borderRebound.handle(ball);
		}
		
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}

	protected Draw buildDraw() {
		return balls;
	}
}
