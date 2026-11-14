package gus.game5.core.minmax;

import java.util.List;

public interface MinmaxGame<S,M> {

	List<M> possibleMoves(S state, int player);

	S applyMove(S state, M move, int player);

	int oppositePlayer(int player);

	float evaluate(S state, int player);
}
