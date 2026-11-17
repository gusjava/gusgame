package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilRandom;

public class AnimPackingAnnealing extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int ZONE_X = 50;
	public static final int ZONE_Y = 150;
	public static final int ZONE_W = 800;
	public static final int ZONE_H = 460;

	public static final int SHAPE_MIN = 15;
	public static final int SHAPE_MAX = 23;
	public static final double SIZE_MIN = 45;
	public static final double SIZE_MAX = 175;
	public static final double MAX_FILL = 0.55;
	public static final double OUT_MARGIN = 45;

	public static final double OUT_WEIGHT = 2;
	public static final double TEMP_MAX = 900;
	public static final double TEMP_MIN = 0.5;
	public static final double MOVE_MAX = 55;
	public static final double MOVE_MIN = 2;
	public static final int MAX_ITER = 40000;
	public static final int ITER_PER_FRAME = 60;
	public static final double COST_EPS = 0.5;
	public static final int PAUSE_FRAMES = 110;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);
	public static final Color COLOR_TITLE = new Color(255,200,60);
	public static final Color COLOR_ZONE = new Color(22,30,42);
	public static final Color COLOR_ZONE_BORDER = new Color(90,110,140);
	public static final Color COLOR_EDGE = new Color(235,240,250,120);
	public static final Color COLOR_BAD = new Color(240,70,70);
	public static final Color COLOR_OK = new Color(140,235,150);

	public static void main(String[] args) {
		AnimPackingAnnealing main = new AnimPackingAnnealing();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Recuit simule - rangement de formes");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int n;
	private double[] x;
	private double[] y;
	private double[] w;
	private double[] h;
	private Color[] color;

	private double cost;
	private int iteration;
	private int accepted;
	private int pause;

	protected void initialize1() {
		buildShapes();
		resetAnnealing();

		addDraw(new Board());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) { restart(); return; }
		if(k.in().F2()) { exit(); return; }

		if(done()) {
			pause++;
			if(pause<PAUSE_FRAMES) return;
			buildShapes();
			resetAnnealing();
			return;
		}

		for(int i=0;i<ITER_PER_FRAME && !done();i++) runIteration();
		cost = totalCost();
	}

	/*
	 * ANNEALING
	 */

	private void resetAnnealing() {
		cost = totalCost();
		iteration = 0;
		accepted = 0;
		pause = 0;
	}

	private boolean done() {
		return cost<=COST_EPS || iteration>=MAX_ITER;
	}

	private void runIteration() {
		double t = temperature();
		double step = MOVE_MIN+(MOVE_MAX-MOVE_MIN)*(t-TEMP_MIN)/(TEMP_MAX-TEMP_MIN);
		int i = UtilRandom.randomInt(n);

		double oldX = x[i];
		double oldY = y[i];
		double before = contribution(i);

		x[i] = clamp(oldX+UtilRandom.randomDouble(-step, step), ZONE_X-OUT_MARGIN, ZONE_X+ZONE_W-w[i]+OUT_MARGIN);
		y[i] = clamp(oldY+UtilRandom.randomDouble(-step, step), ZONE_Y-OUT_MARGIN, ZONE_Y+ZONE_H-h[i]+OUT_MARGIN);

		double delta = contribution(i)-before;

		if(delta<=0 || UtilRandom.randomDouble(1.0)<Math.exp(-delta/t)) {
			cost += delta;
			accepted++;
		} else {
			x[i] = oldX;
			y[i] = oldY;
		}

		iteration++;
	}

	private double temperature() {
		double ratio = Math.min(1, (double)iteration/MAX_ITER);
		return TEMP_MAX*Math.pow(TEMP_MIN/TEMP_MAX, ratio);
	}

	/*
	 * COST
	 */

	private double totalCost() {
		double total = 0;
		for(int i=0;i<n;i++) {
			total += OUT_WEIGHT*outside(i);
			for(int j=i+1;j<n;j++) total += overlap(i, j);
		}
		return total;
	}

	private double contribution(int i) {
		double total = OUT_WEIGHT*outside(i);
		for(int j=0;j<n;j++) if(j!=i) total += overlap(i, j);
		return total;
	}

	private double overlap(int i, int j) {
		double dx = Math.min(x[i]+w[i], x[j]+w[j])-Math.max(x[i], x[j]);
		double dy = Math.min(y[i]+h[i], y[j]+h[j])-Math.max(y[i], y[j]);
		if(dx<=0 || dy<=0) return 0;
		return dx*dy;
	}

	private double outside(int i) {
		double dx = Math.min(x[i]+w[i], ZONE_X+ZONE_W)-Math.max(x[i], ZONE_X);
		double dy = Math.min(y[i]+h[i], ZONE_Y+ZONE_H)-Math.max(y[i], ZONE_Y);
		double inside = dx<=0 || dy<=0 ? 0 : dx*dy;
		return w[i]*h[i]-inside;
	}

	private boolean isBad(int i) {
		if(outside(i)>COST_EPS) return true;
		for(int j=0;j<n;j++) if(j!=i && overlap(i, j)>COST_EPS) return true;
		return false;
	}

	/*
	 * SHAPES
	 */

	private void buildShapes() {
		n = UtilRandom.randomInt(SHAPE_MIN, SHAPE_MAX+1);
		x = new double[n];
		y = new double[n];
		w = new double[n];
		h = new double[n];
		color = new Color[n];

		double budget = MAX_FILL*ZONE_W*ZONE_H/n;

		for(int i=0;i<n;i++) {
			double area = budget*UtilRandom.randomDouble(0.6, 1.4);
			double ratio = UtilRandom.randomDouble(0.6, 1.6);
			w[i] = clamp(Math.sqrt(area*ratio), SIZE_MIN, SIZE_MAX);
			h[i] = clamp(Math.sqrt(area/ratio), SIZE_MIN, SIZE_MAX);
			x[i] = UtilRandom.randomDouble(ZONE_X-OUT_MARGIN, ZONE_X+ZONE_W-w[i]+OUT_MARGIN);
			y[i] = UtilRandom.randomDouble(ZONE_Y-OUT_MARGIN, ZONE_Y+ZONE_H-h[i]+OUT_MARGIN);
			color[i] = buildColor(i);
		}
	}

	private Color buildColor(int index) {
		Color c = Color.getHSBColor((float)index/n, 0.55f, 0.95f);
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), 165);
	}

	private static double clamp(double value, double min, double max) {
		if(value<min) return min;
		if(value>max) return max;
		return value;
	}

	private static double round(double value) {
		return Math.round(value*10)/10.0;
	}

	/*
	 * BOARD
	 */

	private class Board extends Drawing1 {

		protected void draw() {
			fillRect(COLOR_ZONE, p(ZONE_X, ZONE_Y), ZONE_W, ZONE_H);
			drawRect(COLOR_ZONE_BORDER, p(ZONE_X, ZONE_Y), ZONE_W, ZONE_H);

			for(int i=0;i<n;i++) {
				fillRect(color[i], p(x[i], y[i]), w[i], h[i]);
				if(isBad(i)) {
					drawRect(COLOR_BAD, p(x[i], y[i]), w[i], h[i]);
					drawRect(COLOR_BAD, p(x[i]+1, y[i]+1), w[i]-2, h[i]-2);
				} else {
					drawRect(COLOR_EDGE, p(x[i], y[i]), w[i], h[i]);
				}
			}
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TITLE, fontBold(15), p(16, 28), "RECUIT SIMULE - RANGEMENT DE FORMES");
			drawString(COLOR_TEXT, fontPlain(13), p(16, 50),
					"formes "+n+"   iteration "+Math.min(iteration, MAX_ITER)+" / "+MAX_ITER+"   acceptes "+accepted);
			drawString(COLOR_TEXT, fontPlain(13), p(16, 70),
					"cout "+round(cost)+"   temperature "+round(temperature()));

			if(cost<=COST_EPS) {
				drawString(COLOR_OK, fontBold(13), p(16, 92), "range sans chevauchement - nouvel ensemble dans un instant");
			} else if(iteration>=MAX_ITER) {
				drawString(COLOR_TITLE, fontBold(13), p(16, 92), "refroidissement termine - nouvel ensemble dans un instant");
			}

			drawString(COLOR_TEXT, fontPlain(11), p(16, HEIGHT-18), "F1 nouvel ensemble   F2 quitter");
		}
	}
}
