package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN2;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.p2.c.board.quoridor.v1.GameQuoridor1.Cell;

public class PlayerQuoridorHuman extends Player1 {

	private GameQuoridor1 game;

	public PlayerQuoridorHuman(GameQuoridor1 game) {
		this.game = game;
	}

	public boolean play() {
		int[] wall = game.getWallIntersection();
		if(wall!=null) return game.attemptToPlaceWall(getValue(), wall[0], wall[1], game.isWallHorizontal());

		Cell pressedCell = game.getPressedCell();
		if(pressedCell!=null) {
			if(pressedCell.isPlayer(getValue())) game.setDragged(pressedCell);
			return false;
		}

		Cell releasedCell = game.getReleasedCell();
		if(releasedCell!=null) {
			Cell startCell = game.getDragged();
			game.setDragged(null);
			if(startCell==null) return false;
			return game.attemptToMove(getValue(), releasedCell.getI(), releasedCell.getJ());
		}
		return false;
	}

	public int getValue() {
		return isFirst() ? PAWN1 : PAWN2;
	}

	public String getDisplay() {
		return isFirst() ? "Yellow" : "Brown";
	}

	public String getType() {
		return "Human";
	}
}
