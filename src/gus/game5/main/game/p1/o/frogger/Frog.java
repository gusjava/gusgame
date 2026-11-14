package gus.game5.main.game.p1.o.frogger;

public class Frog {

	public static final int HOP_TICKS = 6;

	private double x;
	private int row;
	private double hopDx;
	private double hopDy;
	private int hopTick;

	public Frog(double x, int row) {
		place(x, row);
	}

	public void place(double x, int row) {
		this.x = x;
		this.row = row;
		this.hopDx = 0;
		this.hopDy = 0;
		this.hopTick = 0;
	}

	/*
	 * STATE
	 */

	public double getX() {return x;}
	public int getRow() {return row;}
	public double getCenter() {return x+0.5;}

	public void drift(double d) {
		x += d;
	}

	public boolean isOffBoard(int cols) {
		double center = getCenter();
		return center<0 || center>cols;
	}

	/*
	 * HOPS
	 */

	public boolean moveSide(int dir, int cols) {
		double target = Math.rint(x) + dir;
		if(target<0 || target>cols-1) return false;
		startHop(target-x, 0);
		x = target;
		return true;
	}

	public boolean moveVert(int dir, int rows) {
		int target = row + dir;
		if(target<0 || target>rows-1) return false;
		startHop(0, target-row);
		row = target;
		return true;
	}

	private void startHop(double dx, double dy) {
		hopDx = -dx;
		hopDy = -dy;
		hopTick = HOP_TICKS;
	}

	public void animate() {
		if(hopTick>0) hopTick--;
	}

	/*
	 * RENDERING
	 */

	public double ratio() {
		return hopTick/(double)HOP_TICKS;
	}

	public double drawX() {
		return x + hopDx*ratio();
	}

	public double drawY() {
		return row + hopDy*ratio();
	}

	public double lift() {
		return Math.sin(Math.PI*(1-ratio()))*0.2;
	}
}
