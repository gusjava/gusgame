package gus.game5.main.game.p1.o.flappy;

import gus.game5.core.util.UtilRandom;

public class Pipe {

	public static double randomGapTop() {
		double max = GameFlappy.GROUND_Y - GameFlappy.PIPE_GAP - GameFlappy.GAP_MARGIN;
		return UtilRandom.randomDouble(GameFlappy.GAP_MARGIN, max);
	}

	private double x;
	private double gapTop;
	private boolean passed;

	public Pipe(double x, double gapTop) {
		this.x = x;
		this.gapTop = gapTop;
		this.passed = false;
	}

	/*
	 * MOVE
	 */

	public void move(double dx) {
		x -= dx;
	}

	public boolean isOut() {
		return x + GameFlappy.PIPE_W < 0;
	}

	public void recycle(double period) {
		x += period;
		gapTop = randomGapTop();
		passed = false;
	}

	/*
	 * RULES
	 */

	public boolean hits(Bird bird) {
		if(bird.getRight()<=x) return false;
		if(bird.getLeft()>=x+GameFlappy.PIPE_W) return false;
		return bird.getTop()<gapTop || bird.getBottom()>gapTop+GameFlappy.PIPE_GAP;
	}

	public boolean checkPassed(double birdLeft) {
		if(passed) return false;
		if(x+GameFlappy.PIPE_W>=birdLeft) return false;
		passed = true;
		return true;
	}

	/*
	 * STATE
	 */

	public double getX() {
		return x;
	}

	public double getGapTop() {
		return gapTop;
	}

	public double getGapBottom() {
		return gapTop + GameFlappy.PIPE_GAP;
	}
}
