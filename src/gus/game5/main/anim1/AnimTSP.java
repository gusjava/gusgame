package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;

public class AnimTSP extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int MARGIN_X = 70;
	public static final int MARGIN_TOP = 130;
	public static final int MARGIN_BOTTOM = 50;

	public static final int CITY_MIN = 20;
	public static final int CITY_MAX = 30;
	public static final double CITY_RADIUS = 5;
	public static final double CITY_GAP = 34;
	public static final int PLACE_ATTEMPTS = 200;

	public static final int PAUSE_FRAMES = 110;

	public static final int NN_FRAMES_PER_CITY = 6;
	public static final int OPT_FRAMES_PER_MOVE = 8;
	public static final int OPT_SCAN_PER_STEP = 4000;

	public static final int GA_POP_SIZE = 80;
	public static final int GA_GENERATIONS = 200;
	public static final double GA_MUTATION_RATE = 0.15;
	public static final int GA_TOURNAMENT_SIZE = 4;

	public static final int SA_ITERATIONS = 60000;
	public static final int SA_ITER_PER_FRAME = 240;
	public static final double SA_TEMP_START = 150;
	public static final double SA_TEMP_END = 0.4;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);
	public static final Color COLOR_TITLE = new Color(255,200,60);
	public static final Color COLOR_CITY = new Color(235,240,250);
	public static final Color COLOR_START = new Color(110,230,130);
	public static final Color COLOR_TOUR = new Color(90,170,255);
	public static final Color COLOR_ROW = new Color(120,140,165);
	public static final Color COLOR_BEST = new Color(140,235,150);

	public static final String[] NAMES = {
		"Plus proche voisin",
		"2-opt",
		"Algorithme genetique",
		"Recuit simule"
	};

	public static void main(String[] args) {
		AnimTSP main = new AnimTSP();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Voyageur de commerce");
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

	private Solver solver;
	private int selected;
	private int pause;
	private double[] result;

	protected void initialize1() {
		buildCities();

		result = new double[NAMES.length];
		for(int i=0;i<result.length;i++) result[i] = -1;

		startSolver();

		addDraw(new Board());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();

		if(k.in().F1()) { restart(); return; }
		if(k.in().F2()) { exit(); return; }

		if(k.out().up()) { select(selected+NAMES.length-1); return; }
		else if(k.out().down()) { select(selected+1); return; }

		if(solver.isDone()) {
			pause++;
			if(pause<PAUSE_FRAMES) return;
			if(selected<NAMES.length-1) select(selected+1);
			else { selected = 0; restart(); }
			return;
		}

		solver.step();
		if(solver.isDone()) result[selected] = solver.getDistance();
	}

	private void select(int index) {
		selected = index%NAMES.length;
		startSolver();
	}

	private void startSolver() {
		pause = 0;
		if(selected==0) solver = new NearestNeighbor();
		else if(selected==1) solver = new TwoOpt();
		else if(selected==2) solver = new Genetic();
		else solver = new Annealing();
		result[selected] = -1;
	}

	/*
	 * CITIES
	 */

	private void buildCities() {
		n = CITY_MIN+(int) (Math.random()*(CITY_MAX-CITY_MIN+1));
		cityX = new double[n];
		cityY = new double[n];

		for(int i=0;i<n;i++) {
			double x = 0;
			double y = 0;
			for(int attempt=0;attempt<PLACE_ATTEMPTS;attempt++) {
				x = MARGIN_X+Math.random()*(WIDTH-2*MARGIN_X);
				y = MARGIN_TOP+Math.random()*(HEIGHT-MARGIN_TOP-MARGIN_BOTTOM);
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
			dist[i][j] = Math.sqrt(dx*dx+dy*dy);
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

	/*
	 * TOUR
	 */

	private int[] shuffledTour() {
		int[] t = new int[n];
		for(int i=0;i<n;i++) t[i] = i;
		for(int i=n-1;i>0;i--) {
			int j = (int) (Math.random()*(i+1));
			int tmp = t[i];
			t[i] = t[j];
			t[j] = tmp;
		}
		return t;
	}

	private int[] greedyTour() {
		int[] t = new int[n];
		boolean[] used = new boolean[n];
		t[0] = (int) (Math.random()*n);
		used[t[0]] = true;
		for(int c=1;c<n;c++) {
			int last = t[c-1];
			int best = -1;
			double bestD = 0;
			for(int i=0;i<n;i++) {
				if(used[i]) continue;
				if(best<0 || dist[last][i]<bestD) { best = i; bestD = dist[last][i]; }
			}
			t[c] = best;
			used[best] = true;
		}
		return t;
	}

	private double tourLength(int[] t) {
		double total = 0;
		for(int i=0;i<n;i++) total += dist[t[i]][t[(i+1)%n]];
		return total;
	}

	private double pathLength(int[] t, int count, boolean closed) {
		double total = 0;
		for(int i=0;i+1<count;i++) total += dist[t[i]][t[i+1]];
		if(closed && count>1) total += dist[t[count-1]][t[0]];
		return total;
	}

	private double reverseGain(int[] t, int i, int j) {
		int a = t[i-1];
		int b = t[i];
		int c = t[j];
		int d = t[(j+1)%n];
		return dist[a][b]+dist[c][d]-dist[a][c]-dist[b][d];
	}

	private void reverse(int[] t, int i, int j) {
		while(i<j) {
			int tmp = t[i];
			t[i] = t[j];
			t[j] = tmp;
			i++;
			j--;
		}
	}

	/*
	 * SOLVER
	 */

	private abstract class Solver {

		protected int[] tour;

		public abstract void step();
		public abstract boolean isDone();
		public abstract String getDetail();

		public int[] getTour() {
			return tour;
		}

		public int getCount() {
			return n;
		}

		public boolean isClosed() {
			return true;
		}

		public double getDistance() {
			return tourLength(tour);
		}
	}

	/*
	 * NEAREST NEIGHBOR
	 */

	private class NearestNeighbor extends Solver {

		private boolean[] used;
		private int count;
		private int wait;

		public NearestNeighbor() {
			tour = new int[n];
			used = new boolean[n];
			tour[0] = (int) (Math.random()*n);
			used[tour[0]] = true;
			count = 1;
		}

		public void step() {
			wait++;
			if(wait<NN_FRAMES_PER_CITY) return;
			wait = 0;

			int last = tour[count-1];
			int best = -1;
			double bestD = 0;
			for(int i=0;i<n;i++) {
				if(used[i]) continue;
				if(best<0 || dist[last][i]<bestD) { best = i; bestD = dist[last][i]; }
			}
			tour[count] = best;
			used[best] = true;
			count++;
		}

		public boolean isDone() {
			return count>=n;
		}

		public int getCount() {
			return count;
		}

		public boolean isClosed() {
			return count>=n;
		}

		public double getDistance() {
			return pathLength(tour, count, isClosed());
		}

		public String getDetail() {
			return "villes reliees "+count+"/"+n;
		}
	}

	/*
	 * TWO OPT
	 */

	private class TwoOpt extends Solver {

		private int i;
		private int j;
		private boolean improved;
		private boolean done;
		private int moves;
		private int wait;

		public TwoOpt() {
			tour = greedyTour();
			i = 1;
			j = 2;
		}

		public void step() {
			wait++;
			if(wait<OPT_FRAMES_PER_MOVE) return;
			wait = 0;

			for(int scan=0;scan<OPT_SCAN_PER_STEP;scan++) {
				if(i>n-2) {
					if(!improved) { done = true; return; }
					improved = false;
					i = 1;
					j = 2;
					continue;
				}
				if(j>n-1) {
					i++;
					j = i+1;
					continue;
				}
				int ci = i;
				int cj = j;
				double gain = reverseGain(tour, ci, cj);
				j++;
				if(gain>1e-9) {
					reverse(tour, ci, cj);
					moves++;
					improved = true;
					return;
				}
			}
		}

		public boolean isDone() {
			return done;
		}

		public String getDetail() {
			return done ? "optimum local atteint en "+moves+" echanges" : "echanges appliques "+moves;
		}
	}

	/*
	 * GENETIC
	 */

	private class Genetic extends Solver {

		private int[][] population;
		private double[] fitness;
		private double bestDist;
		private int gen;

		public Genetic() {
			population = new int[GA_POP_SIZE][];
			for(int i=0;i<GA_POP_SIZE;i++) population[i] = shuffledTour();
			fitness = new double[GA_POP_SIZE];
			tour = population[0].clone();
			bestDist = tourLength(tour);
		}

		public void step() {
			for(int i=0;i<GA_POP_SIZE;i++) fitness[i] = tourLength(population[i]);

			int best = 0;
			for(int i=1;i<GA_POP_SIZE;i++) if(fitness[i]<fitness[best]) best = i;
			if(fitness[best]<bestDist) {
				bestDist = fitness[best];
				tour = population[best].clone();
			}

			int[][] next = new int[GA_POP_SIZE][];
			next[0] = tour.clone();
			int size = 1;

			while(size<GA_POP_SIZE) {
				int[] p1 = population[tournament()];
				int[] p2 = population[tournament()];

				int cut1 = (int) (Math.random()*(n-1));
				int cut2 = cut1+1+(int) (Math.random()*(n-1-cut1));

				int[] c1 = orderCrossover(p1, p2, cut1, cut2);
				int[] c2 = orderCrossover(p2, p1, cut1, cut2);
				if(Math.random()<GA_MUTATION_RATE) swapMutate(c1);
				if(Math.random()<GA_MUTATION_RATE) swapMutate(c2);

				next[size] = c1;
				size++;
				if(size<GA_POP_SIZE) { next[size] = c2; size++; }
			}

			population = next;
			gen++;
		}

		private int tournament() {
			int best = (int) (Math.random()*GA_POP_SIZE);
			for(int t=1;t<GA_TOURNAMENT_SIZE;t++) {
				int cand = (int) (Math.random()*GA_POP_SIZE);
				if(fitness[cand]<fitness[best]) best = cand;
			}
			return best;
		}

		private int[] orderCrossover(int[] seg, int[] fill, int cut1, int cut2) {
			int[] child = new int[n];
			boolean[] used = new boolean[n];
			for(int i=0;i<n;i++) child[i] = -1;
			for(int i=cut1;i<=cut2;i++) {
				child[i] = seg[i];
				used[seg[i]] = true;
			}
			int pos = (cut2+1)%n;
			for(int i=0;i<n;i++) {
				int gene = fill[(cut2+1+i)%n];
				if(used[gene]) continue;
				while(child[pos]>=0) pos = (pos+1)%n;
				child[pos] = gene;
				used[gene] = true;
			}
			return child;
		}

		private void swapMutate(int[] t) {
			int a = (int) (Math.random()*n);
			int b = (int) (Math.random()*n);
			int tmp = t[a];
			t[a] = t[b];
			t[b] = tmp;
		}

		public boolean isDone() {
			return gen>=GA_GENERATIONS;
		}

		public double getDistance() {
			return bestDist;
		}

		public String getDetail() {
			return "generation "+gen+"/"+GA_GENERATIONS+"   population "+GA_POP_SIZE;
		}
	}

	/*
	 * ANNEALING
	 */

	private class Annealing extends Solver {

		private double currentDist;
		private int[] best;
		private double bestDist;
		private int iter;

		public Annealing() {
			tour = shuffledTour();
			currentDist = tourLength(tour);
			best = tour.clone();
			bestDist = currentDist;
		}

		public void step() {
			for(int s=0;s<SA_ITER_PER_FRAME && iter<SA_ITERATIONS;s++) {
				iter++;

				int a = 1+(int) (Math.random()*(n-1));
				int b = 1+(int) (Math.random()*(n-1));
				if(a==b) continue;
				if(a>b) { int tmp = a; a = b; b = tmp; }

				double gain = reverseGain(tour, a, b);
				if(gain<=0 && Math.random()>=Math.exp(gain/temperature())) continue;

				reverse(tour, a, b);
				currentDist -= gain;
				if(currentDist<bestDist) {
					bestDist = currentDist;
					best = tour.clone();
				}
			}
			if(iter>=SA_ITERATIONS) {
				tour = best;
				currentDist = bestDist;
			}
		}

		private double temperature() {
			double t = iter/(double) SA_ITERATIONS;
			return SA_TEMP_START*Math.pow(SA_TEMP_END/SA_TEMP_START, t);
		}

		public boolean isDone() {
			return iter>=SA_ITERATIONS;
		}

		public double getDistance() {
			return bestDist;
		}

		public String getDetail() {
			return "iteration "+iter+"/"+SA_ITERATIONS+"   temperature "+round(temperature())
					+"   tournee courante "+round(currentDist);
		}
	}

	/*
	 * BOARD
	 */

	private class Board extends Drawing1 {

		protected void draw() {
			int[] t = solver.getTour();
			int count = solver.getCount();

			for(int i=0;i+1<count;i++) drawThickLine(COLOR_TOUR, city(t[i]), city(t[i+1]), 2);
			if(solver.isClosed() && count>1) drawThickLine(COLOR_TOUR, city(t[count-1]), city(t[0]), 2);

			for(int i=0;i<n;i++) fillRoundC(COLOR_CITY, city(i), CITY_RADIUS);
			if(count>0) fillRoundC(COLOR_START, city(t[0]), CITY_RADIUS+2);
		}
	}

	private static double round(double value) {
		return Math.round(value*10)/10.0;
	}

	private Point1 city(int index) {
		return Drawing1.p(cityX[index], cityY[index]);
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			drawString(COLOR_TITLE, fontBold(15), p(16, 28),
					"APPROCHE "+(selected+1)+"/"+NAMES.length+" : "+NAMES[selected]);
			drawString(COLOR_TEXT, fontPlain(13), p(16, 50), solver.getDetail());
			drawString(COLOR_TEXT, fontPlain(13), p(16, 70),
					"meilleure distance : "+round(solver.getDistance())+"   villes : "+n);

			drawTable();

			drawString(COLOR_ROW, fontPlain(11), p(16, HEIGHT-18),
					"HAUT/BAS changer d'approche sur les memes villes   F1 nouvelles villes   F2 quitter");
		}

		private void drawTable() {
			int best = -1;
			for(int i=0;i<NAMES.length;i++) {
				if(result[i]<0) continue;
				if(best<0 || result[i]<result[best]) best = i;
			}

			drawString(COLOR_ROW, fontPlain(11), p(WIDTH-300, 28), "COMPARAISON SUR CES VILLES");
			for(int i=0;i<NAMES.length;i++) {
				String value = result[i]>=0 ? String.valueOf(round(result[i])) : (i==selected ? "en cours" : "-");
				Color color = i==best ? COLOR_BEST : COLOR_ROW;
				drawString(color, i==selected ? fontBold(12) : fontPlain(12), p(WIDTH-300, 48+i*18),
						NAMES[i]+" : "+value);
			}
		}
	}
}
