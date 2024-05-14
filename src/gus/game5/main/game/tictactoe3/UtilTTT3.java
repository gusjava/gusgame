package gus.game5.main.game.tictactoe3;

import java.util.List;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilTTT3 {

	public static final int EMPTY = 0;
	public static final int NOUGHT = 1;
	public static final int CROSS = 2;
	

	public static int[] randomPlay(int[] data) {
		return UtilRandom.randomElement(findPossiblePlays(data));
	}
	
	public static List<int[]> findPossiblePlays(int[] data) {
		return UtilArray.findAll(data, EMPTY);
	}
	
	public static int searchWinner(int[] data) {
		if(same(data,0,1,2)) return data[0];
		if(same(data,3,4,5)) return data[3];
		if(same(data,6,7,8)) return data[6];

		if(same(data,0,3,6)) return data[0];
		if(same(data,1,4,7)) return data[1];
		if(same(data,2,5,8)) return data[2];

		if(same(data,0,4,8)) return data[0];
		if(same(data,2,4,6)) return data[2];
		
		if(UtilArray.none(data, EMPTY)) return EMPTY;
		return -1;
	}
	
	private static boolean same(int[] data, int i1, int i2, int i3) {
		return data[i1]!=0 && data[i1]==data[i2] && data[i1]==data[i3];
	}
	
	public static int oppositeValue(int value) {
		return value==CROSS ? NOUGHT : value==NOUGHT ? CROSS : value;
	}
	
	public static String dataToString(int[][] data) {
		StringBuffer b = new StringBuffer();
		for(int i=0;i<3;i++) {
			b.append("[");
			for(int j=0;j<3;j++)
				b.append(data[i][j]==1 ? "o" : data[i][j]==2 ? "x" : " ");
			b.append("]\n");
		}
		return b.toString();
	}
}
