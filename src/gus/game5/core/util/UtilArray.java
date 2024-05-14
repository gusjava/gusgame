package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

public class UtilArray {
	
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
	 * CLONE
	 */
	
	public static int[] clone(int[] data) {
		int x = data.length;
		if(x==0) return new int[0];
		
		int[] newData = new int[x];
		for(int i=0;i<x;i++)
			newData[i] = data[i];
		return newData;
	}
	
	public static int[][] clone(int[][] data) {
		int x = data.length;
		if(x==0) return new int[0][0];
		int y = data[0].length;
		if(y==0) return new int[0][0];
		
		int[][] newData = new int[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			newData[i][j] = data[i][j];
		return newData;
	}
	
	public static int[][][] clone(int[][][] data) {
		int x = data.length;
		if(x==0) return new int[0][0][0];
		int y = data[0].length;
		if(y==0) return new int[0][0][0];
		int z = data[0][0].length;
		if(z==0) return new int[0][0][0];
		
		int[][][] newData = new int[x][y][z];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			newData[i][j][k] = data[i][j][k];
		return newData;
	}
	
	/*
	 * FIND
	 */
	
	public static int[] find(int[] data, int value) {
		int x = data.length;
		if(x==0) return null;
		
		for(int i=0;i<x;i++) 
			if(data[i] == value) return new int[] {i};
		return null;
	}
	
	public static int[] find(int[][] data, int value) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return new int[] {i,j};
		return null;
	}
	
	public static int[] find(int[][][] data, int value) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		int z = data[0][0].length;
		if(z==0) return null;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			if(data[i][j][k] == value) return new int[] {i,j,k};
		return null;
	}
	
	/*
	 * FIND ALL
	 */
	
	public static List<int[]> findAll(int[] data, int value) {
		int x = data.length;
		if(x==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++)
			if(data[i] == value) found.add(new int[] {i});
		return found;
	}
	
	public static List<int[]> findAll(int[][] data, int value) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) found.add(new int[] {i,j});
		return found;
	}
	
	public static List<int[]> findAll(int[][][] data, int value) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		int z = data[0][0].length;
		if(z==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++)
			if(data[i][j][k] == value) found.add(new int[] {i,j,k});
		return found;
	}
	
	/*
	 * ANY
	 */
	
	public static boolean any(int[] data, int value) {
		int x = data.length;
		if(x==0) return false;
		
		for(int i=0;i<x;i++)
			if(data[i] == value) return true;
		return false;
	}
	
	public static boolean any(int[][] data, int value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return true;
		return false;
	}
	
	public static boolean any(int[][][] data, int value) {
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
	 * ALL
	 */
	
	public static boolean all(int[] data, int value) {
		if(data.length==0) return true;
		int x = data.length;
		for(int i=0;i<x;i++)
			if(data[i] != value) return false;
		return true;
	}
	
	public static boolean all(int[][] data, int value) {
		if(data.length==0) return true;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] != value) return false;
		return true;
	}
	
	public static boolean all(int[][][] data, int value) {
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
	 * NONE
	 */
	
	public static boolean none(int[] data, int value) {
		return !any(data, value);
	}
	
	public static boolean none(int[][] data, int value) {
		return !any(data, value);
	}
	
	public static boolean none(int[][][] data, int value) {
		return !any(data, value);
	}
}
