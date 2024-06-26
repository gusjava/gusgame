package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.f.F;

public class UtilArrayInt {
	
	/*
	 * RETURN BOOLEAN
	 */
	
	/*
	 * has : boolean (2)
	 * is : boolean (2)
	 * eq : boolean (4)
	 * any : boolean (3)
	 * all : boolean (3)
	 * none : boolean (3)
	 */

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
	 * EQ
	 */
	
	public static boolean eq(int[] data, int i) {
		return data.length==1 && data[0]==i;
	}
	
	public static boolean eq(int[] data, int i, int j) {
		return data.length==2 && data[0]==i && data[1]==j;
	}
	
	public static boolean eq(int[] data, int i, int j, int k) {
		return data.length==3 && data[0]==i && data[1]==j && data[2]==k;
	}
	
	public static boolean eq(int[] data, int i, int j, int k, int l) {
		return data.length==4 && data[0]==i && data[1]==j && data[2]==k && data[3]==l;
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
	
	/*
	 * get : Integer (2)
	 * set (2)
	 * neighborValue : Integer (2)
	 * neighbor4Value : List<Integer> (2)
	 * neighbor8Value : List<Integer> (2)
	 * neighborPos : int[] (2)
	 * neighbor4Pos : List<int[]> (2)
	 * neighbor8Pos : List<int[]> (2)
	 * distance : int (� migrer dans UtilMath)
	 * build1 : int[]
	 * build2 : int[][] (2)
	 * build3 : int[][][] (2)
	 * random2 : int[][]
	 * clone1 : int[]
	 * clone2 : int[][]
	 * clone3 : int[][][]
	 * find : int[] (3)
	 * findAll : List<int[]> (6)
	 * count : int (2)
	 * lenToXY : int[]
	 * perimeter : int
	 */
	
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
	 * BUILD
	 */

	public static int[] build1(int x, int value) {
		int[] data = new int[x];
		for(int i=0;i<x;i++) data[i] = value;
		return data;
	}

	public static int[][] build2(int x, int y, int value) {
		int[][] data = new int[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) data[i][j] = value;
		return data;
	}
	
	public static int[][] build2(int x, int value) {
		return build2(x,x,value);
	}

	public static int[][][] build3(int x, int y, int z, int value) {
		int[][][] data = new int[x][y][z];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) for(int k=0;k<z;k++) data[i][j][k] = value;
		return data;
	}
	
	public static int[][][] build3(int x, int value) {
		return build3(x,x,x,value);
	}
	
	/*
	 * RANDOM
	 */

	public static int[][] random2(int x, int y, int[][] distribution) {
		int[][] data = new int[x][y];
		int total = x*y;
		
		int n = 0;
		for(int i=0;i<distribution.length;i++) {
			n+=distribution[i][0];
		}
		if(total!=n) throw new TechnicalException("Invalid distribution total number: "+n);
		
		List<Integer> l = new ArrayList<>();
		for(int i=0;i<x*y;i++) l.add(i);
		
		for(int i=0;i<distribution.length;i++) {
			int nb = distribution[i][0];
			int val = distribution[i][1];
			
			for(int j=0;j<nb;j++) {
				int index = UtilRandom.pickRandomElement(l);
				int[] pos = lenToXY(data, index);
				set(data, pos, val);
			}
		}
		return data;
	}
	
	/*
	 * CLONE
	 */
	
	public static int[] clone1(int[] data) {
		int x = data.length;
		if(x==0) return new int[0];
		
		int[] newData = new int[x];
		for(int i=0;i<x;i++)
			newData[i] = data[i];
		return newData;
	}
	
	public static int[][] clone2(int[][] data) {
		int x = data.length;
		if(x==0) return new int[0][0];
		int y = data[0].length;
		if(y==0) return new int[0][0];
		
		int[][] newData = new int[x][y];
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) 
			newData[i][j] = data[i][j];
		return newData;
	}
	
	public static int[][][] clone3(int[][][] data) {
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
	 * COUNT
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
	 * LEN TO XY
	 */
	
	public static int[] lenToXY(int[][] data, int len) {
		int x = data.length;
		if(x==0) return null;
		int y = data[0].length;
		if(y==0) return null;
		
		if(len<0 || len>x*y) return null;
		return new int[]{len/y,len%y};
	}
	
	/*
	 * PERIMETER
	 */
	
	public static int perimeter(int[][] data) {
		int x = data.length;
		if(x==0) return 0;
		int y = data[0].length;
		if(y==0) return 0;
		
		if(x==1) return y;
		if(y==1) return x;
		
		return (x+y)*2-4;
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
