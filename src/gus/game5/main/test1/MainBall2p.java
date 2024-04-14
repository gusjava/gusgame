package gus.game5.main.test1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.control.PointControl;
import gus.game5.core.point.control.PointControl2Mouse;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRect;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.Shape0.AnchorType;
import gus.game5.core.shape.control.ShapeControlBlockRebound;
import gus.game5.core.shape.control.ShapeControlBorderReboundR;

public class MainBall2p extends Game1 {
	
	public static final Point2 GRAVITY = new Point2(0, 0.1);
	

	public static void main(String[] args) {
		MainBall2p main = new MainBall2p();
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
	private ShapeList<ShapeRect> plateforms;
	private ShapeControlBorderReboundR borderRebound;
	private ShapeControlBlockRebound<ShapeRect> blockRebound;
	private PointControl centerControl;
	
	
	protected void initialize1() {

		Point2 center = p2(150,200);
		center.setDerived(p2(3, Angle.randomDeg(-50).addDeg(-30)));
		center.getDerived().setDerived(GRAVITY);
		
		ball = new ShapeRound(center, 15, Color.RED, AnchorType.CENTER);
		
		centerControl = new PointControl2Mouse(this, 0.1);
		addDraw(ball);
		
		plateforms = newShapeList();
		plateforms.add(plateform(100, 300, 400, 20));
		
		borderRebound = new ShapeControlBorderReboundR(this, 0.03);
		borderRebound.setTop(false);
		
		blockRebound = new ShapeControlBlockRebound<>(plateforms);
	}

	protected void turn() {
		
		if(mouse().button1().isPressed()) 
			centerControl.handle(ball.getAnchor());
		else ball.getAnchor().getDerived().setDerived(GRAVITY);
		
		ball.goNext();
		borderRebound.handle(ball);
		blockRebound.handle(ball);
		
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}

	
	/*
	 * PLATEFORM
	 */
	
	private ShapeRect plateform(double x0, double y0, double width, double height) {
		return new ShapeRect(p1(x0, y0), width, height, Color.BLACK, AnchorType.NW);
	}
}
