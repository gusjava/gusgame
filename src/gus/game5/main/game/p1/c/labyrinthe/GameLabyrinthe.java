package gus.game5.main.game.p1.c.labyrinthe;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.util.LevelProgress;
import gus.game5.core.util.UtilMaze;
import gus.game5.core.util.maze.MazeSolver;
import gus.game5.core.util.maze.MazeSolverBFS;

public class GameLabyrinthe extends Game1 {

	public static final long SLEEP = 16;

	public static final int CELL = 22;
	public static final int GAP = 16;
	public static final int TOP = 50;
	public static final int BOTTOM = 40;

	public static final int[] SIZES = {15,19,23,27,31,31};
	public static final int CLEAR_TICKS = 60;

	public static final Color COLOR_BG = new Color(10,10,16);
	public static final Color COLOR_WALL = new Color(90,70,60);
	public static final Color COLOR_FLOOR = new Color(40,34,30);
	public static final Color COLOR_START = new Color(70,150,230);
	public static final Color COLOR_END = new Color(90,200,110);
	public static final Color COLOR_PLAYER = new Color(230,220,80);
	public static final Color COLOR_HINT = new Color(230,150,40,140);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);

	public static void main(String[] args) {
		GameLabyrinthe main = new GameLabyrinthe();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		int maxSize = SIZES[SIZES.length-1];
		s.setTitle("Labyrinthe");
		s.setWidth(maxSize*CELL + GAP*2);
		s.setHeight(maxSize*CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int size;
	private boolean[][] data;
	private int[] start;
	private int[] end;
	private int[] player;
	private int moves;
	private int optimalLength;

	private boolean hint;
	private int[][] hintPath;

	private LevelProgress progress;

	protected void initialize1() {
		progress = new LevelProgress(SIZES.length, CLEAR_TICKS);
		progress.reset();
		loadLevel();
	}

	private void loadLevel() {
		clearAll();

		size = SIZES[progress.getLevelIndex()];
		data = UtilMaze.generate(size);
		start = UtilMaze.pickRandom(data);
		end = pickFarEnd();
		player = new int[] {start[0], start[1]};
		moves = 0;
		hint = false;
		hintPath = null;
		optimalLength = solveLength(start, end);

		progress.setState(LevelProgress.State.PLAYING);
		addDraw(new GameDrawing());
	}

	private int[] pickFarEnd() {
		int[] best = start;
		int bestDist = -1;
		for(int i=0;i<20;i++) {
			int[] candidate = UtilMaze.pickRandom(data);
			int dist = Math.abs(candidate[0]-start[0]) + Math.abs(candidate[1]-start[1]);
			if(dist>bestDist) {
				bestDist = dist;
				best = candidate;
			}
		}
		return best;
	}

	private int solveLength(int[] from, int[] to) {
		int[][] path = solvePath(from, to);
		return path!=null ? path.length-1 : -1;
	}

	private int[][] solvePath(int[] from, int[] to) {
		try {
			MazeSolver solver = new MazeSolverBFS(data, from, to);
			solver.solve();
			return solver.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;
		if(k.in().h()) toggleHint();

		if(progress.isPlaying()) playing(k);
		else if(progress.isCleared()) {
			if(progress.tickClearedAndCheckAdvance()) nextLevel();
		}
	}

	private void playing(Keyboard k) {
		int di = 0;
		int dj = 0;
		if(k.in().up())		dj = -1;
		else if(k.in().down())	dj = 1;
		else if(k.in().left())	di = -1;
		else if(k.in().right())di = 1;

		if(di!=0 || dj!=0) tryMove(di, dj);

		if(player[0]==end[0] && player[1]==end[1]) progress.setState(LevelProgress.State.CLEARED);
	}

	private void tryMove(int di, int dj) {
		int ni = player[0]+di;
		int nj = player[1]+dj;
		if(ni<0 || ni>=size || nj<0 || nj>=size) return;
		if(!data[ni][nj]) return;
		player[0] = ni;
		player[1] = nj;
		moves++;
		hintPath = hint ? solvePath(player, end) : null;
	}

	private void toggleHint() {
		hint = !hint;
		if(hint && hintPath==null) {
			hintPath = solvePath(player, end);
		}
	}

	private void nextLevel() {
		if(progress.nextLevel()) loadLevel();
	}

	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		public GameDrawing() {
			super();
			initOrigin().setXY(GAP, TOP);
		}

		protected void draw() {
			drawMaze();
			drawHint();
			drawStartEnd();
			drawPlayer();
			drawHud();
			drawMessage();
		}

		private void drawMaze() {
			for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) {
				Color color = data[i][j] ? COLOR_FLOOR : COLOR_WALL;
				fillRect(color, p(i*CELL, j*CELL), CELL, CELL);
			}
		}

		private void drawHint() {
			if(!hint || hintPath==null) return;
			for(int[] cell : hintPath) {
				fillSquareC(COLOR_HINT, p(cell[0]*CELL+CELL*0.5, cell[1]*CELL+CELL*0.5), CELL*0.28);
			}
		}

		private void drawStartEnd() {
			fillSquareC(COLOR_START, p(start[0]*CELL+CELL*0.5, start[1]*CELL+CELL*0.5), CELL*0.32);
			fillSquareC(COLOR_END, p(end[0]*CELL+CELL*0.5, end[1]*CELL+CELL*0.5), CELL*0.32);
		}

		private void drawPlayer() {
			fillRoundC(COLOR_PLAYER, p(player[0]*CELL+CELL*0.5, player[1]*CELL+CELL*0.5), CELL*0.34);
		}

		private void drawHud() {
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+28), "NIVEAU  "+(progress.getLevelIndex()+1)+"/"+SIZES.length);
			String optimal = optimalLength>0 ? String.valueOf(optimalLength) : "?";
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+48), "DEPLACEMENTS  "+moves+"   OPTIMAL  "+optimal);

			double y = size*CELL + BOTTOM*0.55;
			drawStringC(COLOR_LABEL, fontPlain(11), p(size*CELL/2.0, y),
					"Fleches se deplacer   H indice   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			double w = size*CELL;
			double h = size*CELL;
			if(progress.isCleared()) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(COLOR_TEXT, fontBold(24), p(w/2, h/2), "NIVEAU "+(progress.getLevelIndex()+1)+" TERMINE");
				return;
			}
			if(progress.isFinished()) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(COLOR_END, fontBold(26), p(w/2, h/2-14), "BRAVO");
				drawStringC(COLOR_TEXT, fontPlain(14), p(w/2, h/2+16), "Tous les niveaux resolus - F1 pour rejouer");
			}
		}
	}
}
