package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Iterator;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimHarmonograph extends Game2 {

	public static void main(String[] args) {
		AnimHarmonograph main = new AnimHarmonograph();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Harmonograph animation");
		s.setSize(900, 800);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}

	public static final double TIME_STEP = 0.02;
	public static final int MAX_TRAIL = 6000;
	public static final double FADE_THRESHOLD = 0.004;

	protected void initialize1() {
		Harmonograph harmonograph = new Harmonograph();
		addDraw(harmonograph);
		addDyn(harmonograph);
	}

	/*
	 * PENDULUM
	 */

	private static class Pendulum {
		final double amplitude;
		final double freq;
		final double phase;
		final double damping;

		Pendulum(double amplitude, double freqMin, double freqMax, double dampingMin, double dampingMax) {
			this.amplitude = amplitude;
			this.freq = UtilRandom.randomDouble(freqMin, freqMax);
			this.phase = UtilRandom.randomDouble(0, Math.PI*2);
			this.damping = UtilRandom.randomDouble(dampingMin, dampingMax);
		}

		double value(double t) {
			return amplitude*Math.sin(freq*t+phase)*Math.exp(-damping*t);
		}
	}

	/*
	 * HARMONOGRAPH
	 */

	private class Harmonograph extends Drawing1 implements Dyn {
		private Pendulum pendA;
		private Pendulum pendB;
		private Pendulum pendC;
		private double t;
		private final ArrayDeque<Point1> trail = new ArrayDeque<>();
		private final Point1 center;

		public Harmonograph() {
			center = p1(gameWidth()/2, gameHeight()/2);
			newPattern();
		}

		private void newPattern() {
			pendA = new Pendulum(260, 2.0, 3.2, 0.018, 0.045);
			pendB = new Pendulum(220, 2.9, 4.3, 0.018, 0.045);
			pendC = new Pendulum(180, 1.8, 2.6, 0.018, 0.045);
			t = 0;
			trail.clear();
		}

		public void goNext() {
			t += TIME_STEP;
			addPoint();

			double envelope = pendA.amplitude*Math.exp(-pendA.damping*t)
					+pendB.amplitude*Math.exp(-pendB.damping*t)
					+pendC.amplitude*Math.exp(-pendC.damping*t);
			if(envelope<FADE_THRESHOLD*(pendA.amplitude+pendB.amplitude+pendC.amplitude) && trail.size()>500)
				newPattern();
		}

		public void goBack() {
		}

		private void addPoint() {
			double x = pendA.value(t)+pendB.value(t);
			double y = pendB.value(t*1.001)+pendC.value(t);

			trail.addLast(p1(center.getX()+x, center.getY()+y));
			if(trail.size()>MAX_TRAIL) trail.pollFirst();
		}

		protected void draw() {
			int size = trail.size();
			if(size<2) return;

			Iterator<Point1> it = trail.iterator();
			Point1 previous = it.next();
			int index = 0;
			while(it.hasNext()) {
				Point1 current = it.next();
				float hue = 0.5f+0.45f*((float) index/size);
				drawLine(Color.getHSBColor(hue, 0.8f, 1f), previous, current);
				previous = current;
				index++;
			}
		}
	}
}
