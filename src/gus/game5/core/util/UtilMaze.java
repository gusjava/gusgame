package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gus.game5.core.util.maze.MazeSolver;
import gus.game5.core.util.maze.MazeSolverBFS;
import gus.game5.core.util.maze.MazeSolverDFS;

public class UtilMaze {

	/*
	 * PICK RANDOM
	 */
	
	public static int[] pickRandom(boolean[][] data) {
		List<int[]> list = new ArrayList<>();
		int x = data.length;
		int y = data[0].length;
		
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
		if(data[i][j]) list.add(new int[]{i,j});
		
		int number = list.size();
		int position = UtilRandom.randomInt(number);
		return list.get(position);
	}
	
	/*
	 * GENERATE
	 */

	public static boolean[][] generate(int size) {
		return new Generator(size).getData();
	}
	
	private static class Generator {
		private boolean[][] data;
		
		private boolean[][] getData() {
			return data;
		}
		
		private Generator(int size) {
			data = new boolean[size][size];
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					data[i][j] = false;
			perform(1, 1);
		}
		
		private void perform(int x, int y) {
			data[x][y] = true;
			List<Integer> dirs = randomDirections();
			for (int dir : dirs)
				switch (dir) {
				case 1:
					goNorth(x, y);
					break;
				case 2:
					goSouth(x, y);
					break;
				case 3:
					goWest(x, y);
					break;
				case 4:
					goEast(x, y);
					break;
				default:
					break;
				}
		}

		private List<Integer> randomDirections() {
			List<Integer> dirs = new ArrayList<>();
			for (int i = 1; i <= 4; i++)
				dirs.add(i);
			Collections.shuffle(dirs);
			return dirs;
		}

		private boolean epsilon() {
			return Math.random() < 0.01;
		}

		private void goNorth(int x, int y) {
			if (y < 2)
				return;
			if (!epsilon() && (data[x][y - 1] || data[x][y - 2]))
				return;
			data[x][y - 1] = true;
			perform(x, y - 2);
		}

		private void goSouth(int x, int y) {
			if (y > data[0].length - 3)
				return;
			if (!epsilon() && (data[x][y + 1] || data[x][y + 2]))
				return;
			data[x][y + 1] = true;
			perform(x, y + 2);
		}

		private void goWest(int x, int y) {
			if (x < 2)
				return;
			if (!epsilon() && (data[x - 1][y] || data[x - 2][y]))
				return;
			data[x - 1][y] = true;
			perform(x - 2, y);
		}

		private void goEast(int x, int y) {
			if (x > data.length - 3)
				return;
			if (!epsilon() && (data[x + 1][y] || data[x + 2][y]))
				return;
			data[x + 1][y] = true;
			perform(x + 2, y);
		}
	}
	
	/*
	 * DEPTH FIRST SEARCH (DFS)
	 */
	
	public static int[][] performDFS(boolean[][] maze, int[] start, int[] end) throws Exception {
		MazeSolver solver = new MazeSolverDFS(maze, start, end);
		solver.solve();
		return solver.getPath();
	}
	
	/*
	 * BREADTH FIRST SEARCH (BFS)
	 */
	
	public static int[][] performBFS(boolean[][] maze, int[] start, int[] end) throws Exception {
		MazeSolver solver = new MazeSolverBFS(maze, start, end);
		solver.solve();
		return solver.getPath();
	}
	
	
}
