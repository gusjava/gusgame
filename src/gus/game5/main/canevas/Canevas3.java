package gus.game5.main.canevas;

import java.awt.Color;

import gus.game5.core.game.Settings;
import gus.game5.core.play1.Play1;

public class Canevas3 extends Play1 {
	
	public static void main(String[] args) {
		Canevas3 main = new Canevas3();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - canevas3");
		s.setWidth(800);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	protected void initialize2() {
	}
	
	
	protected void played() {
		
	}
	
	protected void turnStart() {
		
	}
	
	protected void turnEnd() {
		
	}
}
