package gus.game5.core.util.maze;

import java.util.ArrayList;
import java.util.List;

public abstract class MazeSolver {

	public abstract boolean handleStep();
	protected abstract void init();

	private boolean[][] state;
	protected int[] start;
	protected int[] end;
	protected int mazeX;
	protected int mazeY;
	protected int dMax;

	protected int[][] path;
	protected int[] current;

	public MazeSolver(boolean[][] maze, int[] start, int[] end) throws Exception {
		setMaze(maze);
		setStart(start);
		setEnd(end);
	}

	private void setMaze(boolean[][] m) throws Exception {
		if (m.length == 0)
			throw new Exception("maze_x value is 0");
		if (m[0].length == 0)
			throw new Exception("maze_y value is 0");

		mazeX = m.length;
		mazeY = m[0].length;
		dMax = mazeX + mazeY;

		state = new boolean[mazeX][mazeY];
		for (int i = 0; i < mazeX; i++)
			for (int j = 0; j < mazeY; j++)
				state[i][j] = m[i][j];
	}
	
	private void setStart(int[] start) throws Exception {
		this.start = start;
		if (start.length != 2)
			throw new Exception("Invalid size for start int[]: " + start.length);
	}
	
	private void setEnd(int[] end) throws Exception {
		this.end = end;
		if (end.length != 2)
			throw new Exception("Invalid size for end int[]: " + end.length);
	}

	/*
	 * SOLVE
	 */

	public boolean solve() {
		init();
		if (!isFree(end)) return false;
		while(!isComplete()) {
			if(!handleStep()) return false;
		}
		return true;
	}
	
	/*
	 * DATA
	 */
	
	public boolean isComplete() {
		return path!=null;
	}
	
	public int[][] getPath() {
		return path;
	}
	
	public int[] getCurrent() {
		return current;
	}
	
	public boolean[][] getState() {
		return state;
	}
	
	/*
	 * UTILS
	 */

	protected List<int[]> freeNeighbours(int[] p) {
		int x = p[0];
		int y = p[1];

		List<int[]> l = new ArrayList<>();

		if (isFree(x - 1, y))
			l.add(p(x - 1, y));
		if (isFree(x + 1, y))
			l.add(p(x + 1, y));
		if (isFree(x, y - 1))
			l.add(p(x, y - 1));
		if (isFree(x, y + 1))
			l.add(p(x, y + 1));

		return l;
	}

	protected void fill(int[] p) {
		fill(p[0], p[1]);
	}

	protected void fill(int x, int y) {
		if (isValid(x, y))
			state[x][y] = false;
	}

	protected int[] p(int x, int y) {
		return new int[] { x, y };
	}

	protected boolean isFree(int[] p) {
		return p != null && isFree(p[0], p[1]);
	}

	protected boolean isFree(int x, int y) {
		return isValid(x, y) && state[x][y];
	}

	protected boolean isValid(int x, int y) {
		return x >= 0 && x < mazeX && y >= 0 && y < mazeY;
	}

	protected int distanceTo(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	protected boolean equals(int[] p1, int[] p2) {
		return p1 != null && p2 != null && p1[0] == p2[0] && p1[1] == p2[1];
	}

	protected int distanceToEnd(int[] p) {
		return distanceTo(p, end);
	}

	protected boolean isEnd(int[] p) {
		return equals(p, end);
	}

	protected String toString(int[] p) {
		return p[0] + " " + p[1];
	}
}
