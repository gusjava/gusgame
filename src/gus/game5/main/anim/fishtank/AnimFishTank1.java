package gus.game5.main.anim.fishtank;

import java.awt.Color;

import gus.game5.core.angle.Angle;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.Shape0;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class AnimFishTank1 extends Game1 {

	public static void main(String[] args) {
		AnimFishTank1 main = new AnimFishTank1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Fish tank");
		s.setWidth(1400);
		s.setHeight(760);
		s.setSleep(15);
		s.setBackground(COLOR_OCEAN);
	}
	
	public static Color COLOR_FISH = Color.WHITE;
	public static Color COLOR_ROCK = Color.DARK_GRAY;
	public static Color COLOR_OCEAN = Color.BLUE.darker();

	public static int NB_FISH = 1400;
//	public static int NB_FISH = 1;
	public static double FISH_SPEED = 3;
	public static double FISH_LENGTH = 10;
	public static double ROCK_RADIUS_MAX = 40;
	public static double ROCK_RADIUS_INC = 0.5;
	public static int ROCK_LIFETIME = 1500;
	public static int ROCK_RATE = 100;

	public static double D_MIN = 5;
	public static double D_MAX = 40;
	public static double WALL_ACC = 0.3;
	
	public static double D_MIN2 = D_MIN*D_MIN;
	public static double D_MAX2 = D_MAX*D_MAX;
	
	
	private ShapeList<Fish> fishes;
	private ShapeList<Rock> rocks;

	protected void initialize1() {
		fishes = newShapeList();
		rocks = newShapeList();
		
		for(int i=0;i<NB_FISH;i++) {
			fishes.add(new Fish(UtilRandom.randomPoint1(this)));
		}
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

		public Fish(Point0 anchor) {
			super(anchor, 1, 1);
			getAnchor().setDerived(FISH_SPEED, Angle.random());
			setColor(COLOR_FISH);
		}
		protected void drawShape() {
			drawLine(getAnchor().getDerived().pDistSet(FISH_LENGTH));
		}
		public void goNext() {
			reactToEnvironment();
			super.goNext();
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
			
			double x = getAnchor().getX();
			double y = getAnchor().getY();
			
			double vx = getAnchor().getDerived().getX();
			double vy = getAnchor().getDerived().getY();
			
			if(x>D_MIN && x<xmax-D_MIN && y>D_MIN && y<ymax-D_MIN) return false;
			
			if(x<0) x = 0;
			else if(x>xmax) x = xmax;
			
			if(x<D_MIN) vx += WALL_ACC;
			else if(x>xmax-D_MIN) vx -= WALL_ACC;
			
			if(y<0) y = 0;
			else if(y>ymax) y = ymax;
			
			if(y<D_MIN) vy += WALL_ACC;
			else if(y>ymax-D_MIN) vy -= WALL_ACC;
			
			getAnchor().setXY(x,y);
			getAnchor().getDerived().setXY(vx,vy);
			return true;
		}
		
		private boolean avoidRock() {
			Rock nearest = findNearest(rocks);
			if(nearest==null) return false;
			
			Point0 p = nearest.getAnchor().pSub(getAnchor());
			if(p.dist() > nearest.getRadius()) return false;
			
			Point0 dp = p.pDistSet(0.5*FISH_SPEED);

			getAnchor().getDerived().subXY(dp);
			getAnchor().getDerived().setDist(FISH_SPEED);
			return true;
		}
		
		private boolean avoidFish() {
			Fish nearest = findNearest(fishes);
			if(nearest==null) return false;

			Point0 p = nearest.getAnchor().pSub(getAnchor());
			if(p.dist() > D_MIN) return false;
			
			Point0 dp = p.pDistSet(0.25*FISH_SPEED);

			getAnchor().getDerived().subXY(dp);
			getAnchor().getDerived().setDist(FISH_SPEED);
			return true;
		}
		
		private void computeAverageDirection() {
			ShapeList<Fish> neighbors = new ShapeList<>();
			for(Fish fish : fishes) if(fish!=this) {
				double d2 = getAnchor().dist2(fish.getAnchor());
				if(d2>D_MIN2 && d2<D_MAX2) neighbors.add(fish);
			}
			if(neighbors.isEmpty()) return;
			Point0 avgSpeed = neighbors.avgPoint(f->f.getAnchor().getDerived());

			getAnchor().getDerived().addXY(avgSpeed.pMult(0.5));
			getAnchor().getDerived().setDist(FISH_SPEED);
		}
	}
}
