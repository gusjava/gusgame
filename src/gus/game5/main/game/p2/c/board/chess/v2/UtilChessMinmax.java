package gus.game5.main.game.p2.c.board.chess.v2;

import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.BLACK;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.BP;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.WHITE;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.WP;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.blackIsChecked;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.findPossiblePlays;
import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.whiteIsChecked;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;
import gus.game5.core.util.UtilArrayInt;

public class UtilChessMinmax {

	public static final int DEPTH_MAX = 4;

	private static final float MATE_SCORE = -100000f;
	private static final float POSITION_WEIGHT = 0.02f;
	private static final float PAWN_ADVANCE_WEIGHT = 0.03f;

	public static int[] computePlay(int player, int[][] data) {
		return computePlay(player, data, Collections.emptySet());
	}

	public static int[] computePlay(int player, int[][] data, Set<String> playedPositions) {
		MinmaxGame<int[][],int[]> game = new ChessMinmaxGame(data, playedPositions);
		return UtilMinmax.computePlay(game, player, data, DEPTH_MAX);
	}

	public static String positionKey(int[][] data) {
		return Arrays.deepToString(data);
	}

	private static class ChessMinmaxGame implements MinmaxGame<int[][],int[]> {

		private final int[][] rootData;
		private final Set<String> playedPositions;

		public ChessMinmaxGame(int[][] rootData, Set<String> playedPositions) {
			this.rootData = rootData;
			this.playedPositions = playedPositions;
		}

		public List<int[]> possibleMoves(int[][] data, int player) {
			List<int[]> plays = findPossiblePlays(player, data);
			Collections.shuffle(plays);
			if(data!=rootData) return plays;

			List<int[]> newPlays = new ArrayList<>();
			for(int[] play : plays) {
				if(!playedPositions.contains(positionKey(applyMove(data, play, player)))) newPlays.add(play);
			}
			return newPlays.isEmpty() ? plays : newPlays;
		}

		public int[][] applyMove(int[][] data, int[] play, int player) {
			int[][] newData = UtilArrayInt.clone2(data);
			newData[play[0]][play[1]] = 0;
			newData[play[2]][play[3]] = data[play[0]][play[1]];
			return newData;
		}

		public int oppositePlayer(int player) {
			return player==WHITE ? BLACK : WHITE;
		}

		public float evaluate(int[][] data, int player) {
			if(findPossiblePlays(player, data).isEmpty()) {
				boolean checked = player==WHITE ? whiteIsChecked(data) : blackIsChecked(data);
				return checked ? MATE_SCORE : 0f;
			}
			return materialScore(data, player) + positionScore(data, player);
		}

		private float materialScore(int[][] data, int player) {
			float whiteScore = 0f;
			float blackScore = 0f;
			for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
				int value = data[i][j];
				if(value==0) continue;
				float weight = pieceWeight(Math.abs(value));
				if(value>0) whiteScore += weight;
				else blackScore += weight;
			}
			return player==WHITE ? whiteScore-blackScore : blackScore-whiteScore;
		}

		private float positionScore(int[][] data, int player) {
			float whiteScore = 0f;
			float blackScore = 0f;
			for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
				int value = data[i][j];
				if(value==0) continue;
				float score = POSITION_WEIGHT*centrality(i, j);
				if(value>0) {
					if(value==WP) score += PAWN_ADVANCE_WEIGHT*(6-i);
					whiteScore += score;
				}
				else {
					if(value==BP) score += PAWN_ADVANCE_WEIGHT*(i-1);
					blackScore += score;
				}
			}
			return player==WHITE ? whiteScore-blackScore : blackScore-whiteScore;
		}

		private float centrality(int i, int j) {
			return (3.5f-Math.abs(i-3.5f)) + (3.5f-Math.abs(j-3.5f));
		}

		private float pieceWeight(int type) {
			switch(type) {
			case 1: return 1f;
			case 2: return 5f;
			case 3: return 3f;
			case 4: return 3f;
			case 5: return 9f;
			default: return 0f;
			}
		}
	}
}
