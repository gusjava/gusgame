package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN2;

import gus.game5.core.play1.Player1;

public class PlayerQuoridorMinmax extends Player1 {

	private GameQuoridor1 game;

	public PlayerQuoridorMinmax(GameQuoridor1 game) {
		this.game = game;
	}

	public boolean play() throws Exception {
		QuoridorPlay play = UtilQuoridorMinmax.computePlay(getValue(), game.getState());
		if(play==null) throw new Exception("PlayerQuoridorMinmax found no play");
		return game.attemptToPlay(getValue(), play);
	}

	public int getValue() {
		return isFirst() ? PAWN1 : PAWN2;
	}

	public String getDisplay() {
		return isFirst() ? "Yellow" : "Brown";
	}

	public String getType() {
		return "Min-Max";
	}
}
