package gus.game5.main.game.board2.ragus1;

import java.util.List;

import gus.game5.core.util.UtilArray;
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
	
	
	public static boolean isPlayable(int[][] data, int player) {
		for(int i=0;i<13;i++) for(int j=0;j<8;j++) {
			if(data[i][j]*player>0 && !isBlocked(data, new int[] {i,j})) return true;
		}
		return false;
	}
	
	public static PlayerRagus searchWinner(int[][] data, PlayerRagus player1, PlayerRagus player2) {
		if(player1.hasMaxScore()) return player1;
		if(player2.hasMaxScore()) return player2;
		if(!isPlayable(data, player1.getValue())) return player1;
		if(!isPlayable(data, player2.getValue())) return player2;
		return null;
	}
	
	public static boolean canPlay(int player, int[][] data, int[] pos) {
		int value = data[pos[0]][pos[1]];
		int strength = Math.abs(value);
		return value*player>0 && !isBlocked(data, pos, value, strength);
	}
	
	public static int[][] attemptToPlay(int player, int[][] data, int[] start, int[] end) {
		//il faut que les cases start et end soient proches voisines
		if(UtilArray.distance(start, end)!=1) return null;
		
		int startX = start[0];
		int startY = start[1];
		
		int endX = end[0];
		int endY = end[1];
		
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
		
		int[][] newData = UtilArray.clone(data);
		
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
	
	private static boolean isBlocked(int[][] data, int[] pos, int value, int strength) {
		List<int[]> neighbors = UtilArray.neighbor8Pos(data, pos);
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
		return UtilArray.findAll(data, 0);
	}
}
