package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimDoublePendulum extends Game2 {

	public static void main(String[] args) {
		AnimDoublePendulum main = new AnimDoublePendulum();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Double pendulum animation");
		s.setSize(900, 800);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}

	public static final double L1 = 160;
	public static final double L2 = 160;
	public static final double M1 = 1;
	public static final double M2 = 1;
	public static final double G = 1800;
	public static final int SUBSTEPS = 40;
	public static final double DT = 0.0005;
	public static final int TRAIL_SIZE = 350;

	protected void initialize1() {
		Point1 pivot = p1(gameWidth()/2, 220);
		DoublePendulum pendulum = new DoublePendulum(pivot);
		addDraw(pendulum);
		addDyn(pendulum);
	}

	/*
	 * DOUBLE PENDULUM
	 */

	private class DoublePendulum extends Drawing1 implements Dyn {
		private final Point0 pivot;
		private double theta1;
		private double theta2;
		private double omega1 = 0;
		private double omega2 = 0;
		private Point1 pos1;
		private Point1 pos2;
		private final ArrayDeque<Point1> trail = new ArrayDeque<>();

		public DoublePendulum(Point0 pivot) {
			this.pivot = pivot;
			theta1 = Math.PI/2+UtilRandom.randomDouble(-0.05,0.05);
			theta2 = Math.PI/2+UtilRandom.randomDouble(-0.05,0.05);
			updatePositions();
		}

		public void goNext() {
			for(int i=0; i<SUBSTEPS; i++) step();
			updatePositions();
			trail.addLast(pos2);
			if(trail.size()>TRAIL_SIZE) trail.removeFirst();
		}

		public void goBack() {
		}

		private void step() {
			double den = 2*M1+M2-M2*Math.cos(2*theta1-2*theta2);

			double alpha1 = (-G*(2*M1+M2)*Math.sin(theta1)
					-M2*G*Math.sin(theta1-2*theta2)
					-2*Math.sin(theta1-theta2)*M2*(omega2*omega2*L2+omega1*omega1*L1*Math.cos(theta1-theta2)))
					/(L1*den);

			double alpha2 = (2*Math.sin(theta1-theta2)*(omega1*omega1*L1*(M1+M2)
					+G*(M1+M2)*Math.cos(theta1)
					+omega2*omega2*L2*M2*Math.cos(theta1-theta2)))
					/(L2*den);

			omega1 += alpha1*DT;
			omega2 += alpha2*DT;
			theta1 += omega1*DT;
			theta2 += omega2*DT;
		}

		private void updatePositions() {
			pos1 = pivot.pAdd(L1*Math.sin(theta1), L1*Math.cos(theta1));
			pos2 = pos1.pAdd(L2*Math.sin(theta2), L2*Math.cos(theta2));
		}

		protected void draw() {
			int size = trail.size();
			int i = 0;
			for(Point1 p : trail) {
				float frac = size<=1 ? 1 : (float) i/(size-1);
				Color color = Color.getHSBColor(0.55f+0.25f*frac, 0.9f, 1f);
				fillRoundC(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (200*frac)), p, 3);
				i++;
			}

			drawThickLine(new Color(200,200,200), pivot, pos1, 3);
			drawThickLine(new Color(200,200,200), pos1, pos2, 3);

			fillRoundC(new Color(80,80,80), pivot, 5);
			fillRoundC(Color.ORANGE, pos1, 11);
			fillRoundC(Color.CYAN, pos2, 11);
		}
	}
}
