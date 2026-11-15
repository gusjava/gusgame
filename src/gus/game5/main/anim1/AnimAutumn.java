package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape0;
import gus.game5.core.shape.Shape0.AnchorType;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class AnimAutumn extends Game2 {

	public static final int MAX_FALLING = 160;
	public static final int MAX_RESTING = 400;
	public static final int SPAWN_EVERY = 4;
	public static final double GUST_BASE = 0.6;
	public static final double GUST_RANGE = 2.0;

	public static final Color COLOR_SKY = new Color(176, 190, 197);
	public static final Color COLOR_GROUND = new Color(107, 88, 46);
	public static final Color COLOR_GROUND_SHADE = new Color(89, 72, 36);
	public static final Color COLOR_TRUNK = new Color(74, 47, 31);
	public static final Color COLOR_CLOUD = new Color(205, 212, 218);

	public static final Color[] AUTUMN_PALETTE = {
			new Color(230, 126, 34),
			new Color(211, 84, 0),
			new Color(243, 156, 18),
			new Color(192, 57, 43),
			new Color(255, 195, 113),
	};

	public static final Color[] GOLD_PALETTE = {
			new Color(241, 196, 15),
			new Color(255, 214, 102),
			new Color(230, 160, 30),
			new Color(255, 235, 170),
			new Color(212, 172, 13),
	};

	public static final Color[] GREEN_PALETTE = {
			new Color(34, 91, 46),
			new Color(46, 112, 58),
			new Color(27, 74, 38),
			new Color(60, 125, 66),
	};

	private enum Species { ROUND, OVAL, CONE }

	public static void main(String[] args) {
		AnimAutumn main = new AnimAutumn();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Autumn animation");
		s.setWidth(900);
		s.setHeight(700);
		s.setSleep(20);
		s.setBackground(COLOR_SKY);
	}

	/*
	 * DATA
	 */

	private double groundY;
	private ShapeList<Leaf> falling;
	private ShapeList<RestingLeaf> resting;
	private int spawnTimer;
	private double[][] treeTops;

	private double wind;
	private double windTarget;
	private int windTimer;

	protected void initialize1() {
		groundY = gameHeight() - 110;
		falling = newShapeList();
		resting = newShapeList();
		spawnTimer = 0;
		wind = 0.6;
		windTarget = 0.6;
		windTimer = 0;

		addDraw(new Scene());

		Object[][] treeDef = {
				{90.0, 160.0, Species.ROUND},
				{260.0, 130.0, Species.CONE},
				{460.0, 110.0, Species.OVAL},
				{650.0, 150.0, Species.CONE},
				{790.0, 185.0, Species.ROUND},
		};

		java.util.List<double[]> tops = new java.util.ArrayList<>();
		for (Object[] def : treeDef) {
			double x = (double) def[0];
			double h = (double) def[1];
			Species species = (Species) def[2];
			addDraw(new Tree(x, h, species));
			if (species != Species.CONE) {
				tops.add(new double[] { x, groundY - h - h * 0.35 });
			}
		}
		treeTops = tops.toArray(new double[0][]);

		for (int i = 0; i < 20; i++) {
			double y = UtilRandom.randomDouble(10, 340);
			double x = UtilRandom.randomDouble(gameWidth());
			double speed = UtilRandom.randomDouble(0.1, 0.65);
			double scale = UtilRandom.randomDouble(0.4, 2.1);
			int shade = UtilRandom.randomInt(-55, 25);
			int alpha = UtilRandom.randomInt(90, 235);
			newShape(new Cloud(x, y, speed, scale, shade, alpha));
		}
	}

	protected void before() {
		windTimer--;
		if (windTimer <= 0) {
			windTarget = UtilRandom.randomDouble(-0.2, 2.6);
			windTimer = UtilRandom.randomInt(80, 220);
		}
		wind += (windTarget - wind) * 0.02;

		spawnTimer--;
		if (spawnTimer <= 0 && falling.size() < MAX_FALLING) {
			double[] origin = treeTops[UtilRandom.randomInt(treeTops.length)];
			double x = origin[0] + UtilRandom.randomDouble(-40, 40);
			double y = origin[1] + UtilRandom.randomDouble(-20, 20);
			falling.add(new Leaf(x, y));
			spawnTimer = SPAWN_EVERY;
		}
	}

	private void land(Point0 pos, Color color) {
		resting.add(new RestingLeaf(pos, color));
		if (resting.size() > MAX_RESTING) {
			resting.get(0).evict();
		}
	}

	/*
	 * SCENE
	 */

	private class Scene extends Drawing1 {
		protected void draw() {
			fillRect(COLOR_GROUND_SHADE, p(0, groundY), gameWidth(), 6);
			fillRect(COLOR_GROUND, p(0, groundY + 6), gameWidth(), gameHeight() - groundY - 6);
		}
	}

	/*
	 * TREE
	 */

	private class Tree extends Drawing1 {
		private final Species species;
		private final double trunkHeight;
		private final double trunkWidth;
		private double trunkVisibleHeight;

		// ROUND / OVAL (broadleaf) foliage
		private double[] lobeDx;
		private double[] lobeDy;
		private double[] lobeW;
		private double[] lobeH;
		private Color[] lobeColor;

		// ROUND / OVAL bare branch sticks
		private double[] branchSx;
		private double[] branchSy;
		private double[] branchEx;
		private double[] branchEy;
		private double[] branchThickness;

		// CONE (conifer) tiers
		private double[] tierHalfWidth;
		private double[] tierHeight;
		private double[] tierBaseY;
		private Color[] tierColor;

		public Tree(double x, double trunkHeight, Species species) {
			super(x, groundY);
			this.species = species;
			this.trunkHeight = trunkHeight;
			this.trunkWidth = Math.max(10, trunkHeight * UtilRandom.randomDouble(0.085, 0.12));

			if (species == Species.CONE) {
				trunkVisibleHeight = trunkHeight * 0.22;
				buildConifer();
			} else {
				trunkVisibleHeight = trunkHeight;
				buildBroadleaf(species == Species.ROUND ? AUTUMN_PALETTE : GOLD_PALETTE, species == Species.OVAL);
				buildBranches();
			}
		}

		private void buildBroadleaf(Color[] palette, boolean oval) {
			int accents = UtilRandom.randomInt(3, 5);
			int lobes = 1 + accents;
			lobeDx = new double[lobes];
			lobeDy = new double[lobes];
			lobeW = new double[lobes];
			lobeH = new double[lobes];
			lobeColor = new Color[lobes];

			double canopyRadius = trunkHeight * 0.55;
			double stretch = oval ? 1.45 : 1.0;
			double squeeze = oval ? 0.8 : 1.0;

			lobeDx[0] = 0;
			lobeDy[0] = -canopyRadius * (oval ? 0.55 : 0.3);
			lobeW[0] = canopyRadius * 1.7 * squeeze;
			lobeH[0] = canopyRadius * 1.7 * stretch;
			lobeColor[0] = UtilRandom.randomElement(palette);

			for (int i = 1; i < lobes; i++) {
				lobeDx[i] = UtilRandom.randomDouble(-canopyRadius * 0.55, canopyRadius * 0.55) * squeeze;
				lobeDy[i] = UtilRandom.randomDouble(-canopyRadius * 1.3, -canopyRadius * 0.1) * stretch;
				double r = UtilRandom.randomDouble(canopyRadius * 0.4, canopyRadius * 0.6);
				lobeW[i] = r * 2 * squeeze;
				lobeH[i] = r * 2 * stretch;
				lobeColor[i] = UtilRandom.randomElement(palette);
			}
		}

		private void buildBranches() {
			int branches = UtilRandom.randomInt(2, 4);
			branchSx = new double[branches];
			branchSy = new double[branches];
			branchEx = new double[branches];
			branchEy = new double[branches];
			branchThickness = new double[branches];
			for (int i = 0; i < branches; i++) {
				double side = i % 2 == 0 ? -1 : 1;
				double startFrac = UtilRandom.randomDouble(0.45, 0.85);
				double length = trunkHeight * UtilRandom.randomDouble(0.25, 0.45);
				double angleDeg = UtilRandom.randomDouble(25, 60);
				double rad = Math.toRadians(angleDeg);

				branchSx[i] = 0;
				branchSy[i] = -trunkHeight * startFrac;
				branchEx[i] = side * length * Math.sin(rad);
				branchEy[i] = branchSy[i] - length * Math.cos(rad);
				branchThickness[i] = Math.max(3, trunkWidth * 0.3);
			}
		}

		private void buildConifer() {
			int tiers = UtilRandom.randomInt(3, 5);
			tierHalfWidth = new double[tiers];
			tierHeight = new double[tiers];
			tierBaseY = new double[tiers];
			tierColor = new Color[tiers];

			double y = -trunkVisibleHeight;
			double halfWidth = trunkHeight * UtilRandom.randomDouble(0.42, 0.52);
			for (int i = 0; i < tiers; i++) {
				tierBaseY[i] = y;
				tierHalfWidth[i] = halfWidth * (1 - i * 0.2);
				tierHeight[i] = trunkHeight * 0.72 * (1 - i * 0.1);
				tierColor[i] = UtilRandom.randomElement(GREEN_PALETTE);
				y -= tierHeight[i] * 0.62;
			}
		}

		private void branch(double sx, double sy, double ex, double ey, double thickness) {
			double dx = ex - sx;
			double dy = ey - sy;
			double len = Math.sqrt(dx * dx + dy * dy);
			double nx = -dy / len * thickness / 2;
			double ny = dx / len * thickness / 2;
			fillPoly(
					p(sx + nx, sy + ny),
					p(ex + nx, ey + ny),
					p(ex - nx, ey - ny),
					p(sx - nx, sy - ny));
		}

		protected void draw() {
			setColor(COLOR_TRUNK);
			fillRect(p(-trunkWidth / 2, -trunkVisibleHeight), trunkWidth, trunkVisibleHeight);

			if (species == Species.CONE) {
				for (int i = 0; i < tierBaseY.length; i++) {
					double hw = tierHalfWidth[i];
					double th = tierHeight[i];
					double y = tierBaseY[i];
					fillPoly(tierColor[i], p(-hw, y), p(hw, y), p(0, y - th));
				}
				return;
			}

			double top = -trunkHeight;
			for (int i = 0; i < lobeDx.length; i++) {
				fillOvalC(lobeColor[i], p(lobeDx[i], top + lobeDy[i]), lobeW[i], lobeH[i]);
			}

			setColor(COLOR_TRUNK);
			for (int i = 0; i < branchSx.length; i++) {
				branch(branchSx[i], branchSy[i], branchEx[i], branchEy[i], branchThickness[i]);
			}
		}
	}

	/*
	 * CLOUD
	 */

	private class Cloud extends Shape0 {
		private final double[] lobeDx;
		private final double[] lobeDy;
		private final double[] lobeW;
		private final double[] lobeH;
		private final Color color;

		public Cloud(double x, double y, double speed, double scale, int shade, int alpha) {
			super(new Point2(x, y), 120 * scale, 50 * scale, AnchorType.CENTER);
			getAnchor().setDerived(speed, 0);

			int lobes = UtilRandom.randomInt(4, 9);
			lobeDx = new double[lobes];
			lobeDy = new double[lobes];
			lobeW = new double[lobes];
			lobeH = new double[lobes];
			for (int i = 0; i < lobes; i++) {
				lobeDx[i] = UtilRandom.randomDouble(-50, 50) * scale;
				lobeDy[i] = UtilRandom.randomDouble(-12, 12) * scale;
				lobeW[i] = UtilRandom.randomDouble(28, 48) * scale;
				lobeH[i] = UtilRandom.randomDouble(16, 26) * scale;
			}

			int r = clampChannel(COLOR_CLOUD.getRed() + shade);
			int g = clampChannel(COLOR_CLOUD.getGreen() + shade);
			int b = clampChannel(COLOR_CLOUD.getBlue() + shade);
			color = new Color(r, g, b, alpha);
		}

		private int clampChannel(int v) {
			return Math.max(0, Math.min(255, v));
		}

		public void goNext() {
			super.goNext();
			if (getAnchor().getX() > gameWidth() + 100) {
				getAnchor().addX(-(gameWidth() + 200));
			}
		}

		protected void drawShape() {
			setColor(color);
			for (int i = 0; i < lobeDx.length; i++) {
				fillOval(p(lobeDx[i], lobeDy[i]), lobeW[i], lobeH[i]);
			}
		}
	}

	/*
	 * LEAF
	 */

	private class Leaf extends Shape0 {
		private static final double SIZE = 9;

		private final double phase;
		private final double spin;
		private final double fallSpeed;
		private final double liftFactor;
		private final Color color;
		private Angle angle;
		private double swirlPhase;
		private double swirlX;
		private double swirlY;
		private boolean over;

		public Leaf(double x, double y) {
			super(new Point2(x, y), SIZE, SIZE, AnchorType.CENTER);
			color = UtilRandom.randomElement(AUTUMN_PALETTE);
			phase = UtilRandom.randomDouble(0, 1000);
			spin = UtilRandom.randomDouble(-0.035, 0.035);
			fallSpeed = UtilRandom.randomDouble(0.6, 1.3);
			liftFactor = UtilRandom.randomDouble(0.7, 1.7);
			swirlPhase = UtilRandom.randomDouble(0, Angle.PI2);
			angle = Angle.random();
		}

		public void goNext() {
			super.goNext();

			double gustStrength = Math.max(0, Math.min(1, (wind - GUST_BASE) / GUST_RANGE));
			double flutter = Math.sin((getCount() + phase) * 0.07) * 0.9;
			double verticalSpeed = fallSpeed - gustStrength * liftFactor * fallSpeed * 2.4;
			double horizontalSpeed = wind * (1 + gustStrength * 0.6) + flutter;

			swirlPhase += 0.18 + gustStrength * 1.1;
			double radius = gustStrength * 9;
			double newSwirlX = Math.cos(swirlPhase) * radius;
			double newSwirlY = Math.sin(swirlPhase) * radius * 0.6;

			getAnchor().addX(horizontalSpeed + (newSwirlX - swirlX));
			getAnchor().addY(verticalSpeed + (newSwirlY - swirlY));
			swirlX = newSwirlX;
			swirlY = newSwirlY;

			angle = angle.add(spin * (1 + gustStrength));

			if (getAnchor().getY() >= groundY) {
				over = true;
				land(getAnchor(), color);
			} else if (getAnchor().getX() < -40 || getAnchor().getX() > gameWidth() + 40
					|| getAnchor().getY() < -60) {
				over = true;
			}
		}

		public boolean isOver() {
			return over;
		}

		protected void drawShape() {
			setColor(color);
			double hw = SIZE / 2;
			double hh = SIZE * 0.6 / 2;
			Point0 p0 = p(angle.rotationX(0, -hh), angle.rotationY(0, -hh));
			Point0 p1 = p(angle.rotationX(hw, 0), angle.rotationY(hw, 0));
			Point0 p2 = p(angle.rotationX(0, hh), angle.rotationY(0, hh));
			Point0 p3 = p(angle.rotationX(-hw, 0), angle.rotationY(-hw, 0));
			fillPoly(p0, p1, p2, p3);
		}
	}

	/*
	 * RESTING LEAF
	 */

	private class RestingLeaf extends ShapeRound {
		private boolean evicted;

		public RestingLeaf(Point0 pos, Color color) {
			super(p1(pos), UtilRandom.randomDouble(2.5, 4.5), color);
		}

		public void evict() {
			evicted = true;
		}

		public boolean isOver() {
			return evicted;
		}
	}
}
