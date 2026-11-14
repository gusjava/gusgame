package gus.game5.main.game.p2.c.board.reversi.v3;

import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.BLACK;
import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.EMPTY;
import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.WHITE;
import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.findPossiblePlaysOrPass;
import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.hasAnyLegalMove;
import static gus.game5.main.game.p2.c.board.reversi.v3.UtilReversi3.oppositeValue;

import java.util.List;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;
import gus.game5.core.util.UtilArrayInt;

public class UtilReversi3Minmax {

	public static final int DEPTH_MAX = 9;

	private static final MinmaxGame<int[][],List<int[]>> GAME = new ReversiMinmaxGame();

	public static List<int[]> computePlay(int player, int[][] data) {
		return UtilMinmax.computePlay(GAME, player, data, DEPTH_MAX);
	}

	private static class ReversiMinmaxGame implements MinmaxGame<int[][],List<int[]>> {

		public List<List<int[]>> possibleMoves(int[][] data, int player) {
			return findPossiblePlaysOrPass(player, data);
		}

		public int[][] applyMove(int[][] data, List<int[]> play, int player) {
			if(play.isEmpty()) return data;

			int[][] newData = UtilArrayInt.clone2(data);
			for(int[] p : play) newData[p[0]][p[1]] = player;
			return newData;
		}

		public int oppositePlayer(int player) {
			return oppositeValue(player);
		}

		public float evaluate(int[][] data, int player) {
			if(!hasAnyLegalMove(WHITE, data) && !hasAnyLegalMove(BLACK, data)) {
				return evaluateFinal(data, player);
			}
			return evaluateHeuristic(data, player);
		}

		private float evaluateFinal(int[][] data, int player) {
			float playerCount = 0f;
			float opponentCount = 0f;
			for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
				if(data[i][j]==player) playerCount++;
				else if(data[i][j]!=EMPTY) opponentCount++;
			}
			float diff = playerCount - opponentCount;
			if(diff>0) return 10000f + diff;
			if(diff<0) return -10000f + diff;
			return 0f;
		}

		private float evaluateHeuristic(int[][] data, int player) {
			float emptyCount = 0f;
			float playerCount = 0f;
			float opponentCount = 0f;
			for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
				if (data[i][j] == EMPTY) emptyCount++;
				else if (data[i][j] == player) playerCount++;
				else opponentCount++;
			}
			float v = (playerCount - opponentCount) / Math.max(emptyCount, 1f);

			if(data[0][0]!=EMPTY) v += data[0][0]==player ? 5 : -5;
			if(data[0][7]!=EMPTY) v += data[0][7]==player ? 5 : -5;
			if(data[7][0]!=EMPTY) v += data[7][0]==player ? 5 : -5;
			if(data[7][7]!=EMPTY) v += data[7][7]==player ? 5 : -5;

			return v;
		}
	}
}
