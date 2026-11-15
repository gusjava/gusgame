package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class AnimLSystem extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int MAX_SEGMENTS = 14000;
	public static final int MAX_STACK = 64;
	public static final int GROW_FRAMES = 320;
	public static final int PAUSE_FRAMES = 130;
	public static final double MARGIN = 0.86;
	public static final double MAX_THICKNESS = 4.5;

	public static final Color COLOR_BG = new Color(10,14,20);
	public static final Color COLOR_TEXT = new Color(150,170,195);

	public static final double UP = -Math.PI/2;

	public static final Variant[] VARIANTS = {
		new Variant("Plante", "X", 25, 6, UP,
				new String[] {"X", "F+[[X]-X]-F[-FX]+X", "F", "FF"},
				new Color(120,80,45), new Color(110,220,110)),
		new Variant("Buisson", "F", 22.5, 4, UP,
				new String[] {"F", "FF+[+F-F-F]-[-F+F+F]"},
				new Color(95,70,40), new Color(160,235,90)),
		new Variant("Herbe folle", "F", 25.7, 5, UP,
				new String[] {"F", "F[+F]F[-F]F"},
				new Color(70,110,50), new Color(200,245,120)),
		new Variant("Flocon de Koch", "F++F++F", 60, 4, 0,
				new String[] {"F", "F-F++F-F"},
				new Color(90,170,255), new Color(220,245,255)),
		new Variant("Courbe du dragon", "FX", 90, 12, 0,
				new String[] {"X", "X+YF+", "Y", "-FX-Y"},
				new Color(255,110,80), new Color(255,215,120)),
		new Variant("Triangle de Sierpinski", "F-G-G", 120, 6, 0,
				new String[] {"F", "F-G+F+G-F", "G", "GG"},
				new Color(190,110,255), new Color(120,230,235))
	};

	public static void main(String[] args) {
		AnimLSystem main = new AnimLSystem();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("L-system");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[] x1;
	private double[] y1;
	private double[] x2;
	private double[] y2;
	private int[] level;
	private int count;
	private int maxLevel;

	private int variant;
	private double drawn;
	private double speed;
	private int pause;

	protected void initialize1() {
		x1 = new double[MAX_SEGMENTS];
		y1 = new double[MAX_SEGMENTS];
		x2 = new double[MAX_SEGMENTS];
		y2 = new double[MAX_SEGMENTS];
		level = new int[MAX_SEGMENTS];

		variant = 0;
		build();

		addDraw(new Plant());
		addDraw(new Hud());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1()) restart();
		if(k.F2()) exit();

		if(drawn<count) {
			drawn = Math.min(count, drawn+speed);
			return;
		}

		pause++;
		if(pause<PAUSE_FRAMES) return;

		variant = (variant+1)%VARIANTS.length;
		build();
	}

	/*
	 * BUILD
	 */

	private void build() {
		Variant v = VARIANTS[variant];
		String word = expand(v);
		trace(v, word);
		fitToWindow();

		drawn = 0;
		pause = 0;
		speed = Math.max(1, count/(double) GROW_FRAMES);
	}

	private String expand(Variant v) {
		String word = v.getAxiom();
		for(int i=0;i<v.getIterations();i++) {
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<word.length();j++) sb.append(v.replace(word.charAt(j)));
			if(sb.length()>MAX_SEGMENTS*6) return word;
			word = sb.toString();
		}
		return word;
	}

	private void trace(Variant v, String word) {
		double delta = Math.toRadians(v.getAngleDeg());
		double x = 0;
		double y = 0;
		double a = v.getStartAngle();
		int depth = 0;

		double[] stackX = new double[MAX_STACK];
		double[] stackY = new double[MAX_STACK];
		double[] stackA = new double[MAX_STACK];
		int stack = 0;

		count = 0;
		maxLevel = 0;

		for(int i=0;i<word.length() && count<MAX_SEGMENTS;i++) {
			char c = word.charAt(i);

			if(c=='F' || c=='G') {
				double nx = x+Math.cos(a);
				double ny = y+Math.sin(a);
				x1[count] = x;
				y1[count] = y;
				x2[count] = nx;
				y2[count] = ny;
				level[count] = depth;
				count++;
				if(depth>maxLevel) maxLevel = depth;
				x = nx;
				y = ny;
			}
			else if(c=='f') {
				x += Math.cos(a);
				y += Math.sin(a);
			}
			else if(c=='+') a += delta;
			else if(c=='-') a -= delta;
			else if(c=='[' && stack<MAX_STACK) {
				stackX[stack] = x;
				stackY[stack] = y;
				stackA[stack] = a;
				stack++;
				depth++;
			}
			else if(c==']' && stack>0) {
				stack--;
				x = stackX[stack];
				y = stackY[stack];
				a = stackA[stack];
				depth--;
			}
		}
	}

	private void fitToWindow() {
		if(count==0) return;

		double minX = x1[0];
		double maxX = x1[0];
		double minY = y1[0];
		double maxY = y1[0];

		for(int i=0;i<count;i++) {
			minX = Math.min(minX, Math.min(x1[i], x2[i]));
			maxX = Math.max(maxX, Math.max(x1[i], x2[i]));
			minY = Math.min(minY, Math.min(y1[i], y2[i]));
			maxY = Math.max(maxY, Math.max(y1[i], y2[i]));
		}

		double spanX = Math.max(1e-6, maxX-minX);
		double spanY = Math.max(1e-6, maxY-minY);
		double scale = Math.min(WIDTH*MARGIN/spanX, HEIGHT*MARGIN/spanY);
		double offsetX = (WIDTH-spanX*scale)/2-minX*scale;
		double offsetY = (HEIGHT-spanY*scale)/2-minY*scale;

		for(int i=0;i<count;i++) {
			x1[i] = x1[i]*scale+offsetX;
			y1[i] = y1[i]*scale+offsetY;
			x2[i] = x2[i]*scale+offsetX;
			y2[i] = y2[i]*scale+offsetY;
		}
	}

	/*
	 * PLANT
	 */

	private class Plant extends Drawing1 {

		protected void draw() {
			Variant v = VARIANTS[variant];
			int last = (int) drawn;

			for(int i=0;i<last;i++) {
				double t = maxLevel==0 ? i/(double) Math.max(1, count) : level[i]/(double) maxLevel;
				double thickness = Math.max(1, MAX_THICKNESS*(1-t));
				drawThickLine(v.colorAt(t), p(x1[i], y1[i]), p(x2[i], y2[i]), thickness);
			}
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			Variant v = VARIANTS[variant];
			drawString(COLOR_TEXT, fontPlain(13), p(14, 24),
					v.getName()+"   angle "+v.getAngleDeg()+" deg   "+count+" segments");
		}
	}

	/*
	 * VARIANT
	 */

	public static class Variant {

		private final String name;
		private final String axiom;
		private final double angleDeg;
		private final int iterations;
		private final double startAngle;
		private final String[] rules;
		private final Color colorBase;
		private final Color colorTip;

		public Variant(String name, String axiom, double angleDeg, int iterations, double startAngle,
				String[] rules, Color colorBase, Color colorTip) {
			this.name = name;
			this.axiom = axiom;
			this.angleDeg = angleDeg;
			this.iterations = iterations;
			this.startAngle = startAngle;
			this.rules = rules;
			this.colorBase = colorBase;
			this.colorTip = colorTip;
		}

		public String getName() {
			return name;
		}

		public String getAxiom() {
			return axiom;
		}

		public double getAngleDeg() {
			return angleDeg;
		}

		public int getIterations() {
			return iterations;
		}

		public double getStartAngle() {
			return startAngle;
		}

		public String replace(char c) {
			for(int i=0;i<rules.length;i+=2) {
				if(rules[i].charAt(0)==c) return rules[i+1];
			}
			return String.valueOf(c);
		}

		public Color colorAt(double t) {
			int r = (int) (colorBase.getRed()+(colorTip.getRed()-colorBase.getRed())*t);
			int g = (int) (colorBase.getGreen()+(colorTip.getGreen()-colorBase.getGreen())*t);
			int b = (int) (colorBase.getBlue()+(colorTip.getBlue()-colorBase.getBlue())*t);
			return new Color(r, g, b);
		}
	}
}
