package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Iterator;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimFlowField extends Game2 {

	public static void main(String[] args) {
		AnimFlowField main = new AnimFlowField();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Flow field animation");
		s.setSize(900, 700);
		s.setSleep(16);
		s.setBackground(Color.BLACK);
	}

	public static final int PARTICLE_COUNT = 260;
	public static final double SPEED = 2.4;
	public static final double NOISE_SCALE = 0.008;
	public static final double TIME_SCALE = 0.004;
	public static final int TRAIL_SIZE = 14;

	protected void initialize1() {
		FlowField flowField = new FlowField();
		addDraw(flowField);
		addDyn(flowField);
	}

	/*
	 * PARTICLE
	 */

	private class Particle {
		private double x;
		private double y;
		private final ArrayDeque<double[]> trail = new ArrayDeque<>();
		private final float hue;

		Particle() {
			x = UtilRandom.randomDouble(gameWidth());
			y = UtilRandom.randomDouble(gameHeight());
			hue = (float) UtilRandom.randomDouble(1);
		}

		void move(double t) {
			double angle = fieldAngle(x, y, t);
			x += Math.cos(angle)*SPEED;
			y += Math.sin(angle)*SPEED;

			boolean wrapped = false;
			if(x<0) { x += gameWidth(); wrapped = true; }
			if(x>=gameWidth()) { x -= gameWidth(); wrapped = true; }
			if(y<0) { y += gameHeight(); wrapped = true; }
			if(y>=gameHeight()) { y -= gameHeight(); wrapped = true; }

			if(wrapped) trail.clear();

			trail.addLast(new double[] {x,y});
			if(trail.size()>TRAIL_SIZE) trail.removeFirst();
		}

		void draw(Drawing1 d) {
			int size = trail.size();
			if(size<2) return;

			Iterator<double[]> it = trail.iterator();
			double[] previous = it.next();
			int index = 0;
			while(it.hasNext()) {
				double[] current = it.next();
				int alpha = (int) (200.0*index/size);
				Color color = Color.getHSBColor(hue, 0.75f, 1f);
				d.drawLine(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha),
						d.p(previous[0], previous[1]), d.p(current[0], current[1]));
				previous = current;
				index++;
			}
		}
	}

	private double fieldAngle(double x, double y, double t) {
		double v = Math.sin(x*NOISE_SCALE+t)
				+Math.sin(y*NOISE_SCALE*1.3-t*0.8)
				+Math.sin((x+y)*NOISE_SCALE*0.7+t*0.5)
				+Math.sin((x-y)*NOISE_SCALE*0.9-t*0.6);
		return v*Math.PI*0.5;
	}

	/*
	 * FLOW FIELD
	 */

	private class FlowField extends Drawing1 implements Dyn {
		private final Particle[] particles;
		private double t;

		public FlowField() {
			particles = new Particle[PARTICLE_COUNT];
			for(int i=0; i<particles.length; i++)
				particles[i] = new Particle();
		}

		public void goNext() {
			t += TIME_SCALE;
			for(Particle p : particles)
				p.move(t);
		}

		public void goBack() {
		}

		protected void draw() {
			for(Particle p : particles)
				p.draw(this);
		}
	}
}
