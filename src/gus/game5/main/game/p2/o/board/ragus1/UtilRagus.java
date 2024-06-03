package gus.game5.main.game.p2.o.board.ragus1;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArrayInt;
import gus.game5.core.util.UtilRandom;

public class UtilRagus {
	
	public static final int PLAYER_DINO = 1;
	public static final int PLAYER_ANUBIS = -1;

    public static final int SIDE1_HOME = 0;
    public static final int SIDE2_HOME = 12;
	
	public static int[][] INIT_STATE = {
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			
			{1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1},
			
			{0,0,0,0,0,0,0,0},

			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			{-1,-1,-1,-1,-1,-1,-1,-1},
			
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
	};
	
	/*
	 * INDEXES
	 */
	
	public static int targetIndex(int player) {
		return player==PLAYER_DINO ? SIDE2_HOME : SIDE1_HOME;
	}
	
	public static int homeIndex(int player) {
		return player==PLAYER_DINO ? SIDE1_HOME : SIDE2_HOME;
	}
	
	
	
	/*
	 * IS PLAYABLE
	 */
	
	public static boolean isPlayable(int player, int[][] data) {
		for(int i=0;i<13;i++) for(int j=0;j<8;j++) {
			if(isPlayable(player, data, i, j)) return true;
		}
		return false;
	}
	
	public static boolean isPlayable(int player, int[][] data, int[] pos) {
		return isPlayable(player, data, pos[0], pos[1]);
	}
	
	private static boolean isPlayable(int player, int[][] data, int i, int j) {
			return findPossiblePlays(player, data, i, j).size()>0;
	}
	
	/*
	 * WINNER
	 */
	
	public static PlayerRagus searchWinner(int[][] data, PlayerRagus player1, PlayerRagus player2) {
		if(player1.hasMaxScore()) return player1;
		if(player2.hasMaxScore()) return player2;
		if(!isPlayable(player1.getValue(), data)) return player1;
		if(!isPlayable(player2.getValue(), data)) return player2;
		return null;
	}
	
	/*
	 * ATTEMPT TO PLAY
	 */
	
	public static int[][] attemptToPlay(int player, int[][] data, int[] start, int[] end) {
		//il faut que les cases start et end soient proches voisines
		if(UtilArrayInt.distance(start, end)!=1) return null;
		
		int startX = start[0];
		int startY = start[1];
		
		int endX = end[0];
		int endY = end[1];
		
		//il faut que la case end soit en dehors du home du player
		if(endX==homeIndex(player)) return null;
		
		int value1 = data[startX][startY];
		int value2 = data[endX][endY];
		
		//il faut que la case start soit du signe du player
		if(value1*player<=0) return null;
		
		int strength1 = Math.abs(value1);
		int strength2 = Math.abs(value2);
		
		boolean blocked1 = isBlocked(data, start, value1, strength1);
		boolean blocked2 = isBlocked(data, end, value2, strength2);
		
		//il faut que la case start ne soit pas bloquée
		if(blocked1) return null;
		
		int[][] newData = UtilArrayInt.clone(data);
		
		if(strength1==1 && strength2==0) {
			//duplication
			newData[endX][endY] = value1;
			//si on est à 2 lignes de la fin, déplacement simple
			if(player==PLAYER_DINO && (endX>9 || startX>9))
				newData[startX][startY] = 0;
			else if(player==PLAYER_ANUBIS && (endX<3 || startX<3))
				newData[startX][startY] = 0;
			return newData;
		}
		if(value1==value2) {
			//merge
			if(blocked2) return null;
			newData[startX][startY] = 0;
			newData[endX][endY] = value2 + player;
			return newData;
		}
		//inversion
		newData[startX][startY] = value2;
		newData[endX][endY] = value1;
		return newData;
	}
	
	/*
	 * BLOCKED
	 */
	
	public static boolean isBlocked(int[][] data, int[] pos) {
		int value = data[pos[0]][pos[1]];
		int strength = Math.abs(value);
		return isBlocked(data, pos, value, strength);
	}
	
	public static boolean isBlocked(int[][] data, int[] pos, int value, int strength) {
		List<int[]> neighbors = UtilArrayInt.neighbor8Pos(data, pos);
		for(int[] neighbor : neighbors) {
			int value1 = data[neighbor[0]][neighbor[1]];
			if(value1*value<0) {
				int strenght1 = Math.abs(value1);
				if(strenght1>strength) {
					if(!isBlocked(data, neighbor)) return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * RANDOM PLAY
	 */
	
	public static int[] randomPlay(int player, int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(player, data));
	}
	
	/*
	 * POSSIBLE PLAYS
	 */
	
	public static List<int[]> findPossiblePlays(int player, int[][] data) {
		List<int[]> playList = new ArrayList<>();
		for(int i=0;i<13;i++) for(int j=0;j<8;j++)
			playList.addAll(findPossiblePlays(player, data, i, j));
		return playList;
	}
	
	private static List<int[]> findPossiblePlays(int player, int[][] data, int i, int j) {
		List<int[]> playList = new ArrayList<>();
		if(data[i][j]*player<=0) return playList;
		if(isBlocked(data, new int[] {i,j})) return playList;
		
		if(possiblePlay(player, data, i,j, i-1, j)) playList.add(new int[] {i,j,i-1,j});
		if(possiblePlay(player, data, i,j, i+1, j)) playList.add(new int[] {i,j,i+1,j});
		if(possiblePlay(player, data, i,j, i, j-1)) playList.add(new int[] {i,j,i,j-1});
		if(possiblePlay(player, data, i,j, i, j+1)) playList.add(new int[] {i,j,i,j+1});
		return playList;
	}
	
	private static boolean possiblePlay(int player, int[][] data, int i1, int j1, int i2, int j2) {
		if(!UtilArrayInt.has(data, i2,j2)) return false;
		if(i2==homeIndex(player)) return false;
		return data[i1][j1]!=data[i2][j2] || !isBlocked(data, new int[] {i2,j2});
	}
}
