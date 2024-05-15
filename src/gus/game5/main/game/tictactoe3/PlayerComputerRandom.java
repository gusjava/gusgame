package gus.game5.main.game.tictactoe3;

import gus.game5.core.play1.Player1;
import static gus.game5.main.game.tictactoe3.UtilTTT3.*;

public class PlayerComputerRandom extends Player1 {
	
	private GameTicTacToe3 game;
	
	public PlayerComputerRandom(GameTicTacToe3 game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilTTT3.randomPlay(game.boardData());
		if(play==null) throw new RuntimeException("PlayerComputerRandom found no play");
		
		game.setValueAt(play[0], getValue());
		return true;
	}
	
	public int getValue() {
		return isFirst() ? NOUGHT : CROSS;
	}
	
	public String getDisplay() {
		return isFirst() ? "Circle" : "Cross";
	}
	
	public String getType() {
		return "Random";
	}
}
