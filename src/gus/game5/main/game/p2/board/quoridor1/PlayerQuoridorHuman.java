package gus.game5.main.game.p2.board.quoridor1;

import static gus.game5.main.game.p2.board.quoridor1.UtilQuoridor.*;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.p2.board.quoridor1.GameQuoridor1.Cell;

public class PlayerQuoridorHuman extends Player1 {
	
	private GameQuoridor1 game;
	
	public PlayerQuoridorHuman(GameQuoridor1 game) {
		this.game = game;
	}

	public boolean play() {
		Cell pressedCell = game.getPressedCell();
		if(pressedCell!=null) {
			if(pressedCell.isPlayer(getValue())) game.setDragged(pressedCell);
			return false;
		}
		Cell releasedCell = game.getReleasedCell();
		if(releasedCell!=null) {
			Cell startCell = game.getDragged();
			Cell endCell = releasedCell;
			
			game.setDragged(null);
			if(startCell==null) return false;
			
			int[] start = startCell.getIJ();
			int[] end = endCell.getIJ();
			
			return game.attemptToPlay(getValue(), start, end);
		}
		return false;
	}
	
	public int getValue() {
		return isFirst() ? PAWN1 : PAWN2;
	}
	
	public String getDisplay() {
		return isFirst() ? "Pawn 1" : "Pawn 2";
	}
	
	public String getType() {
		return "Human";
	}
}
