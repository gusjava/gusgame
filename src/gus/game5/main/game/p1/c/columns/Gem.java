package gus.game5.main.game.p1.c.columns;

import java.awt.Color;
import java.util.Random;

public enum Gem {

	RED(new Color(228,52,52)),
	ORANGE(new Color(240,140,32)),
	YELLOW(new Color(238,222,64)),
	GREEN(new Color(64,204,84)),
	BLUE(new Color(64,128,240)),
	PURPLE(new Color(184,84,228));

	private static final Random RANDOM = new Random();

	private Color color;

	private Gem(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public static Gem random() {
		return values()[RANDOM.nextInt(values().length)];
	}

	public static Gem[] randomColumn() {
		Gem[] gems = new Gem[Column.SIZE];
		for(int i=0;i<gems.length;i++) gems[i] = random();
		return gems;
	}
}
