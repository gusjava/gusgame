package gus.game5.main.canevas;

import java.awt.Color;

import gus.game5.core.draw.Draw;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;

public class Canevas extends Game {

	public static void main(String[] args) {
		Canevas main = new Canevas();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - canevas");
		s.setWidth(600);
		s.setHeight(400);
		s.setSleep(5);
		s.setBackground(Color.WHITE);
	}
	
	
	protected void initialize() {
	}

	protected void turn() {
		Keyboard k = keyboard();
		
		if(k.F1())		restart();
		if(k.F2())		exit();
	}
	
	
	protected Draw buildDraw() {
		return null;
	}
}
