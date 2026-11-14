package gus.game5.main.game.p1.c.drmario;

import java.awt.Color;
import java.util.Random;

public enum GColor {

	RED(new Color(228,52,52)),
	YELLOW(new Color(238,222,64)),
	BLUE(new Color(64,128,240));

	private static final Random RANDOM = new Random();

	private Color color;

	private GColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public static GColor random() {
		return values()[RANDOM.nextInt(values().length)];
	}

	public static GColor[] randomPair() {
		return new GColor[] {random(), random()};
	}
}
