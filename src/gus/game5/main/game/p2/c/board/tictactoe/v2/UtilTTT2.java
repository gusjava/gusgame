package gus.game5.main.game.p2.c.board.tictactoe.v2;

import java.util.List;

import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilTTT2 {

	public static int[] randomPlay(int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(data));
	}
	
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
}
