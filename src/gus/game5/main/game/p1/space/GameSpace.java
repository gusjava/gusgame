package gus.game5.main.game.p1.space;

import java.awt.Color;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.map.GameMap;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;

public class GameSpace extends Game1 {
	
	public static void main(String[] args) {
		GameSpace main = new GameSpace();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - Space");
		s.setWidth(1400);
		s.setHeight(760);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}
	
	private ShapeList<Town> townList;
	private Town selected;
	private GameMap gameMap;
	
	protected void initialize1() {
		gameMap = newGameMap(5000, 5000);
		gameMap.setSpeed(4);
		gameMap.getMapOrigin().setXY(0,0);
		gameMap.getFrameOrigin().setXY(10,10);
		gameMap.setFrameSize(1000, 740);
		
		townList = gameMap.newShapeList();
		townList.add(new Town(400,400));
		townList.add(new Town(400,600));
		
		newDrawingText(new Point1(20,20), gameMap.getMapOrigin()::toString);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		
		if(mouse().button1().justPressed()) {
			click1();
		}
		else if(mouse().button3().justPressed()) {
			click2();
		}
		goNext();
	}
	
	
	
	private void click1() {
		townList.forEach(Town::unselect);
		
		selected = townList.findNearest(gameMap.mousePosition());
		if(selected!=null) {
			selected.select();
			selected.upgrade();
		}
	}
	
	private void click2() {
		townList.forEach(Town::unselect);
		
		selected = townList.findNearest(gameMap.mousePosition());
		if(selected!=null) {
			selected.select();
			selected.downgrade();
		}
	}
}
