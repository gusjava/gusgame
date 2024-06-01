package gus.game5.main.game.p2.board.chess2;

import static gus.game5.main.game.p2.board.chess2.UtilChess.*;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.p2.board.chess2.GameChess2.Cell;

public class PlayerChessHuman extends Player1 {
	
	private GameChess2 game;
	
	public PlayerChessHuman(GameChess2 game) {
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
		return isFirst() ? WHITE : BLACK;
	}
	
	public String getDisplay() {
		return isFirst() ? "White" : "Black";
	}
	
	public String getType() {
		return "Human";
	}
}
