package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.WALL_SIZE;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.applyPlay;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.bfsDistance;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.canPlaceWall;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.hasWon;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.opponent;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.possibleMoves;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gus.game5.core.minmax.MinmaxGame;
import gus.game5.core.minmax.UtilMinmax;

public class UtilQuoridorMinmax {

	public static final int DEPTH_MAX = 2;

	private static final int WALL_RANGE = 2;
	private static final float WIN_SCORE = 100000f;
	private static final float WALL_WEIGHT = 0.5f;

	private static final MinmaxGame<QuoridorState,QuoridorPlay> GAME = new QuoridorMinmaxGame();

	public static QuoridorPlay computePlay(int player, QuoridorState state) {
		return UtilMinmax.computePlay(GAME, player, state, DEPTH_MAX);
	}

	private static class QuoridorMinmaxGame implements MinmaxGame<QuoridorState,QuoridorPlay> {

		public List<QuoridorPlay> possibleMoves(QuoridorState state, int player) {
			List<QuoridorPlay> plays = new ArrayList<>();
			if(hasWon(state, player) || hasWon(state, opponent(player))) return plays;

			for(int[] move : UtilQuoridor.possibleMoves(state, player)) {
				plays.add(QuoridorPlay.move(move[0], move[1]));
			}
			if(state.wallsLeft(player)>0) {
				for(int i=0;i<WALL_SIZE;i++) for(int j=0;j<WALL_SIZE;j++) {
					if(!isNearPawn(state, i, j)) continue;
					if(canPlaceWall(state, i, j, true)) plays.add(QuoridorPlay.wall(i, j, true));
					if(canPlaceWall(state, i, j, false)) plays.add(QuoridorPlay.wall(i, j, false));
				}
			}
			Collections.shuffle(plays);
			return plays;
		}

		public QuoridorState applyMove(QuoridorState state, QuoridorPlay play, int player) {
			return applyPlay(state, play, player);
		}

		public int oppositePlayer(int player) {
			return opponent(player);
		}

		public float evaluate(QuoridorState state, int player) {
			int other = opponent(player);
			if(hasWon(state, player)) return WIN_SCORE;
			if(hasWon(state, other)) return -WIN_SCORE;

			int myDistance = bfsDistance(state, player);
			int otherDistance = bfsDistance(state, other);
			if(myDistance<0) return -WIN_SCORE;
			if(otherDistance<0) return WIN_SCORE;

			float walls = state.wallsLeft(player)-state.wallsLeft(other);
			return otherDistance-myDistance + WALL_WEIGHT*walls;
		}

		private boolean isNearPawn(QuoridorState state, int i, int j) {
			return isNear(state.pawn(UtilQuoridor.PAWN1), i, j) || isNear(state.pawn(UtilQuoridor.PAWN2), i, j);
		}

		private boolean isNear(int[] pawn, int i, int j) {
			return Math.abs(pawn[0]-i)<=WALL_RANGE && Math.abs(pawn[1]-j)<=WALL_RANGE;
		}
	}
}
