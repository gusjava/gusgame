package gus.game5.main.game.p1.o.asteroids;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class Asteroid {

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;

	public static final double[] RADIUS = {13, 25, 45};
	public static final int[] SCORE = {100, 50, 20};
	public static final double[] SPEED_MIN = {1.5, 1.0, 0.6};
	public static final double[] SPEED_MAX = {2.9, 1.9, 1.3};

	public static final int VERTICES = 11;
	public static final double SHAPE_MIN = 0.70;
	public static final double SHAPE_MAX = 1.12;
	public static final double HIT_FACTOR = 0.90;
	public static final double SPIN_MAX = 0.7;
	public static final int PIECES = 2;


	private int size;
	private Point1 position;
	private Point1 speed;
	private Angle rotation;
	private double spin;
	private double[] radii;


	public Asteroid(int size, Point0 p) {
		this.size = size;
		position = new Point1(p);
		Field.wrap(position);
		speed = new Point1(UtilRandom.randomDouble(SPEED_MIN[size], SPEED_MAX[size]), Angle.random());
		rotation = Angle.random();
		spin = UtilRandom.randomDouble(-SPIN_MAX, SPIN_MAX);
		radii = new double[VERTICES];
		for(int i=0;i<VERTICES;i++)
			radii[i] = RADIUS[size]*UtilRandom.randomDouble(SHAPE_MIN, SHAPE_MAX);
	}


	/*
	 * MOVE
	 */

	public void goNext() {
		position.addXY(speed);
		Field.wrap(position);
		rotation = rotation.addDeg(spin);
	}


	/*
	 * SPLIT
	 */

	public List<Asteroid> split() {
		List<Asteroid> pieces = new ArrayList<>();
		if(size==SMALL) return pieces;
		for(int i=0;i<PIECES;i++) pieces.add(new Asteroid(size-1, position));
		return pieces;
	}


	/*
	 * SHAPE
	 */

	public Point0[] outline(Point0 center) {
		Point0[] pp = new Point0[VERTICES];
		for(int i=0;i<VERTICES;i++)
			pp[i] = center.pAdd(radii[i], rotation.addDeg(i*360.0/VERTICES));
		return pp;
	}


	/*
	 * STATE
	 */

	public int getSize() {return size;}
	public int getScore() {return SCORE[size];}
	public Point1 getPosition() {return position;}
	public double getRadius() {return RADIUS[size];}
	public double getHitRadius() {return RADIUS[size]*HIT_FACTOR;}
}
