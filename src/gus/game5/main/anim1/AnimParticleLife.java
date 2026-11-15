package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilRandom;

public class AnimParticleLife extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int TYPE_COUNT = 5;
	public static final int PER_TYPE = 130;
	public static final int COUNT = TYPE_COUNT*PER_TYPE;

	public static final double R_MIN = 14;
	public static final double R_MAX = 82;
	public static final double REPULSION = 2.4;
	public static final double FORCE_FACTOR = 0.52;
	public static final double FRICTION = 0.86;
	public static final double SPEED_MAX = 4.5;
	public static final double RADIUS = 2.6;

	public static final int RESEED_PERIOD = 1700;
	public static final int FADE_LENGTH = 90;

	public static final Color COLOR_BG = new Color(8,8,16);

	public static final Color[] PALETTE = {
		new Color(255,95,95),
		new Color(255,200,80),
		new Color(110,235,140),
		new Color(100,185,255),
		new Color(220,130,255)
	};

	public static void main(String[] args) {
		AnimParticleLife main = new AnimParticleLife();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Particle life");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[] px;
	private double[] py;
	private double[] vx;
	private double[] vy;
	private int[] type;
	private double[][] matrix;
	private int sinceReseed;

	private int gridW;
	private int gridH;
	private double cellW;
	private double cellH;
	private int[] cellStart;
	private int[] bucket;

	protected void initialize1() {
		px = new double[COUNT];
		py = new double[COUNT];
		vx = new double[COUNT];
		vy = new double[COUNT];
		type = new int[COUNT];

		for(int i=0;i<COUNT;i++) {
			px[i] = UtilRandom.randomDouble(WIDTH);
			py[i] = UtilRandom.randomDouble(HEIGHT);
			type[i] = i/PER_TYPE;
		}

		matrix = randomMatrix();
		sinceReseed = 0;

		gridW = Math.max(1, (int) (WIDTH/R_MAX));
		gridH = Math.max(1, (int) (HEIGHT/R_MAX));
		cellW = WIDTH/(double) gridW;
		cellH = HEIGHT/(double) gridH;
		bucket = new int[COUNT];

		addDraw(new Field());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		sinceReseed++;
		if(sinceReseed>=RESEED_PERIOD) {
			matrix = randomMatrix();
			sinceReseed = 0;
		}

		applyForces();
		moveParticles();
	}

	private double[][] randomMatrix() {
		double[][] m = new double[TYPE_COUNT][TYPE_COUNT];
		for(int a=0;a<TYPE_COUNT;a++)
		for(int b=0;b<TYPE_COUNT;b++)
		m[a][b] = UtilRandom.randomDouble(-1.0, 1.0);
		return m;
	}

	private void applyForces() {
		buildGrid();

		for(int i=0;i<COUNT;i++) {
			double fx = 0;
			double fy = 0;

			int cx = cellX(px[i]);
			int cy = cellY(py[i]);

			for(int oy=-1;oy<=1;oy++)
			for(int ox=-1;ox<=1;ox++) {
				int nx = ((cx+ox)%gridW+gridW)%gridW;
				int ny = ((cy+oy)%gridH+gridH)%gridH;
				int cell = ny*gridW+nx;

				for(int k=cellStart[cell];k<cellStart[cell+1];k++) {
					int j = bucket[k];
					if(i==j) continue;

					double dx = shortest(px[j]-px[i], WIDTH);
					double dy = shortest(py[j]-py[i], HEIGHT);
					double d2 = dx*dx+dy*dy;
					if(d2>=R_MAX*R_MAX || d2==0) continue;

					double d = Math.sqrt(d2);
					double f = force(d, matrix[type[i]][type[j]]);
					fx += f*dx/d;
					fy += f*dy/d;
				}
			}

			vx[i] = (vx[i]+fx*FORCE_FACTOR)*FRICTION;
			vy[i] = (vy[i]+fy*FORCE_FACTOR)*FRICTION;

			double speed = Math.sqrt(vx[i]*vx[i]+vy[i]*vy[i]);
			if(speed>SPEED_MAX) {
				vx[i] *= SPEED_MAX/speed;
				vy[i] *= SPEED_MAX/speed;
			}
		}
	}

	private void buildGrid() {
		int cells = gridW*gridH;
		int[] start = new int[cells+1];

		for(int i=0;i<COUNT;i++) start[cellOf(i)+1]++;
		for(int c=0;c<cells;c++) start[c+1] += start[c];

		int[] cursor = start.clone();
		for(int i=0;i<COUNT;i++) bucket[cursor[cellOf(i)]++] = i;

		cellStart = start;
	}

	private int cellOf(int i) {
		return cellY(py[i])*gridW+cellX(px[i]);
	}

	private int cellX(double x) {
		int cx = (int) (x/cellW);
		return cx>=gridW ? gridW-1 : cx;
	}

	private int cellY(double y) {
		int cy = (int) (y/cellH);
		return cy>=gridH ? gridH-1 : cy;
	}

	private double force(double d, double attraction) {
		if(d<R_MIN) return REPULSION*(d/R_MIN-1);
		double mid = (R_MIN+R_MAX)/2;
		return attraction*(1-Math.abs(d-mid)/(mid-R_MIN));
	}

	private double shortest(double delta, double span) {
		if(delta>span/2) return delta-span;
		if(delta<-span/2) return delta+span;
		return delta;
	}

	private void moveParticles() {
		for(int i=0;i<COUNT;i++) {
			px[i] = wrap(px[i]+vx[i], WIDTH);
			py[i] = wrap(py[i]+vy[i], HEIGHT);
		}
	}

	private double wrap(double v, double span) {
		if(v<0) return v+span;
		if(v>=span) return v-span;
		return v;
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		protected void draw() {
			for(int i=0;i<COUNT;i++) {
				fillRoundC(colorOf(type[i]), p(px[i], py[i]), RADIUS);
			}
		}

		private Color colorOf(int t) {
			Color base = PALETTE[t];
			if(sinceReseed>=FADE_LENGTH) return base;
			int alpha = 90+165*sinceReseed/FADE_LENGTH;
			return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
		}
	}
}
