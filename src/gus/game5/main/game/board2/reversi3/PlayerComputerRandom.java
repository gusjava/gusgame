package gus.game5.main.game.board2.reversi3;

import gus.game5.core.play1.Player1;

import static gus.game5.main.game.board2.reversi3.UtilReversi3.*;

import java.util.List;

public class PlayerComputerRandom extends Player1 {
	
	private GameReversi3 game;
	
	public PlayerComputerRandom(GameReversi3 game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		List<int[]> play = UtilReversi3.randomPlay(getValue(), game.boardData());
		if(play==null) throw new RuntimeException("PlayerComputerRandom found no play");
		
		game.setValues(play, getValue());
		return true;
	}
	
	public int getValue() {
		return isFirst() ? WHITE : BLACK;
	}
	
	public String getDisplay() {
		return isFirst() ? "White" : "Black";
	}
	
	public String getType() {
		return "Random";
	}
}