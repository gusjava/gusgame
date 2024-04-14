package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.draw.DrawGameBorder;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.Shape0.AnchorType;

public class AnimLandscape extends Game2 {
	
	public static void main(String[] args) {
		AnimLandscape main = new AnimLandscape();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Landscape");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
	}
	
	public static final int BORDER = 20;
	public static final int GROUND_Y = 650;
	public static final int CLOUD_RADIUS = 15;
	public static final double WIND = 2;
	
	public static final int MILLSAIL_LENGTH = 100;
	public static final int MILLSAIL_THICKNESS = 5;
	public static final int MILL_WIDTH = 90;
	public static final int MILL_HEIGHT = 165;
	public static final double MILL_SPEED = 0.05;
	
	public static final Color COLOR_TREE1 = Color.ORANGE.darker();
	public static final Color COLOR_TREE2 = Color.GREEN.darker();
	public static final Color COLOR_SUN = Color.YELLOW;
	public static final Color COLOR_CLOUD = Color.WHITE;
	public static final Color COLOR_SKY = Color.CYAN;
	public static final Color COLOR_GROUND = Color.GREEN;
	
	
	protected void initialize1() {
		int W = gameWidth();
		int H = gameHeight();
		
		plateform(0, 0, W, GROUND_Y, COLOR_SKY);
		plateform(0, GROUND_Y, W, H - GROUND_Y, COLOR_GROUND);
		
		addDraw(new Sun(700,200));
		addDraw(new Mill(450));
		
		addDraw(new Tree(200,80));
		addDraw(new Tree(675,100));
		addDraw(new Tree(735,130));
		addDraw(new Tree(820,55));
		
		cloud(50,35);
		cloud(70,35);
		cloud(90,35);
		cloud(110,40);

		cloud(60,55);
		cloud(80,55);
		cloud(90,55);
		cloud(422,55);
		cloud(466,51);
		cloud(453,33);
		cloud(439,58);
		
		cloud(260,210);
		cloud(270,230);
		cloud(270,205);
		cloud(300,197);
		cloud(255,198);
		cloud(244,205);
		cloud(289,200);
		
		cloud(444,42);
		
		addDraw(new DrawGameBorder(this, BORDER));
	}
	
	
	/*
	 * PLATEFORM
	 */
	
	private void plateform(double x0, double y0, double width, double height, Color color) {
		newShapeRect(p1(x0, y0), width, height, color, AnchorType.NW);
	}
	
	/*
	 * CLOUD
	 */
	
	private void cloud(double xc, double yc) {
		newShape(new Cloud(p2(xc + BORDER, yc + BORDER)));
	}
	
	private class Cloud extends ShapeRound {
		public Cloud(Point2 anchor) {
			super(anchor, CLOUD_RADIUS);
			setColor(COLOR_CLOUD);
			anchor.setDerived(WIND, 0);
		}
		
		public void goNext() {
			super.goNext();
			if(getP0().getX()>gameWidth()) 
				getAnchor().addX(-gameWidth()-100);
		}
	}
	
	/*
	 * SUN
	 */
	
	private class Sun extends Drawing1 {
		public Sun(double x, double y) {
			super(x, y);
			setColor(COLOR_SUN);
		}

		protected void draw() {
			fillRoundC(25);
			drawStarC(80, 40);
		}
	}
	
	/*
	 * TREE
	 */
	
	private class Tree extends Drawing1 {
		private int h;

		public Tree(int x, int h) {
			super(x + BORDER, GROUND_Y - h);
			this.h = h;
		}
		protected void draw() {
			fillRect(COLOR_TREE1, 20, h);
			fillOval(COLOR_TREE2, p(-20,-60) ,60, 80);
		}
	}
	
	/*
	 * MILL
	 */
	
	private class Mill extends Drawing1 {
		
		public Mill(int x) {
			super(x + BORDER, GROUND_Y);
		}

		protected void draw() {
			Color color1 = new Color(255, 255, 153);
			Color color2 = new Color(153, 51, 0);

			setColor(color1);
			//mur
			fillRect(p(0,-MILL_HEIGHT),MILL_WIDTH, MILL_HEIGHT);

			setColor(color2);
			//porte
			fillRect(p(30,-45),30,45);
			//fenetre
			fillRect(p(25,-125),40,40);
			
			setColor(color1);
			//carreaux
			fillRect(p(28,-122),15,15);
			fillRect(p(28,-103),15,15);
			fillRect(p(47,-122),15,15);
			fillRect(p(47,-103),15,15);
			
			//poignet
			fillOval(p(53,-24),5,5);
			
			setColor(Color.RED);
			//toit
			fillPoly(
					p(-5,-MILL_HEIGHT), 
					p(MILL_WIDTH/2,-240), 
					p(MILL_WIDTH+5, -MILL_HEIGHT));

			setColor(Color.BLACK);
			//rond
			fillOval(p(30,-210),30,30);
			
			setColor(color2);
			//voiles
			Angle angle = new Angle(getCount()*MILL_SPEED);
			drawSail(angle);
			drawSail(angle.add90());
			drawSail(angle.add180());
			drawSail(angle.add270());
		}
		
		private void drawSail(Angle angle) {
			Point0 p0 = p(MILL_WIDTH/2, -195);
			Point0 p1 = p0.pAdd(MILLSAIL_LENGTH, angle);
			
			Point0 p0a = p0.pAdd(MILLSAIL_THICKNESS, angle.add90());
			Point0 p0b = p0.pAdd(MILLSAIL_THICKNESS, angle.sub90());

			Point0 p1a = p1.pAdd(MILLSAIL_THICKNESS, angle.add90());
			Point0 p1b = p1.pAdd(MILLSAIL_THICKNESS, angle.sub90());
			
			fillPoly(p1a, p1b, p0b, p0a);
		}
	}
	
}
