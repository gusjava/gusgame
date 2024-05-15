package gus.game5.main.game.tictactoe3;

import gus.game5.core.play1.Player1;
import static gus.game5.main.game.tictactoe3.UtilTTT3.*;

public class PlayerComputerMinmax extends Player1 {
	
	private GameTicTacToe3 game;
	
	public PlayerComputerMinmax(GameTicTacToe3 game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilTTT3Minmax.computePlay(getValue(), game.boardData());
		if(play==null) throw new RuntimeException("PlayerComputerMinmax found no play");
		
		game.setValueAt(play[0], getValue());
		return true;
	}
	
	public int getValue() {
		return isFirst() ? NOUGHT : CROSS;
	}
	
	public String getDisplay() {
		return isFirst() ? "Nought" : "Cross";
	}
	
	public String getType() {
		return "Min-Max";
	}
}
