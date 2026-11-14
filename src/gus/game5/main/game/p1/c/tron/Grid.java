package gus.game5.main.game.p1.c.tron;

import java.util.ArrayDeque;
import java.util.Deque;

public class Grid {

	public static final int EMPTY = 0;

	private final int cols;
	private final int rows;
	private final int[][] owner;

	public Grid(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		this.owner = new int[cols][rows];
	}

	public void reset() {
		for(int col=0;col<cols;col++)
			for(int row=0;row<rows;row++)
				owner[col][row] = EMPTY;
	}

	public boolean inside(int col, int row) {
		return col>=0 && col<cols && row>=0 && row<rows;
	}

	public boolean isWall(int col, int row) {
		return inside(col, row) && owner[col][row]!=EMPTY;
	}

	public void setWall(int col, int row, int id) {
		owner[col][row] = id;
	}

	public int ownerAt(int col, int row) {
		return owner[col][row];
	}


	/*
	 * FLOOD FILL
	 */

	public int floodFill(int col, int row) {
		if(!inside(col, row) || isWall(col, row)) return 0;

		boolean[][] visited = new boolean[cols][rows];
		Deque<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {col, row});
		visited[col][row] = true;
		int count = 0;

		while(!queue.isEmpty()) {
			int[] cell = queue.poll();
			count++;
			for(Dir dir : Dir.values()) {
				int nextCol = cell[0]+dir.getDx();
				int nextRow = cell[1]+dir.getDy();
				if(!inside(nextCol, nextRow) || visited[nextCol][nextRow] || isWall(nextCol, nextRow)) continue;
				visited[nextCol][nextRow] = true;
				queue.add(new int[] {nextCol, nextRow});
			}
		}
		return count;
	}
}
