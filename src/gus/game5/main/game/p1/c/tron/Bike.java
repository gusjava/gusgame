package gus.game5.main.game.p1.c.tron;

import gus.game5.core.util.Dir;

public class Bike {

	private int col;
	private int row;
	private Dir dir;

	public void place(int col, int row, Dir dir) {
		this.col = col;
		this.row = row;
		this.dir = dir;
	}

	public void turn(Dir dir) {
		this.dir = dir;
	}

	public void move() {
		col += dir.getDx();
		row += dir.getDy();
	}

	public int peekNextCol() {
		return col + dir.getDx();
	}

	public int peekNextRow() {
		return row + dir.getDy();
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public Dir getDir() {
		return dir;
	}
}
