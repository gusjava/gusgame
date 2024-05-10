package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

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
	
	/*
	 * FIND ALL
	 */
	
	public static List<int[]> findAll(int[][] data, int value) {
		if(data.length==0) return null;
		int x = data.length;
		int y = data[0].length;
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) found.add(new int[] {i,j});
		return found;
	}
	
	/*
	 * ANY
	 */
	
	public static boolean any(int[][] data, int value) {
		if(data.length==0) return false;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return true;
		return false;
	}
	
	/*
	 * ALL
	 */
	
	public static boolean all(int[][] data, int value) {
		if(data.length==0) return true;
		int x = data.length;
		int y = data[0].length;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] != value) return false;
		return true;
	}
	
	/*
	 * NONE
	 */
	
	public static boolean none(int[][] data, int value) {
		return !any(data, value);
	}
}
