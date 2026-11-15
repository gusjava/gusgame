package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Iterator;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimSpirograph extends Game1 {

	public static final long SLEEP = 16;
	public static final double THETA_STEP = 0.025;
	public static final int MAX_TRAIL = 3500;
	public static final int CYCLE_TICKS = 2800;

	public static void main(String[] args) {
		AnimSpirograph main = new AnimSpirograph();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Spirograph");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	/*
	 * DATA
	 */

	private double bigR;
	private double smallR;
	private double d;
	private double theta;
	private int cycleTimer;
	private ArrayDeque<Point1> trail;

	protected void initialize1() {
		trail = new ArrayDeque<>();
		newPattern();
		addDraw(new Curve());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		theta += THETA_STEP;
		cycleTimer--;
		if(cycleTimer<=0) newPattern();

		addPoint();
	}

	private void newPattern() {
		bigR = UtilRandom.randomDouble(120, 260);
		smallR = UtilRandom.randomDouble(20, 90);
		d = UtilRandom.randomDouble(5, smallR*1.2);
		theta = 0;
		cycleTimer = CYCLE_TICKS;
		trail.clear();
	}

	private void addPoint() {
		double ratio = (bigR-smallR)/smallR;
		double x = (bigR-smallR)*Math.cos(theta) + d*Math.cos(ratio*theta);
		double y = (bigR-smallR)*Math.sin(theta) - d*Math.sin(ratio*theta);

		Point1 center = gameCenter();
		trail.addLast(p1(center.getX()+x, center.getY()+y));
		if(trail.size()>MAX_TRAIL) trail.pollFirst();
	}

	/*
	 * CURVE
	 */

	private class Curve extends Drawing1 {

		protected void draw() {
			int size = trail.size();
			if(size<2) return;

			Iterator<Point1> it = trail.iterator();
			Point1 previous = it.next();
			int index = 0;
			while(it.hasNext()) {
				Point1 current = it.next();
				float hue = (float) index/size;
				drawLine(Color.getHSBColor(hue, 0.8f, 1f), previous, current);
				previous = current;
				index++;
			}
		}
	}
}
