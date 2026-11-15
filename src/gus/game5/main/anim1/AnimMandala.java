package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class AnimMandala extends Game1 {

	public static final long SLEEP = 20;
	public static final int BRANCHES = 10;
	public static final double R_OUTER = 260;
	public static final double R_INNER = 90;
	public static final double SPREAD_DEG = 14;
	public static final double TIME_STEP = 0.02;

	public static void main(String[] args) {
		AnimMandala main = new AnimMandala();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Mandala");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(Color.BLACK);
	}

	/*
	 * DATA
	 */

	private double t;

	protected void initialize1() {
		t = 0;
		addDraw(new Rosace());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();
		t += TIME_STEP;
	}

	/*
	 * ROSACE
	 */

	private class Rosace extends Drawing1 {

		protected void draw() {
			Point1 center = gameCenter();
			double breathe = 1+0.15*Math.sin(t*0.6);
			Angle rotation = Angle.angleRad(t*0.15);
			Angle part = Angle.anglePart(BRANCHES);

			for(int i=0;i<BRANCHES;i++) {
				Angle branch = part.mult(i).add(rotation);
				drawPetal(center, branch, breathe, i);
			}
		}

		private void drawPetal(Point1 center, Angle branch, double breathe, int index) {
			Point0 tip = center.pAdd(R_OUTER*breathe, branch);
			Point0 left = center.pAdd(R_INNER, branch.addDeg(SPREAD_DEG));
			Point0 right = center.pAdd(R_INNER, branch.subDeg(SPREAD_DEG));

			float hue = (float) (((double) index/BRANCHES + t*0.05) % 1.0);
			Color color = Color.getHSBColor(hue, 0.75f, 1f);

			fillPoly(color, center, left, tip, right);
			fillRoundC(Color.WHITE, tip, 4);
		}
	}
}
