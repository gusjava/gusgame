package gus.game5.main.genetic;

import java.awt.Color;
import java.awt.Graphics2D;

import gus.game5.core.util.UtilRandom;

class ShapeGene {

	private static final int MIN_ALPHA = 40;
	private static final int MAX_ALPHA = 160;
	private static final int MIN_RADIUS = 6;
	private static final int MAX_RADIUS = 45;
	private static final int TRIANGLE_SPREAD = 50;
	private static final int JITTER = 20;

	private boolean circle;
	private int red;
	private int green;
	private int blue;
	private int alpha;

	private double cx;
	private double cy;
	private double radius;

	private double[] xs;
	private double[] ys;

	static ShapeGene random(int width, int height) {
		ShapeGene gene = new ShapeGene();
		gene.circle = UtilRandom.randomBoolean();
		gene.randomizeColor();
		gene.randomizePosition(width, height);
		return gene;
	}

	private void randomizeColor() {
		red = UtilRandom.randomInt(256);
		green = UtilRandom.randomInt(256);
		blue = UtilRandom.randomInt(256);
		alpha = UtilRandom.randomInt(MIN_ALPHA, MAX_ALPHA);
	}

	private void randomizePosition(int width, int height) {
		if(circle) {
			cx = UtilRandom.randomInt(width);
			cy = UtilRandom.randomInt(height);
			radius = UtilRandom.randomInt(MIN_RADIUS, MAX_RADIUS);
		} else {
			double centerX = UtilRandom.randomInt(width);
			double centerY = UtilRandom.randomInt(height);
			xs = new double[3];
			ys = new double[3];
			for(int i=0;i<3;i++) {
				xs[i] = clamp(centerX+UtilRandom.randomInt(-TRIANGLE_SPREAD, TRIANGLE_SPREAD), width);
				ys[i] = clamp(centerY+UtilRandom.randomInt(-TRIANGLE_SPREAD, TRIANGLE_SPREAD), height);
			}
		}
	}

	void render(Graphics2D g2) {
		g2.setColor(new Color(red, green, blue, alpha));
		if(circle) {
			g2.fillOval((int) (cx-radius), (int) (cy-radius), (int) (radius*2), (int) (radius*2));
		} else {
			int[] px = {(int) xs[0], (int) xs[1], (int) xs[2]};
			int[] py = {(int) ys[0], (int) ys[1], (int) ys[2]};
			g2.fillPolygon(px, py, 3);
		}
	}

	void mutate(int width, int height) {
		switch(UtilRandom.chances(45, 45, 10)) {
			case 0: randomizeColor(); break;
			case 1: jitterPosition(width, height); break;
			default: randomizePosition(width, height); break;
		}
	}

	private void jitterPosition(int width, int height) {
		if(circle) {
			cx = clamp(cx+UtilRandom.randomInt(-JITTER, JITTER), width);
			cy = clamp(cy+UtilRandom.randomInt(-JITTER, JITTER), height);
			radius = Math.max(MIN_RADIUS, Math.min(MAX_RADIUS, radius+UtilRandom.randomInt(-JITTER, JITTER)));
		} else {
			int index = UtilRandom.randomInt(3);
			xs[index] = clamp(xs[index]+UtilRandom.randomInt(-JITTER, JITTER), width);
			ys[index] = clamp(ys[index]+UtilRandom.randomInt(-JITTER, JITTER), height);
		}
	}

	private static double clamp(double value, double max) {
		return Math.max(0, Math.min(max, value));
	}

	ShapeGene copy() {
		ShapeGene gene = new ShapeGene();
		gene.circle = circle;
		gene.red = red;
		gene.green = green;
		gene.blue = blue;
		gene.alpha = alpha;
		gene.cx = cx;
		gene.cy = cy;
		gene.radius = radius;
		gene.xs = xs==null ? null : xs.clone();
		gene.ys = ys==null ? null : ys.clone();
		return gene;
	}

}
