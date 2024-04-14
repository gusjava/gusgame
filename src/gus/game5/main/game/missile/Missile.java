package gus.game5.main.game.missile;

import java.awt.Color;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Missile extends Element {
	public final static int SIZE = 3;

	public Missile(Point1 start, Point0 target, Color color, double speed, double blastSize) {
		super(start, target, color, SIZE, speed, blastSize);
		initOrigin();
	}
	public void draw() {
	}
}