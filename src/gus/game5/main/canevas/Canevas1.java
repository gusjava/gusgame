package gus.game5.main.canevas;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class Canevas1 extends Game1 {
	
	public static void main(String[] args) {
		Canevas1 main = new Canevas1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - canevas1");
		s.setWidth(800);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	protected void initialize1() {
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();	
	}
	
}
