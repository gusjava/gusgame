package gus.game5.main.game.tictactoe3;

import java.util.List;

import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilTTT3 {

	public static final int EMPTY = 0;
	public static final int CIRCLE = 1;
	public static final int CROSS = 2;
	

	public static int[] randomPlay(int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(data));
	}
	
//	public static int[] searchBestPlay(int[][] data, int side) {
//		return new TTTSearch1(data, side).search();
//	}
	
	public static List<int[]> findPossiblePlays(int[][] data) {
		return UtilArray.findAll(data, 0);
	}
	
	public static int searchWinner(int[][] data) {
		int side11 = data[1][1];
		if(side11!=0) {
			if(data[0][0]==side11 && data[2][2]==side11) return side11;
			if(data[0][1]==side11 && data[2][1]==side11) return side11;
			if(data[0][2]==side11 && data[2][0]==side11) return side11;
			if(data[1][0]==side11 && data[1][2]==side11) return side11;
		}
		int side00 = data[0][0];
		if(side00!=0) {
			if(data[0][1]==side00 && data[0][2]==side00) return side00;
			if(data[1][0]==side00 && data[2][0]==side00) return side00;
		}
		int side22 = data[2][2];
		if(side22!=0) {
			if(data[2][0]==side22 && data[2][1]==side22) return side22;
			if(data[0][2]==side22 && data[1][2]==side22) return side22;
		}
		if(UtilArray.none(data, 0)) return 0;
		return -1;
	}
	
	public static int oppositeValue(int value) {
		return value==CROSS ? CIRCLE : value==CIRCLE ? CROSS : value;
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
