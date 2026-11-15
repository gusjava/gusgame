package gus.game5.main.game.p2.c.board.hex.v1;

import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.findPossiblePlays;
import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.oppositeValue;
import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.searchWinner;
import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.shortestConnectionLength;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;
import gus.game5.core.util.UtilArrayInt;

public class UtilHexMinmax {

	public static final int DEPTH_MAX = 2;

	private static final float WIN_SCORE = 100000f;

	private static final MinmaxGame<int[][],int[]> GAME = new HexMinmaxGame();

	public static int[] computePlay(int player, int[][] data) {
		return UtilMinmax.computePlay(GAME, player, data, DEPTH_MAX);
	}

	private static class HexMinmaxGame implements MinmaxGame<int[][],int[]> {

		public List<int[]> possibleMoves(int[][] data, int player) {
			if(searchWinner(data)!=-1) return new ArrayList<>();
			return findPossiblePlays(data);
		}

		public int[][] applyMove(int[][] data, int[] play, int player) {
			int[][] newData = UtilArrayInt.clone2(data);
			newData[play[0]][play[1]] = player;
			return newData;
		}

		public int oppositePlayer(int player) {
			return oppositeValue(player);
		}

		public float evaluate(int[][] data, int player) {
			int other = oppositeValue(player);
			int winner = searchWinner(data);
			if(winner==player) return WIN_SCORE;
			if(winner==other) return -WIN_SCORE;

			int myDistance = shortestConnectionLength(data, player);
			int otherDistance = shortestConnectionLength(data, other);
			return otherDistance - myDistance;
		}
	}
}
