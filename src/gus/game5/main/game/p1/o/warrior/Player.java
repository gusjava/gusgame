package gus.game5.main.game.p1.o.warrior;

import java.awt.Color;
import java.awt.Paint;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game;
import gus.game5.core.point.control.PointControl;
import gus.game5.core.point.control.PointControl1Mouse;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;

public class Player extends ShapeRound {
	
	public static final double RADIUS = 10;
	public static final Color COLOR = Color.BLUE;
	public static final double SPEED1 = 3;
	public static final double SPEED2 = 12;
	public static final double ENERGY_MAX = 20;
	
	private Game game;
	private PointControl control;
	private PointControl control1;
	private PointControl control2;
	
	private double energy;
	

	public Player(Game game, double x, double y) {
		super(new Point2(x,y), RADIUS, COLOR, AnchorType.CENTER);
		
		this.game = game;
		control1 = new PointControl1Mouse(game, SPEED1);
		control2 = new PointControl1Mouse(game, SPEED2);
		energy = ENERGY_MAX;
	}
	
	public void stopMotion() {
		control = null;
		setColor(Color.BLACK);
	}
	
	public void initControl1() {
		control = control1;
		setColor(Color.BLUE);
	}
	
	public void initControl2() {
		control = control2;
		setColor(Color.RED);
	}
	
	public void goNext() {
		if(control!=null) control.handle(getAnchor());
		else getAnchor().setDerived(null);
		super.goNext();
	}
	
	
	public void draw() {
		super.draw();
		Angle mouseAngle = mouseAngle();
		if(mouseAngle!=null) {
			Angle angle1 = mouseAngle.addDeg(-25);
			Angle angle2 = Angle.angleDeg(50);
			
			double dist = 100;
			Paint paint = buildCircularGradient(p(0,0), dist, getColor(), Color.WHITE);
			fillArcC(paint, p(0,0), dist, angle1, angle2);
		}
	}
	
	public Angle mouseAngle() {
		Point0 p = game.mouse().pointCurrent();
		return p!=null ? p.pSub(getAnchor()).angle() : null;
	}
	
	public double getEnergy() {
		return energy;
	}
}
