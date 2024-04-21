package gus.game5.main.test2;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.draw.Draw;
import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game;
import gus.game5.core.game.Settings;
import gus.game5.core.shape.ShapeImg;
import gus.game5.core.util.UtilImage;

public class MainDraws extends Game {

	public static void main(String[] args) {
		MainDraws main = new MainDraws();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Draws");
		s.setBackground(Color.WHITE);
		s.setSize(1000, 800);
	}
	
	private Drawing1 drawing = new Drawing1() {
		protected void draw() {
			for(int i=1;i<10;i++) {
				double thickness = i*0.5;
				drawThickLine(p(100,200+i*20), p(150,250+i*20), thickness);
				drawString(p(160,250+i*20), ""+thickness);
			}
		}
	};
	
	protected void initialize() {
	}

	protected void turn() {
	}
	
	protected Draw buildDraw() {
		return drawing;
	}
}
