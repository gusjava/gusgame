package gus.game5.main.game.p2.c.board.hex.v1;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import gus.game5.core.util.UtilArrayBoolean;
import gus.game5.core.util.UtilArrayInt;
import gus.game5.core.util.UtilRandom;

public class UtilHex {
	
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int BLUE = 2;

	public static int[] randomPlay(int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(data));
	}
	
	public static List<int[]> findPossiblePlays(int[][] data) {
		return UtilArrayInt.findAll(data, EMPTY);
	}
	
	public static int[] findPlay(int[][] data, int[] p) {
		return data[p[0]][p[1]]==EMPTY ? p : null;
	}
	
	public static int searchWinner(int[][] data) {
		int x = data.length;
		int y = data[0].length;
		
		boolean[][] b = UtilArrayBoolean.build2(x, y, false);
		for(int i=0;i<x;i++) {
			if(isConnectedToLeftRed(x, y, data, b, i, y-1)) return RED;
		}
		b = UtilArrayBoolean.build2(x, y, false);
		for(int j=0;j<y;j++) {
			if(isConnectedToTopBlue(x, y, data, b, x-1, j)) return BLUE;
		}
		if(UtilArrayInt.any2(data, EMPTY)) return -1;
		return EMPTY;
	}
	
	public static int oppositeValue(int value) {
		return value==RED ? BLUE : value==BLUE ? RED : value;
	}

	/*
	 * SHORTEST CONNECTION (used by the Min-Max heuristic)
	 */

	public static int shortestConnectionLength(int[][] data, int player) {
		int x = data.length;
		int y = data[0].length;
		int opponent = oppositeValue(player);

		int[][] dist = new int[x][y];
		for(int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

		PriorityQueue<int[]> queue = new PriorityQueue<>((a,b)->Integer.compare(a[2], b[2]));

		if(player==RED) for(int i=0;i<x;i++) offer(queue, dist, data, opponent, i, 0, 0);
		else for(int j=0;j<y;j++) offer(queue, dist, data, opponent, 0, j, 0);

		while(!queue.isEmpty()) {
			int[] current = queue.poll();
			int i = current[0];
			int j = current[1];
			int d = current[2];
			if(d>dist[i][j]) continue;
			offerNeighbor(queue, dist, data, opponent, x, y, i-1, j, d);
			offerNeighbor(queue, dist, data, opponent, x, y, i-1, j-1, d);
			offerNeighbor(queue, dist, data, opponent, x, y, i, j-1, d);
			offerNeighbor(queue, dist, data, opponent, x, y, i, j+1, d);
			offerNeighbor(queue, dist, data, opponent, x, y, i+1, j, d);
			offerNeighbor(queue, dist, data, opponent, x, y, i+1, j+1, d);
		}

		int best = Integer.MAX_VALUE;
		if(player==RED) for(int i=0;i<x;i++) best = Math.min(best, dist[i][y-1]);
		else for(int j=0;j<y;j++) best = Math.min(best, dist[x-1][j]);
		return best;
	}

	private static void offerNeighbor(PriorityQueue<int[]> queue, int[][] dist, int[][] data, int opponent, int x, int y, int i, int j, int fromDist) {
		if(i<0 || i>=x || j<0 || j>=y) return;
		offer(queue, dist, data, opponent, i, j, fromDist);
	}

	private static void offer(PriorityQueue<int[]> queue, int[][] dist, int[][] data, int opponent, int i, int j, int fromDist) {
		if(data[i][j]==opponent) return;
		int cost = fromDist + (data[i][j]==EMPTY ? 1 : 0);
		if(cost<dist[i][j]) {
			dist[i][j] = cost;
			queue.add(new int[] {i,j,cost});
		}
	}

	private static boolean isConnectedToLeftRed(int x, int y, int[][] data, boolean[][] b, int i, int j) {
		if(i<0 || i>=x) return false;
		if(j<0 || j>=y) return false;
		if(b[i][j]) return false;
		b[i][j] = true;
		if(data[i][j]!=RED) return false;
		
		if(j==0) return true;
		
		if(isConnectedToLeftRed(x, y, data, b, i-1, j)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i-1, j-1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i, j-1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i, j+1)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i+1, j)) return true;
		if(isConnectedToLeftRed(x, y, data, b, i+1, j+1)) return true;
		return false;
	}
	
	private static boolean isConnectedToTopBlue(int x, int y, int[][] data, boolean[][] b, int i, int j) {
		if(i<0 || i>=x) return false;
		if(j<0 || j>=y) return false;
		if(b[i][j]) return false;
		b[i][j] = true;
		if(data[i][j]!=BLUE) return false;
		
		if(i==0) return true;
		
		if(isConnectedToTopBlue(x, y, data, b, i-1, j)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i-1, j-1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i, j-1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i, j+1)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i+1, j)) return true;
		if(isConnectedToTopBlue(x, y, data, b, i+1, j+1)) return true;
		return false;
	}
}
