package gus.game5.main.game.p1.c.madvirus;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArrayInt;
import gus.game5.core.util.UtilArrayBoolean;
import gus.game5.core.util.UtilRandom;

public class UtilMadVirus {
	
	public static final Color[] COLORS = new Color[] {
			Color.BLACK,
			new Color(216,255,56), //YELLOW
			new Color(0,180,0), //GREEN
			new Color(255,0,0), //RED
			new Color(255,0,255), //VIOLET
			new Color(255,127,0), //ORANGE
			new Color(0,47,188) //BLUE
	};
	
	public static final int[] CELL_SIZES = new int[] {
			40, 35, 32, 30
	};
	
	public static final int[] TURN_LEFT = new int[] {
			20, 24, 30, 31
	};

	public static int[][] generateForLevel(int level) {
		switch(level) {
			case 1:return UtilArrayInt.random2(10, 17, new int[][] { //170 = 12+39+34+42+43
				{12,0}, //12: 0 (black)
				{39,1}, //39: 1 (jaune)
				{34,2}, //34: 2 (vert)
				{42,3}, //42: 3 (rouge)
				{43,4} //43: 4 (violet)
			});
			case 2:return UtilArrayInt.random2(11, 20, new int[][] { //220 = 14+44+39+36+45+42
				{14,0}, //14: 0 (black)
				{44,1}, //44: 1 (jaune)
				{39,2}, //39: 2 (vert)
				{36,3}, //36: 3 (rouge)
				{45,4}, //45: 4 (violet)
				{42,5} //42: 5 (orange)
			});
			case 3:return UtilArrayInt.random2(13, 23, new int[][] { //299 = 24+49+44+40+52+47+43
				{24,0}, //14: 0 (black)
				{49,1}, //44: 1 (jaune)
				{44,2}, //39: 2 (vert)
				{40,3}, //36: 3 (rouge)
				{52,4}, //45: 4 (violet)
				{47,5}, //42: 5 (orange)
				{43,6} //42: 6 (blue)
			});
			case 4:return UtilArrayInt.random2(14, 24, new int[][] { //336 = 29+54+49+45+60+52+47
				{30,0}, //14: 0 (black)
				{54,1}, //44: 1 (jaune)
				{49,2}, //39: 2 (vert)
				{45,3}, //36: 3 (rouge)
				{59,4}, //45: 4 (violet)
				{52,5}, //42: 5 (orange)
				{47,6} //42: 6 (blue)
			});
		}
		return null;
	}
	
	public static int[] findRandomStart(int[][] data) {
		int x = data.length;
		int y = data[0].length;
		
		List<int[]> pos = new ArrayList<>();
		for(int i=0;i<x;i++) {
			if(data[i][0]!=0) pos.add(new int[] {i,0});
			if(data[i][y-1]!=0) pos.add(new int[] {i,y-1});
		}
		for(int j=0;j<y;j++) {
			if(data[0][j]!=0) pos.add(new int[] {0,j});
			if(data[x-1][j]!=0) pos.add(new int[] {x-1,j});
		}
		return UtilRandom.randomElement(pos);
	}
	
	public static boolean attempToInfect(int[][] data, boolean[][] infected, int value) {
		if(value==0) return false;
		
		boolean spread = false;
		while(spreadInfection(data, infected, value)) spread = true;
		if(!spread) return false;
		
		changeInfection(data, infected, value);
		return true;
	}
	
	private static void changeInfection(int[][] data, boolean[][] infected, int value) {
		int x = data.length;
		int y = data[0].length;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			if(infected[i][j]) data[i][j] = value;
		}
	}
	
	private static boolean spreadInfection(int[][] data, boolean[][] infected, int value) {
		int x = data.length;
		int y = data[0].length;
		
		boolean spread = false;
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			if(!infected[i][j] && data[i][j]==value && nextToInfected(infected, i, j)) {
				infected[i][j] = true;
				spread = true;
			}
		}
		return spread;
	}
	
	private static boolean nextToInfected(boolean[][] infected, int i, int j) {
		if(j%2==0) return 
				UtilArrayBoolean.is(infected, i, j-1, true) || 
				UtilArrayBoolean.is(infected, i, j+1, true) || 
				UtilArrayBoolean.is(infected, i+1, j, true) || 
				UtilArrayBoolean.is(infected, i-1, j-1, true) || 
				UtilArrayBoolean.is(infected, i-1, j, true) || 
				UtilArrayBoolean.is(infected, i-1, j+1, true);

		return 
				UtilArrayBoolean.is(infected, i, j-1, true) || 
				UtilArrayBoolean.is(infected, i, j+1, true) || 
				UtilArrayBoolean.is(infected, i-1, j, true) || 
				UtilArrayBoolean.is(infected, i+1, j-1, true) || 
				UtilArrayBoolean.is(infected, i+1, j, true) || 
				UtilArrayBoolean.is(infected, i+1, j+1, true);
	}
	
	public static boolean isAllInfected(int[][] data, boolean[][] infected) {
		int x = data.length;
		int y = data[0].length;
		
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			if(data[i][j]>0 && !infected[i][j]) return false;
		}
		return true;
	}
}
