package gus.game5.main.game.p1.o.flappy;

import gus.game5.core.util.UtilRandom;

public class Cloud {

	public static double randomY() {
		return UtilRandom.randomDouble(GameFlappy.CLOUD_TOP, GameFlappy.CLOUD_BOTTOM);
	}

	private double x;
	private double y;

	public Cloud(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(double dx, double period) {
		x -= dx;
		if(x+GameFlappy.CLOUD_W<0) {
			x += period;
			y = randomY();
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
