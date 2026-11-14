package gus.game5.main.game.p1.c.game2048;

import java.awt.Color;

public class Palette {

	public static final Color WINDOW = new Color(0xFAF8EF);
	public static final Color BOARD = new Color(0xBBADA0);
	public static final Color EMPTY = new Color(0xCDC1B4);
	public static final Color DARK_TEXT = new Color(0x776E65);
	public static final Color LIGHT_TEXT = new Color(0xF9F6F2);
	public static final Color LABEL = new Color(0xEEE4DA);
	public static final Color HIGHLIGHT = new Color(0xF65E3B);
	public static final Color VEIL = new Color(238, 228, 218, 150);
	public static final Color BEYOND = new Color(0x3C3A32);

	private static final int[] VALUES = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};

	private static final Color[] COLORS = {
		new Color(0xEEE4DA), new Color(0xEDE0C8), new Color(0xF2B179), new Color(0xF59563),
		new Color(0xF67C5F), new Color(0xF65E3B), new Color(0xEDCF72), new Color(0xEDCC61),
		new Color(0xEDC850), new Color(0xEDC53F), new Color(0xEDC22E)};

	public static Color tile(int value) {
		for(int i=0;i<VALUES.length;i++) if(VALUES[i]==value) return COLORS[i];
		return BEYOND;
	}

	public static Color text(int value) {
		return value<=4 ? DARK_TEXT : LIGHT_TEXT;
	}

	public static int fontSize(int value) {
		if(value<100) return 42;
		if(value<1000) return 34;
		if(value<10000) return 28;
		return 22;
	}
}
