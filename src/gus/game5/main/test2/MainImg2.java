package gus.game5.main.test2;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gus.game5.core.game.Game2;
import gus.game5.core.game.Settings;
import gus.game5.core.point.point2.Point2Dxy;
import gus.game5.core.shape.Shape;
import gus.game5.core.shape.Shape0.AnchorType;
import gus.game5.core.util.UtilImage;

public class MainImg2 extends Game2 {

	public static void main(String[] args) {
		MainImg2 main = new MainImg2();
		main.displayInWindows();
		main.start();
	}
	
	protected void initSettings(Settings s) {
		s.setTitle("Bonhomme sur roulettes (2)");
		s.setBackground(Color.WHITE);
		s.setSize(400, 250);
	}

	protected void initialize1() {
		BufferedImage img = UtilImage.read("/game5/main/test2/img.jpg");
		Shape shape = newShapeImg(p1(100,100), 80, 100, AnchorType.NE, img);
		shape.getAnchor().setDerived(new Point2Dxy(()->getCount()%200<100 ? 1 : -1, ()->0));
//		shape.debugFrame(Color.RED);
//		shape.debugOrigin(Color.BLUE);
//		shape.debugAnchor(Color.GREEN);
	}
}
