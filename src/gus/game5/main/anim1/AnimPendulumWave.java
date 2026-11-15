package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class AnimPendulumWave extends Game2 {

	public static void main(String[] args) {
		AnimPendulumWave main = new AnimPendulumWave();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Pendulum wave animation");
		s.setSize(1500, 620);
		s.setSleep(10);
		s.setBackground(new Color(15,15,35));
	}

	public static final int NUMBER = 15;
	public static final int MARGIN_X = 60;
	public static final int ATTACH_Y = 70;
	public static final double LENGTH_MIN = 80;
	public static final double LENGTH_STEP = 15;
	public static final double AMPLITUDE = Angle.degToRad(24);
	public static final double BASE_OMEGA = 0.4;
	public static final double BOB_RADIUS = 9;

	protected void initialize1() {
		addDraw(new Wave());
	}

	/*
	 * WAVE
	 */

	private class Wave extends Drawing1 {
		private final Point1[] attaches = new Point1[NUMBER];
		private final double[] lengths = new double[NUMBER];
		private final double[] omegas = new double[NUMBER];
		private final Color[] colors = new Color[NUMBER];

		public Wave() {
			double spacing = (gameWidth()-2.0*MARGIN_X)/(NUMBER-1);
			for(int i=0; i<NUMBER; i++) {
				attaches[i] = p(MARGIN_X+i*spacing, ATTACH_Y);
				lengths[i] = LENGTH_MIN+i*LENGTH_STEP;
				omegas[i] = BASE_OMEGA/Math.sqrt(lengths[i]);
				colors[i] = Color.getHSBColor((float) i/NUMBER, 0.85f, 1f);
			}
		}

		protected void draw() {
			for(int i=0; i<NUMBER; i++) {
				double theta = AMPLITUDE*Math.cos(omegas[i]*getCount());
				Angle angle = Angle.ANGLE90.add(theta);
				Point0 bob = attaches[i].pAdd(lengths[i], angle);

				drawThickLine(new Color(120,120,140), attaches[i], bob, 2);
				fillRoundC(colors[i], bob, BOB_RADIUS);
			}
		}
	}
}
