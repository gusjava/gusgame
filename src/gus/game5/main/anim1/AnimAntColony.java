package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilDistance;
import gus.game5.core.util.UtilRandom;

public class AnimAntColony extends Game1 {

	public static final long SLEEP = 16;
	public static final int CELL = 3;
	public static final int GW = 300;
	public static final int GH = 225;
	public static final int SIZE = GW*GH;

	public static final int ANT_COUNT = 900;
	public static final double STEP = 0.75;
	public static final double SENSOR_DIST = 7;
	public static final double SENSOR_ANGLE = Math.toRadians(32);
	public static final double TURN_ANGLE = Math.toRadians(20);
	public static final double WANDER = Math.toRadians(14);

	public static final double DEPOSIT = 1.0;
	public static final double EVAPORATE = 0.992;
	public static final double TRAIL_LIFE = 700;
	public static final double LEVEL_MAX = 6;

	public static final double NEST_RADIUS = 7;
	public static final double NEST_SIGHT = 26;
	public static final double FOOD_RADIUS = 9;
	public static final double FOOD_SIGHT = 22;
	public static final int FOOD_SOURCES = 4;
	public static final int FOOD_AMOUNT = 420;
	public static final double FOOD_MARGIN = 45;
	public static final double FOOD_MIN_DIST = 70;

	public static final Color COLOR_BG = new Color(14,11,8);
	public static final Color COLOR_NEST = new Color(255,210,90);
	public static final Color COLOR_FOOD = new Color(110,230,120);
	public static final Color COLOR_ANT = new Color(225,225,215);
	public static final Color COLOR_ANT_LOADED = new Color(150,255,160);

	public static void main(String[] args) {
		AnimAntColony main = new AnimAntColony();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Ant colony");
		s.setWidth(GW*CELL);
		s.setHeight(GH*CELL);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[] toHome;
	private double[] toFood;
	private double[] antX;
	private double[] antY;
	private double[] antA;
	private double[] antSince;
	private boolean[] antLoaded;
	private double[] foodX;
	private double[] foodY;
	private int[] foodLeft;
	private double nestX;
	private double nestY;

	protected void initialize1() {
		toHome = new double[SIZE];
		toFood = new double[SIZE];
		antX = new double[ANT_COUNT];
		antY = new double[ANT_COUNT];
		antA = new double[ANT_COUNT];
		antSince = new double[ANT_COUNT];
		antLoaded = new boolean[ANT_COUNT];
		foodX = new double[FOOD_SOURCES];
		foodY = new double[FOOD_SOURCES];
		foodLeft = new int[FOOD_SOURCES];

		nestX = GW/2.0;
		nestY = GH/2.0;

		for(int i=0;i<ANT_COUNT;i++) {
			antX[i] = nestX;
			antY[i] = nestY;
			antA[i] = UtilRandom.randomDouble(2*Math.PI);
		}

		for(int i=0;i<FOOD_SOURCES;i++) placeFood(i);

		addDraw(new Field());
		addDraw(new Ants());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		moveAnts();
		evaporate();
	}

	private void placeFood(int i) {
		while(true) {
			double x = UtilRandom.randomDouble(FOOD_MARGIN, GW-FOOD_MARGIN);
			double y = UtilRandom.randomDouble(FOOD_MARGIN, GH-FOOD_MARGIN);
			if(UtilDistance.dist(x, y, nestX, nestY)<FOOD_MIN_DIST) continue;
			foodX[i] = x;
			foodY[i] = y;
			foodLeft[i] = FOOD_AMOUNT;
			return;
		}
	}

	private void moveAnts() {
		for(int i=0;i<ANT_COUNT;i++) {
			antSince[i]++;
			double a = antLoaded[i] ? steerHome(i) : steerFood(i);
			a += UtilRandom.randomDouble(-WANDER, WANDER);

			double x = antX[i]+STEP*Math.cos(a);
			double y = antY[i]+STEP*Math.sin(a);

			if(x<0.5 || x>GW-1.5) {
				a = Math.PI-a;
				x = antX[i];
			}
			if(y<0.5 || y>GH-1.5) {
				a = -a;
				y = antY[i];
			}

			antX[i] = x;
			antY[i] = y;
			antA[i] = a;

			deposit(i);
			checkTargets(i);
		}
	}

	private double steerFood(int i) {
		int source = sourceInSight(i);
		if(source>=0) return Math.atan2(foodY[source]-antY[i], foodX[source]-antX[i]);
		return follow(toFood, i);
	}

	private double steerHome(int i) {
		if(UtilDistance.dist(antX[i], antY[i], nestX, nestY)<NEST_SIGHT) {
			return Math.atan2(nestY-antY[i], nestX-antX[i]);
		}
		return follow(toHome, i);
	}

	private double follow(double[] grid, int i) {
		double a = antA[i];
		double front = sense(grid, antX[i], antY[i], a);
		double left = sense(grid, antX[i], antY[i], a-SENSOR_ANGLE);
		double right = sense(grid, antX[i], antY[i], a+SENSOR_ANGLE);

		if(front>=left && front>=right) return a;
		if(left>right) return a-TURN_ANGLE;
		return a+TURN_ANGLE;
	}

	private double sense(double[] grid, double x, double y, double angle) {
		double sx = x+SENSOR_DIST*Math.cos(angle);
		double sy = y+SENSOR_DIST*Math.sin(angle);
		if(sx<0 || sx>=GW || sy<0 || sy>=GH) return -1;
		return grid[(int) sy*GW+(int) sx];
	}

	private void deposit(int i) {
		double strength = 1-antSince[i]/TRAIL_LIFE;
		if(strength<=0) return;

		double[] grid = antLoaded[i] ? toFood : toHome;
		grid[(int) antY[i]*GW+(int) antX[i]] += DEPOSIT*strength;
	}

	private void checkTargets(int i) {
		if(antLoaded[i]) {
			if(UtilDistance.dist(antX[i], antY[i], nestX, nestY)>NEST_RADIUS) return;
			antLoaded[i] = false;
			antA[i] += Math.PI;
			antSince[i] = 0;
			return;
		}

		int source = sourceAt(i);
		if(source<0) return;

		foodLeft[source]--;
		if(foodLeft[source]<=0) placeFood(source);

		antLoaded[i] = true;
		antA[i] += Math.PI;
		antSince[i] = 0;
	}

	private int sourceAt(int i) {
		for(int s=0;s<FOOD_SOURCES;s++) {
			if(foodLeft[s]>0 && UtilDistance.dist(antX[i], antY[i], foodX[s], foodY[s])<FOOD_RADIUS) return s;
		}
		return -1;
	}

	private int sourceInSight(int i) {
		for(int s=0;s<FOOD_SOURCES;s++) {
			if(foodLeft[s]>0 && UtilDistance.dist(antX[i], antY[i], foodX[s], foodY[s])<FOOD_SIGHT) return s;
		}
		return -1;
	}

	private void evaporate() {
		for(int c=0;c<SIZE;c++) {
			toHome[c] *= EVAPORATE;
			toFood[c] *= EVAPORATE;
		}
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		private final BufferedImage img = new BufferedImage(GW, GH, BufferedImage.TYPE_INT_RGB);
		private final int[] pixels = new int[SIZE];

		protected void draw() {
			for(int c=0;c<SIZE;c++) {
				double h = Math.min(1, toHome[c]/LEVEL_MAX);
				double f = Math.min(1, toFood[c]/LEVEL_MAX);
				int r = clamp(COLOR_BG.getRed()+f*215);
				int g = clamp(COLOR_BG.getGreen()+f*140+h*60);
				int b = clamp(COLOR_BG.getBlue()+h*215);
				pixels[c] = (r<<16)|(g<<8)|b;
			}
			img.setRGB(0, 0, GW, GH, pixels, 0, GW);
			paintImage(p(0,0), gameWidth(), gameHeight(), img);
		}

		private int clamp(double v) {
			return v>255 ? 255 : (int) v;
		}
	}

	/*
	 * ANTS
	 */

	private class Ants extends Drawing1 {

		protected void draw() {
			for(int s=0;s<FOOD_SOURCES;s++) {
				if(foodLeft[s]<=0) continue;
				double radius = FOOD_RADIUS*CELL*Math.sqrt(foodLeft[s]/(double) FOOD_AMOUNT);
				fillRoundC(COLOR_FOOD, p(foodX[s]*CELL, foodY[s]*CELL), radius);
			}

			fillRoundC(COLOR_NEST, p(nestX*CELL, nestY*CELL), NEST_RADIUS*CELL);

			for(int i=0;i<ANT_COUNT;i++) {
				Color color = antLoaded[i] ? COLOR_ANT_LOADED : COLOR_ANT;
				fillRoundC(color, p(antX[i]*CELL, antY[i]*CELL), 1.6);
			}
		}
	}
}
