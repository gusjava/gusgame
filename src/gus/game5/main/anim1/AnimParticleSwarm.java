package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Locale;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilRandom;

public class AnimParticleSwarm extends Game1 {

	public static final long SLEEP = 30;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int PLOT_X = 20;
	public static final int PLOT_Y = 104;
	public static final int PLOT_W = 860;
	public static final int PLOT_H = 560;
	public static final int MARK = 20;

	public static final double DOM_MIN = -5.12;
	public static final double DOM_MAX = 5.12;
	public static final double SCALE_X = PLOT_W/(DOM_MAX-DOM_MIN);
	public static final double SCALE_Y = PLOT_H/(DOM_MAX-DOM_MIN);
	public static final double SHIFT_MAX = 2.2;
	public static final double BOWL = 0.40;

	public static final int SWARM = 50;
	public static final double INERTIA = 0.86;
	public static final double COEF_SELF = 1.0;
	public static final double COEF_SWARM = 0.65;
	public static final double SPEED_MAX = 0.30;
	public static final double SPEED_INIT = 0.5;
	public static final double REBOUND = -0.4;

	public static final int MAX_ITER = 500;
	public static final int STALL_MAX = 110;
	public static final double IMPROVE_EPS = 1e-4;
	public static final int PAUSE_FRAMES = 110;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);
	public static final Color COLOR_TITLE = new Color(255,200,60);
	public static final Color COLOR_PLOT_BORDER = new Color(90,110,140);
	public static final Color COLOR_PARTICLE = new Color(255,185,70);
	public static final Color COLOR_PARTICLE_EDGE = new Color(20,12,6,190);
	public static final Color COLOR_TRAIL = new Color(255,200,120,110);
	public static final Color COLOR_BEST = new Color(255,90,60);
	public static final Color COLOR_TARGET = new Color(120,255,150,170);
	public static final Color COLOR_OK = new Color(140,235,150);

	public static void main(String[] args) {
		AnimParticleSwarm main = new AnimParticleSwarm();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Essaim de particules - minimisation d'une fonction");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double shiftX;
	private double shiftY;

	private BufferedImage image;
	private int[] pixels;

	private double[] x;
	private double[] y;
	private double[] vx;
	private double[] vy;
	private double[] bestX;
	private double[] bestY;
	private double[] bestVal;

	private double globalX;
	private double globalY;
	private double globalVal;

	private double lastBest;
	private int iteration;
	private int stall;
	private int pause;

	protected void initialize1() {
		image = new BufferedImage(PLOT_W, PLOT_H, BufferedImage.TYPE_INT_RGB);
		pixels = new int[PLOT_W*PLOT_H];

		x = new double[SWARM];
		y = new double[SWARM];
		vx = new double[SWARM];
		vy = new double[SWARM];
		bestX = new double[SWARM];
		bestY = new double[SWARM];
		bestVal = new double[SWARM];

		buildLandscape();
		resetSwarm();

		addDraw(new Landscape());
		addDraw(new Swarm());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) { restart(); return; }
		if(k.in().F2()) { exit(); return; }

		if(done()) {
			pause++;
			if(pause<PAUSE_FRAMES) return;
			buildLandscape();
			resetSwarm();
			return;
		}

		runIteration();
	}

	/*
	 * LANDSCAPE
	 */

	private void buildLandscape() {
		shiftX = UtilRandom.randomDouble(-SHIFT_MAX, SHIFT_MAX);
		shiftY = UtilRandom.randomDouble(-SHIFT_MAX, SHIFT_MAX);

		double max = 0;
		for(int py=0;py<PLOT_H;py++) {
			double wy = worldY(PLOT_Y+py);
			for(int px=0;px<PLOT_W;px++) max = Math.max(max, value(worldX(PLOT_X+px), wy));
		}

		for(int py=0;py<PLOT_H;py++) {
			double wy = worldY(PLOT_Y+py);
			for(int px=0;px<PLOT_W;px++) pixels[py*PLOT_W+px] = heatColor(value(worldX(PLOT_X+px), wy)/max);
		}

		image.setRGB(0, 0, PLOT_W, PLOT_H, pixels, 0, PLOT_W);
	}

	private double value(double wx, double wy) {
		double a = wx-shiftX;
		double b = wy-shiftY;
		return 20 + BOWL*(a*a+b*b) - 10*Math.cos(2*Math.PI*a) - 10*Math.cos(2*Math.PI*b);
	}

	private static int heatColor(double ratio) {
		double t = 1-Math.pow(clamp(ratio, 0, 1), 0.45);
		return Color.HSBtoRGB((float)(0.74-0.28*t), (float)(0.95-0.50*t), (float)(0.08+0.64*t));
	}

	/*
	 * SWARM
	 */

	private void resetSwarm() {
		globalVal = Double.MAX_VALUE;

		for(int i=0;i<SWARM;i++) {
			x[i] = UtilRandom.randomDouble(DOM_MIN, DOM_MAX);
			y[i] = UtilRandom.randomDouble(DOM_MIN, DOM_MAX);
			vx[i] = UtilRandom.randomDouble(-SPEED_INIT, SPEED_INIT);
			vy[i] = UtilRandom.randomDouble(-SPEED_INIT, SPEED_INIT);
			bestX[i] = x[i];
			bestY[i] = y[i];
			bestVal[i] = value(x[i], y[i]);

			if(bestVal[i]<globalVal) {
				globalVal = bestVal[i];
				globalX = x[i];
				globalY = y[i];
			}
		}

		lastBest = globalVal;
		iteration = 0;
		stall = 0;
		pause = 0;
	}

	private boolean done() {
		return iteration>=MAX_ITER || stall>=STALL_MAX;
	}

	private void runIteration() {
		for(int i=0;i<SWARM;i++) {
			vx[i] = INERTIA*vx[i]
					+ COEF_SELF*UtilRandom.randomDouble(1.0)*(bestX[i]-x[i])
					+ COEF_SWARM*UtilRandom.randomDouble(1.0)*(globalX-x[i]);
			vy[i] = INERTIA*vy[i]
					+ COEF_SELF*UtilRandom.randomDouble(1.0)*(bestY[i]-y[i])
					+ COEF_SWARM*UtilRandom.randomDouble(1.0)*(globalY-y[i]);

			vx[i] = clamp(vx[i], -SPEED_MAX, SPEED_MAX);
			vy[i] = clamp(vy[i], -SPEED_MAX, SPEED_MAX);

			x[i] += vx[i];
			y[i] += vy[i];

			if(x[i]<DOM_MIN) { x[i] = DOM_MIN; vx[i] *= REBOUND; }
			if(x[i]>DOM_MAX) { x[i] = DOM_MAX; vx[i] *= REBOUND; }
			if(y[i]<DOM_MIN) { y[i] = DOM_MIN; vy[i] *= REBOUND; }
			if(y[i]>DOM_MAX) { y[i] = DOM_MAX; vy[i] *= REBOUND; }

			double v = value(x[i], y[i]);

			if(v<bestVal[i]) {
				bestVal[i] = v;
				bestX[i] = x[i];
				bestY[i] = y[i];
			}
			if(v<globalVal) {
				globalVal = v;
				globalX = x[i];
				globalY = y[i];
			}
		}

		iteration++;

		if(globalVal<lastBest-IMPROVE_EPS) {
			lastBest = globalVal;
			stall = 0;
		} else {
			stall++;
		}
	}

	/*
	 * GEOMETRY
	 */

	private static double worldX(double screenX) {
		return DOM_MIN+(screenX-PLOT_X)/SCALE_X;
	}

	private static double worldY(double screenY) {
		return DOM_MIN+(screenY-PLOT_Y)/SCALE_Y;
	}

	private static double screenX(double worldX) {
		return PLOT_X+(worldX-DOM_MIN)*SCALE_X;
	}

	private static double screenY(double worldY) {
		return PLOT_Y+(worldY-DOM_MIN)*SCALE_Y;
	}

	private static double markX(double worldX) {
		return clamp(screenX(worldX), PLOT_X+MARK, PLOT_X+PLOT_W-MARK);
	}

	private static double markY(double worldY) {
		return clamp(screenY(worldY), PLOT_Y+MARK, PLOT_Y+PLOT_H-MARK);
	}

	private static double clamp(double value, double min, double max) {
		if(value<min) return min;
		if(value>max) return max;
		return value;
	}

	private static String text(double value, int digits) {
		return String.format(Locale.US, "%."+digits+"f", value);
	}

	/*
	 * LANDSCAPE DRAWING
	 */

	private class Landscape extends Drawing1 {

		protected void draw() {
			paintImage(p(PLOT_X, PLOT_Y), PLOT_W, PLOT_H, image);
			drawRect(COLOR_PLOT_BORDER, p(PLOT_X, PLOT_Y), PLOT_W, PLOT_H);
		}
	}

	/*
	 * SWARM DRAWING
	 */

	private class Swarm extends Drawing1 {

		protected void draw() {
			double tx = markX(shiftX);
			double ty = markY(shiftY);
			drawRoundC(COLOR_TARGET, p(tx, ty), 7);
			drawRoundC(COLOR_TARGET, p(tx, ty), 11);

			for(int i=0;i<SWARM;i++) {
				double sx = screenX(x[i]);
				double sy = screenY(y[i]);
				drawLine(COLOR_TRAIL,
						p(clamp(sx-vx[i]*SCALE_X, PLOT_X, PLOT_X+PLOT_W),
						  clamp(sy-vy[i]*SCALE_Y, PLOT_Y, PLOT_Y+PLOT_H)),
						p(sx, sy));
				fillRoundC(COLOR_PARTICLE, p(sx, sy), 3);
				drawRoundC(COLOR_PARTICLE_EDGE, p(sx, sy), 3);
			}

			double gx = markX(globalX);
			double gy = markY(globalY);
			drawRoundC(COLOR_BEST, p(gx, gy), 9);
			drawRoundC(COLOR_BEST, p(gx, gy), 14);
			drawLine(COLOR_BEST, p(gx-20, gy), p(gx-14, gy));
			drawLine(COLOR_BEST, p(gx+14, gy), p(gx+20, gy));
			drawLine(COLOR_BEST, p(gx, gy-20), p(gx, gy-14));
			drawLine(COLOR_BEST, p(gx, gy+14), p(gx, gy+20));
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TITLE, fontBold(15), p(16, 28), "ESSAIM DE PARTICULES - MINIMISATION D'UNE FONCTION");
			drawString(COLOR_TEXT, fontPlain(13), p(16, 50),
					"particules "+SWARM+"   iteration "+Math.min(iteration, MAX_ITER)+" / "+MAX_ITER+"   stagnation "+Math.min(stall, STALL_MAX)+" / "+STALL_MAX);
			drawString(COLOR_TEXT, fontPlain(13), p(16, 70),
					"meilleure valeur "+text(globalVal, 4)+"   position ("+text(globalX, 2)+" , "+text(globalY, 2)+")   minimum global ("+text(shiftX, 2)+" , "+text(shiftY, 2)+")");

			if(stall>=STALL_MAX) {
				drawString(COLOR_OK, fontBold(13), p(16, 92), "essaim converge - nouveau paysage dans un instant");
			} else if(iteration>=MAX_ITER) {
				drawString(COLOR_TITLE, fontBold(13), p(16, 92), "limite d'iterations atteinte - nouveau paysage dans un instant");
			}

			drawString(COLOR_TEXT, fontPlain(11), p(16, HEIGHT-18), "F1 nouveau paysage   F2 quitter   cercle vert : minimum global   croix rouge : meilleure solution trouvee");
		}
	}
}
