package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN2;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.possiblePlays;

import java.util.List;
import java.util.Random;

import gus.game5.core.play1.Player1;

public class PlayerQuoridorRandom extends Player1 {

	private static final Random RANDOM = new Random();

	private GameQuoridor1 game;

	public PlayerQuoridorRandom(GameQuoridor1 game) {
		this.game = game;
	}

	public boolean play() throws Exception {
		List<QuoridorPlay> plays = possiblePlays(game.getState(), getValue());
		if(plays.isEmpty()) throw new Exception("PlayerQuoridorRandom found no play");

		QuoridorPlay play = plays.get(RANDOM.nextInt(plays.size()));
		return game.attemptToPlay(getValue(), play);
	}

	public int getValue() {
		return isFirst() ? PAWN1 : PAWN2;
	}

	public String getDisplay() {
		return isFirst() ? "Yellow" : "Brown";
	}

	public String getType() {
		return "Random";
	}
}
