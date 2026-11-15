package gus.game5.main.game.p1.c.flowfree;

import java.awt.Color;

public class UtilFlowFree {

	public static final int CELL = 60;
	public static final int MAX_ROWS = 8;
	public static final int MAX_COLS = 8;

	public static final String[][] LEVELS = {
		{
			"A...A",
			".....",
			"B...B",
			".....",
			"C...C"
		},
		{
			"A..A..",
			".....D",
			"B..B..",
			"......",
			"C..C..",
			".....D"
		},
		{
			"A...A..",
			"......D",
			"B...B..",
			".......",
			"C...C..",
			"......D",
			"E...E.."
		},
		{
			"A....A..",
			".......E",
			"B....B..",
			"......F.",
			"C....C..",
			"......F.",
			"D....D..",
			".......E"
		}
	};

	public static final Color[] COLORS = {
		new Color(220,60,60),
		new Color(60,120,220),
		new Color(60,170,90),
		new Color(230,150,40),
		new Color(150,80,190),
		new Color(50,180,190)
	};

	public static Color colorOf(char letter) {
		return COLORS[letter-'A'];
	}
}
