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
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.control.ShapeControl;
import gus.game5.core.shape.control.ShapeControlBorderRebound;

public class MainBall1 extends Game {
	
	

	public static void main(String[] args) {
		MainBall1 main = new MainBall1();
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
	
	

	private ShapeRound ball;
	private ShapeControl borderRebound;
	private PointControl centerControl;
	
	
	protected void initialize() {

		Point2 center = p2(200,100);
		center.setDerived(p2(3, Angle.random()));
		
		ball = new ShapeRound(center, 20, Color.RED);
		
		borderRebound = new ShapeControlBorderRebound(this);
		centerControl = new PointControl2Mouse(this, 0.1);
	}

	protected void turn() {
		
		if(mouse().button1().isPressed()) 
			centerControl.handle(ball.getAnchor());
		else ball.getAnchor().initDerived().setDerived(null);
		
		ball.goNext();
		borderRebound.handle(ball);
		
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}

	protected Draw buildDraw() {
		return ball;
	}
}
