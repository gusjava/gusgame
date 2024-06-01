package gus.game5.main.game.p2board.reversi3;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilReversi3 {
	
	public static final int EMPTY = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	
	public static int[][] INIT_STATE = {
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,1,2,0,0,0},
		{0,0,0,2,1,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0}
	};

	public static List<int[]> randomPlay(int player, int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(player, data));
	}
	
	public static List<List<int[]>> findPossiblePlays(int player, int[][] data) {
		List<List<int[]>> playList = new ArrayList<>();
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			List<int[]> play = findPlay(player, data, new int[] {i,j});
			if(play!=null) playList.add(play);
		}
		return playList;
	}
	
	public static List<int[]> findPlay(int player, int[][] data, int[] p) {
		List<int[]> list = scan(player, data, p);
		if(list==null || list.isEmpty()) return null;
		list.add(p);
		return list;
	}
	
	public static int searchWinner(int player, int[][] data) {
		int scoreWhite = 0;
		int scoreBlack = 0;
		boolean playFound = false;
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			if(data[i][j]==WHITE) scoreWhite++;
			if(data[i][j]==BLACK) scoreBlack++;
			List<int[]> play = findPlay(player, data, new int[] {i,j});
			if(play!=null) playFound = true;
		}
		if(playFound) return -1;
		return scoreWhite > scoreBlack ? WHITE : scoreBlack > scoreWhite ? BLACK : EMPTY;
	}
	
	public static int oppositeValue(int value) {
		return value==WHITE ? BLACK : value==BLACK ? WHITE : value;
	}
	
	/*
	 * SCAN
	 */
	
	private static List<int[]> scan(int player, int[][] data, int[] p) {
		if(data[p[0]][p[1]]!=EMPTY) return null;
		List<int[]> list = new ArrayList<>();
		list.addAll(scan(player, data, p,0,1));
		list.addAll(scan(player, data, p,0,-1));
		list.addAll(scan(player, data, p,1,0));
		list.addAll(scan(player, data, p,1,1));
		list.addAll(scan(player, data, p,1,-1));
		list.addAll(scan(player, data, p,-1,0));
		list.addAll(scan(player, data, p,-1,1));
		list.addAll(scan(player, data, p,-1,-1));
		return list;
	}
	
	private static List<int[]> scan(int player, int[][] data, int[] p, int di, int dj) {
		int oppositePlayer = oppositeValue(player);
		int[] pn = UtilArray.neighborPos(data, p, di, dj);
		List<int[]> list = new ArrayList<>();
		while(pn!=null && data[pn[0]][pn[1]]==oppositePlayer) {
			list.add(pn);
			pn = UtilArray.neighborPos(data, pn, di, dj);
		}
		if(pn==null || data[pn[0]][pn[1]]!=player) list.clear();
		return list;
	}
}
