package gus.game5.main.test1;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2D0;
import gus.game5.core.shape.ShapeSquare;
import gus.game5.core.shape.Shape0.AnchorType;

public class MainMouse1 extends Game1 {
	
	public static void main(String[] args) {
		MainMouse1 main = new MainMouse1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Mouse1");
		s.setWidth(1000);
		s.setHeight(700);
		s.setSleep(2);
		s.setBackground(Color.WHITE);
	}
	

	private ShapeSquare square1;
	private ShapeSquare square2;
	
	protected void initialize1() {
		square1 = new ShapeSquare(gameCenter(), 60, Color.BLACK, AnchorType.CENTER);
		square2 = new ShapeSquare(new Point2D0(mouse()::point), 40, Color.GRAY, AnchorType.CENTER);
		
		addDraw(square1);
		addDraw(square2);
	}

	protected void turn() {
		
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		if(square1.includes(square2))
			square2.setColor(Color.RED);
		else if(square1.intersects(square2))
			square2.setColor(Color.ORANGE);
		else square2.setColor(Color.GRAY);
	}
}
