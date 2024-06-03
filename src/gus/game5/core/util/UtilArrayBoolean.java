package gus.game5.core.util;

public class UtilArrayBoolean {

	
	/*
	 * IS
	 */
	
	public static boolean is(boolean[][] data, int[] pos, boolean val) {
		return is(data, pos[0], pos[1], val);
	}
	
	public static boolean is(boolean[][] data, int i, int j, boolean val) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y && data[i][j]==val;
	}
	
	/*
	 * GENERATE BOOL ARRAY
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

	public static boolean[][][] boolArray3(int x, int y, int z, boolean value) {
		boolean[][][] data = new boolean[x][y][z];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++) data[i][j][k] = value;
		return data;
	}
	
	public static boolean[][][] boolArray3(int x, boolean value) {
		return boolArray3(x,x,x,value);
	}
	
	/*
	 * CLONE BOOLEAN ARRAY
	 */
	
	public static boolean[] clone(boolean[] data) {
		int x = data.length;
		if(x==0) return new boolean[0];
		
		boolean[] newData = new boolean[x];
		for(int i=0;i<x;i++)
			newData[i] = data[i];
		return newData;
	}
	
	public static boolean[][] clone(boolean[][] data) {
		int x = data.length;
		if(x==0) return new boolean[0][0];
		int y = data[0].length;
		if(y==0) return new boolean[0][0];
		
		boolean[][] newData = new boolean[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			newData[i][j] = data[i][j];
		return newData;
	}
	
	public static boolean[][][] clone(boolean[][][] data) {
		int x = data.length;
		if(x==0) return new boolean[0][0][0];
		int y = data[0].length;
		if(y==0) return new boolean[0][0][0];
		int z = data[0][0].length;
		if(z==0) return new boolean[0][0][0];
		
		boolean[][][] newData = new boolean[x][y][z];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			newData[i][j][k] = data[i][j][k];
		return newData;
	}
	
	/*
	 * ANY : BOOLEAN[], BOOLEAN[][], BOOLEAN[][][]
	 */
	
	public static boolean any(boolean[] data, boolean value) {
		int x = data.length;
		if(x==0) return false;
		
		for(int i=0;i<x;i++)
			if(data[i] == value) return true;
		return false;
	}
	
	public static boolean any(boolean[][] data, boolean value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return true;
		return false;
	}
	
	public static boolean any(boolean[][][] data, boolean value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		int z = data[0][0].length;
		if(z==0) return false;

		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			if(data[i][j][k] == value) return true;
		return false;
	}
	
	/*
	 * ALL : BOOLEAN[],BOOLEAN[][], BOOLEAN[][][]
	 */
	
	public static boolean all(boolean[] data, boolean value) {
		if(data.length==0) return true;
		int x = data.length;
		for(int i=0;i<x;i++)
			if(data[i] != value) return false;
		return true;
	}
	
	public static boolean all(boolean[][] data, boolean value) {
		if(data.length==0) return true;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] != value) return false;
		return true;
	}
	
	public static boolean all(boolean[][][] data, boolean value) {
		int x = data.length;
		if(x==0) return true;
		int y = data[0].length;
		if(y==0) return true;
		int z = data[0][0].length;
		if(z==0) return true;

		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			if(data[i][j][k] != value) return false;
		return true;
	}
	
	/*
	 * NONE : BOOLEAN[],BOOLEAN[][], BOOLEAN[][][]
	 */
	
	public static boolean none(boolean[] data, boolean value) {
		return !any(data, value);
	}
	
	public static boolean none(boolean[][] data, boolean value) {
		return !any(data, value);
	}
	
	public static boolean none(boolean[][][] data, boolean value) {
		return !any(data, value);
	}
}
