package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Locale;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.UtilRandom;

public class AnimNeuralClassifier extends Game1 {

	public static final long SLEEP = 30;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int PLOT_X = 20;
	public static final int PLOT_Y = 110;
	public static final int PLOT_SIZE = 560;

	public static final int PANEL_X = 605;
	public static final int PANEL_W = 275;

	public static final int CURVE_LABEL_X = 606;
	public static final int CURVE_X = 645;
	public static final int CURVE_Y = 460;
	public static final int CURVE_W = 235;
	public static final int CURVE_H = 150;
	public static final double CURVE_MIN = 0.5;

	public static final int MAP_GRID = 120;

	public static final int HIDDEN = 10;
	public static final int SAMPLES = 240;
	public static final double LEARNING_RATE = 0.25;
	public static final double DECAY_STEPS = 8000;
	public static final double WEIGHT_INIT = 1.0;
	public static final int STEPS_PER_FRAME = 60;

	public static final double ACCURACY_TARGET = 0.95;
	public static final int HOLD_FRAMES = 70;
	public static final int MAX_STEPS = 80000;
	public static final int PAUSE_FRAMES = 110;

	public static final int PATTERN_XOR = 0;
	public static final int PATTERN_RINGS = 1;
	public static final int PATTERN_SPIRAL = 2;
	public static final int PATTERN_COUNT = 3;

	public static final Color COLOR_BG = new Color(10, 14, 20);
	public static final Color COLOR_TEXT = new Color(150, 170, 195);
	public static final Color COLOR_TITLE = new Color(255, 200, 60);
	public static final Color COLOR_BORDER = new Color(90, 110, 140);
	public static final Color COLOR_CLASS_0 = new Color(90, 180, 255);
	public static final Color COLOR_CLASS_1 = new Color(255, 170, 60);
	public static final Color COLOR_EDGE = new Color(12, 16, 22, 210);
	public static final Color COLOR_WRONG = new Color(255, 60, 60);
	public static final Color COLOR_OK = new Color(140, 235, 150);
	public static final Color COLOR_CURVE = new Color(120, 235, 180);
	public static final Color COLOR_GRID = new Color(45, 55, 70);
	public static final Color COLOR_BOUNDARY = new Color(228, 232, 238);

	public static final int MAP_RGB_0 = new Color(26, 54, 104).getRGB();
	public static final int MAP_RGB_1 = new Color(128, 70, 22).getRGB();
	public static final int MAP_RGB_EDGE = COLOR_BOUNDARY.getRGB();

	public static void main(String[] args) {
		AnimNeuralClassifier main = new AnimNeuralClassifier();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Reseau de neurones - frontiere de decision apprise");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[][] weight1;
	private double[] bias1;
	private double[] weight2;
	private double bias2;
	private double[] hidden;

	private double[] sampleX;
	private double[] sampleY;
	private int[] sampleLabel;
	private boolean[] sampleWrong;

	private int pattern;
	private double patternPhase;

	private int step;
	private double accuracy;
	private int hold;
	private int pause;

	private BufferedImage image;
	private int[] pixels;
	private double[] mapOut;

	private double[] history;
	private int historySize;
	private int historyEvery;
	private int historyTick;

	protected void initialize1() {
		weight1 = new double[HIDDEN][2];
		bias1 = new double[HIDDEN];
		weight2 = new double[HIDDEN];
		hidden = new double[HIDDEN];

		sampleX = new double[SAMPLES];
		sampleY = new double[SAMPLES];
		sampleLabel = new int[SAMPLES];
		sampleWrong = new boolean[SAMPLES];

		image = new BufferedImage(MAP_GRID, MAP_GRID, BufferedImage.TYPE_INT_RGB);
		pixels = new int[MAP_GRID*MAP_GRID];
		mapOut = new double[MAP_GRID*MAP_GRID];

		history = new double[CURVE_W];

		resetRun(UtilRandom.randomInt(PATTERN_COUNT));

		addDraw(new DecisionMap());
		addDraw(new Samples());
		addDraw(new Curve());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) { restart(); return; }
		if(k.in().F2()) { exit(); return; }

		if(done()) {
			pause++;
			if(pause<PAUSE_FRAMES) return;
			resetRun((pattern+1+UtilRandom.randomInt(PATTERN_COUNT-1))%PATTERN_COUNT);
			return;
		}

		for(int i=0;i<STEPS_PER_FRAME;i++) learn(UtilRandom.randomInt(SAMPLES));
		step += STEPS_PER_FRAME;

		evaluate();
		pushHistory();
		buildMap();

		if(accuracy>=ACCURACY_TARGET) hold++;
		else hold = 0;
	}

	/*
	 * RUN
	 */

	private void resetRun(int newPattern) {
		pattern = newPattern;
		patternPhase = UtilRandom.randomDouble(0.0, 2*Math.PI);

		for(int j=0;j<HIDDEN;j++) {
			weight1[j][0] = UtilRandom.randomDouble(-WEIGHT_INIT, WEIGHT_INIT);
			weight1[j][1] = UtilRandom.randomDouble(-WEIGHT_INIT, WEIGHT_INIT);
			bias1[j] = UtilRandom.randomDouble(-WEIGHT_INIT, WEIGHT_INIT);
			weight2[j] = UtilRandom.randomDouble(-WEIGHT_INIT, WEIGHT_INIT);
		}
		bias2 = UtilRandom.randomDouble(-WEIGHT_INIT, WEIGHT_INIT);

		for(int i=0;i<SAMPLES;i++) {
			sampleX[i] = UtilRandom.randomDouble(-1.0, 1.0);
			sampleY[i] = UtilRandom.randomDouble(-1.0, 1.0);
			sampleLabel[i] = label(sampleX[i], sampleY[i]);
			sampleWrong[i] = false;
		}

		step = 0;
		hold = 0;
		pause = 0;
		historySize = 0;
		historyEvery = 1;
		historyTick = 0;

		evaluate();
		pushHistory();
		buildMap();
	}

	private boolean done() {
		return hold>=HOLD_FRAMES || step>=MAX_STEPS;
	}

	/*
	 * PATTERN
	 */

	private int label(double x, double y) {
		if(pattern==PATTERN_XOR) {
			double a = x*Math.cos(patternPhase)-y*Math.sin(patternPhase);
			double b = x*Math.sin(patternPhase)+y*Math.cos(patternPhase);
			return a*b>0 ? 1 : 0;
		}
		if(pattern==PATTERN_RINGS) {
			double r = Math.sqrt(x*x+y*y);
			return Math.sin(patternPhase*0.1+r*3.6)>0 ? 1 : 0;
		}
		double r = Math.sqrt(x*x+y*y);
		double theta = Math.atan2(y, x);
		return Math.sin(theta+patternPhase+r*3.4)>0 ? 1 : 0;
	}

	private String patternName() {
		if(pattern==PATTERN_XOR) return "XOR (quadrants croises)";
		if(pattern==PATTERN_RINGS) return "anneaux concentriques";
		return "spirale";
	}

	/*
	 * NETWORK
	 */

	private double forward(double x, double y) {
		double sum = bias2;
		for(int j=0;j<HIDDEN;j++) {
			hidden[j] = Math.tanh(weight1[j][0]*x+weight1[j][1]*y+bias1[j]);
			sum += weight2[j]*hidden[j];
		}
		return 1/(1+Math.exp(-sum));
	}

	private double rate() {
		return LEARNING_RATE/(1+step/DECAY_STEPS);
	}

	private void learn(int index) {
		double x = sampleX[index];
		double y = sampleY[index];
		double rate = rate();
		double out = forward(x, y);
		double error = out-sampleLabel[index];

		for(int j=0;j<HIDDEN;j++) {
			double inner = error*weight2[j]*(1-hidden[j]*hidden[j]);
			weight2[j] -= rate*error*hidden[j];
			weight1[j][0] -= rate*inner*x;
			weight1[j][1] -= rate*inner*y;
			bias1[j] -= rate*inner;
		}
		bias2 -= rate*error;
	}

	private void evaluate() {
		int good = 0;
		for(int i=0;i<SAMPLES;i++) {
			int predicted = forward(sampleX[i], sampleY[i])>0.5 ? 1 : 0;
			sampleWrong[i] = predicted!=sampleLabel[i];
			if(!sampleWrong[i]) good++;
		}
		accuracy = (double)good/SAMPLES;
	}

	/*
	 * MAP
	 */

	private void buildMap() {
		for(int j=0;j<MAP_GRID;j++) {
			double wy = 1-(j+0.5)*2.0/MAP_GRID;
			for(int i=0;i<MAP_GRID;i++) {
				double wx = -1+(i+0.5)*2.0/MAP_GRID;
				mapOut[j*MAP_GRID+i] = forward(wx, wy);
			}
		}

		for(int j=0;j<MAP_GRID;j++) {
			for(int i=0;i<MAP_GRID;i++) {
				int index = j*MAP_GRID+i;
				pixels[index] = boundary(i, j) ? MAP_RGB_EDGE : blend(MAP_RGB_0, MAP_RGB_1, mapOut[index]);
			}
		}

		image.setRGB(0, 0, MAP_GRID, MAP_GRID, pixels, 0, MAP_GRID);
	}

	private boolean boundary(int i, int j) {
		boolean side = mapOut[j*MAP_GRID+i]>0.5;
		if(i+1<MAP_GRID && (mapOut[j*MAP_GRID+i+1]>0.5)!=side) return true;
		if(j+1<MAP_GRID && (mapOut[(j+1)*MAP_GRID+i]>0.5)!=side) return true;
		return false;
	}

	private static int blend(int rgb0, int rgb1, double t) {
		int r = (int)(((rgb0>>16)&0xFF)*(1-t)+((rgb1>>16)&0xFF)*t);
		int g = (int)(((rgb0>>8)&0xFF)*(1-t)+((rgb1>>8)&0xFF)*t);
		int b = (int)((rgb0&0xFF)*(1-t)+(rgb1&0xFF)*t);
		return (r<<16)|(g<<8)|b;
	}

	/*
	 * HISTORY
	 */

	private void pushHistory() {
		historyTick++;
		if(historyTick<historyEvery) return;
		historyTick = 0;

		if(historySize>=CURVE_W) {
			for(int i=0;i<CURVE_W/2;i++) history[i] = history[2*i];
			historySize = CURVE_W/2;
			historyEvery *= 2;
		}

		history[historySize] = accuracy;
		historySize++;
	}

	/*
	 * GEOMETRY
	 */

	private static double screenX(double worldX) {
		return PLOT_X+(worldX+1)*0.5*PLOT_SIZE;
	}

	private static double screenY(double worldY) {
		return PLOT_Y+(1-worldY)*0.5*PLOT_SIZE;
	}

	private static double curveY(double value) {
		double ratio = (value-CURVE_MIN)/(1-CURVE_MIN);
		if(ratio<0) ratio = 0;
		if(ratio>1) ratio = 1;
		return CURVE_Y+CURVE_H*(1-ratio);
	}

	private static String percent(double value) {
		return String.format(Locale.US, "%.1f", 100*value)+" %";
	}

	/*
	 * DECISION MAP DRAWING
	 */

	private class DecisionMap extends Drawing1 {

		protected void draw() {
			paintImage(p(PLOT_X, PLOT_Y), PLOT_SIZE, PLOT_SIZE, image);
			drawRect(COLOR_BORDER, p(PLOT_X, PLOT_Y), PLOT_SIZE, PLOT_SIZE);
		}
	}

	/*
	 * SAMPLES DRAWING
	 */

	private class Samples extends Drawing1 {

		protected void draw() {
			for(int i=0;i<SAMPLES;i++) {
				double sx = screenX(sampleX[i]);
				double sy = screenY(sampleY[i]);
				Color color = sampleLabel[i]==1 ? COLOR_CLASS_1 : COLOR_CLASS_0;
				fillRoundC(color, p(sx, sy), 4);
				if(sampleWrong[i]) {
					drawRoundC(COLOR_WRONG, p(sx, sy), 6);
					drawRoundC(COLOR_WRONG, p(sx, sy), 7);
				} else {
					drawRoundC(COLOR_EDGE, p(sx, sy), 4);
				}
			}
		}
	}

	/*
	 * CURVE DRAWING
	 */

	private class Curve extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TEXT, fontPlain(12), p(PANEL_X, CURVE_Y-12), "precision au fil des iterations");
			fillRect(COLOR_BG, p(CURVE_X, CURVE_Y), CURVE_W, CURVE_H);
			drawRect(COLOR_BORDER, p(CURVE_X, CURVE_Y), CURVE_W, CURVE_H);

			for(int i=1;i<5;i++) {
				double y = CURVE_Y+CURVE_H*i/5.0;
				drawLine(COLOR_GRID, p(CURVE_X+1, y), p(CURVE_X+CURVE_W-1, y));
			}

			double targetY = curveY(ACCURACY_TARGET);
			drawLine(COLOR_OK, p(CURVE_X+1, targetY), p(CURVE_X+CURVE_W-1, targetY));

			for(int i=1;i<historySize;i++) {
				drawLine(COLOR_CURVE,
						p(CURVE_X+i-1, curveY(history[i-1])),
						p(CURVE_X+i, curveY(history[i])));
			}

			drawString(COLOR_TEXT, fontPlain(11), p(CURVE_LABEL_X, CURVE_Y+5), "100 %");
			drawString(COLOR_OK, fontPlain(11), p(CURVE_LABEL_X, targetY+4), percent(ACCURACY_TARGET));
			drawString(COLOR_TEXT, fontPlain(11), p(CURVE_LABEL_X, CURVE_Y+CURVE_H+4), "50 %");
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TITLE, fontBold(15), p(16, 28), "RESEAU DE NEURONES - FRONTIERE DE DECISION APPRISE");
			drawString(COLOR_TEXT, fontPlain(13), p(16, 50),
					"perceptron multicouche 2 - "+HIDDEN+" (tanh) - 1 (sigmoide), retropropagation, pas d'apprentissage "+String.format(Locale.US, "%.4f", rate()));
			drawString(COLOR_TEXT, fontPlain(13), p(16, 70),
					"motif cache : "+patternName()+"   non lineairement separable : une droite ne peut pas le trancher");

			drawString(COLOR_TITLE, fontBold(14), p(PANEL_X, 140), "ETAT");
			drawString(COLOR_TEXT, fontPlain(13), p(PANEL_X, 168), "iterations   "+Math.min(step, MAX_STEPS)+" / "+MAX_STEPS);
			drawString(COLOR_TEXT, fontPlain(13), p(PANEL_X, 190), "exemples     "+SAMPLES);
			drawString(COLOR_TEXT, fontPlain(13), p(PANEL_X, 212), "precision    "+percent(accuracy));
			drawString(COLOR_TEXT, fontPlain(13), p(PANEL_X, 234), "mal classes  "+wrongCount()+" / "+SAMPLES);
			drawString(COLOR_TEXT, fontPlain(13), p(PANEL_X, 256), "palier       "+Math.min(hold, HOLD_FRAMES)+" / "+HOLD_FRAMES);

			drawString(COLOR_TITLE, fontBold(14), p(PANEL_X, 300), "LEGENDE");
			fillRoundC(COLOR_CLASS_0, p(PANEL_X+8, 322), 4);
			drawString(COLOR_TEXT, fontPlain(12), p(PANEL_X+24, 326), "classe 0 (fond bleu appris)");
			fillRoundC(COLOR_CLASS_1, p(PANEL_X+8, 344), 4);
			drawString(COLOR_TEXT, fontPlain(12), p(PANEL_X+24, 348), "classe 1 (fond orange appris)");
			drawRoundC(COLOR_WRONG, p(PANEL_X+8, 366), 6);
			drawString(COLOR_TEXT, fontPlain(12), p(PANEL_X+24, 370), "point mal classe actuellement");
			drawLine(COLOR_BOUNDARY, p(PANEL_X+2, 388), p(PANEL_X+14, 388));
			drawString(COLOR_TEXT, fontPlain(12), p(PANEL_X+24, 392), "lisere clair : frontiere de decision");

			if(hold>=HOLD_FRAMES) {
				drawString(COLOR_OK, fontBold(13), p(16, 92), "frontiere apprise et stabilisee - nouveau motif dans un instant");
			} else if(step>=MAX_STEPS) {
				drawString(COLOR_TITLE, fontBold(13), p(16, 92), "limite d'iterations atteinte - nouveau motif dans un instant");
			}

			drawString(COLOR_TEXT, fontPlain(11), p(16, HEIGHT-16), "F1 relancer   F2 quitter");
		}
	}

	private int wrongCount() {
		int count = 0;
		for(int i=0;i<SAMPLES;i++) if(sampleWrong[i]) count++;
		return count;
	}
}
