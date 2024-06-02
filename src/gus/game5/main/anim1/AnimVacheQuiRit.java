package gus.game5.main.anim1;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.util.UtilScreen;

public class AnimVacheQuiRit extends Game1 {
	
	public static void main(String[] args) {
		AnimVacheQuiRit main = new AnimVacheQuiRit();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Vache qui rit");
		s.setSize(1200, 675);
		s.setSleep(10);
		s.setBackground(Color.BLACK);
	}
	
	private ShapeImg shapeImg;
	
	protected void initialize1() {
		shapeImg = newShapeImg(gameCenter(), gameWidth(), gameHeight(), null);
	}

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		BufferedImage image = UtilScreen.captureScreen();
		shapeImg.setImg(image);
	}
}
