package gus.game5.core.util;

public class UtilArray {
	
	/*
	 * GENERATE
	 */

	public static boolean[][] boolArray2(int x, int y, boolean value) {
		boolean[][] data = new boolean[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) data[i][j] = value;
		return data;
	}
	
	public static boolean[][] boolArray2(int x, boolean value) {
		return boolArray2(x,x,value);
	}
	
	/*
	 * FIND
	 */
	
	public static int[] find(int[][] array, int value) {
		if(array.length==0) return null;
		for(int i=0;i<array.length;i++) for(int j=0;j<array[0].length;j++) 
			if(array[i][j] == value) return new int[] {i,j};
		return null;
	}
}
