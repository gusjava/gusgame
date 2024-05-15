package gus.game5.main.game.reversi3;

import static gus.game5.main.game.reversi3.UtilReversi3.*;

import java.util.List;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.reversi3.GameReversi3.Cell;

public class PlayerHuman extends Player1 {
	
	private GameReversi3 game;
	
	public PlayerHuman(GameReversi3 game) {
		this.game = game;
	}

	public boolean play() {
		Cell c = game.getPressedCell();
		if(c==null) return false;
		
		List<int[]> play = findPlay(getValue(), game.boardData(), c.getIJ());
		if(play==null) return false;
		
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
		return "Human";
	}
}
