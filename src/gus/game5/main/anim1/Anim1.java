package gus.game5.main.anim1;

import java.awt.Color;

import gus.game5.core.alter.AlterDilate;
import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Anim1 extends Game1 {
	
	public static void main(String[] args) {
		Anim1 main = new Anim1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - anim 1");
		s.setWidth(800);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	private MainDrawing mainDrawing;
	private AlterDilate alterDilate;
	
	private Point1 repere;
	private double factor;
	
	
	
	protected void initialize1() {
		mainDrawing = new MainDrawing();
		addDraw(mainDrawing);

		factor = 1;
		repere = new Point1();
		
		alterDilate = new AlterDilate(repere, this::getFactor);
		mainDrawing.addAlter(alterDilate);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		Mouse m = mouse();
		double r = m.wheelRotation();
		if(r!=0) {
			factor += r*0.05;
			repere.setXY(alterDilate.revPoint(m.pointCurrent()));
		}
		
//		mainDrawing.incrAngle(0.0002);
		mainDrawing.rotate();
//		mainDrawing.translate();
	}
	
	
	private double getFactor() {
		return factor;
	}
	
	
	public class MainDrawing extends Drawing1 {
		private Point1 p1 = addStruct(100,100);
		private Point1 p2 = addStruct(100,200);
		private Point1 p3 = addStruct(150,200);
		private Point1 p4 = addStruct(150,100);
		private Point1 pc = addStruct(avg(p1,p2,p3,p4));
		
		private Angle angle = new Angle(0.02);
		private Point0 translation = p(0.5,0);

		protected void draw() {
			fillPoly(Color.ORANGE, p1, p2, p3, p4);
			
			Point0 p23 = avg(p2,p3);
			Point0 p14 = avg(p1,p4);
			
			drawLine(Color.RED, p1,p3);
			drawLine(Color.RED, p2,p4);
			drawLine(Color.RED, p23,p14);
			
			fillRoundC(pc,4);
		}
		
		public void rotate() {
			structure.rotate(pc, angle);
		}
		
		public void translate() {
			structure.addXY(translation);
		}
		
		public void incrAngle(double incr) {
			angle = angle.add(incr);
		}
	}
}
