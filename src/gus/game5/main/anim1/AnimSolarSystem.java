package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilRandom;

public class AnimSolarSystem extends Game2 {

	public static void main(String[] args) {
		AnimSolarSystem main = new AnimSolarSystem();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Solar system animation");
		s.setSize(900, 900);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}

	public static final int STAR_NUMBER = 150;
	public static final int TRAIL_LENGTH = 18;
	public static final int TRAIL_GAP = 4;

	protected void initialize1() {
		Point1 center = p1(gameWidth()/2, gameHeight()/2);

		addDraw(new Starfield());
		addDraw(new Sun(center));

		addDraw(new Planet(center, 70, 0.040, 10, new Color(190,190,190)));
		addDraw(new Planet(center, 115, 0.028, 14, new Color(210,150,90)));
		addDraw(new Planet(center, 170, 0.019, 16, new Color(90,150,220), 28, 0.12, 5, Color.LIGHT_GRAY));
		addDraw(new Planet(center, 250, 0.011, 22, new Color(200,100,70)));
		addDraw(new Planet(center, 340, 0.006, 30, new Color(220,190,130)));
	}

	/*
	 * STARFIELD
	 */

	private class Starfield extends Drawing1 {
		private final Point1[] stars;

		public Starfield() {
			stars = new Point1[STAR_NUMBER];
			for(int i=0; i<stars.length; i++)
				stars[i] = p(UtilRandom.randomDouble(gameWidth()), UtilRandom.randomDouble(gameHeight()));
		}

		protected void draw() {
			for(Point1 star : stars)
				fillRoundC(Color.WHITE, star, 1);
		}
	}

	/*
	 * SUN
	 */

	private class Sun extends Drawing1 {
		public Sun(Point0 center) {
			super(center);
		}

		protected void draw() {
			fillRoundC(new Color(255,160,40,60), 45);
			fillRoundC(Color.YELLOW, 28);
		}
	}

	/*
	 * PLANET
	 */

	private class Planet extends Drawing1 {
		private final double orbitRadius;
		private final double angularSpeed;
		private final double baseAngle;
		private final double radius;
		private final Color color;

		private final boolean hasMoon;
		private final double moonOrbitRadius;
		private final double moonAngularSpeed;
		private final double moonBaseAngle;
		private final double moonRadius;
		private final Color moonColor;

		private final Color[] trailColors;

		public Planet(Point0 center, double orbitRadius, double angularSpeed, double radius, Color color) {
			this(center, orbitRadius, angularSpeed, radius, color, 0, 0, 0, null);
		}

		public Planet(Point0 center, double orbitRadius, double angularSpeed, double radius, Color color,
				double moonOrbitRadius, double moonAngularSpeed, double moonRadius, Color moonColor) {
			super(center);
			this.orbitRadius = orbitRadius;
			this.angularSpeed = angularSpeed;
			this.baseAngle = UtilRandom.randomDouble(Angle.PI2);
			this.radius = radius;
			this.color = color;

			this.hasMoon = moonColor!=null;
			this.moonOrbitRadius = moonOrbitRadius;
			this.moonAngularSpeed = moonAngularSpeed;
			this.moonBaseAngle = UtilRandom.randomDouble(Angle.PI2);
			this.moonRadius = moonRadius;
			this.moonColor = moonColor;

			this.trailColors = new Color[TRAIL_LENGTH+1];
			for(int i=1; i<=TRAIL_LENGTH; i++) {
				int alpha = Math.max(0, (int) (90 - i*(80.0/TRAIL_LENGTH)));
				trailColors[i] = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
			}
		}

		protected void draw() {
			drawRoundC(new Color(255,255,255,25), orbitRadius);

			for(int i=TRAIL_LENGTH; i>=1; i--) {
				Angle trailAngle = Angle.angleRad(baseAngle + angularSpeed*(getCount()-i*TRAIL_GAP));
				Point0 trailPos = p(0,0).pAdd(orbitRadius, trailAngle);
				fillRoundC(trailColors[i], trailPos, radius*0.6);
			}

			Angle angle = Angle.angleRad(baseAngle + angularSpeed*getCount());
			Point0 pos = p(0,0).pAdd(orbitRadius, angle);
			fillRoundC(color, pos, radius);

			if(hasMoon) {
				Angle moonAngle = Angle.angleRad(moonBaseAngle + moonAngularSpeed*getCount());
				Point0 moonPos = pos.pAdd(moonOrbitRadius, moonAngle);
				fillRoundC(moonColor, moonPos, moonRadius);
			}
		}
	}
}
