package gus.game5.main.canevas;

import java.awt.Color;

import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;

public class Canevas2 extends Game2 {
	
	public static void main(String[] args) {
		Canevas2 main = new Canevas2();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - canevas2");
		s.setWidth(800);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	protected void initialize1() {
	}
}
