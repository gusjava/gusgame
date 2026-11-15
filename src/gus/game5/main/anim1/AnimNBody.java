package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimNBody extends Game2 {

	public static void main(String[] args) {
		AnimNBody main = new AnimNBody();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("N-body gravitation animation");
		s.setSize(900, 800);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}

	public static final int BODY_COUNT = 5;
	public static final double G_CONST = 900;
	public static final double SOFTENING = 45;
	public static final double VIRIAL_RATIO = 0.35;
	public static final double MAX_SPEED = 260;
	public static final double SAFE_RADIUS = 330;
	public static final double CONTAINMENT_K = 6;
	public static final int SUBSTEPS = 6;
	public static final double DT = 0.02;
	public static final int TRAIL_SIZE = 400;
	public static final Color[] PALETTE = {
		new Color(255,120,120), new Color(120,200,255), new Color(255,220,100),
		new Color(150,255,150), new Color(220,140,255), new Color(255,170,90)
	};

	protected void initialize1() {
		NBodySystem system = new NBodySystem();
		addDraw(system);
		addDyn(system);
	}

	/*
	 * BODY
	 */

	private class Body {
		double x;
		double y;
		double vx;
		double vy;
		final double mass;
		final Color color;
		final ArrayDeque<Point1> trail = new ArrayDeque<>();

		Body(double x, double y, double vx, double vy, double mass, Color color) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.mass = mass;
			this.color = color;
		}
	}

	/*
	 * N BODY SYSTEM
	 */

	private class NBodySystem extends Drawing1 implements Dyn {
		private final Body[] bodies;
		private final Point1 center;

		public NBodySystem() {
			center = p1(gameWidth()/2, gameHeight()/2);
			bodies = new Body[BODY_COUNT];

			double[] radii = new double[BODY_COUNT];
			double[] angles = new double[BODY_COUNT];
			double[] masses = new double[BODY_COUNT];
			double[] px = new double[BODY_COUNT];
			double[] py = new double[BODY_COUNT];
			double totalMass = 0;

			for(int i=0; i<BODY_COUNT; i++) {
				radii[i] = 90+i*45+UtilRandom.randomDouble(-12, 12);
				angles[i] = i*(Math.PI*2/BODY_COUNT)+UtilRandom.randomDouble(-0.3, 0.3);
				masses[i] = UtilRandom.randomDouble(400, 1200);
				totalMass += masses[i];
				px[i] = center.getX()+radii[i]*Math.cos(angles[i]);
				py[i] = center.getY()+radii[i]*Math.sin(angles[i]);
			}

			double potentialEnergy = 0;
			for(int i=0; i<BODY_COUNT; i++)
				for(int j=i+1; j<BODY_COUNT; j++) {
					double dx = px[j]-px[i];
					double dy = py[j]-py[i];
					double dist = Math.sqrt(dx*dx+dy*dy+SOFTENING*SOFTENING);
					potentialEnergy -= G_CONST*masses[i]*masses[j]/dist;
				}

			double targetKineticEnergy = Math.abs(potentialEnergy)*VIRIAL_RATIO;
			double commonSpeed = Math.sqrt(2*targetKineticEnergy/totalMass);

			double[] vx = new double[BODY_COUNT];
			double[] vy = new double[BODY_COUNT];
			double momentumX = 0;
			double momentumY = 0;

			for(int i=0; i<BODY_COUNT; i++) {
				double angle = angles[i];
				vx[i] = -commonSpeed*Math.sin(angle);
				vy[i] = commonSpeed*Math.cos(angle);
				momentumX += masses[i]*vx[i];
				momentumY += masses[i]*vy[i];
			}

			double driftVx = momentumX/totalMass;
			double driftVy = momentumY/totalMass;

			for(int i=0; i<BODY_COUNT; i++)
				bodies[i] = new Body(px[i], py[i], vx[i]-driftVx, vy[i]-driftVy, masses[i], PALETTE[i%PALETTE.length]);
		}

		public void goNext() {
			for(int s=0; s<SUBSTEPS; s++) step();

			for(Body b : bodies) {
				b.trail.addLast(p1(b.x, b.y));
				if(b.trail.size()>TRAIL_SIZE) b.trail.removeFirst();
			}
		}

		public void goBack() {
		}

		private void step() {
			double[] ax = new double[bodies.length];
			double[] ay = new double[bodies.length];

			for(int i=0; i<bodies.length; i++) {
				Body bi = bodies[i];
				for(int j=0; j<bodies.length; j++) {
					if(i==j) continue;
					Body bj = bodies[j];

					double dx = bj.x-bi.x;
					double dy = bj.y-bi.y;
					double distSq = dx*dx+dy*dy+SOFTENING*SOFTENING;
					double dist = Math.sqrt(distSq);
					double force = G_CONST*bj.mass/distSq;

					ax[i] += force*dx/dist;
					ay[i] += force*dy/dist;
				}

				double cx = bi.x-center.getX();
				double cy = bi.y-center.getY();
				double centerDist = Math.sqrt(cx*cx+cy*cy);
				if(centerDist>SAFE_RADIUS) {
					double pull = CONTAINMENT_K*(centerDist-SAFE_RADIUS);
					ax[i] -= pull*cx/centerDist;
					ay[i] -= pull*cy/centerDist;
				}
			}

			for(int i=0; i<bodies.length; i++) {
				Body b = bodies[i];
				b.vx += ax[i]*DT;
				b.vy += ay[i]*DT;

				double speed = Math.sqrt(b.vx*b.vx+b.vy*b.vy);
				if(speed>MAX_SPEED) {
					b.vx *= MAX_SPEED/speed;
					b.vy *= MAX_SPEED/speed;
				}

				b.x += b.vx*DT;
				b.y += b.vy*DT;
			}
		}

		protected void draw() {
			for(Body b : bodies) {
				int size = b.trail.size();
				int i = 0;
				for(Point1 p : b.trail) {
					float frac = size<=1 ? 1 : (float) i/(size-1);
					int alpha = (int) (180*frac);
					fillRoundC(new Color(b.color.getRed(), b.color.getGreen(), b.color.getBlue(), alpha), p, 2);
					i++;
				}

				double radius = 4+Math.cbrt(b.mass)*0.6;
				fillRoundC(b.color, p1(b.x, b.y), radius);
			}
		}
	}
}
