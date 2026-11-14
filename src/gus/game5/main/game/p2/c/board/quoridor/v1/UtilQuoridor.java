package gus.game5.main.game.p2.c.board.quoridor.v1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UtilQuoridor {
	
	public static final int PAWN1 = 1;
	public static final int PAWN2 = 2;

	public static final int SIZE = 9;
	public static final int CELL_SIZE = 50;
	public static final int WALL_SIZE = SIZE-1;
	public static final int WALL_COUNT = 10;

	public static final int[][] INIT_STATE = new int[][] {
		{0,0,0,0,2,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0}
	};

	private static final int[][] DIRECTIONS = {{-1,0},{1,0},{0,-1},{0,1}};

	public static int opponent(int player) {
		return player==PAWN1 ? PAWN2 : PAWN1;
	}

	public static boolean inBoard(int i, int j) {
		return i>=0 && i<SIZE && j>=0 && j<SIZE;
	}

	/*
	 * WALLS
	 */

	public static boolean isBlocked(QuoridorState state, int i, int j, int i2, int j2) {
		if(i2==i+1) return state.wallH(i, j) || state.wallH(i, j-1);
		if(i2==i-1) return state.wallH(i2, j) || state.wallH(i2, j-1);
		if(j2==j+1) return state.wallV(i, j) || state.wallV(i-1, j);
		if(j2==j-1) return state.wallV(i, j2) || state.wallV(i-1, j2);
		return true;
	}

	public static boolean canPlaceWall(QuoridorState state, int i, int j, boolean horizontal) {
		if(i<0 || i>=WALL_SIZE || j<0 || j>=WALL_SIZE) return false;
		if(state.wallH(i, j) || state.wallV(i, j)) return false;
		if(horizontal) {
			if(state.wallH(i, j-1) || state.wallH(i, j+1)) return false;
		}
		else {
			if(state.wallV(i-1, j) || state.wallV(i+1, j)) return false;
		}
		QuoridorState next = state.copy();
		next.setWall(i, j, horizontal);
		return bfsDistance(next, PAWN1)>=0 && bfsDistance(next, PAWN2)>=0;
	}

	/*
	 * MOVES
	 */

	public static List<int[]> possibleMoves(QuoridorState state, int player) {
		List<int[]> moves = new ArrayList<>();
		int[] from = state.pawn(player);
		int[] other = state.pawn(opponent(player));

		for(int[] d : DIRECTIONS) {
			int i = from[0]+d[0];
			int j = from[1]+d[1];
			if(!inBoard(i, j)) continue;
			if(isBlocked(state, from[0], from[1], i, j)) continue;

			if(i!=other[0] || j!=other[1]) {
				addMove(moves, i, j);
				continue;
			}

			int i2 = i+d[0];
			int j2 = j+d[1];
			if(inBoard(i2, j2) && !isBlocked(state, i, j, i2, j2)) {
				addMove(moves, i2, j2);
				continue;
			}
			for(int[] side : DIRECTIONS) {
				if(side[0]==d[0] && side[1]==d[1]) continue;
				if(side[0]==-d[0] && side[1]==-d[1]) continue;
				int i3 = i+side[0];
				int j3 = j+side[1];
				if(!inBoard(i3, j3)) continue;
				if(isBlocked(state, i, j, i3, j3)) continue;
				addMove(moves, i3, j3);
			}
		}
		return moves;
	}

	public static boolean canMove(QuoridorState state, int player, int i, int j) {
		for(int[] move : possibleMoves(state, player)) {
			if(move[0]==i && move[1]==j) return true;
		}
		return false;
	}

	/*
	 * PLAYS
	 */

	public static List<QuoridorPlay> possiblePlays(QuoridorState state, int player) {
		List<QuoridorPlay> plays = new ArrayList<>();
		for(int[] move : possibleMoves(state, player)) plays.add(QuoridorPlay.move(move[0], move[1]));
		if(state.wallsLeft(player)>0) {
			for(int i=0;i<WALL_SIZE;i++) for(int j=0;j<WALL_SIZE;j++) {
				if(canPlaceWall(state, i, j, true)) plays.add(QuoridorPlay.wall(i, j, true));
				if(canPlaceWall(state, i, j, false)) plays.add(QuoridorPlay.wall(i, j, false));
			}
		}
		return plays;
	}

	public static QuoridorState applyPlay(QuoridorState state, QuoridorPlay play, int player) {
		QuoridorState next = state.copy();
		if(play.isMove()) next.setPawn(player, play.getI(), play.getJ());
		else {
			next.setWall(play.getI(), play.getJ(), play.isHorizontal());
			next.useWall(player);
		}
		return next;
	}

	/*
	 * GOAL
	 */

	public static boolean hasWon(QuoridorState state, int player) {
		return state.pawn(player)[0]==state.goalRow(player);
	}

	public static int winner(QuoridorState state) {
		if(hasWon(state, PAWN1)) return PAWN1;
		if(hasWon(state, PAWN2)) return PAWN2;
		return 0;
	}

	public static int bfsDistance(QuoridorState state, int player) {
		int goal = state.goalRow(player);
		int[] from = state.pawn(player);
		if(from[0]==goal) return 0;

		boolean[][] seen = new boolean[SIZE][SIZE];
		Queue<int[]> queue = new LinkedList<>();
		seen[from[0]][from[1]] = true;
		queue.add(new int[] {from[0], from[1], 0});

		while(!queue.isEmpty()) {
			int[] current = queue.poll();
			for(int[] d : DIRECTIONS) {
				int i = current[0]+d[0];
				int j = current[1]+d[1];
				if(!inBoard(i, j) || seen[i][j]) continue;
				if(isBlocked(state, current[0], current[1], i, j)) continue;
				if(i==goal) return current[2]+1;
				seen[i][j] = true;
				queue.add(new int[] {i, j, current[2]+1});
			}
		}
		return -1;
	}

	private static void addMove(List<int[]> moves, int i, int j) {
		moves.add(new int[] {i,j});
	}
}
