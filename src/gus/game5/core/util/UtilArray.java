package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gus.game5.core.features.f.F;
import gus.game5.core.features.g.GDouble;

public class UtilArray {
	
	/*
	 * HAS
	 */
	
	public static boolean has(int[][] data, int[] pos) {
		return has(data, pos[0], pos[1]);
	}
	
	public static boolean has(int[][] data, int i, int j) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y;
	}
	
	/*
	 * IS
	 */
	
	public static boolean is(int[][] data, int[] pos, int val) {
		return is(data, pos[0], pos[1], val);
	}
	
	public static boolean is(int[][] data, int i, int j, int val) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		return i>=0 && i<x && j>=0 && j<y && data[i][j]==val;
	}
	
	/*
	 * GET
	 */
	
	public static Integer get(int[][] data, int[] pos) {
		return get(data, pos[0], pos[1]);
	}
	
	public static Integer get(int[][] data, int i, int j) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		if(i<0 || i>=x) return null;
		if(j<0 || j>=y) return null;
		return data[i][j];
	}
	
	/*
	 * SET
	 */
	
	public static boolean set(int[][] data, int[] pos, int value) {
		return set(data, pos[0], pos[1], value);
	}
	
	public static boolean set(int[][] data, int i, int j, int value) {
		int x = data.length;
		if(x==0) return false;
		int y = data[0].length;
		if(y==0) return false;
		
		if(i<0 || i>=x) return false;
		if(j<0 || j>=y) return false;
		
		data[i][j] = value;
		return true;
	}
	
	/*
	 * NEIGHBOR VALUE
	 */
	
	public static Integer neighborValue(int[][] data, int[] pos, int di, int dj) {
		return neighborValue(data, pos[0], pos[1], di, dj);
	}
	
	public static Integer neighborValue(int[][] data, int i, int j, int di, int dj) {
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
	
	/*
	 * NEIGHBOR VALUE 4
	 */
	
	public static List<Integer> neighbor4Value(int[][] data, int[] pos) {
		return neighbor4Value(data, pos[0], pos[1]);
	}
	
	public static List<Integer> neighbor4Value(int[][] data, int i, int j) {
		List<Integer> list = new ArrayList<>();
		
		Integer v1 = neighborValue(data, i,  j, -1, 0);
		Integer v2 = neighborValue(data, i,  j, 1, 0);
		Integer v3 = neighborValue(data, i,  j, 0, -1);
		Integer v4 = neighborValue(data, i,  j, 0, 1);
		
		return UtilList.addAllNotNull(list, v1, v2, v3, v4);
	}
	
	/*
	 * NEIGHBOR VALUE 8
	 */
	
	public static List<Integer> neighbor8Value(int[][] data, int[] pos) {
		return neighbor8Value(data, pos[0], pos[1]);
	}
	
	public static List<Integer> neighbor8Value(int[][] data, int i, int j) {
		List<Integer> list = new ArrayList<>();
		
		Integer v1 = neighborValue(data, i,  j, -1, -1);
		Integer v2 = neighborValue(data, i,  j, -1, 1);
		Integer v3 = neighborValue(data, i,  j, -1, 0);
		Integer v4 = neighborValue(data, i,  j, 1, -1);
		Integer v5 = neighborValue(data, i,  j, 1, 1);
		Integer v6 = neighborValue(data, i,  j, 1, 0);
		Integer v7 = neighborValue(data, i,  j, 0, -1);
		Integer v8 = neighborValue(data, i,  j, 0, 1);
		
		return UtilList.addAllNotNull(list, v1, v2, v3, v4, v5, v6, v7, v8);
	}
	
	/*
	 * NEIGHBOR POS
	 */
	
	public static int[] neighborPos(int[][] data, int[] pos, int di, int dj) {
		return neighborPos(data, pos[0], pos[1], di, dj);
	}
	
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
	
	/*
	 * NEIGHBOR POS 4
	 */
	
	public static List<int[]> neighbor4Pos(int[][] data, int[] pos) {
		return neighbor4Pos(data, pos[0], pos[1]);
	}
	
	public static List<int[]> neighbor4Pos(int[][] data, int i, int j) {
		List<int[]> list = new ArrayList<>();
		
		int[] n1 = neighborPos(data, i,  j, -1, 0);
		int[] n2 = neighborPos(data, i,  j, 1, 0);
		int[] n3 = neighborPos(data, i,  j, 0, -1);
		int[] n4 = neighborPos(data, i,  j, 0, 1);
		
		return UtilList.addAllNotNull(list, n1, n2, n3, n4);
	}
	
	/*
	 * NEIGHBOR POS 8
	 */
	
	public static List<int[]> neighbor8Pos(int[][] data, int[] pos) {
		return neighbor8Pos(data, pos[0], pos[1]);
	}
	
	public static List<int[]> neighbor8Pos(int[][] data, int i, int j) {
		List<int[]> list = new ArrayList<>();
		
		int[] n1 = neighborPos(data, i,  j, -1, -1);
		int[] n2 = neighborPos(data, i,  j, -1, 1);
		int[] n3 = neighborPos(data, i,  j, -1, 0);
		int[] n4 = neighborPos(data, i,  j, 1, -1);
		int[] n5 = neighborPos(data, i,  j, 1, 1);
		int[] n6 = neighborPos(data, i,  j, 1, 0);
		int[] n7 = neighborPos(data, i,  j, 0, -1);
		int[] n8 = neighborPos(data, i,  j, 0, 1);
		
		return UtilList.addAllNotNull(list, n1, n2, n3, n4, n5, n6, n7, n8);
	}
	
	/*
	 * DISTANCE
	 */
	
	public static int distance(int[] p1, int[] p2) {
		return Math.abs(p1[0]-p2[0]) + Math.abs(p1[1]-p2[1]);
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
	 * COLLECT
	 */
	
	public static double[] collectDouble2(GDouble... dd) {
		double[] data = new double[dd.length];
		for(int i=0;i<data.length;i++) data[i] = dd[i].gDouble();
		return data;
	}
	
	public static double[] collectDouble2(List<Double> list) {
		double[] data = new double[list.size()];
		for(int i=0;i<data.length;i++) data[i] = list.get(i);
		return data;
	}
	
	public static double[] collectDouble2(Set<Double> set) {
		double[] data = new double[set.size()];
		Iterator<Double> it = set.iterator();
		for(int i=0;i<data.length;i++) data[i] = it.next();
		return data;
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
	
	/*
	 * TO STRING
	 */
	
	public static String toString(int[] data) {
		StringBuffer b = new StringBuffer();
		for(int i=0;i<data.length;i++) {
			b.append(data[i]);
			if(i<data.length-1) b.append(",");
		}
		return b.toString();
	}
}
