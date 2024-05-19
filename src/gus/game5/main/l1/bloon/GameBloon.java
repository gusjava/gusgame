package gus.game5.main.l1.bloon;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.shape.ShapeSquare;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilRandom;

public class GameBloon extends Game1 {
	
	public static final double BLOON_RADIUS = 12;
	public static final double TOWER_LENGTH = 20;
	public static final double MISSILE_RADIUS = 4;
	public static final double MISSILE_SPEED = 15;
	
	public static void main(String[] args) {
		GameBloon main = new GameBloon();
		main.displayInWindows();
		main.start();
	}
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Bloon");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	// DATA
	
	private ShapeList<Bloon> bloons;
	private ShapeList<Tower> towers;
	private ShapeList<Missile> missiles;
	private List<Point1> path;
	private double life;
	private int round;
	private int step;
	
	protected void initialize1() {
		
		life = 200;
		round = 0;
		step = 0;
		
		path = new ArrayList<>();
		path.add(p1(0, 50));
		path.add(p1(50, 50));
		path.add(p1(80, 90));
		path.add(p1(120, 500));
		path.add(p1(700, 500));
		path.add(p1(600, 470));
		path.add(p1(200, 600));
		path.add(p1(400, 200));
		path.add(p1(600, 300));
		path.add(p1(750, 300));
		path.add(p1(900, 200));
		path.add(p1(1000, 460));
		
		bloons = newShapeList();
		towers = newShapeList();
		missiles = newShapeList();
		
		addDraw(new PathDraw());
		
		DrawingText dt = newDrawingText(Color.GREEN, p1(800, 100), ()->"Life = "+life);
		dt.setFont(30);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(isGameOver()) return;
		
		if(step==0) handleStep0();
		else handleStep1();
	}
	
	private void handleStep0() {
		if(mouse().button1().justPressed()) {
			Tower tower1 = new Tower1(mouse().pointCurrent());
			towers.add(tower1);
			if(towers.size()==3) {
				step = 1;
			}
		}
	}
			
	private void handleStep1() {
		if (UtilRandom.chance(30)) {
			bloons.add(new RedBloon());
		}
		if (UtilRandom.chance(50)) {
			bloons.add(new BlueBloon());
		}
		if (UtilRandom.chance(90)) {
			bloons.add(new GreenBloon());
		}
		if (UtilRandom.chance(130)) {
			bloons.add(new YellowBloon());
		}
		if (UtilRandom.chance(150)) {
			bloons.add(new PinkBloon());
		}
		
		for(Tower tower : towers) {
			for(Bloon bloon : bloons) {
				double distance = bloon.getAnchor().dist(tower.getAnchor());
				if(distance<=tower.getRange()) {
					tower.targetBloon(bloon);
				}
			}
		}
		
		for(Missile missile : missiles) {
			for(Bloon bloon : bloons) {
				if(missile.intersects(bloon)) {
					missile.explode();
					bloon.damage(1);
				}
			}
		}

		goNext();
		clean();
	}
	
	private void handleGameOver() {
		life = 0;
		DrawingText dt = newDrawingTextC(Color.RED, gameCenter(), "Game Over");
		dt.setFont(50);
	}
	
	private boolean isGameOver() {
		return life<=0;
	}
	
	private abstract class Bloon extends ShapeRound {
		private int targetIndex;
		private Point1 target;
		private double speed;
		private double bloonLife;
		
		public Bloon(double speed, double bloonLife) {
			super(path.get(0), BLOON_RADIUS);
			this.speed = speed;
			this.bloonLife = bloonLife;
			setTarget(1);
		}
		
		public void goNext() {
			super.goNext();
			if(getAnchor().near(target, speed)) {
				setTarget(targetIndex+1);
			}
			
			if(isOver()) {
				life-= bloonLife;
				if(life<0) handleGameOver();
			}
		}
		
		protected void drawShape() {
			super.drawShape();
			drawRoundC(Color.BLACK, getRadius());
		}
		
		public boolean isOver() {
			return target==null || bloonLife<=0;
		}
		
		private void setTarget(int targetIndex) {
			this.targetIndex = targetIndex;
			target = UtilList.get(path, targetIndex);
			getAnchor().setDerived(target, speed);
		}
		
		public void damage(int damage) {
			bloonLife -= damage;
		}
	}
	
	
	private class RedBloon extends Bloon {
		public RedBloon() {
			super(1, 1);
			setColor(Color.RED);
		}
	}
	
	
	private class BlueBloon extends Bloon {
		public BlueBloon() {
			super(2, 2);
			setColor(Color.BLUE);
		}
	}
	
	private class GreenBloon extends Bloon {
		public GreenBloon() {
			super(3, 3);
			setColor(Color.GREEN);
		}
	}
	
	private class YellowBloon extends Bloon {
		public YellowBloon() {
			super(4, 5);
			setColor(Color.YELLOW);
		}
	}
	
	private class PinkBloon extends Bloon {
		public PinkBloon() {
			super(5, 7);
			setColor(Color.PINK);
		}
	}
	
	private abstract class Tower extends ShapeSquare {
		
		protected double range;
		private long countLastMissile = -1;
		private int shotCount = 0;
		
		public Tower(Point0 anchor) {
			super(anchor, TOWER_LENGTH);
		}

		protected void drawShape() {
			super.drawShape();
			drawRoundC(range);
			drawString(p(15,10), ""+shotCount);
		}
		
		public double getRange() {
			return range;
		}
		
		public void targetBloon(Bloon bloon) {
			if(getCount()<countLastMissile + 10) return;
			Missile missile = new Missile(getAnchor(), bloon.getAnchor());
			missiles.add(missile);
			countLastMissile = getCount();
			shotCount++;
		}
	}
	
	private class Tower1 extends Tower {
		public Tower1(Point0 anchor) {
			super(anchor);
			setColor(Color.BLACK);
			range = 100;
		}
	}
	
	private class Missile extends ShapeRound {
		private int carburant = 1000;

		public Missile(Point0 anchor, Point0 target) {
			super(anchor, MISSILE_RADIUS);
			setColor(Color.BLACK);
			getAnchor().setDerived(target, MISSILE_SPEED);
		}
		
		public void explode() {
			if(carburant==0) return;
			carburant = 0;
		}
		
		public void goNext() {
			super.goNext();
			carburant--;
		}
		
		public boolean isOver() {
			return carburant<=0;
		}
	}
	
	private class PathDraw extends Drawing1 {

		protected void draw() {
			for(int i=1;i<path.size();i++) {
				drawLine(path.get(i-1), path.get(i)); 
			}
		}
		
	}
}
