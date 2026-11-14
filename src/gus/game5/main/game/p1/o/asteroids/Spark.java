package gus.game5.main.game.p1.o.asteroids;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class Spark {

	public static final double SPEED_MIN = 0.6;
	public static final double SPEED_MAX = 3.2;
	public static final int LIFE_MIN = 18;
	public static final int LIFE_MAX = 46;
	public static final double FRICTION = 0.97;
	public static final double LENGTH = 3.5;


	private Point1 position;
	private Point1 speed;
	private int life;
	private int lifeMax;


	public Spark(Point0 origin, double spread) {
		position = new Point1(origin);
		position.addXY(UtilRandom.randomDouble(spread), Angle.random());
		Field.wrap(position);
		speed = new Point1(UtilRandom.randomDouble(SPEED_MIN, SPEED_MAX), Angle.random());
		lifeMax = UtilRandom.randomInt(LIFE_MIN, LIFE_MAX);
		life = lifeMax;
	}


	/*
	 * MOVE
	 */

	public void goNext() {
		position.addXY(speed);
		Field.wrap(position);
		speed.multXY(FRICTION);
		life--;
	}


	/*
	 * STATE
	 */

	public Point1 getPosition() {return position;}
	public boolean isDead() {return life<=0;}

	public Point0 getTail(Point0 center) {
		return center.pSub(speed.pMult(LENGTH));
	}

	public double getFade() {
		return (double)life/lifeMax;
	}
}
