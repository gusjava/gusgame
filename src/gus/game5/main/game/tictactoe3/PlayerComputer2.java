package gus.game5.main.game.tictactoe3;

import gus.game5.core.play1.Player1;
import static gus.game5.main.game.tictactoe3.UtilTTT3.*;

public class PlayerComputer2 extends Player1 {
	
	private GameTicTacToe3 game;
	
	public PlayerComputer2(GameTicTacToe3 game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilTTT3.randomPlay(game.boardData());
		game.setValue(play[0], play[1], getValue());
		return true;
	}
	
	public int getValue() {
		return isFirst() ? CIRCLE : CROSS;
	}
	
	public String getDisplay() {
		return isFirst() ? "Circle" : "Cross";
	}
}
