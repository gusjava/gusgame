package gus.game5.main.game.p1.o.asteroids;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class Ship {

	public static final double RADIUS = 10;
	public static final double NOSE = 17;
	public static final double SIDE = 12;
	public static final double EXTENT = 23;
	public static final Angle SIDE_ANGLE = Angle.angleDeg(142);

	public static final double THRUST = 0.135;
	public static final double FRICTION = 0.9925;
	public static final double SPEED_MAX = 7.2;
	public static final double ROTATION = 4.2;

	public static final int BLINK = 6;


	private Point1 position;
	private Point1 speed;
	private Angle heading;
	private boolean thrusting;
	private int shield;


	public Ship() {
		position = new Point1(Field.W/2, Field.H/2);
		speed = new Point1();
		heading = Angle.ANGLE270;
	}


	/*
	 * CONTROL
	 */

	public void control(boolean left, boolean right, boolean up) {
		if(left) heading = heading.addDeg(-ROTATION);
		if(right) heading = heading.addDeg(ROTATION);
		thrusting = up;
		if(!up) return;
		speed.addXY(THRUST, heading);
		if(speed.dist()>SPEED_MAX) speed.setDist(SPEED_MAX);
	}

	public void hyperspace() {
		position.setXY(UtilRandom.randomDouble(Field.W), UtilRandom.randomDouble(Field.H));
		speed.setXY(0, 0);
		thrusting = false;
	}


	/*
	 * MOVE
	 */

	public void goNext() {
		position.addXY(speed);
		Field.wrap(position);
		speed.multXY(FRICTION);
		if(shield>0) shield--;
	}


	/*
	 * SHAPE
	 */

	public Point0 nose(Point0 center) {
		return center.pAdd(NOSE, heading);
	}

	public Point0[] outline(Point0 center) {
		return new Point0[] {
			center.pAdd(NOSE, heading),
			center.pAdd(SIDE, heading.add(SIDE_ANGLE)),
			center.pAdd(SIDE*0.45, heading.addDeg(180)),
			center.pAdd(SIDE, heading.add(SIDE_ANGLE).addDeg(76))
		};
	}

	public Point0[] flame(Point0 center) {
		Angle back = heading.addDeg(180);
		return new Point0[] {
			center.pAdd(SIDE*0.5, heading.add(SIDE_ANGLE)),
			center.pAdd(SIDE*0.5, heading.add(SIDE_ANGLE).addDeg(76)),
			center.pAdd(SIDE + UtilRandom.randomDouble(3, 11), back)
		};
	}


	/*
	 * STATE
	 */

	public Point1 getPosition() {return position;}
	public Point1 getSpeed() {return speed;}
	public Angle getHeading() {return heading;}
	public boolean isThrusting() {return thrusting;}

	public void setShield(int shield) {this.shield = shield;}
	public boolean isShielded() {return shield>0;}

	public boolean isVisible() {
		return shield==0 || (shield/BLINK)%2==0;
	}
}
