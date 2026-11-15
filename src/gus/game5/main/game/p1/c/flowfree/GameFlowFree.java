package gus.game5.main.game.p1.c.flowfree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.util.UtilGrid;

public class GameFlowFree extends Game1 {

	public static final long SLEEP = 16;
	public static final int CLEAR_TICKS = 60;

	public static final int CELL = UtilFlowFree.CELL;
	public static final int GAP = 16;
	public static final int TOP = 50;
	public static final int BOTTOM = 40;

	public static final Color COLOR_BG = new Color(10,10,16);
	public static final Color COLOR_GRID = new Color(60,60,70);
	public static final Color COLOR_TILE = new Color(30,30,38);
	public static final Color COLOR_TEXT = new Color(235,235,245);
	public static final Color COLOR_LABEL = new Color(150,150,180);
	public static final Color COLOR_VEIL = new Color(0,0,0,180);

	public enum State {PLAYING, CLEARED, FINISHED}


	public static void main(String[] args) {
		GameFlowFree main = new GameFlowFree();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Flow Free");
		s.setWidth(UtilFlowFree.MAX_COLS*CELL + GAP*2);
		s.setHeight(UtilFlowFree.MAX_ROWS*CELL + TOP + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private int levelIndex;
	private int rows;
	private int cols;
	private char[][] board;
	private char[][] owner;
	private Map<Character,int[][]> endpoints;
	private Map<Character,List<int[]>> paths;
	private Set<Character> completed;

	private boolean dragging;
	private char dragColor;

	private State state;
	private int stateTick;

	protected void initialize1() {
		levelIndex = 0;
		loadLevel();
	}

	private void loadLevel() {
		clearAll();

		String[] data = UtilFlowFree.LEVELS[levelIndex];
		rows = data.length;
		cols = data[0].length();

		board = new char[rows][cols];
		owner = new char[rows][cols];
		endpoints = new HashMap<>();
		paths = new HashMap<>();
		completed = new HashSet<>();

		for(int r=0;r<rows;r++)
		for(int c=0;c<cols;c++) {
			char ch = data[r].charAt(c);
			board[r][c] = ch;
			owner[r][c] = ch;
			if(ch!='.') addEndpoint(ch, r, c);
		}

		dragging = false;
		setState(State.PLAYING);
		addDraw(new GameDrawing());
	}

	private void addEndpoint(char letter, int r, int c) {
		int[][] pp = endpoints.get(letter);
		if(pp==null) {
			endpoints.put(letter, new int[][] {{r,c}, null});
		} else {
			pp[1] = new int[] {r,c};
		}
	}

	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1()) {
			restart();
			return;
		}
		if(k.in().F2()) exit();

		switch(state) {
			case PLAYING: playing(); break;
			case CLEARED: stateTick++; if(stateTick>CLEAR_TICKS) nextLevel(); break;
			default: break;
		}
	}

	private void playing() {
		handleMouse();
		if(completed.size()==endpoints.size()) setState(State.CLEARED);
	}

	private void handleMouse() {
		if(mouse().button1().justPressed()) {
			int[] cell = cellAt(mouse().button1().justPressedPoint());
			if(cell!=null && board[cell[0]][cell[1]]!='.') startDrag(board[cell[0]][cell[1]], cell);
		}
		else if(mouse().button1().justReleased()) {
			dragging = false;
		}
		else if(dragging && mouse().button1().isPressed()) {
			Point0 p = mouse().pointCurrent();
			if(p==null) return;
			int[] cell = cellAt(p);
			if(cell!=null) extendOrRetract(cell);
		}
	}

	private int[] cellAt(Point0 p) {
		return UtilGrid.cellAt(p, GAP, TOP, CELL, rows, cols);
	}

	private void startDrag(char color, int[] cell) {
		List<int[]> old = paths.get(color);
		if(old!=null) {
			for(int[] p : old) {
				if(board[p[0]][p[1]]=='.') owner[p[0]][p[1]] = '.';
			}
		}

		List<int[]> path = new ArrayList<>();
		path.add(cell);
		paths.put(color, path);
		completed.remove(color);

		dragging = true;
		dragColor = color;
	}

	private void extendOrRetract(int[] cell) {
		List<int[]> path = paths.get(dragColor);
		int[] last = path.get(path.size()-1);
		if(same(last, cell)) return;

		if(path.size()>=2 && same(path.get(path.size()-2), cell)) {
			int[] removed = path.remove(path.size()-1);
			if(board[removed[0]][removed[1]]=='.') owner[removed[0]][removed[1]] = '.';
			completed.remove(dragColor);
			return;
		}

		if(!isAdjacent(last, cell)) return;

		char occupant = owner[cell[0]][cell[1]];
		int[] startCell = path.get(0);

		if(occupant=='.') {
			path.add(cell);
			owner[cell[0]][cell[1]] = dragColor;
		}
		else if(occupant==dragColor && board[cell[0]][cell[1]]!='.' && !same(cell, startCell)) {
			path.add(cell);
			completed.add(dragColor);
			dragging = false;
		}
	}

	private boolean same(int[] a, int[] b) {
		return UtilGrid.same(a, b);
	}

	private boolean isAdjacent(int[] a, int[] b) {
		int d = Math.abs(a[0]-b[0]) + Math.abs(a[1]-b[1]);
		return d==1;
	}

	private void setState(State s) {
		state = s;
		stateTick = 0;
	}

	private void nextLevel() {
		levelIndex++;
		if(levelIndex>=UtilFlowFree.LEVELS.length) {
			setState(State.FINISHED);
			return;
		}
		loadLevel();
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
			drawGrid();
			drawPaths();
			drawEndpoints();
			drawHud();
			drawMessage();
		}

		private void drawGrid() {
			for(int r=0;r<rows;r++)
			for(int c=0;c<cols;c++) {
				double x = c*CELL;
				double y = r*CELL;
				fillRect(COLOR_TILE, p(x+1,y+1), CELL-2, CELL-2);
				drawRect(COLOR_GRID, p(x,y), CELL, CELL);
			}
		}

		private void drawPaths() {
			for(Map.Entry<Character,List<int[]>> e : paths.entrySet()) {
				List<int[]> path = e.getValue();
				if(path.size()<2) continue;
				Color color = UtilFlowFree.colorOf(e.getKey());
				for(int i=0;i<path.size()-1;i++) {
					Point0 p1 = cellCenter(path.get(i));
					Point0 p2 = cellCenter(path.get(i+1));
					drawThickLine(color, p1, p2, CELL*0.3);
				}
			}
		}

		private void drawEndpoints() {
			for(Map.Entry<Character,int[][]> e : endpoints.entrySet()) {
				Color color = UtilFlowFree.colorOf(e.getKey());
				for(int[] cell : e.getValue()) {
					fillRoundC(color, cellCenter(cell), CELL*0.32);
				}
			}
		}

		private Point0 cellCenter(int[] cell) {
			return p(cell[1]*CELL+CELL*0.5, cell[0]*CELL+CELL*0.5);
		}

		private void drawHud() {
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+28), "NIVEAU  "+(levelIndex+1)+"/"+UtilFlowFree.LEVELS.length);
			drawString(COLOR_TEXT, fontBold(16), p(2, -TOP+48), "CONNEXIONS  "+completed.size()+"/"+endpoints.size());

			double y = rows*CELL + BOTTOM*0.55;
			drawStringC(COLOR_LABEL, fontPlain(11), p(cols*CELL/2.0, y),
					"Glisser depuis une extremite pour relier les paires   F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			double w = cols*CELL;
			double h = rows*CELL;
			if(state==State.CLEARED) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(COLOR_TEXT, fontBold(24), p(w/2, h/2), "NIVEAU "+(levelIndex+1)+" TERMINE");
				return;
			}
			if(state==State.FINISHED) {
				fillRect(COLOR_VEIL, p(0,0), w, h);
				drawStringC(UtilFlowFree.colorOf('A'), fontBold(26), p(w/2, h/2-14), "BRAVO");
				drawStringC(COLOR_TEXT, fontPlain(14), p(w/2, h/2+16), "Tous les niveaux resolus - F1 pour rejouer");
			}
		}
	}
}
