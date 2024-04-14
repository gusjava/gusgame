package gus.game5.main.test1;

import java.awt.Color;

import gus.game5.core.draw.Draw;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class MainSoucoupe1 extends Game {

	public static void main(String[] args) {
		MainSoucoupe1 main = new MainSoucoupe1();
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
	
	
	

	private Soucoupe soucoupe;
	
	protected void initialize() {
		soucoupe = new Soucoupe();
		soucoupe.initOrigin().setXY(200, 100);
	}

	protected void turn() {
		Keyboard k = keyboard();
		
		if(k.down())	soucoupe.move(0, 1);
		if(k.up())		soucoupe.move(0, -1);
		if(k.right())	soucoupe.move(1, 0);
		if(k.left())	soucoupe.move(-1, 0);
		
		if(k.F1())		restart();
		if(k.F2())		exit();
	}
	
	
	public class Soucoupe extends Drawing1 {
		protected void draw() {
			fillRoundC(Color.BLACK, 15);
			fillRoundC(Color.DARK_GRAY, 14);
			fillRoundC(Color.GRAY,  10);
			fillRoundC(Color.LIGHT_GRAY, 8);
			fillRoundC(Color.RED, 5);
		}
		
		public void move(int dx, int dy) {
			initOrigin().addXY(dx, dy);
		}
	}
	
	protected Draw buildDraw() {
		return soucoupe;
	}
}
