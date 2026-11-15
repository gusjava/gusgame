package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.util.UtilRandom;

public class AnimStarfield extends Game2 {

	public static final long SLEEP = 16;

	public static final int STAR_COUNT = 420;
	public static final double SPEED_BASE = 0.6;
	public static final double SPEED_FACTOR = 0.028;
	public static final double TRAIL_FACTOR = 0.55;

	public static final Color COLOR_BG = Color.BLACK;


	public static void main(String[] args) {
		AnimStarfield main = new AnimStarfield();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Starfield");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private ShapeList<Star> stars;

	protected void initialize1() {
		stars = newShapeList();
		Point2 center = p2(gameWidth()/2.0, gameHeight()/2.0);
		for(int i=0;i<STAR_COUNT;i++) {
			stars.add(new Star(center));
		}
	}

	/*
	 * STAR
	 */

	private class Star extends Shape0 {

		private Angle angle;
		private double distance;

		public Star(Point2 center) {
			super(center, 1, 1);
			respawn();
		}

		private void respawn() {
			angle = Angle.random();
			distance = UtilRandom.randomDouble(0, 40);
		}

		public void goNext() {
			super.goNext();
			distance += SPEED_BASE + distance*SPEED_FACTOR;
			if(distance>maxDistance()) respawn();
		}

		private double maxDistance() {
			return Math.max(gameWidth(), gameHeight())*0.75;
		}

		protected void drawShape() {
			Point0 tip = angle.pointAt(distance);
			Point0 tail = angle.pointAt(Math.max(0, distance-distance*TRAIL_FACTOR-2));
			float brightness = (float) Math.min(1, 0.3+distance/maxDistance());
			Color color = new Color(brightness, brightness, (float) Math.min(1, brightness+0.1));
			double thickness = Math.min(3, 0.5+distance*0.01);
			drawThickLine(color, tail, tip, thickness);
		}
	}
}
