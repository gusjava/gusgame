package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class AnimSnowfall extends Game2 {

	public static final long SLEEP = 25;

	public static final int MAX_FALLING = 220;
	public static final int MAX_RESTING = 500;
	public static final int SPAWN_EVERY = 2;

	public static final Color COLOR_SKY = new Color(8,10,30);
	public static final Color COLOR_GROUND = new Color(225,232,240);
	public static final Color COLOR_TREE = new Color(18,38,28);
	public static final Color COLOR_MOON = new Color(235,235,210);


	public static void main(String[] args) {
		AnimSnowfall main = new AnimSnowfall();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Snowfall");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_SKY);
	}

	/*
	 * DATA
	 */

	private double groundY;
	private ShapeList<Snowflake> falling;
	private ShapeList<RestingFlake> resting;
	private int spawnTimer;

	protected void initialize1() {
		groundY = gameHeight()-70;
		falling = newShapeList();
		resting = newShapeList();
		spawnTimer = 0;

		addDraw(new Scene());
	}

	protected void before() {
		spawnTimer--;
		if(spawnTimer<=0 && falling.size()<MAX_FALLING) {
			falling.add(new Snowflake());
			spawnTimer = SPAWN_EVERY;
		}
	}

	private void land(Point0 pos, double radius) {
		RestingFlake flake = new RestingFlake(pos, radius);
		resting.add(flake);
		if(resting.size()>MAX_RESTING) {
			resting.get(0).evict();
		}
	}

	/*
	 * SNOWFLAKE
	 */

	private class Snowflake extends ShapeRound {

		private final double phase;
		private boolean landed;

		public Snowflake() {
			super(p2(UtilRandom.randomDouble(gameWidth()), -10), UtilRandom.randomDouble(1.5, 3.5), Color.WHITE);
			phase = UtilRandom.randomDouble(0, 1000);
			getAnchor().setDerived(0, UtilRandom.randomDouble(1, 2.5));
		}

		public void goNext() {
			super.goNext();
			double drift = Math.sin((getCount()+phase)*0.05)*0.6;
			getAnchor().addX(drift);
			if(getAnchor().getY()>=groundY) {
				landed = true;
				land(getAnchor(), getRadius());
			}
		}

		public boolean isOver() {
			return landed;
		}
	}

	/*
	 * RESTING FLAKE
	 */

	private class RestingFlake extends ShapeRound {

		private boolean evicted;

		public RestingFlake(Point0 pos, double radius) {
			super(p1(pos), radius, Color.WHITE);
		}

		public void evict() {
			evicted = true;
		}

		public boolean isOver() {
			return evicted;
		}
	}

	/*
	 * SCENE
	 */

	private class Scene extends Drawing1 {

		protected void draw() {
			fillRoundC(COLOR_MOON, p(gameWidth()-110, 90), 40);
			fillRect(COLOR_GROUND, p(0, groundY), gameWidth(), gameHeight()-groundY);

			tree(120, 90);
			tree(230, 70);
			tree(620, 100);
			tree(760, 75);
		}

		private void tree(double x, double height) {
			fillPoly(COLOR_TREE,
					p(x, groundY-height),
					p(x-height*0.45, groundY),
					p(x+height*0.45, groundY));
		}
	}
}
