package gus.game5.main.game.warrior;

import java.awt.Color;

import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeList;

public class GameWarrior extends Game1 {
	
	public static void main(String[] args) {
		GameWarrior main = new GameWarrior();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(getClass().getSimpleName()+" - warrior");
		s.setWidth(1200);
		s.setHeight(800);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
	}
	
	
	private Player player;
	private ShapeList<Bullet> bullets;
	
	
	protected void initialize1() {
		player = new Player(this, 400, 400);
		addDraw(player);
		
		bullets = newShapeList();
		
		DrawingText drawingText = new DrawingText(new Point1(30, 30), ()->"bullets: "+bullets.size());
		drawingText.setDrawable(bullets::isFilled);
		addDraw(drawingText);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		if(k.in().space()) shoot();
		
		boolean insideMouse = player.contains(mouse().pointCurrent());
		boolean pressed1 = mouse().button1().isPressed();
		boolean pressed3 = mouse().button3().isPressed();
		
		if(insideMouse) player.stopMotion();
		else if(pressed3) player.initControl2();
		else if(pressed1) player.initControl1();
		else player.stopMotion();
		
		player.goNext();
		bullets.goNext();
		
		bullets.removeIf(bullet->bullet.getLife()<=0);
	}

	

	private void shoot() {
		Bullet bullet = new Bullet(player.getAnchor(), player.mouseAngle());
		bullets.add(bullet);
	}
}
