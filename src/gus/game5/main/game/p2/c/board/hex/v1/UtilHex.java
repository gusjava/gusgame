package gus.game5.main.game.p2.c.board.hex.v1;

import java.util.List;

import gus.game5.core.util.UtilArrayBoolean;
import gus.game5.core.util.UtilArrayInt;
import gus.game5.core.util.UtilRandom;

public class UtilHex {
	
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;

	public static int[] randomPlay(int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(data));
	}
	
	public static List<int[]> findPossiblePlays(int[][] data) {
		return UtilArrayInt.findAll(data, EMPTY);
	}
	
	public static int[] findPlay(int[][] data, int[] p) {
		return data[p[0]][p[1]]==EMPTY ? p : null;
	}
	
	public static int searchWinner(int[][] data) {
		int x = data.length;
		int y = data[0].length;
		
		boolean[][] b = UtilArrayBoolean.build2(x, y, false);
		for(int i=0;i<x;i++) {
			if(isConnectedToLeftRed(x, y, data, b, i, y-1)) return RED;
		}
		b = UtilArrayBoolean.build2(x, y, false);
		for(int j=0;j<y;j++) {
			if(isConnectedToTopBlue(x, y, data, b, x-1, j)) return BLUE;
		}
		if(UtilArrayInt.any(data, EMPTY)) return -1;
		return EMPTY;
	}
	
	public static int oppositeValue(int value) {
		return value==RED ? BLUE : value==BLUE ? RED : value;
	}
	
	private static boolean isConnectedToLeftRed(int x, int y, int[][] data, boolean[][] b, int i, int j) {
		if(i<0 || i>=x) return false;
		if(j<0 || j>=y) return false;
		if(b[i][j]) return false;
		b[i][j] = true;
		if(data[i][j]!=RED) return false;
		
		if(j==0) return true;
		
		if(isConnectedToLeftRed(x, y, data, b, i-1, j)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i-1, j-1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i, j-1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i, j+1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i+1, j)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i+1, j+1)) return true;
		return false;
	}
	
	private static boolean isConnectedToTopBlue(int x, int y, int[][] data, boolean[][] b, int i, int j) {
		if(i<0 || i>=x) return false;
		if(j<0 || j>=y) return false;
		if(b[i][j]) return false;
		b[i][j] = true;
		if(data[i][j]!=BLUE) return false;
		
		if(i==0) return true;
		
		if(isConnectedToTopBlue(x, y, data, b, i-1, j)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i-1, j-1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i, j-1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i, j+1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i+1, j)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i+1, j+1)) return true;
		return false;
	}
}
