package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class AnimFireworks extends Game2 {

	public static final long SLEEP = 16;

	public static final double ROCKET_SPEED = 7;
	public static final int ROCKET_MIN_DELAY = 18;
	public static final int ROCKET_MAX_DELAY = 45;

	public static final int SPARK_COUNT = 70;
	public static final double SPARK_SPEED_MIN = 1.5;
	public static final double SPARK_SPEED_MAX = 5.5;
	public static final double GRAVITY = 0.05;
	public static final int SPARK_LIFETIME = 90;
	public static final double SPARK_FADE = 255.0/SPARK_LIFETIME;

	public static final int STAR_COUNT = 120;

	public static final Color COLOR_BG = new Color(5,5,18);

	public static final Color[] PALETTE = {
		new Color(255,90,90),
		new Color(255,205,70),
		new Color(130,255,130),
		new Color(110,200,255),
		new Color(220,130,255),
		new Color(255,150,210)
	};


	public static void main(String[] args) {
		AnimFireworks main = new AnimFireworks();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Fireworks");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private ShapeList<Rocket> rockets;
	private ShapeList<Spark> sparks;
	private int rocketTimer;

	protected void initialize1() {
		rockets = newShapeList();
		sparks = newShapeList();
		rocketTimer = ROCKET_MIN_DELAY;

		addDraw(new Sky());
	}

	protected void before() {
		rocketTimer--;
		if(rocketTimer<=0) {
			launchRocket();
			rocketTimer = UtilRandom.randomInt(ROCKET_MIN_DELAY, ROCKET_MAX_DELAY);
		}
	}

	private void launchRocket() {
		double x = UtilRandom.randomDouble(80, gameWidth()-80);
		Color color = UtilRandom.randomElement(PALETTE);
		rockets.add(new Rocket(x, color));
	}

	private void explode(Point0 pos, Color color) {
		for(int i=0;i<SPARK_COUNT;i++) {
			double speed = UtilRandom.randomDouble(SPARK_SPEED_MIN, SPARK_SPEED_MAX);
			sparks.add(new Spark(pos, color, speed, Angle.random()));
		}
	}

	/*
	 * ROCKET
	 */

	private class Rocket extends ShapeRound {

		private final Color color;
		private final double targetY;
		private boolean exploded;

		public Rocket(double x, Color color) {
			super(p2(x, gameHeight()), 2.5, Color.WHITE);
			this.color = color;
			this.targetY = UtilRandom.randomDouble(90, gameHeight()*0.55);
			getAnchor().setDerived(0, -ROCKET_SPEED);
		}

		public void goNext() {
			super.goNext();
			if(!exploded && getAnchor().getY()<=targetY) {
				exploded = true;
				explode(getAnchor(), color);
			}
		}

		public boolean isOver() {
			return exploded;
		}
	}

	/*
	 * SPARK
	 */

	private class Spark extends ShapeRound {

		private final int r;
		private final int g;
		private final int b;

		public Spark(Point0 pos, Color color, double speed, Angle angle) {
			super(p2(pos), 2.5);
			r = color.getRed();
			g = color.getGreen();
			b = color.getBlue();
			getAnchor().setDerived(speed, angle);
			setGColor(this::currentColor);
		}

		private Color currentColor() {
			int alpha = (int) Math.max(0, 255 - getCount()*SPARK_FADE);
			return new Color(r, g, b, alpha);
		}

		public void goNext() {
			super.goNext();
			getAnchor().getDerived().addY(GRAVITY);
		}

		public boolean isOver() {
			return getCount()>SPARK_LIFETIME;
		}
	}

	/*
	 * SKY
	 */

	private class Sky extends Drawing1 {

		private final double[] starX = new double[STAR_COUNT];
		private final double[] starY = new double[STAR_COUNT];
		private final double[] starR = new double[STAR_COUNT];

		public Sky() {
			super();
			for(int i=0;i<STAR_COUNT;i++) {
				starX[i] = UtilRandom.randomDouble(gameWidth());
				starY[i] = UtilRandom.randomDouble(gameHeight()*0.7);
				starR[i] = UtilRandom.randomDouble(0.5, 1.6);
			}
		}

		protected void draw() {
			for(int i=0;i<STAR_COUNT;i++) {
				fillRoundC(Color.WHITE, p(starX[i], starY[i]), starR[i]);
			}
		}
	}
}
