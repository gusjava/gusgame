package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.util.UtilRandom;

public class AnimBoids extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	public static final int COUNT = 200;

	public static final double PERCEPTION = 60;
	public static final double SEPARATION_RADIUS = 20;
	public static final double WEIGHT_SEPARATION = 1.5;
	public static final double WEIGHT_ALIGNMENT = 1.0;
	public static final double WEIGHT_COHESION = 1.0;
	public static final double ACCEL_MAX = 0.3;
	public static final double SPEED_MIN = 1.5;
	public static final double SPEED_MAX = 4.5;
	public static final double LENGTH = 7;
	public static final double WIDTH_BACK = 3.5;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_BOID = new Color(120,200,255);

	public static void main(String[] args) {
		AnimBoids main = new AnimBoids();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Boids");
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

		for(int i=0;i<COUNT;i++) {
			px[i] = UtilRandom.randomDouble(WIDTH);
			py[i] = UtilRandom.randomDouble(HEIGHT);
			Angle a = Angle.random();
			double speed = UtilRandom.randomDouble(SPEED_MIN, SPEED_MAX);
			vx[i] = speed*a.cos();
			vy[i] = speed*a.sin();
		}

		gridW = Math.max(1, (int) (WIDTH/PERCEPTION));
		gridH = Math.max(1, (int) (HEIGHT/PERCEPTION));
		cellW = WIDTH/(double) gridW;
		cellH = HEIGHT/(double) gridH;
		bucket = new int[COUNT];

		addDraw(new Field());
	}

	protected void turn() {
		handleRestartExit();

		buildGrid();
		updateVelocities();
		moveBoids();
	}

	private void updateVelocities() {
		for(int i=0;i<COUNT;i++) {
			double sepX = 0, sepY = 0;
			double aliX = 0, aliY = 0;
			double cohX = 0, cohY = 0;
			int count = 0;

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
					if(d2>=PERCEPTION*PERCEPTION || d2==0) continue;

					count++;
					aliX += vx[j];
					aliY += vy[j];
					cohX += dx;
					cohY += dy;

					if(d2<SEPARATION_RADIUS*SEPARATION_RADIUS) {
						sepX -= dx/d2;
						sepY -= dy/d2;
					}
				}
			}

			double ax = sepX*WEIGHT_SEPARATION;
			double ay = sepY*WEIGHT_SEPARATION;

			if(count>0) {
				ax += (aliX/count)*WEIGHT_ALIGNMENT/SPEED_MAX;
				ay += (aliY/count)*WEIGHT_ALIGNMENT/SPEED_MAX;
				ax += (cohX/count)*WEIGHT_COHESION/PERCEPTION;
				ay += (cohY/count)*WEIGHT_COHESION/PERCEPTION;
			}

			double aLen = Math.sqrt(ax*ax+ay*ay);
			if(aLen>ACCEL_MAX) {
				ax *= ACCEL_MAX/aLen;
				ay *= ACCEL_MAX/aLen;
			}

			double nvx = vx[i]+ax;
			double nvy = vy[i]+ay;
			double speed = Math.sqrt(nvx*nvx+nvy*nvy);
			if(speed<SPEED_MIN) {
				double scale = speed==0 ? 1 : SPEED_MIN/speed;
				nvx *= scale;
				nvy *= scale;
			}
			else if(speed>SPEED_MAX) {
				nvx *= SPEED_MAX/speed;
				nvy *= SPEED_MAX/speed;
			}

			vx[i] = nvx;
			vy[i] = nvy;
		}
	}

	private void moveBoids() {
		for(int i=0;i<COUNT;i++) {
			px[i] = wrap(px[i]+vx[i], WIDTH);
			py[i] = wrap(py[i]+vy[i], HEIGHT);
		}
	}

	private double shortest(double delta, double span) {
		if(delta>span/2) return delta-span;
		if(delta<-span/2) return delta+span;
		return delta;
	}

	private double wrap(double v, double span) {
		if(v<0) return v+span;
		if(v>=span) return v-span;
		return v;
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

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		protected void draw() {
			for(int i=0;i<COUNT;i++) {
				Angle heading = Angle.angleXY(vx[i], vy[i]);
				double nx = px[i]+LENGTH*heading.cos();
				double ny = py[i]+LENGTH*heading.sin();
				Angle back1 = heading.add150();
				Angle back2 = heading.sub150();
				double bx1 = px[i]+WIDTH_BACK*back1.cos();
				double by1 = py[i]+WIDTH_BACK*back1.sin();
				double bx2 = px[i]+WIDTH_BACK*back2.cos();
				double by2 = py[i]+WIDTH_BACK*back2.sin();

				fillPoly(COLOR_BOID, p(nx,ny), p(bx1,by1), p(bx2,by2));
			}
		}
	}
}
