package gus.game5.main.game.p1.c.puyo;

import java.awt.Color;
import java.util.Random;

public enum Puyo {

	RED(new Color(230,58,58)),
	GREEN(new Color(68,200,80)),
	BLUE(new Color(62,126,242)),
	YELLOW(new Color(240,206,52));

	private static final Random RANDOM = new Random();

	private Color color;

	private Puyo(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public static Puyo random() {
		return values()[RANDOM.nextInt(values().length)];
	}

	public static Puyo[] randomPair() {
		return new Puyo[]{random(), random()};
	}
}
