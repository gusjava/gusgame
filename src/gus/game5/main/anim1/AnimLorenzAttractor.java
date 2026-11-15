package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimLorenzAttractor extends Game2 {

	public static void main(String[] args) {
		AnimLorenzAttractor main = new AnimLorenzAttractor();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Lorenz attractor animation");
		s.setSize(900, 800);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}

	public static final double SIGMA = 10;
	public static final double RHO = 28;
	public static final double BETA = 8.0/3.0;
	public static final int SUBSTEPS = 20;
	public static final double DT = 0.0025;
	public static final double SCALE = 11;
	public static final int TRAIL_SIZE = 2500;

	protected void initialize1() {
		Lorenz lorenz = new Lorenz();
		addDraw(lorenz);
		addDyn(lorenz);
	}

	/*
	 * LORENZ
	 */

	private class Lorenz extends Drawing1 implements Dyn {
		private double x;
		private double y;
		private double z;
		private final Point1 center;
		private final ArrayDeque<Point1> trail = new ArrayDeque<>();

		public Lorenz() {
			x = UtilRandom.randomDouble(-5, 5);
			y = UtilRandom.randomDouble(-5, 5);
			z = 15+UtilRandom.randomDouble(-5, 5);
			center = p1(gameWidth()/2, gameHeight()/2);
		}

		public void goNext() {
			for(int i=0; i<SUBSTEPS; i++) step();
			trail.addLast(project());
			if(trail.size()>TRAIL_SIZE) trail.removeFirst();
		}

		public void goBack() {
		}

		private void step() {
			double dx = SIGMA*(y-x);
			double dy = x*(RHO-z)-y;
			double dz = x*y-BETA*z;
			x += dx*DT;
			y += dy*DT;
			z += dz*DT;
		}

		private Point1 project() {
			return p1(center.getX()+x*SCALE, center.getY()-(z-RHO)*SCALE);
		}

		protected void draw() {
			int size = trail.size();
			if(size<2) return;

			int i = 0;
			Point1 previous = null;
			for(Point1 p : trail) {
				if(previous!=null) {
					float frac = (float) i/(size-1);
					Color color = Color.getHSBColor(0.55f+0.35f*frac, 0.85f, 1f);
					int alpha = Math.max(0, (int) (255*frac));
					drawLine(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha), previous, p);
				}
				previous = p;
				i++;
			}

			fillRoundC(Color.WHITE, previous, 4);
		}
	}
}
