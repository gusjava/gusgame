package gus.game5.main.game.p2.o.board.ragus1;

import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.PLAYER_DINO;
import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.attemptToPlay;
import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.findPossiblePlays;
import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.isBlocked;
import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.isPlayable;
import static gus.game5.main.game.p2.o.board.ragus1.UtilRagus.randomPlay;

import java.util.List;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;

public class UtilRagusMinmax {

	public static final int DEPTH_MAX = 4;

	private static final float[] F1 = new float[] {0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 5, 6, 10};
	private static final float[] F2 = new float[] {10, 6, 5, 4, 3, 2, 0, 0, 0, 0, 0, 0, 0};

	private static final float WIN_SCORE = 100000f;

	private static final MinmaxGame<int[][],int[]> GAME = new RagusMinmaxGame();

	public static int[] computePlay(int player, int[][] data) {
		int[] play = UtilMinmax.computePlay(GAME, player, data, DEPTH_MAX);
		if(play==null) return randomPlay(player, data);
		return play;
	}

	private static class RagusMinmaxGame implements MinmaxGame<int[][],int[]> {

		public List<int[]> possibleMoves(int[][] data, int player) {
			return findPossiblePlays(player, data);
		}

		public int[][] applyMove(int[][] data, int[] play, int player) {
			int[] start = new int[] {play[0], play[1]};
			int[] end = new int[] {play[2], play[3]};
			return attemptToPlay(player, data, start, end);
		}

		public int oppositePlayer(int player) {
			return player * -1;
		}

		public float evaluate(int[][] data, int player) {
			int other = oppositePlayer(player);
			if(!isPlayable(other, data)) return WIN_SCORE;
			if(!isPlayable(player, data)) return -WIN_SCORE;

			float[] fMe = player==PLAYER_DINO ? F1 : F2;
			float[] fHim = player==PLAYER_DINO ? F2 : F1;

			float count = 0f;
			for(int i=0;i<13;i++) for(int j=0;j<8;j++) {
				int value = data[i][j];
				if(value!=0) {
					int strength = Math.abs(value);
					int[] pos = new int[] {i,j};
					boolean blocked = isBlocked(data, pos, value, strength);
					boolean isMe = data[i][j]*player>0;

					if(!blocked) {
						if(isMe) count += fMe[i] * strength;
						else count -= fHim[i] * strength;
					}
				}
			}
			return count;
		}
	}
}
