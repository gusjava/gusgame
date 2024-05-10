package gus.game5.main.game.tictactoe3;

import static gus.game5.main.game.tictactoe3.UtilTTT3.CIRCLE;
import static gus.game5.main.game.tictactoe3.UtilTTT3.CROSS;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.tictactoe3.GameTicTacToe3.Cell;

public class PlayerHuman extends Player1 {
	
	private GameTicTacToe3 game;
	
	public PlayerHuman(GameTicTacToe3 game) {
		this.game = game;
	}

	public boolean play() {
		Cell c = game.getPressedCell();
		if(c==null || c.getValue()!=0) return false;
		c.setValue(getValue());
		return true;
	}
	
	public int getValue() {
		return isFirst() ? CIRCLE : CROSS;
	}
	
	public String getDisplay() {
		return isFirst() ? "Circle" : "Cross";
	}

}
