package gus.game5.main.anim1;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimPathfinding extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int GRID_W = 40;
	public static final int GRID_H = 30;
	public static final int CELL = 16;

	public static final int MARGIN_TOP = 130;
	public static final int MARGIN_X = (WIDTH-GRID_W*CELL)/2;

	public static final double WALL_PROB = 0.28;
	public static final double DIFFICULT_PROB = 0.18;
	public static final double DIFFICULT_COST = 6;

	public static final int CELLS_PER_STEP = 4;
	public static final int PAUSE_FRAMES = 110;
	public static final int MAX_GENERATION_ATTEMPTS = 2000;

	public static final int[] DX = {1,-1,0,0};
	public static final int[] DY = {0,0,1,-1};

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);
	public static final Color COLOR_TITLE = new Color(255,200,60);
	public static final Color COLOR_WALL = new Color(40,44,52);
	public static final Color COLOR_DIFFICULT = new Color(110,75,40);
	public static final Color COLOR_FRONTIER = new Color(255,205,80);
	public static final Color COLOR_VISITED = new Color(60,95,140);
	public static final Color COLOR_PATH = new Color(140,235,150);
	public static final Color COLOR_START = new Color(110,230,130);
	public static final Color COLOR_END = new Color(230,90,90);
	public static final Color COLOR_ROW = new Color(120,140,165);
	public static final Color COLOR_BEST = new Color(140,235,150);

	public static final String[] NAMES = {
		"BFS (parcours en largeur)",
		"Dijkstra",
		"A*"
	};

	public static void main(String[] args) {
		AnimPathfinding main = new AnimPathfinding();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Recherche de chemin : BFS / Dijkstra / A*");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private boolean[][] wall;
	private boolean[][] difficult;
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	private Solver solver;
	private int selected;
	private int pause;
	private int[] result;

	protected void initialize1() {
		buildGrid();

		result = new int[NAMES.length];
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
		if(solver.isDone()) result[selected] = solver.isFound() ? solver.getPath().length : -1;
	}

	private void select(int index) {
		selected = index%NAMES.length;
		startSolver();
	}

	private void startSolver() {
		pause = 0;
		if(selected==0) solver = new BFS();
		else if(selected==1) solver = new Dijkstra();
		else solver = new AStar();
		result[selected] = -1;
	}

	/*
	 * GRID
	 */

	private void buildGrid() {
		startX = 1;
		startY = GRID_H/2;
		endX = GRID_W-2;
		endY = GRID_H/2;

		for(int attempt=0;attempt<MAX_GENERATION_ATTEMPTS;attempt++) {
			generateWalls();
			if(isConnected()) {
				generateDifficultTerrain();
				return;
			}
		}
		throw new gus.game5.core.exception.TechnicalException("no walkable grid generated");
	}

	private void generateWalls() {
		wall = new boolean[GRID_W][GRID_H];
		for(int x=0;x<GRID_W;x++)
		for(int y=0;y<GRID_H;y++) {
			if(isStartOrEnd(x, y)) continue;
			wall[x][y] = UtilRandom.randomDouble(1.0)<WALL_PROB;
		}
	}

	private void generateDifficultTerrain() {
		difficult = new boolean[GRID_W][GRID_H];
		for(int x=0;x<GRID_W;x++)
		for(int y=0;y<GRID_H;y++) {
			if(wall[x][y] || isStartOrEnd(x, y)) continue;
			difficult[x][y] = UtilRandom.randomDouble(1.0)<DIFFICULT_PROB;
		}
	}

	private boolean isStartOrEnd(int x, int y) {
		return (x==startX && y==startY) || (x==endX && y==endY);
	}

	private boolean isConnected() {
		boolean[][] seen = new boolean[GRID_W][GRID_H];
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.add(new int[]{startX,startY});
		seen[startX][startY] = true;

		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			int cx = cur[0];
			int cy = cur[1];
			if(cx==endX && cy==endY) return true;
			for(int d=0;d<4;d++) {
				int nx = cx+DX[d];
				int ny = cy+DY[d];
				if(!isValid(nx, ny) || wall[nx][ny] || seen[nx][ny]) continue;
				seen[nx][ny] = true;
				queue.add(new int[]{nx,ny});
			}
		}
		return false;
	}

	private boolean isValid(int x, int y) {
		return x>=0 && x<GRID_W && y>=0 && y<GRID_H;
	}

	private double cellCost(int x, int y) {
		return difficult[x][y] ? DIFFICULT_COST : 1;
	}

	/*
	 * SOLVER
	 */

	private abstract class Solver {

		protected boolean[][] closed = new boolean[GRID_W][GRID_H];
		protected boolean[][] frontier = new boolean[GRID_W][GRID_H];
		protected int[][] parentX = new int[GRID_W][GRID_H];
		protected int[][] parentY = new int[GRID_W][GRID_H];
		protected boolean done;
		protected boolean found;
		protected int visitedCount = 1;
		protected int[][] path;
		protected double pathCost;

		public Solver() {
			for(int x=0;x<GRID_W;x++)
			for(int y=0;y<GRID_H;y++) {
				parentX[x][y] = -1;
				parentY[x][y] = -1;
			}
			parentX[startX][startY] = startX;
			parentY[startX][startY] = startY;
			frontier[startX][startY] = true;
		}

		public abstract void step();

		public boolean isDone() {
			return done;
		}

		public boolean isFound() {
			return found;
		}

		public int getVisitedCount() {
			return visitedCount;
		}

		public int[][] getPath() {
			return path;
		}

		public abstract String getDetail();

		protected void onFound(int ex, int ey, double cost) {
			found = true;
			done = true;
			pathCost = cost;
			path = buildPath(ex, ey);
		}

		protected int[][] buildPath(int ex, int ey) {
			List<int[]> list = new ArrayList<>();
			int x = ex;
			int y = ey;
			while(true) {
				list.add(0, new int[]{x,y});
				if(x==startX && y==startY) break;
				int px = parentX[x][y];
				int py = parentY[x][y];
				x = px;
				y = py;
			}
			return list.toArray(new int[list.size()][]);
		}
	}

	/*
	 * BFS
	 */

	private class BFS extends Solver {

		private ArrayDeque<int[]> queue = new ArrayDeque<>();

		public BFS() {
			queue.add(new int[]{startX,startY});
		}

		public void step() {
			int processed = 0;
			while(processed<CELLS_PER_STEP && !queue.isEmpty()) {
				int[] cur = queue.poll();
				int cx = cur[0];
				int cy = cur[1];
				if(closed[cx][cy]) continue;
				closed[cx][cy] = true;
				frontier[cx][cy] = false;
				processed++;

				if(cx==endX && cy==endY) { onFound(cx, cy, 0); return; }

				for(int d=0;d<4;d++) {
					int nx = cx+DX[d];
					int ny = cy+DY[d];
					if(!isValid(nx, ny) || wall[nx][ny] || parentX[nx][ny]!=-1) continue;
					parentX[nx][ny] = cx;
					parentY[nx][ny] = cy;
					frontier[nx][ny] = true;
					visitedCount++;
					queue.add(new int[]{nx,ny});
				}
			}
			if(queue.isEmpty() && !found) done = true;
		}

		public String getDetail() {
			return "file d'attente : "+queue.size()+"   cases visitees : "+visitedCount;
		}
	}

	/*
	 * DIJKSTRA
	 */

	private class Dijkstra extends Solver {

		protected double[][] dist = new double[GRID_W][GRID_H];
		protected PriorityQueue<double[]> queue = new PriorityQueue<>((a,b)->Double.compare(a[0], b[0]));

		public Dijkstra() {
			for(int x=0;x<GRID_W;x++)
			for(int y=0;y<GRID_H;y++) dist[x][y] = Double.MAX_VALUE;
			dist[startX][startY] = 0;
			queue.add(new double[]{0,startX,startY});
		}

		public void step() {
			int processed = 0;
			while(processed<CELLS_PER_STEP && !queue.isEmpty()) {
				double[] cur = queue.poll();
				int cx = (int) cur[1];
				int cy = (int) cur[2];
				if(closed[cx][cy]) continue;
				closed[cx][cy] = true;
				frontier[cx][cy] = false;
				processed++;

				if(cx==endX && cy==endY) { onFound(cx, cy, dist[cx][cy]); return; }

				for(int d=0;d<4;d++) {
					int nx = cx+DX[d];
					int ny = cy+DY[d];
					if(!isValid(nx, ny) || wall[nx][ny] || closed[nx][ny]) continue;
					double newDist = dist[cx][cy]+cellCost(nx, ny);
					if(newDist<dist[nx][ny]) {
						if(!frontier[nx][ny]) { frontier[nx][ny] = true; visitedCount++; }
						dist[nx][ny] = newDist;
						parentX[nx][ny] = cx;
						parentY[nx][ny] = cy;
						queue.add(new double[]{newDist,nx,ny});
					}
				}
			}
			if(queue.isEmpty() && !found) done = true;
		}

		public String getDetail() {
			return "file d'attente : "+queue.size()+"   cases visitees : "+visitedCount+"   cout cumule : "+round(pathCost);
		}
	}

	/*
	 * A STAR
	 */

	private class AStar extends Dijkstra {

		public void step() {
			int processed = 0;
			while(processed<CELLS_PER_STEP && !queue.isEmpty()) {
				double[] cur = queue.poll();
				int cx = (int) cur[1];
				int cy = (int) cur[2];
				if(closed[cx][cy]) continue;
				closed[cx][cy] = true;
				frontier[cx][cy] = false;
				processed++;

				if(cx==endX && cy==endY) { onFound(cx, cy, dist[cx][cy]); return; }

				for(int d=0;d<4;d++) {
					int nx = cx+DX[d];
					int ny = cy+DY[d];
					if(!isValid(nx, ny) || wall[nx][ny] || closed[nx][ny]) continue;
					double newDist = dist[cx][cy]+cellCost(nx, ny);
					if(newDist<dist[nx][ny]) {
						if(!frontier[nx][ny]) { frontier[nx][ny] = true; visitedCount++; }
						dist[nx][ny] = newDist;
						parentX[nx][ny] = cx;
						parentY[nx][ny] = cy;
						queue.add(new double[]{newDist+heuristic(nx, ny),nx,ny});
					}
				}
			}
			if(queue.isEmpty() && !found) done = true;
		}

		private double heuristic(int x, int y) {
			return Math.abs(x-endX)+Math.abs(y-endY);
		}

		public String getDetail() {
			return "file d'attente : "+queue.size()+"   cases visitees : "+visitedCount+"   cout cumule : "+round(pathCost)+"   heuristique : Manhattan";
		}
	}

	/*
	 * BOARD
	 */

	private class Board extends Drawing1 {

		protected void draw() {
			for(int x=0;x<GRID_W;x++)
			for(int y=0;y<GRID_H;y++) {
				if(wall[x][y]) fillRect(COLOR_WALL, cellOrigin(x, y), CELL, CELL);
				else if(difficult[x][y]) fillRect(COLOR_DIFFICULT, cellOrigin(x, y), CELL, CELL);
			}

			for(int x=0;x<GRID_W;x++)
			for(int y=0;y<GRID_H;y++) {
				if(wall[x][y]) continue;
				if(solver.frontier[x][y]) fillRect(COLOR_FRONTIER, cellOrigin(x, y), CELL, CELL);
				else if(solver.closed[x][y]) fillRect(COLOR_VISITED, cellOrigin(x, y), CELL, CELL);
			}

			if(solver.path!=null) {
				for(int[] cell : solver.path) fillRect(COLOR_PATH, cellOrigin(cell[0], cell[1]), CELL, CELL);
			}

			fillRect(COLOR_START, cellOrigin(startX, startY), CELL, CELL);
			fillRect(COLOR_END, cellOrigin(endX, endY), CELL, CELL);
		}
	}

	private static Point1 cellOrigin(int x, int y) {
		return Drawing1.p(MARGIN_X+x*CELL, MARGIN_TOP+y*CELL);
	}

	private static double round(double value) {
		return Math.round(value*10)/10.0;
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
					"cases explorees : "+solver.getVisitedCount()+"   longueur du chemin : "+
					(solver.isFound() ? String.valueOf(solver.getPath().length) : "-"));

			drawTable();

			drawString(COLOR_ROW, fontPlain(11), p(16, HEIGHT-18),
					"HAUT/BAS changer d'approche sur la meme grille   F1 nouvelle grille   F2 quitter");
		}

		private void drawTable() {
			int best = -1;
			for(int i=0;i<NAMES.length;i++) {
				if(result[i]<0) continue;
				if(best<0 || result[i]<result[best]) best = i;
			}

			drawString(COLOR_ROW, fontPlain(11), p(WIDTH-320, 28), "COMPARAISON SUR CETTE GRILLE (longueur du chemin)");
			for(int i=0;i<NAMES.length;i++) {
				String value = result[i]>=0 ? String.valueOf(result[i]) : (i==selected ? "en cours" : "-");
				Color color = i==best ? COLOR_BEST : COLOR_ROW;
				drawString(color, i==selected ? fontBold(12) : fontPlain(12), p(WIDTH-320, 48+i*18),
						NAMES[i]+" : "+value);
			}
		}
	}
}
