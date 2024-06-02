package gus.game5.main.game.p2.c.board.tictactoe.v3;

import static gus.game5.main.game.p2.c.board.tictactoe.v3.UtilTTT3.*;

import gus.game5.core.play1.Player1;

public class PlayerComputerMinmax extends Player1 {
	
	private GameTicTacToe3 game;
	
	public PlayerComputerMinmax(GameTicTacToe3 game) {
		this.game = game;
	}

	public boolean play() throws Exception {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilTTT3Minmax.computePlay(getValue(), game.boardData());
		if(play==null) throw new Exception("PlayerComputerMinmax found no play");
		
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
