package gus.game5.main.anim.fishtank;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilAngle;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilRandom;

public class AnimFishTank2 extends Game1 {
	
	// TODO créer un timeWatcher pour visualiser les durées de chaque méthode de Fish

	public static void main(String[] args) {
		AnimFishTank2 main = new AnimFishTank2();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Fish tank (v2)");
		s.setWidth(1400);
		s.setHeight(760);
		s.setSleep(10);
		s.setBackground(COLOR_OCEAN);
	}
	
	public static Color COLOR_FISH = Color.WHITE;
	public static Color COLOR_ROCK = Color.DARK_GRAY;
	public static Color COLOR_OCEAN = Color.BLUE.brighter();

	public static int NB_FISH = 300;
	public static double FISH_SPEED = 3;
	public static double FISH_LENGTH = 8;
	public static double ROCK_RADIUS_MAX = 40;
	public static double ROCK_RADIUS_INC = 0.5;
	public static int ROCK_LIFETIME = 1500;
	public static int ROCK_RATE = 100;

	public static double D_MIN = 8;
	public static double D_MAX = 30;
	
	public static double D_MIN2 = D_MIN*D_MIN;
	public static double D_MAX2 = D_MAX*D_MAX;
	public static double ANGLE_WALL = UtilAngle.degToRad(15);
	
	
	private ShapeList<Fish> fishes;
	private ShapeList<Rock> rocks;

	protected void initialize1() {
		fishes = newShapeList();
		rocks = newShapeList();
		
		for(int i=0;i<NB_FISH;i++) {
			fishes.add(new Fish(UtilRandom.randomPoint1(this)));
		}
		
		newDrawingText(p1(50,100), ()->"STEP: "+UtilDisplay.dec2(time().getStepDuration())).setFontBold(20);
		newDrawingText(p1(50,130), ()->"CYCLE: "+UtilDisplay.dec2(time().getStep10Duration())).setFontBold(20);
		newDrawingText(p1(50,160), ()->"NEIGHBORS: "+fishes.sumInt(Fish::getNeighborNumber)).setFontBold(20);
	}

	protected void turn() {
		Keyboard k = keyboard();
		
		if(k.F1()) restart();
		if(k.F2()) exit();
		
		Mouse m = mouse();
		if(m.button1().isPressed()) {
			rocks.add(new Rock(m.pointCurrent()));
		}
		else if(UtilRandom.chance(ROCK_RATE)) {
			rocks.add(new Rock(UtilRandom.randomPoint1(this)));
		}
		
		fishes.goNext();
		rocks.goNext();
		rocks.clean();
		
		System.gc();
	}
	

	private class Rock extends ShapeRound {

		public Rock(Point0 anchor) {
			super(anchor, 1);
			setColor(COLOR_ROCK);
		}
		public void goNext() {
			super.goNext();
			if(getRadius()<ROCK_RADIUS_MAX)
			incrRadius(ROCK_RADIUS_INC);
		}
		public boolean isOver() {
			return getCount()>=ROCK_LIFETIME;
		}
	}

	
	private class Fish extends Shape0 {
		private Angle direction;
		private ShapeList<Fish> neighbors1;
		private ShapeList<Fish> neighbors2;
		
		public int getNeighborNumber() {
			return neighbors2.size();
		}

		public Fish(Point0 anchor) {
			super(anchor, 1, 1);
			direction = Angle.random();
			neighbors1 = new ShapeList<>();
			neighbors2 = new ShapeList<>();
			setColor(COLOR_FISH);
		}
		protected void drawShape() {
			drawLine(direction.pointAt(-FISH_LENGTH), new Point1());
			drawString(p1(5,5), ""+getNeighborNumber());
		}
		public void goNext() {
			computeNeighbors();
			reactToEnvironment();
			getAnchor().addXY(direction.pointAt(FISH_SPEED));
		}
		
		private void computeNeighbors() {
			neighbors1.clear();
			neighbors2.clear();
			for(Fish fish : fishes) if(fish!=this) {
				double d2 = getAnchor().dist2(fish.getAnchor());
				if(d2<D_MIN2) neighbors1.add(fish);
				else if(d2<D_MAX2) neighbors2.add(fish);
			}
		}
		
		private void reactToEnvironment() {
			if(avoidBorder()) return;
			if(avoidRock()) return;
			if(avoidFish()) return;
			computeAverageDirection();
		}
		
		private boolean avoidBorder() {
			double xmax = gameWidth();
			double ymax = gameHeight();
			
			Point2 anchor = getAnchor();
			double x = anchor.getX();
			double y = anchor.getY();
			
			if(x>D_MIN && x<xmax-D_MIN && y>D_MIN && y<ymax-D_MIN) return false;
			
			// change position
			if(x<0) x = 0;
			else if(x>xmax) x = xmax;
			if(y<0) y = 0;
			else if(y>ymax) y = ymax;
			anchor.setXY(x,y);
			
			boolean nearW = x<D_MIN;
			boolean nearE = x>xmax-D_MIN;
			boolean nearN = y<D_MIN;
			boolean nearS = y>ymax-D_MIN;

			if(nearW && nearS) direction = direction.approach(Angle.ANGLE45, ANGLE_WALL);
			else if(nearE && nearS) direction = direction.approach(Angle.ANGLE135, ANGLE_WALL);
			else if(nearE && nearN) direction = direction.approach(Angle.ANGLE225, ANGLE_WALL);
			else if(nearW && nearN) direction = direction.approach(Angle.ANGLE315, ANGLE_WALL);
			else if(nearW) direction = direction.approach(Angle.ANGLE0, ANGLE_WALL);
			else if(nearS) direction = direction.approach(Angle.ANGLE90, ANGLE_WALL);
			else if(nearE) direction = direction.approach(Angle.ANGLE180, ANGLE_WALL);
			else if(nearN) direction = direction.approach(Angle.ANGLE270, ANGLE_WALL);
			
			return true;
		}
		
		private boolean avoidRock() {
			Rock nearest = findNearest(rocks);
			if(nearest==null) return false;
			
			Point0 p = nearest.getAnchor().pSub(getAnchor());
			if(p.dist() > nearest.getRadius() + D_MIN) return false;
			
			Point0 dp = p.pDistSet(0.5);
			direction = direction.point().pSub(dp).angle();
			return true;
		}
		
		private boolean avoidFish() {
			Fish nearest = findNearest(neighbors1);
			if(nearest==null) return false;

			Point0 p = nearest.getAnchor().pSub(getAnchor());
			Point0 dp = p.pDistSet(0.25);
			direction = direction.point().pSub(dp).angle();
			return true;
		}
		
		private void computeAverageDirection() {
			if(neighbors2.isEmpty()) return;
			
			Point0 p = neighbors2.avgPoint(f->f.direction.point());
			direction = direction.point().pAdd(p.pMult(0.5)).angle();
		}
	}
}
