package gus.game5.main.game.chess;

public class Snapshot {
	final private int[][] data;
	
	public Snapshot(int[][] data) {
		this.data = data;
	}
	public int[][] getData() {
		return data;
	}
}
