package gus.game5.main.game.p1.o.asteroids;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Bullet {

	public static final double SPEED = 7.8;
	public static final double RADIUS = 2;
	public static final int LIFE = 68;


	private Point1 position;
	private Point1 speed;
	private int life;


	public Bullet(Point0 origin, Angle heading, Point0 inherited) {
		position = new Point1(origin);
		Field.wrap(position);
		speed = new Point1(SPEED, heading);
		speed.addXY(inherited);
		life = LIFE;
	}


	/*
	 * MOVE
	 */

	public void goNext() {
		position.addXY(speed);
		Field.wrap(position);
		life--;
	}


	/*
	 * STATE
	 */

	public Point1 getPosition() {return position;}
	public boolean isDead() {return life<=0;}
}
