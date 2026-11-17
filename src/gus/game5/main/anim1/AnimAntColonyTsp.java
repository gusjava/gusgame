package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimAntColonyTsp extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int MARGIN_X = 70;
	public static final int MARGIN_TOP = 70;
	public static final int MARGIN_BOTTOM = 50;

	public static final int CITY_MIN = 20;
	public static final int CITY_MAX = 30;
	public static final double CITY_RADIUS = 5;
	public static final double CITY_GAP = 34;
	public static final int PLACE_ATTEMPTS = 200;

	public static final int ANT_COUNT = 40;
	public static final double ALPHA = 1;
	public static final double BETA = 2;
	public static final double EVAPORATION = 0.15;
	public static final double PHEROMONE_INIT = 1;
	public static final double PHEROMONE_MIN = 0.01;
	public static final int ITER_PER_FRAME = 1;

	public static final int STALL_LIMIT = 300;
	public static final int PAUSE_FRAMES = 110;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);
	public static final Color COLOR_TITLE = new Color(255,200,60);
	public static final Color COLOR_CITY = new Color(235,240,250);
	public static final Color COLOR_START = new Color(110,230,130);
	public static final Color COLOR_PHEROMONE = new Color(90,170,255);
	public static final Color COLOR_BEST = new Color(140,235,150);

	public static void main(String[] args) {
		AnimAntColonyTsp main = new AnimAntColonyTsp();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Colonie de fourmis - voyageur de commerce");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int n;
	private double[] cityX;
	private double[] cityY;
	private double[][] dist;
	private double[][] pheromone;

	private int[] bestTour;
	private double bestDistance;
	private int iteration;
	private int stall;
	private int pause;

	protected void initialize1() {
		buildCities();
		resetColony();

		addDraw(new Board());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) { restart(); return; }
		if(k.in().F2()) { exit(); return; }

		if(stall>=STALL_LIMIT) {
			pause++;
			if(pause<PAUSE_FRAMES) return;
			buildCities();
			resetColony();
			return;
		}

		for(int i=0;i<ITER_PER_FRAME;i++) runIteration();
	}

	private void resetColony() {
		pheromone = new double[n][n];
		for(int i=0;i<n;i++)
		for(int j=0;j<n;j++) pheromone[i][j] = PHEROMONE_INIT;

		bestTour = null;
		bestDistance = -1;
		iteration = 0;
		stall = 0;
		pause = 0;
	}

	private void runIteration() {
		int[][] tours = new int[ANT_COUNT][];
		double[] lengths = new double[ANT_COUNT];

		for(int a=0;a<ANT_COUNT;a++) {
			tours[a] = buildTour();
			lengths[a] = tourLength(tours[a]);
		}

		evaporate();
		depositAll(tours, lengths);

		int best = 0;
		for(int a=1;a<ANT_COUNT;a++) if(lengths[a]<lengths[best]) best = a;

		if(bestTour==null || lengths[best]<bestDistance) {
			bestTour = tours[best];
			bestDistance = lengths[best];
			stall = 0;
		} else {
			stall++;
		}

		iteration++;
	}

	private int[] buildTour() {
		int[] tour = new int[n];
		boolean[] visited = new boolean[n];

		int start = UtilRandom.randomInt(n);
		tour[0] = start;
		visited[start] = true;

		for(int step=1;step<n;step++) {
			int from = tour[step-1];
			tour[step] = chooseNext(from, visited);
			visited[tour[step]] = true;
		}

		return tour;
	}

	private int chooseNext(int from, boolean[] visited) {
		double total = 0;
		double[] weight = new double[n];

		for(int j=0;j<n;j++) {
			if(visited[j]) continue;
			weight[j] = Math.pow(pheromone[from][j], ALPHA)*Math.pow(1/dist[from][j], BETA);
			total += weight[j];
		}

		double pick = UtilRandom.randomDouble(total);
		double sum = 0;
		for(int j=0;j<n;j++) {
			if(visited[j]) continue;
			sum += weight[j];
			if(sum>=pick) return j;
		}

		for(int j=0;j<n;j++) if(!visited[j]) return j;
		return -1;
	}

	private void evaporate() {
		for(int i=0;i<n;i++)
		for(int j=0;j<n;j++) pheromone[i][j] = Math.max(PHEROMONE_MIN, pheromone[i][j]*(1-EVAPORATION));
	}

	private void depositAll(int[][] tours, double[] lengths) {
		for(int ant=0;ant<ANT_COUNT;ant++) {
			double drop = 1/lengths[ant];
			int[] tour = tours[ant];
			for(int i=0;i<n;i++) {
				int a = tour[i];
				int b = tour[(i+1)%n];
				pheromone[a][b] += drop;
				pheromone[b][a] += drop;
			}
		}
	}

	/*
	 * CITIES
	 */

	private void buildCities() {
		n = CITY_MIN+UtilRandom.randomInt(CITY_MAX-CITY_MIN+1);
		cityX = new double[n];
		cityY = new double[n];

		for(int i=0;i<n;i++) {
			double x = 0;
			double y = 0;
			for(int attempt=0;attempt<PLACE_ATTEMPTS;attempt++) {
				x = MARGIN_X+UtilRandom.randomDouble(WIDTH-2*MARGIN_X);
				y = MARGIN_TOP+UtilRandom.randomDouble(HEIGHT-MARGIN_TOP-MARGIN_BOTTOM);
				if(isFarEnough(x, y, i)) break;
			}
			cityX[i] = x;
			cityY[i] = y;
		}

		dist = new double[n][n];
		for(int i=0;i<n;i++)
		for(int j=0;j<n;j++) {
			double dx = cityX[i]-cityX[j];
			double dy = cityY[i]-cityY[j];
			dist[i][j] = i==j ? 1e-6 : Math.sqrt(dx*dx+dy*dy);
		}
	}

	private boolean isFarEnough(double x, double y, int count) {
		for(int i=0;i<count;i++) {
			double dx = cityX[i]-x;
			double dy = cityY[i]-y;
			if(dx*dx+dy*dy<CITY_GAP*CITY_GAP) return false;
		}
		return true;
	}

	private double tourLength(int[] tour) {
		double total = 0;
		for(int i=0;i<n;i++) total += dist[tour[i]][tour[(i+1)%n]];
		return total;
	}

	/*
	 * BOARD
	 */

	private class Board extends Drawing1 {

		protected void draw() {
			double maxPheromone = 1e-9;
			for(int i=0;i<n;i++)
			for(int j=i+1;j<n;j++) maxPheromone = Math.max(maxPheromone, pheromone[i][j]);

			for(int i=0;i<n;i++)
			for(int j=i+1;j<n;j++) {
				double level = Math.pow(pheromone[i][j]/maxPheromone, 3);
				if(level<0.02) continue;
				int alpha = (int) Math.min(255, 30+level*225);
				Color color = new Color(COLOR_PHEROMONE.getRed(), COLOR_PHEROMONE.getGreen(), COLOR_PHEROMONE.getBlue(), alpha);
				drawThickLine(color, city(i), city(j), 1+level*3);
			}

			if(bestTour!=null) {
				for(int i=0;i<n;i++) drawThickLine(COLOR_BEST, city(bestTour[i]), city(bestTour[(i+1)%n]), 2.5);
			}

			for(int i=0;i<n;i++) fillRoundC(COLOR_CITY, city(i), CITY_RADIUS);
			if(bestTour!=null) fillRoundC(COLOR_START, city(bestTour[0]), CITY_RADIUS+2);
		}
	}

	private Point1 city(int index) {
		return Drawing1.p(cityX[index], cityY[index]);
	}

	private static double round(double value) {
		return Math.round(value*10)/10.0;
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TITLE, fontBold(15), p(16, 28), "COLONIE DE FOURMIS - VOYAGEUR DE COMMERCE");
			drawString(COLOR_TEXT, fontPlain(13), p(16, 50), "iteration "+iteration+"   villes "+n+"   fourmis "+ANT_COUNT);
			drawString(COLOR_TEXT, fontPlain(13), p(16, 70),
					bestTour==null ? "meilleure tournee : -" : "meilleure tournee : "+round(bestDistance));
			if(stall>=STALL_LIMIT) {
				drawString(COLOR_BEST, fontBold(13), p(16, 92), "converge - nouvelles villes dans un instant");
			}

			drawString(COLOR_TEXT, fontPlain(11), p(16, HEIGHT-18), "F1 nouvelles villes   F2 quitter");
		}
	}
}
