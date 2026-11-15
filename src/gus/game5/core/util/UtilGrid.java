package gus.game5.core.util;

import gus.game5.core.point.point0.Point0;

public class UtilGrid {

	public static int[] cellAt(Point0 p, double gap, double top, double cell, int rows, int cols) {
		if(p==null) return null;
		int c = (int) Math.floor((p.getX()-gap)/cell);
		int r = (int) Math.floor((p.getY()-top)/cell);
		if(r<0 || r>=rows || c<0 || c>=cols) return null;
		return new int[] {r,c};
	}

	public static boolean same(int[] a, int[] b) {
		return a[0]==b[0] && a[1]==b[1];
	}
}
