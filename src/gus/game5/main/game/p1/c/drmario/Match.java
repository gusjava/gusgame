package gus.game5.main.game.p1.c.drmario;

public class Match {

	private boolean[][] cells = new boolean[Bottle.TOTAL][Bottle.COLS];
	private int count;
	private int score;

	public void add(int col, int row) {
		if(cells[row][col]) return;
		cells[row][col] = true;
		count++;
	}

	public void addRun(int length) {
		score += 20*length*(length-3);
	}

	public boolean contains(int col, int row) {
		return cells[row][col];
	}

	public boolean isEmpty() {
		return count==0;
	}

	public int getCount() {
		return count;
	}

	public int getScore() {
		return score;
	}
}
