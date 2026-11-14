package gus.game5.main.game.p1.c.game2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {

	public static final int SIZE = 4;
	public static final int WIN_VALUE = 2048;
	public static final int FOUR_PERCENT = 10;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	private int[][] cells;
	private int score;
	private boolean win;

	private Random random;

	public Grid(Random random) {
		this.random = random;
		this.cells = new int[SIZE][SIZE];
	}

	public int at(int col, int row) {
		return cells[row][col];
	}

	public void set(int col, int row, int value) {
		cells[row][col] = value;
	}

	public int getScore() {
		return score;
	}

	public boolean isWin() {
		return win;
	}

	public int[][] copyCells() {
		int[][] copy = new int[SIZE][SIZE];
		for(int row=0;row<SIZE;row++) System.arraycopy(cells[row], 0, copy[row], 0, SIZE);
		return copy;
	}

	public void restore(int[][] state, int score, boolean win) {
		for(int row=0;row<SIZE;row++) System.arraycopy(state[row], 0, cells[row], 0, SIZE);
		this.score = score;
		this.win = win;
	}


	/*
	 * MOVE
	 */

	public boolean move(int dir) {
		boolean moved = false;
		for(int line=0;line<SIZE;line++) {
			int[] values = readLine(dir, line);
			int[] result = collapse(values);
			if(same(values, result)) continue;
			writeLine(dir, line, result);
			moved = true;
		}
		return moved;
	}

	public int[] collapse(int[] values) {
		int[] result = new int[values.length];
		int count = 0;
		boolean merged = false;
		for(int k=0;k<values.length;k++) {
			int value = values[k];
			if(value==0) continue;
			if(count>0 && !merged && result[count-1]==value) {
				int doubled = value*2;
				result[count-1] = doubled;
				score += doubled;
				if(doubled==WIN_VALUE) win = true;
				merged = true;
				continue;
			}
			result[count++] = value;
			merged = false;
		}
		return result;
	}

	private int[] readLine(int dir, int line) {
		int[] values = new int[SIZE];
		for(int k=0;k<SIZE;k++) values[k] = cells[rowOf(dir, line, k)][colOf(dir, line, k)];
		return values;
	}

	private void writeLine(int dir, int line, int[] values) {
		for(int k=0;k<SIZE;k++) cells[rowOf(dir, line, k)][colOf(dir, line, k)] = values[k];
	}

	private int rowOf(int dir, int line, int k) {
		if(dir==LEFT || dir==RIGHT) return line;
		return dir==UP ? k : SIZE-1-k;
	}

	private int colOf(int dir, int line, int k) {
		if(dir==UP || dir==DOWN) return line;
		return dir==LEFT ? k : SIZE-1-k;
	}

	private boolean same(int[] a, int[] b) {
		for(int k=0;k<SIZE;k++) if(a[k]!=b[k]) return false;
		return true;
	}


	/*
	 * SPAWN
	 */

	public boolean spawn() {
		List<int[]> free = new ArrayList<>();
		for(int row=0;row<SIZE;row++)
			for(int col=0;col<SIZE;col++)
				if(cells[row][col]==0) free.add(new int[] {col, row});
		if(free.isEmpty()) return false;
		int[] cell = free.get(random.nextInt(free.size()));
		cells[cell[1]][cell[0]] = random.nextInt(100)<FOUR_PERCENT ? 4 : 2;
		return true;
	}

	public boolean canMove() {
		for(int row=0;row<SIZE;row++)
			for(int col=0;col<SIZE;col++) {
				int value = cells[row][col];
				if(value==0) return true;
				if(col+1<SIZE && cells[row][col+1]==value) return true;
				if(row+1<SIZE && cells[row+1][col]==value) return true;
			}
		return false;
	}
}
