package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilColor;
import gus.game5.core.util.UtilRandom;

public class AnimSlimeMold extends Game1 {

	public static final long SLEEP = 16;
	public static final int CELL = 3;
	public static final int GW = 300;
	public static final int GH = 233;

	public static final int AGENT_COUNT = 10000;
	public static final double SENSOR_DIST = 9;
	public static final double SENSOR_ANGLE = Math.toRadians(30);
	public static final double TURN_ANGLE = Math.toRadians(26);
	public static final double STEP = 1.0;
	public static final double DEPOSIT = 5.0;
	public static final double DECAY = 0.90;
	public static final double DIFFUSE = 0.45;
	public static final double LEVEL_MAX = 11;

	public static final Color COLOR_BG = new Color(6,4,14);
	public static final Color COLOR_LOW = new Color(70,20,110);
	public static final Color COLOR_MID = new Color(235,90,120);
	public static final Color COLOR_HIGH = new Color(255,245,205);

	public static void main(String[] args) {
		AnimSlimeMold main = new AnimSlimeMold();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Slime mold");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[] trail;
	private double[] next;
	private double[] agentX;
	private double[] agentY;
	private double[] agentA;
	private boolean[] occupied;
	private int[] palette;

	protected void initialize1() {
		trail = new double[GW*GH];
		next = new double[GW*GH];
		agentX = new double[AGENT_COUNT];
		agentY = new double[AGENT_COUNT];
		agentA = new double[AGENT_COUNT];
		occupied = new boolean[GW*GH];
		palette = buildPalette();

		for(int i=0;i<AGENT_COUNT;i++) {
			int c = freeCell();
			occupied[c] = true;
			agentX[i] = c%GW+0.5;
			agentY[i] = c/GW+0.5;
			agentA[i] = UtilRandom.randomDouble(2*Math.PI);
		}

		addDraw(new Field());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		moveAgents();
		diffuse();
	}

	private int[] buildPalette() {
		int[] pp = new int[256];
		for(int i=0;i<256;i++) {
			double t = i/255.0;
			Color c = t<0.5 ? UtilColor.blend(COLOR_BG, COLOR_LOW, t*2)
					: t<0.8 ? UtilColor.blend(COLOR_LOW, COLOR_MID, (t-0.5)/0.3)
					: UtilColor.blend(COLOR_MID, COLOR_HIGH, (t-0.8)/0.2);
			pp[i] = c.getRGB();
		}
		return pp;
	}

	private int freeCell() {
		while(true) {
			int c = UtilRandom.randomInt(GW*GH);
			if(!occupied[c]) return c;
		}
	}

	private void moveAgents() {
		for(int i=0;i<AGENT_COUNT;i++) {
			double a = steer(i);
			double x = wrap(agentX[i]+STEP*Math.cos(a), GW);
			double y = wrap(agentY[i]+STEP*Math.sin(a), GH);

			int from = cell(agentY[i])*GW+cell(agentX[i]);
			int to = cell(y)*GW+cell(x);

			if(to!=from && occupied[to]) {
				agentA[i] = UtilRandom.randomDouble(2*Math.PI);
				continue;
			}

			occupied[from] = false;
			occupied[to] = true;

			agentX[i] = x;
			agentY[i] = y;
			agentA[i] = a;

			trail[to] += DEPOSIT;
		}
	}

	private double steer(int i) {
		double a = agentA[i];
		double front = sense(agentX[i], agentY[i], a);
		double left = sense(agentX[i], agentY[i], a-SENSOR_ANGLE);
		double right = sense(agentX[i], agentY[i], a+SENSOR_ANGLE);

		if(front>=left && front>=right) return a;
		if(front<left && front<right) return a+(UtilRandom.randomBoolean() ? TURN_ANGLE : -TURN_ANGLE);
		if(left>right) return a-TURN_ANGLE;
		return a+TURN_ANGLE;
	}

	private double sense(double x, double y, double angle) {
		double sx = wrap(x+SENSOR_DIST*Math.cos(angle), GW);
		double sy = wrap(y+SENSOR_DIST*Math.sin(angle), GH);
		return trail[cell(sy)*GW+cell(sx)];
	}

	private int cell(double v) {
		int c = (int) Math.floor(v);
		if(c<0) return 0;
		return c;
	}

	private double wrap(double v, int span) {
		if(v<0) return v+span;
		if(v>=span) return v-span;
		return v;
	}

	private void diffuse() {
		for(int y=0;y<GH;y++) {
			int ym = (y>0 ? y-1 : GH-1)*GW;
			int y0 = y*GW;
			int yp = (y<GH-1 ? y+1 : 0)*GW;

			for(int x=0;x<GW;x++) {
				int xm = x>0 ? x-1 : GW-1;
				int xp = x<GW-1 ? x+1 : 0;

				double sum = trail[ym+xm]+trail[ym+x]+trail[ym+xp]
						+ trail[y0+xm]+trail[y0+x]+trail[y0+xp]
						+ trail[yp+xm]+trail[yp+x]+trail[yp+xp];

				double v = trail[y0+x]+(sum/9-trail[y0+x])*DIFFUSE;
				next[y0+x] = v*DECAY;
			}
		}

		double[] swap = trail;
		trail = next;
		next = swap;
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[GW*GH];

		protected void draw() {
			for(int i=0;i<pixels.length;i++) {
				int level = (int) (255*Math.min(1, trail[i]/LEVEL_MAX));
				pixels[i] = palette[level];
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}
	}
}
