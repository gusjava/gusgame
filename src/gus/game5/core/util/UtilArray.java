package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.f.F;

public class UtilArray {
	
	/*
	 * HAS
	 */
	
	public static boolean has(int[][] data, int i, int j) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y;
	}
	
	public static boolean has(int[][] data, int[] pos) {
		return has(data, pos[0], pos[1]);
	}
	
	/*
	 * IS
	 */
	
	public static boolean is(int[][] data, int i, int j, int val) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y && data[i][j]==val;
	}
	
	public static boolean is(int[][] data, int[] pos, int val) {
		return is(data, pos[0], pos[1], val);
	}
	
	/*
	 * GET
	 */
	
	public static Integer get(int[][] data, int i, int j) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		if(i<0 || i>=x) return null;
		if(j<0 || j>=y) return null;
		return data[i][j];
	}
	
	public static Integer get(int[][] data, int[] pos) {
		return get(data, pos[0], pos[1]);
	}
	
	/*
	 * NEIGHBOR
	 */
	
	public static Integer neighbor(int[][] data, int i, int j, int di, int dj) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		int i1 = i+di;
		int j1 = j+dj;
		if(i1<0 || i1>=x) return null;
		if(j1<0 || j1>=y) return null;
		return data[i1][j1];
	}
	
	public static Integer neighbor(int[][] data, int[] pos, int di, int dj) {
		return neighbor(data, pos[0], pos[1], di, dj);
	}
	
	/*
	 * NEIGHBOR POS
	 */
	
	public static int[] neighborPos(int[][] data, int i, int j, int di, int dj) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		int i1 = i+di;
		int j1 = j+dj;
		if(i1<0 || i1>=x) return null;
		if(j1<0 || j1>=y) return null;
		return new int[] {i1,j1};
	}
	
	public static int[] neighborPos(int[][] data, int[] pos, int di, int dj) {
		return neighborPos(data, pos[0], pos[1], di, dj);
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
	 * FIND ALL : INT[]
	 */
	
	public static List<int[]> findAll(int[] data, int value) {
		int x = data.length;
		if(x==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++)
			if(data[i] == value) found.add(new int[] {i});
		return found;
	}
	
	public static List<int[]> findAll(int[] data, F<int[]> f) {
		int x = data.length;
		if(x==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) {
			int[] p = new int[] {i};
			if(f.f(p)) found.add(p);
		}
		return found;
	}
	
	/*
	 * FIND ALL : INT[][]
	 */
	
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
	
	public static List<int[]> findAll(int[][] data, F<int[]> f) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			int[] p = new int[] {i,j};
			if(f.f(p)) found.add(p);
		}
		return found;
	}
	
	/*
	 * FIND ALL : INT[][][]
	 */
	
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
	
	public static List<int[]> findAll(int[][][] data, F<int[]> f) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		int z = data[0][0].length;
		if(z==0) return null;
		
		List<int[]> found = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++) {
			int[] p = new int[] {i,j,k};
			if(f.f(p)) found.add(p);
		}
		return found;
	}
	
	/*
	 * COUNT : INT[][]
	 */
	
	public static int count(int[][] data, int value) {
		int x = data.length;
		if(x==0) return 0;
		int y = data[0].length;
		if(y==0) return 0;
		
		int count = 0;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) count++;
		return count;
	}
	
	public static int count(int[][] data, F<int[]> f) {
		int x = data.length;
		if(x==0) return 0;
		int y = data[0].length;
		if(y==0) return 0;

		int count = 0;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			int[] p = new int[] {i,j};
			if(f.f(p)) count++;
		}
		return count;
	}
	
	/*
	 * ANY : INT[]
	 */
	
	public static boolean any(int[] data, int value) {
		int x = data.length;
		if(x==0) return false;
		
		for(int i=0;i<x;i++)
			if(data[i] == value) return true;
		return false;
	}
	
	/*
	 * ANY : INT[][]
	 */
	
	public static boolean any(int[][] data, int value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			if(data[i][j] == value) return true;
		return false;
	}
	
	/*
	 * ANY : INT[][][]
	 */
	
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
