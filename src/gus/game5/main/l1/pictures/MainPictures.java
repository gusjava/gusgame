package gus.game5.main.l1.pictures;

import java.awt.Color;
import java.awt.Font;

import gus.game5.core.angle.Angle;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilDate;

public class MainPictures extends Game1 {
	
	public static void main(String[] args) {
		MainPictures main = new MainPictures();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Pictures");
		s.setSize(800, 800);
		s.setSleep(10);
		s.setBackground(Color.BLUE);
	}
	
	private Pictures pictures;
	private Clock clock;
	private Cloud cloud;
	
	
	protected void initialize1() {
		pictures = new Pictures();
		clock = new Clock();
		cloud = new Cloud(p1(300, 100), 20);
		
		
		newShape(clock);
		newShape(cloud);
		addDraw(pictures);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();	
		
		goNext();
	}
	
	private class Cloud extends ShapeRound {

		public Cloud(Point0 anchor, double radius) {
			super(anchor, radius);
			
			setColor(Color.WHITE);
			getAnchor().initDerived().setX(2);
		}
		
		public void goNext() {
			super.goNext();
			if(getAnchor().getX()>850) {
				getAnchor().setX(-50);
			}
		}
		
	}
	
	private class Pictures extends Drawing1 {
		public Pictures() {
			super();
		}
		
		protected void draw() {

			// dessin de la verdure

			fillRect(Color.GREEN, p1(0, 780), 800, 20);

			// dessin de l'arbre 

			// dessin du tronc

			fillRect(new Color(165, 42, 42), p1(625, 400), 100, 380);

			// dessin des feuilles

			fillOval(Color.GREEN, p1(600, 250), 150, 200);

			// dessin des nuages
			
		
			
			
			// dessin d'une pancarte
			
			// dessin du premier poteau

			fillRect(new Color(165, 42, 42), p1(400, 700), 15, 80);

			// dessin du deuxi�me poteau

			fillRect(new Color(165, 42, 42), p1(315, 700), 15, 80);
			
			// dessin de la plaque

			fillRect(new Color(165, 42, 42), p1(275, 625), 180, 75);
			
			// �criture

			drawStringC(Color.WHITE, fontPlain(30), p1(365, 660), "Bienvenue !");
			
			
			
			
			

		}
	}
	
	private class Clock extends ShapeRound {
		public Clock() {
			super(p1(150, 250), 120);
		}
		
		protected void drawShape() {
			double r = getRadius();
			
			// dessin du support
			
			fillRect(Color.orange.darker(), p1(-10, 0), 20, 650);
			
			
			int[] now = UtilDate.thisHourMinuteSecond();
			int hour = now[0];
			int minute = now[1];
			int second = now[2];
			int minute2 = 60*hour+minute;
			
			Angle angle6 = Angle.angleDeg(6);
			
			
			
			// dessin du cadre
			fillRoundC(Color.orange.darker(), r+15);
			fillRoundC(c(255,248,222), r);
			
			
			// marqueur
			for(int i=0; i<60; i++) {
				
				// conditions
				boolean isBig1 = i%5==0;
				boolean isBig2 = i%15==0;
				
				// variables
				Color color = isBig1 ? Color.BLACK : Color.LIGHT_GRAY;
				double thickness = isBig2 ? 4 : isBig1 ? 2 : 1;
				double length = isBig2 ? 15 : isBig1 ? 12 : 8;
				Angle am = angle6.mult(i);
				
				// dessin
				Point0 p1 = am.pointAt(r-5-length);
				Point0 p2 = am.pointAt(r-5);
				drawThickLine(color, p1, p2, thickness);
			}
			
			//  dessin de l'aiguille des secondes
			{
				Angle a = Angle.angleDeg(second*6).sub90();
				Point0 p1 = a.pointAt(-25);
				Point0 p2 = a.pointAt(r-25);
				drawThickLine(Color.LIGHT_GRAY, p1, p2, 2);
			}

			//  dessin de l'aiguille des minutes
			{
				Angle a = Angle.angleDeg(minute*6).sub90();
				Point0 p1 = a.pointAt(-15);
				Point0 p2 = a.pointAt(r-40);
				drawThickLine(p1, p2, 3);
			}

			//  dessin de l'aiguille des heures
			{
				Angle a = Angle.angleDeg(minute2*0.5).sub90();
				Point0 p1 = a.pointAt(-10);
				Point0 p2 = a.pointAt(r-60);
				drawThickLine(p1, p2, 4);
			}
			
			// dessin du cercle2
			fillRoundC(5);
			
			// dessin des nombres
			for(int i=0; i<12; i++) {
				Angle a = Angle.angleDeg(i*30).sub90();
				Point0 p = a.pointAt(r-40);
				drawStringC(new Font("Candara Light", Font.PLAIN, 24), p, ""+ (i==0 ? 12 : i));
			}
		}
	}
}
