package gus.game5.main.test1;

import java.awt.Color;

import gus.game5.core.draw.Draw;
import gus.game5.core.draw.DrawList;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.control.PointControl;
import gus.game5.core.point.control.PointControl0Mouse;
import gus.game5.core.point.control.PointControl1KbD4;
import gus.game5.core.point.control.PointControl1KbD8;
import gus.game5.core.point.control.PointControl1Mouse;
import gus.game5.core.point.control.PointControl2KbD4;
import gus.game5.core.point.control.PointControl2KbD8;
import gus.game5.core.point.control.PointControl2Mouse;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;

public class MainSoucoupe2 extends Game {
	
	public static final double SPEED = 1;
	public static final double ACC = 0.01;
	

	public static void main(String[] args) {
		MainSoucoupe2 main = new MainSoucoupe2();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Soucoupe");
		s.setWidth(1000);
		s.setHeight(400);
		s.setSleep(5);
		s.setBackground(Color.WHITE);
	}
	
	
	

	private Point2 center;
	private Soucoupe soucoupe;
	private PointControl control;
	private DrawList<Draw> drawList;
	
	
	protected void initialize() {
		center = new Point2(200,100);
		soucoupe = new Soucoupe(center);
		
		control = new PointControl0Mouse(this);
		
		drawList = new DrawList<>();
		drawList.add(soucoupe);
		drawList.add(new TextArea(10, 30));
	}

	protected void turn() {
		Keyboard kb = keyboard();
		
		if(kb.in().F1())	restart();
		if(kb.in().F2())	exit();

		if(kb.in().num0())	control = new PointControl0Mouse(this);
		
		if(kb.in().num1())	control = new PointControl1KbD4(this,SPEED);
		if(kb.in().num4())	control = new PointControl1KbD8(this,SPEED);
		if(kb.in().num7())	control = new PointControl1Mouse(this,SPEED);
		
		if(kb.in().num2())	control = new PointControl2KbD4(this,ACC);
		if(kb.in().num3())	control = new PointControl2KbD8(this,ACC);
		if(kb.in().num8())	control = new PointControl2Mouse(this,ACC);
		
		control.handle(center);
		center.goNext();
	}
	
	
	public class Soucoupe extends Drawing1 {
		public Soucoupe(Point1 center) {
			setOrigin(center);
		}
		protected void draw() {
			fillRoundC(Color.BLACK, 15);
			fillRoundC(Color.DARK_GRAY, 14);
			fillRoundC(Color.GRAY, 10);
			fillRoundC(Color.LIGHT_GRAY, 8);
			fillRoundC(Color.RED, 5);
		}
	}
	
	
	public class TextArea extends Drawing1 {
		public TextArea(double x, double y) {
			initOrigin().setXY(x,y);
		}
		protected void draw() {
			drawString(p(0, 15), "Position: "+center);
			drawString(p(0, 30), "Speed: "+center.initDerived());
			drawString(p(0, 45), "Acc: "+center.initDerived().initDerived());
			drawString(p(0, 60), "Control: "+control.getClass().getSimpleName());
		}
	}
	
	protected Draw buildDraw() {
		return drawList;
	}
}
