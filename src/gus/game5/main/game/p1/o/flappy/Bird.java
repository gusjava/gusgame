package gus.game5.main.game.p1.o.flappy;

public class Bird {

	private double y;
	private double vy;

	public Bird(double y) {
		this.y = y;
		this.vy = 0;
	}

	/*
	 * MOVE
	 */

	public void flap() {
		vy = GameFlappy.FLAP_SPEED;
	}

	public void fall() {
		vy += GameFlappy.GRAVITY;
		if(vy>GameFlappy.MAX_FALL) vy = GameFlappy.MAX_FALL;
		y += vy;
	}

	public void hover(long count) {
		vy = 0;
		y = GameFlappy.HOVER_Y + GameFlappy.HOVER_AMP*Math.sin(count*GameFlappy.HOVER_RATE);
	}

	public void restOnCeiling() {
		y = GameFlappy.BIRD_R;
		if(vy<0) vy = 0;
	}

	public void restOnGround() {
		y = GameFlappy.GROUND_Y - GameFlappy.BIRD_R;
		vy = 0;
	}

	/*
	 * STATE
	 */

	public double getY() {
		return y;
	}

	public double getVy() {
		return vy;
	}

	public double getTop() {
		return y - GameFlappy.BIRD_RY;
	}

	public double getBottom() {
		return y + GameFlappy.BIRD_RY;
	}

	public double getLeft() {
		return GameFlappy.BIRD_X - GameFlappy.BIRD_RX;
	}

	public double getRight() {
		return GameFlappy.BIRD_X + GameFlappy.BIRD_RX;
	}

	public double tiltDeg() {
		double tilt = vy*GameFlappy.TILT_RATE;
		if(tilt<GameFlappy.TILT_UP) return GameFlappy.TILT_UP;
		if(tilt>GameFlappy.TILT_DOWN) return GameFlappy.TILT_DOWN;
		return tilt;
	}
}
