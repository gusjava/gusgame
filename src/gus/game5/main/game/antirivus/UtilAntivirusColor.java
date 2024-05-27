package gus.game5.main.game.antirivus;

import java.awt.Color;

public class UtilAntivirusColor {

	public static final Color LIGHT_GRAY = Color.LIGHT_GRAY; //EDGES, EMPTY
	public static final Color WHITE = Color.WHITE; // BLOCKED

	public static final Color RED = Color.RED;
	public static final Color BLUE = new Color(0,153,255);
	public static final Color ORANGE = new Color(255,153,51);
	public static final Color PINK = new Color(255,51,153);
	public static final Color DARK_GREEN = Color.GREEN.darker();
	public static final Color DARK_BLUE = Color.BLUE.darker();
	public static final Color VIOLET = Color.MAGENTA.darker();
	public static final Color GREEN = new Color(153,255,102);
	public static final Color YELLOW = Color.YELLOW;
	
	public static final Color[] COLORS = new Color[] {
			RED,
			BLUE,
			ORANGE,
			PINK,
			DARK_GREEN,
			DARK_BLUE,
			VIOLET,
			GREEN,
			YELLOW
	};
	
	public static Color getLevelColor(int level) {
		if(level<=12) return GREEN;// STARTER
		if(level<=24) return ORANGE;// JUNIOR
		if(level<=36) return RED;// EXPERT
		if(level<=48) return VIOLET;// MASTER
		return DARK_BLUE; //WIZARD
	}
	
	public static Color getValueColor(int value) {
		if(value==UtilAntivirus.EMPTY) return LIGHT_GRAY;
		if(value==UtilAntivirus.BLOCKED) return WHITE;
		return COLORS[value];
	}
}
