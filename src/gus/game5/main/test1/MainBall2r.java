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
import gus.game5.core.shape.Shape0.AnchorType;
import gus.game5.core.shape.control.ShapeControlBorderReboundR;

public class MainBall2r extends Game {
	
	public static final Point2 GRAVITY = new Point2(0, 0.1);
	

	public static void main(String[] args) {
		MainBall2r main = new MainBall2r();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Ball2");
		s.setWidth(1000);
		s.setHeight(600);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	

	private ShapeRound ball;
	private ShapeControlBorderReboundR borderRebound;
	private PointControl centerControl;
	
	
	protected void initialize() {

		Point2 center = p2(200,300);
		center.setDerived(p2(3, Angle.random()));
		center.getDerived().setDerived(GRAVITY);
		
		ball = new ShapeRound(center, 20, Color.RED, AnchorType.CENTER);
		
		borderRebound = new ShapeControlBorderReboundR(this, 0.03);
		borderRebound.setTop(false);
		
		centerControl = new PointControl2Mouse(this, 0.1);
	}

	protected void turn() {
		
		if(mouse().button1().isPressed()) 
			centerControl.handle(ball.getAnchor());
		else ball.getAnchor().getDerived().setDerived(GRAVITY);
		
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
