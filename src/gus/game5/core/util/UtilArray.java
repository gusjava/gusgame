package gus.game5.core.util;

public class UtilArray {
	
	/*
	 * GENERATE
	 */

	public static boolean[] boolArray(int x, boolean value) {
		boolean[] data = new boolean[x];
		for(int i=0;i<x;i++) data[i] = value;
		return data;
	}

	public static boolean[][] boolArray2(int x, int y, boolean value) {
		boolean[][] data = new boolean[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) data[i][j] = value;
		return data;
	}
	
	public static boolean[][] boolArray2(int x, boolean value) {
		return boolArray2(x,x,value);
	}
	
	/*
	 * CLONE
	 */
	
	public static int[][] clone(int[][] data) {
		if(data.length==0) return new int[0][0];
		int x = data.length;
		int y = data[0].length;
		int[][] newData = new int[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			newData[i][j] = data[i][j];
		return newData;
	}
	
	/*
	 * FIND
	 */
	
	public static int[] find(int[][] data, int value) {
		if(data.length==0) return null;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return new int[] {i,j};
		return null;
	}
}
