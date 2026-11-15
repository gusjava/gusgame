package gus.game5.main.game.p2.c.board.hex.v1;

import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.BLUE;
import static gus.game5.main.game.p2.c.board.hex.v1.UtilHex.RED;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.play1.Player1;

public class PlayerHexMinmax extends Player1 {

	private GameHex1 game;

	public PlayerHexMinmax(GameHex1 game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilHexMinmax.computePlay(getValue(), game.boardData());
		if(play==null) throw new TechnicalException("PlayerHexMinmax found no play");

		game.setValueAt(play, getValue());
		return true;
	}

	public int getValue() {
		return isFirst() ? RED : BLUE;
	}

	public String getDisplay() {
		return isFirst() ? "Red" : "Blue";
	}

	public String getType() {
		return "Min-Max";
	}
}
