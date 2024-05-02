package gus.game5.core.util.maze;

public class MazeSolverDFS extends MazeSolver {

	private int[][] path0;
	private int index;
	
	public MazeSolverDFS(boolean[][] maze, int[] start, int[] end) throws Exception {
		super(maze, start, end);
		init();
	}
	
	public void init() {
		path = null;
		current = null;
		path0 = new int[(int) (mazeX * mazeY * 0.5)][2];
		index = 0;
		path0[index] = start;
		current = start;
		fill(start);
	}

	public boolean handleStep() {
		int[] move = findNextMove();
		if (move != null) {
			// forward
			increaseCurrent(move);
			if (isEnd(move)) {
				resolvePath();
				return false;
			}
		} else {
			// backward
			decreaseCurrent();
			if (index == -1)
				return false;
		}
		return true;
	}

	private void decreaseCurrent() {
		index--;
		current = index >= 0 ? path0[index] : null;
	}

	private void increaseCurrent(int[] p) {
		index++;
		path0[index] = p;
		current = p;
		fill(p);
	}

	private void resolvePath() {
		path = new int[index + 1][2];
		for (int i = 0; i < index + 1; i++)
			path[i] = path0[i];
	}

	private int[] findNextMove() {
		int dMin = dMax + 1;
		int[] pMin = null;

		for (int[] p : freeNeighbours(current)) {
			int d = distanceToEnd(p);
			if (d < dMin) {
				dMin = d;
				pMin = p;
			}
		}
		return pMin;
	}
}