package gus.game5.main.game.p2.c.board.tictactoe.v3;

import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.EMPTY;
import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.NOUGHT;
import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.findPossiblePlays;
import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.oppositeValue;
import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.searchWinner;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;
import gus.game5.core.util.UtilArrayInt;

public class UtilTTT3Minmax {

	public static final int DEPTH_MAX = 9;

	private static final MinmaxGame<int[],int[]> GAME = new TTT3MinmaxGame();

	public static int[] computePlay(int player, int[] data) {
		return UtilMinmax.computePlay(GAME, player, data, DEPTH_MAX);
	}

	private static class TTT3MinmaxGame implements MinmaxGame<int[],int[]> {

		public List<int[]> possibleMoves(int[] data, int player) {
			if(searchWinner(data)!=-1) return new ArrayList<>();
			return findPossiblePlays(data);
		}

		public int[] applyMove(int[] data, int[] play, int player) {
			int[] newData = UtilArrayInt.clone1(data);
			newData[play[0]] = player;
			return newData;
		}

		public int oppositePlayer(int player) {
			return oppositeValue(player);
		}

		public float evaluate(int[] data, int player) {
			int winner = searchWinner(data);

			int count = 10;
			for (int i=0; i<9; i++) if (data[i] == EMPTY) count--;

			float base = 1.0f;
			if(data[4]==player) {
				if(player == NOUGHT) base -= 0.4f;
				else base += 0.4f;
			}

			if(winner==player) return base + (1.0f / count);
			if(winner==oppositeValue(player)) return - base - (1.0f / count);
			return base - 1.0f;
		}
	}
}
