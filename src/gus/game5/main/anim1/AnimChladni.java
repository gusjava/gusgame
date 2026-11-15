package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimChladni extends Game2 {

	public static void main(String[] args) {
		AnimChladni main = new AnimChladni();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Chladni plate animation");
		s.setSize(900, 900);
		s.setSleep(16);
		s.setBackground(Color.BLACK);
	}

	public static final int PARTICLE_COUNT = 1600;
	public static final double PLATE_SIZE = 760;
	public static final double SPEED = 0.006;
	public static final double JITTER = 0.0035;
	public static final double GRADIENT_H = 0.01;
	public static final int CYCLE_TICKS = 900;
	public static final int[][] MODES = {
		{2,3}, {3,4}, {1,4}, {4,5}, {2,5}, {3,5}, {1,3}, {5,6}
	};

	protected void initialize1() {
		ChladniField field = new ChladniField();
		addDraw(field);
		addDyn(field);
	}

	/*
	 * CHLADNI FIELD
	 */

	private class ChladniField extends Drawing1 implements Dyn {
		private final double[] px = new double[PARTICLE_COUNT];
		private final double[] py = new double[PARTICLE_COUNT];
		private int modeN;
		private int modeM;
		private int cycleTimer;
		private final Point1 center;

		public ChladniField() {
			center = p1(gameWidth()/2, gameHeight()/2);
			newMode();
		}

		private void newMode() {
			int[] mode = UtilRandom.randomElement(MODES);
			modeN = mode[0];
			modeM = mode[1];
			cycleTimer = CYCLE_TICKS;

			for(int i=0; i<PARTICLE_COUNT; i++) {
				px[i] = UtilRandom.randomDouble(-1, 1);
				py[i] = UtilRandom.randomDouble(-1, 1);
			}
		}

		public void goNext() {
			cycleTimer--;
			if(cycleTimer<=0) {
				newMode();
				return;
			}

			for(int i=0; i<PARTICLE_COUNT; i++) {
				double x = px[i];
				double y = py[i];

				double f = chladni(x, y);
				double dfx = (chladni(x+GRADIENT_H, y)-chladni(x-GRADIENT_H, y))/(2*GRADIENT_H);
				double dfy = (chladni(x, y+GRADIENT_H)-chladni(x, y-GRADIENT_H))/(2*GRADIENT_H);

				double sign = Math.signum(f);
				double vx = -sign*dfx;
				double vy = -sign*dfy;
				double norm = Math.sqrt(vx*vx+vy*vy)+1e-9;

				x += SPEED*vx/norm+UtilRandom.randomDouble(-JITTER, JITTER);
				y += SPEED*vy/norm+UtilRandom.randomDouble(-JITTER, JITTER);

				if(x<-1) x = -1;
				if(x>1) x = 1;
				if(y<-1) y = -1;
				if(y>1) y = 1;

				px[i] = x;
				py[i] = y;
			}
		}

		public void goBack() {
		}

		private double chladni(double x, double y) {
			return Math.cos(modeN*Math.PI*x)*Math.cos(modeM*Math.PI*y)
					-Math.cos(modeM*Math.PI*x)*Math.cos(modeN*Math.PI*y);
		}

		protected void draw() {
			double half = PLATE_SIZE/2;
			drawRectC(new Color(60,60,80), center, PLATE_SIZE, PLATE_SIZE);

			for(int i=0; i<PARTICLE_COUNT; i++) {
				Point1 p = p1(center.getX()+px[i]*half, center.getY()+py[i]*half);
				fillRoundC(Color.WHITE, p, 1.1);
			}
		}
	}
}
