package gus.game5.main.game.warrior;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;

public class Bullet extends ShapeRound {
	
	public static final double RADIUS = 3;
	public static final Color COLOR = Color.GRAY;
	public static final double SPEED = 6;
	public static final int INIT_LIFE = 500;
	
	private int life;

	public Bullet(Point0 start, Angle angle) {
		super(new Point2(start), RADIUS, AnchorType.CENTER);
		getAnchor().setDerived(SPEED, angle);
		setColor(COLOR);
		life = INIT_LIFE;
	}
	
	public int getLife() {
		return life;
	}
	
	public void goNext() {
		super.goNext();
		life--;
	}
}
