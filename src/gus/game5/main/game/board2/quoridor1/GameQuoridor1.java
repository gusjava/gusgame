package gus.game5.main.game.board2.quoridor1;

import java.awt.Color;

import gus.game5.core.game.Settings;
import gus.game5.core.play1.Play1;

public class GameQuoridor1 extends Play1 {
	
	public static final String TITLE = "Quoridor";
	
	public static void main(String[] args) {
		GameQuoridor1 main = new GameQuoridor1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
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
