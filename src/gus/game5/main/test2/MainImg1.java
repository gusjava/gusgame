package gus.game5.main.test2;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.draw.Draw;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.util.UtilImage;

public class MainImg1 extends Game {

	public static void main(String[] args) {
		MainImg1 main = new MainImg1();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Bonhomme sur roulettes (1)");
		s.setBackground(Color.WHITE);
		s.setSize(400, 250);
	}
	
	private ShapeImg shape;
	
	protected void initialize() {
		BufferedImage img = UtilImage.read("/game5/main/test2/img.jpg");
		shape = new ShapeImg(p1(100,100), 80, 100, img);
	}

	protected void turn() {
		shape.getAnchor().addX(getCount()%200<100 ? 1 : -1);
	}
	
	protected Draw buildDraw() {
		return shape;
	}
}
