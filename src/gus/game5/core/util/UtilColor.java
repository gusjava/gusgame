package gus.game5.core.util;

import java.awt.Color;

public class UtilColor {

	public static Color blend(Color a, Color b, double t) {
		int r = (int) (a.getRed()+(b.getRed()-a.getRed())*t);
		int g = (int) (a.getGreen()+(b.getGreen()-a.getGreen())*t);
		int bl = (int) (a.getBlue()+(b.getBlue()-a.getBlue())*t);
		return new Color(r,g,bl);
	}
}
