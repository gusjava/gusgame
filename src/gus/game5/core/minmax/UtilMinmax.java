package gus.game5.core.minmax;

import java.util.List;

public class UtilMinmax {

	public static <S,M> M computePlay(MinmaxGame<S,M> game, int player, S state, int depthMax) {
		Result<M> state0 = alphaBeta(game, player, state, 0, depthMax, 1000000.0f, -1000000.0f);
		return state0.move;
	}

	private static <S,M> Result<M> alphaBeta(MinmaxGame<S,M> game, int player, S state, int depth, int depthMax, float alpha, float beta) {
		List<M> moves = game.possibleMoves(state, player);
		if(moves.isEmpty() || depth==depthMax) {
			Result<M> result = new Result<>();
			result.fitness = game.evaluate(state, player);
			return result;
		}

		int oppositePlayer = game.oppositePlayer(player);
		Result<M> best = new Result<>();
		best.fitness = beta;

		for(int i=0; i<moves.size(); i++) {
			M move = moves.get(i);

			S nextState = game.applyMove(state, move, player);

			Result<M> next = alphaBeta(game, oppositePlayer, nextState, depth+1, depthMax, -best.fitness, -alpha);
			float value = -next.fitness;

			if(value > best.fitness) {
				best.move = move;
				best.fitness = value;
			}
			if(best.fitness >= alpha) break;
		}
		return best;
	}

	private static class Result<M> {
		public M move;
		public float fitness;
	}
}
