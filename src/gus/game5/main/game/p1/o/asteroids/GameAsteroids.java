package gus.game5.main.game.p1.o.asteroids;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.features.p.P;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class GameAsteroids extends Game1 {

	public static final String TITLE = "Asteroids";
	public static final long SLEEP = 16;

	public static final int LIVES = 3;
	public static final int WAVE_FIRST = 4;
	public static final int WAVE_MAX = 11;
	public static final double SPAWN_MARGIN = 190;
	public static final int WAVE_DELAY = 70;

	public static final int BULLETS_MAX = 4;
	public static final int FIRE_DELAY = 9;

	public static final int HYPER_DELAY = 45;
	public static final int HYPER_RISK = 7;

	public static final int RESPAWN_DELAY = 90;
	public static final int SHIELD_DELAY = 150;

	public static final int SPARKS_ASTEROID = 11;
	public static final int SPARKS_SHIP = 22;

	public static final int EXTRA_LIFE_SCORE = 10000;
	public static final int LIVES_SHOWN = 8;

	public static final Color BG = new Color(6, 6, 14);
	public static final Color INK = new Color(232, 236, 248);
	public static final Color DIM = new Color(116, 124, 158);
	public static final Color ROCK = new Color(176, 184, 206);
	public static final Color SHOT = new Color(255, 236, 160);
	public static final Color FLAME = new Color(255, 148, 56);
	public static final Color SHIELD = new Color(120, 200, 255);
	public static final Color OVER = new Color(236, 92, 76);
	public static final Color VEIL = new Color(0, 0, 0, 170);

	public static final Font FONT_SCORE = new Font(Font.DIALOG, Font.BOLD, 20);
	public static final Font FONT_SMALL = new Font(Font.DIALOG, Font.PLAIN, 11);
	public static final Font FONT_BIG = new Font(Font.DIALOG, Font.BOLD, 34);
	public static final Font FONT_MID = new Font(Font.DIALOG, Font.PLAIN, 15);


	private Ship ship;
	private List<Asteroid> asteroids;
	private List<Bullet> bullets;
	private List<Spark> sparks;

	private int score;
	private int lives;
	private int wave;
	private int nextExtra;

	private int fireTick;
	private int hyperTick;
	private int respawnTick;
	private int waveTick;

	private boolean paused;
	private boolean over;


	public static void main(String[] args) {
		GameAsteroids main = new GameAsteroids();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth((int)Field.W);
		s.setHeight((int)Field.H);
		s.setSleep(SLEEP);
		s.setBackground(BG);
	}

	protected void initialize1() {
		asteroids = new ArrayList<>();
		bullets = new ArrayList<>();
		sparks = new ArrayList<>();

		score = 0;
		lives = LIVES;
		wave = 0;
		nextExtra = EXTRA_LIFE_SCORE;
		fireTick = 0;
		hyperTick = 0;
		respawnTick = 0;
		waveTick = 0;
		paused = false;
		over = false;

		ship = new Ship();
		ship.setShield(SHIELD_DELAY);
		nextWave();

		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) {
			restart();
			return;
		}
		if(k.F2()) exit();
		if(!over && k.in().p()) paused = !paused;
		if(paused) return;

		if(!over) control(k);
		move();
		if(!over) collide();
		clearDead();
		if(!over) checkWave();
	}

	private void control(Keyboard k) {
		if(fireTick>0) fireTick--;
		if(hyperTick>0) hyperTick--;

		if(respawnTick>0) {
			respawnTick--;
			if(respawnTick==0) respawn();
			return;
		}

		ship.control(k.left(), k.right(), k.up());
		if(k.space()) fire();
		if(k.in().h()) hyperspace();
	}

	private void move() {
		if(!over && respawnTick==0) ship.goNext();
		for(Asteroid a : asteroids) a.goNext();
		for(Bullet b : bullets) b.goNext();
		for(Spark s : sparks) s.goNext();
	}

	private void clearDead() {
		for(Iterator<Bullet> it=bullets.iterator();it.hasNext();)
			if(it.next().isDead()) it.remove();
		for(Iterator<Spark> it=sparks.iterator();it.hasNext();)
			if(it.next().isDead()) it.remove();
	}


	/*
	 * SHOOTING
	 */

	private void fire() {
		if(fireTick>0 || bullets.size()>=BULLETS_MAX) return;
		Point0 nose = ship.nose(ship.getPosition());
		bullets.add(new Bullet(nose, ship.getHeading(), ship.getSpeed()));
		fireTick = FIRE_DELAY;
	}

	private void hyperspace() {
		if(hyperTick>0) return;
		hyperTick = HYPER_DELAY;
		ship.hyperspace();
		if(UtilRandom.chance(HYPER_RISK)) {
			destroyShip();
			return;
		}
		if(!ship.isShielded() && findShipCollision()!=null) destroyShip();
	}


	/*
	 * COLLISIONS
	 */

	private void collide() {
		for(Iterator<Bullet> it=bullets.iterator();it.hasNext();) {
			Bullet b = it.next();
			Asteroid a = findHit(b.getPosition(), Bullet.RADIUS);
			if(a==null) continue;
			it.remove();
			breakAsteroid(a);
		}
		if(respawnTick>0 || ship.isShielded()) return;
		Asteroid hit = findShipCollision();
		if(hit==null) return;
		breakAsteroid(hit);
		destroyShip();
	}

	private Asteroid findShipCollision() {
		return findHit(ship.getPosition(), Ship.RADIUS);
	}

	private Asteroid findHit(Point0 p, double radius) {
		for(Asteroid a : asteroids)
			if(Field.hit(p, radius, a.getPosition(), a.getHitRadius())) return a;
		return null;
	}

	private void breakAsteroid(Asteroid a) {
		asteroids.remove(a);
		addScore(a.getScore());
		burst(a.getPosition(), SPARKS_ASTEROID, a.getRadius()*0.5);
		asteroids.addAll(a.split());
	}

	private void destroyShip() {
		burst(ship.getPosition(), SPARKS_SHIP, Ship.RADIUS);
		lives--;
		if(lives<=0) {
			over = true;
			return;
		}
		respawnTick = RESPAWN_DELAY;
	}

	private void respawn() {
		ship = new Ship();
		ship.setShield(SHIELD_DELAY);
	}

	private void burst(Point0 p, int count, double spread) {
		for(int i=0;i<count;i++) sparks.add(new Spark(p, spread));
	}

	private void addScore(int points) {
		score += points;
		if(score<nextExtra) return;
		lives++;
		nextExtra += EXTRA_LIFE_SCORE;
	}


	/*
	 * WAVES
	 */

	private void checkWave() {
		if(!asteroids.isEmpty()) {
			waveTick = 0;
			return;
		}
		waveTick++;
		if(waveTick>=WAVE_DELAY) nextWave();
	}

	private void nextWave() {
		wave++;
		waveTick = 0;
		int count = Math.min(WAVE_FIRST + wave - 1, WAVE_MAX);
		for(int i=0;i<count;i++) asteroids.add(new Asteroid(Asteroid.LARGE, spawnPoint()));
	}

	private Point0 shipRefPoint() {
		return respawnTick>0 ? new Point1(Field.W/2, Field.H/2) : ship.getPosition();
	}

	private Point1 spawnPoint() {
		Point1 p = new Point1();
		for(int i=0;i<60;i++) {
			p.setXY(UtilRandom.randomDouble(Field.W), UtilRandom.randomDouble(Field.H));
			if(Field.dist(p, shipRefPoint())>SPAWN_MARGIN) return p;
		}
		return p;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		protected void draw() {
			drawAsteroids();
			drawBullets();
			drawSparks();
			drawShip();
			drawHud();
			drawMessage();
		}

		private void drawAsteroids() {
			for(Asteroid a : asteroids)
				wrapped(a.getPosition(), a.getRadius()*Asteroid.SHAPE_MAX,
						c -> drawPoly(ROCK, a.outline(c)));
		}

		private void drawBullets() {
			for(Bullet b : bullets)
				wrapped(b.getPosition(), Bullet.RADIUS,
						c -> fillRoundC(SHOT, c, Bullet.RADIUS));
		}

		private void drawSparks() {
			for(Spark s : sparks)
				wrapped(s.getPosition(), Spark.LENGTH*Spark.SPEED_MAX,
						c -> drawLine(fade(SHOT, s.getFade()), c, s.getTail(c)));
		}

		private void drawShip() {
			if(over || respawnTick>0) return;
			if(ship.isShielded())
				wrapped(ship.getPosition(), Ship.RADIUS*1.8,
						c -> drawRoundC(fade(SHIELD, 0.55), c, Ship.RADIUS*1.7));
			if(!ship.isVisible()) return;
			wrapped(ship.getPosition(), Ship.EXTENT, c -> {
				if(ship.isThrusting()) drawPoly(FLAME, ship.flame(c));
				drawPoly(INK, ship.outline(c));
			});
		}


		/*
		 * HUD
		 */

		private void drawHud() {
			drawString(INK, FONT_SCORE, p(18, 32), ""+score);
			for(int i=0;i<Math.min(lives, LIVES_SHOWN);i++) drawLife(28 + i*20, 56);
			drawString(DIM, FONT_SMALL, p(Field.W-132, 24), "VAGUE "+wave);
			drawString(DIM, FONT_SMALL, p(Field.W-132, 40), "ROCHERS "+asteroids.size());
			drawString(DIM, FONT_SMALL, p(18, Field.H-30),
					"Fleches tourner / pousser   ESPACE tirer   H hyperespace");
			drawString(DIM, FONT_SMALL, p(18, Field.H-14),
					"P pause   F1 rejouer   F2 quitter");
		}

		private void drawLife(double x, double y) {
			Point0 c = p(x, y);
			Angle up = Angle.ANGLE270;
			drawPoly(INK,
					c.pAdd(11, up),
					c.pAdd(8, up.add(Ship.SIDE_ANGLE)),
					c.pAdd(3.6, up.addDeg(180)),
					c.pAdd(8, up.add(Ship.SIDE_ANGLE).addDeg(76)));
		}

		private void drawMessage() {
			if(!over && !paused) return;
			fillRect(VEIL, p(0, 0), Field.W, Field.H);
			if(paused) {
				drawStringC(INK, FONT_BIG, p(Field.W/2, Field.H/2), "PAUSE");
				return;
			}
			drawStringC(OVER, FONT_BIG, p(Field.W/2, Field.H/2-20), "GAME OVER");
			drawStringC(INK, FONT_MID, p(Field.W/2, Field.H/2+18), "Score : "+score);
			drawStringC(DIM, FONT_MID, p(Field.W/2, Field.H/2+44), "F1 pour rejouer");
		}


		/*
		 * TOOLS
		 */

		private void wrapped(Point0 center, double radius, P<Point0> painter) {
			for(int i=-1;i<=1;i++)
				for(int j=-1;j<=1;j++) {
					double x = center.getX() + i*Field.W;
					double y = center.getY() + j*Field.H;
					if(x+radius<0 || x-radius>Field.W) continue;
					if(y+radius<0 || y-radius>Field.H) continue;
					painter.p(p(x, y));
				}
		}

		private Color fade(Color color, double ratio) {
			int alpha = (int)Math.round(255*Math.max(0, Math.min(1, ratio)));
			return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		}
	}
}
