package gus.game5.main.l1.bloon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilRandom;

public class GameBloon extends Game1 {
	
	public static final double BLOON_RADIUS = 12;
	public static final double BLOON_SPEED = 2;
	
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
	
	protected void initialize1() {
		
		path = new ArrayList<>();
		path.add(p1(50, 50));
		path.add(p1(80, 90));
		path.add(p1(120, 500));
		path.add(p1(700, 500));
		path.add(p1(600, 470));
		path.add(p1(200, 600));
		path.add(p1(400, 200));
		
		list = newShapeList();
		list.add(new Bloon());
		
		addDraw(new PathDraw());
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(UtilRandom.chance(50)) {
			list.add(new Bloon());
		}
		
		goNext();
		clean();
	}
	
	private class Bloon extends ShapeRound {
		
		private int targetIndex = 0;
		
		public Bloon() {
			super(path.get(0), BLOON_RADIUS);
			setColor(Color.RED);
		}
		
		public void goNext() {
			
			Point1 target = path.get(targetIndex);
			Point1 targetDist = target.pSub(getAnchor());
			double speed = Math.min(BLOON_SPEED, targetDist.dist());
			Point0 v = targetDist.pDistSet(speed);
			
			getAnchor().initDerived().setXY(v);
			super.goNext();
			
			if(getAnchor().is(target)) {
				targetIndex++;
			}
		}
		
		public boolean isOver() {
			return targetIndex==path.size();
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
