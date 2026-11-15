package gus.game5.main.game.p1.c.pacman;

import gus.game5.core.util.Dir;

public abstract class Mover {
	
	protected Maze maze;
	
	private int x;
	private int y;
	private Dir dir;
	
	public Mover(Maze maze) {
		this.maze = maze;
	}
	
	public void place(int col, int row, Dir dir) {
		this.x = Maze.centerX(col);
		this.y = Maze.centerY(row);
		this.dir = dir;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	
	public Dir getDir() {
		return dir;
	}
	
	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	public int getCol() {
		return Math.floorDiv(x, Maze.CELL);
	}
	
	public int getRow() {
		return Math.floorDiv(y, Maze.CELL);
	}
	
	public boolean isCentered() {
		return Math.floorMod(x-Maze.CELL/2, Maze.CELL)==0 
			&& Math.floorMod(y-Maze.CELL/2, Maze.CELL)==0;
	}
	
	protected abstract boolean doorOpen();
	
	protected boolean canGo(Dir d) {
		return maze.canWalk(getCol()+d.getDx(), getRow()+d.getDy(), doorOpen());
	}
	
	protected void advance(int speed) {
		x += dir.getDx()*speed;
		y += dir.getDy()*speed;
		int width = Maze.COLS*Maze.CELL;
		if(x<0) x += width;
		if(x>=width) x -= width;
	}
	
	protected void stepToward(int targetX, int targetY, int speed) {
		if(x!=targetX) x += Integer.signum(targetX-x)*Math.min(speed, Math.abs(targetX-x));
		else if(y!=targetY) y += Integer.signum(targetY-y)*Math.min(speed, Math.abs(targetY-y));
	}
}
