package gus.game5.main.game.p1.c.mastermind;

import java.awt.Color;

public class Palette {

	public static final Color WINDOW = new Color(0x263238);
	public static final Color BOARD = new Color(0x37474F);
	public static final Color ROW_ACTIVE = new Color(0x455A64);
	public static final Color EMPTY = new Color(0x546E7A);
	public static final Color INDIC_EMPTY = new Color(0x455A64);
	public static final Color INDIC_BLACK = new Color(0x111111);
	public static final Color INDIC_WHITE = new Color(0xECEFF1);
	public static final Color LIGHT_TEXT = new Color(0xECEFF1);
	public static final Color LABEL = new Color(0xB0BEC5);
	public static final Color HIGHLIGHT = new Color(0x43A047);
	public static final Color LOSE = new Color(0xE53935);
	public static final Color VEIL = new Color(38, 50, 56, 190);

	private static final Color[] PEGS = {
		new Color(0xE53935), new Color(0xFB8C00), new Color(0xFDD835),
		new Color(0x43A047), new Color(0x1E88E5), new Color(0x8E24AA)};

	public static Color peg(int color) {
		return PEGS[color];
	}
}
