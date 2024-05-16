package gus.game5.main.l1.bloon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilRandom;

public class GameBloon extends Game1 {
	
	public static final double BLOON_RADIUS = 12;
	
	public static void main(String[] args) {
		GameBloon main = new GameBloon();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Bloon");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	private ShapeList<Bloon> list;
	private List<Point1> path;
	private double life;
	
	protected void initialize1() {
		
		life = 200;
		
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
		
		list = newShapeList();
		
		addDraw(new PathDraw());
		
		DrawingText dt = newDrawingText(Color.GREEN, p1(800, 100), ()->"Life = "+life);
		dt.setFont(30);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(!isGameOver()) {
			if(UtilRandom.chance(50)) {
				list.add(new RedBloon());
			}
			if(UtilRandom.chance(70)) {
				list.add(new BlueBloon());
			}
			if(UtilRandom.chance(100)) {
				list.add(new GreenBloon());
			}
			if(UtilRandom.chance(200)) {
				list.add(new YellowBloon());
			}
			if(UtilRandom.chance(300)) {
				list.add(new PinkBloon());
			}
			
			goNext();
			clean();
		}
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
	}
	
	
	private class RedBloon extends Bloon {
		public RedBloon() {
			super(1, 1);
			setColor(Color.RED);
		}
	}
	
	
	private class BlueBloon extends Bloon {
		public BlueBloon() {
			super(2, 3);
			setColor(Color.BLUE);
		}
	}
	
	private class GreenBloon extends Bloon {
		public GreenBloon() {
			super(3, 5);
			setColor(Color.GREEN);
		}
	}
	
	private class YellowBloon extends Bloon {
		public YellowBloon() {
			super(5, 9);
			setColor(Color.YELLOW);
		}
	}
	
	private class PinkBloon extends Bloon {
		public PinkBloon() {
			super(6, 20);
			setColor(Color.PINK);
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
