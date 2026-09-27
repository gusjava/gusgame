package gus.game5.core.util;

public class UtilArrayBoolean {
	
	/*
	 * IS 1
	 */
	
	public static boolean is1(boolean[] data, int[] pos, boolean val) {
		return is1(data, pos[0], val);
	}
	
	public static boolean is1(boolean[] data, int i, boolean val) {
		int x = data.length;
		if(x==0) return false;
		
		return i>=0 && i<x && data[i]==val;
	}
	
	/*
	 * IS 2
	 */
	
	public static boolean is2(boolean[][] data, int[] pos, boolean val) {
		return is2(data, pos[0], pos[1], val);
	}
	
	public static boolean is2(boolean[][] data, int i, int j, boolean val) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y && data[i][j]==val;
	}
	
	/*
	 * IS 3
	 */
	
	public static boolean is3(boolean[][][] data, int[] pos, boolean val) {
		return is3(data, pos[0], pos[1], pos[2], val);
	}
	
	public static boolean is3(boolean[][][] data, int i, int j, int k, boolean val) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		int z = data[0][0].length;
		if(z==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y && k>=0 && k<z && data[i][j][k]==val;
	}
	
	/*
	 * BUILD 1
	 */

	public static boolean[] build1(int x, boolean value) {
		boolean[] data = new boolean[x];
		for(int i=0;i<x;i++) data[i] = value;
		return data;
	}
	
	/*
	 * BUILD 2
	 */

	public static boolean[][] build2(int x, int y, boolean value) {
		boolean[][] data = new boolean[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) data[i][j] = value;
		return data;
	}
	
	public static boolean[][] build2(int x, boolean value) {
		return build2(x,x,value);
	}
	
	/*
	 * BUILD 3
	 */

	public static boolean[][][] build3(int x, int y, int z, boolean value) {
		boolean[][][] data = new boolean[x][y][z];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++) data[i][j][k] = value;
		return data;
	}
	
	public static boolean[][][] build3(int x, boolean value) {
		return build3(x,x,x,value);
	}
	
	/*
	 * CLONE 1
	 */
	
	public static boolean[] clone1(boolean[] data) {
		int x = data.length;
		if(x==0) return new boolean[0];
		
		boolean[] newData = new boolean[x];
		for(int i=0;i<x;i++)
			newData[i] = data[i];
		return newData;
	}
	
	/*
	 * CLONE 2
	 */
	
	public static boolean[][] clone2(boolean[][] data) {
		int x = data.length;
		if(x==0) return new boolean[0][0];
		int y = data[0].length;
		if(y==0) return new boolean[0][0];
		
		boolean[][] newData = new boolean[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			newData[i][j] = data[i][j];
		return newData;
	}
	
	/*
	 * CLONE 3
	 */
	
	public static boolean[][][] clone3(boolean[][][] data) {
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
	 * ANY 1
	 */
	
	public static boolean any1(boolean[] data, boolean value) {
		int x = data.length;
		if(x==0) return false;
		
		for(int i=0;i<x;i++)
			if(data[i] == value) return true;
		return false;
	}
	
	/*
	 * ANY 2
	 */
	
	public static boolean any2(boolean[][] data, boolean value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return true;
		return false;
	}
	
	/*
	 * ANY 3
	 */
	
	public static boolean any3(boolean[][][] data, boolean value) {
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
	 * ALL 1
	 */
	
	public static boolean all1(boolean[] data, boolean value) {
		if(data.length==0) return true;
		int x = data.length;
		for(int i=0;i<x;i++)
			if(data[i] != value) return false;
		return true;
	}
	
	/*
	 * ALL 2
	 */
	
	public static boolean all2(boolean[][] data, boolean value) {
		if(data.length==0) return true;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] != value) return false;
		return true;
	}
	
	/*
	 * ALL 3
	 */
	
	public static boolean all3(boolean[][][] data, boolean value) {
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
	 * NONE 1
	 */
	
	public static boolean none1(boolean[] data, boolean value) {
		return !any1(data, value);
	}
	
	/*
	 * NONE 2
	 */
	
	public static boolean none2(boolean[][] data, boolean value) {
		return !any2(data, value);
	}
	
	/*
	 * NONE 3
	 */
	
	public static boolean none3(boolean[][][] data, boolean value) {
		return !any3(data, value);
	}
}
