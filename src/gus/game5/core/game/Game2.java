package gus.game5.core.game;

import gus.game5.core.keyboard.Keyboard;

public abstract class Game2 extends Game1 {

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	onF1();
		if(k.in().F2())	onF2();
		
		before();
		goNext();
		clean();
		after();
	}
	
	protected void onF1() {
		restart();
	}
	
	protected void onF2() {
		exit();
	}
	
	protected void before() {
		
	}
	
	protected void after() {
		
	}
}
