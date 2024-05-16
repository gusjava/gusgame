package gus.game5.main.game.board2.hex1;

import static gus.game5.main.game.board2.hex1.UtilHex.BLUE;
import static gus.game5.main.game.board2.hex1.UtilHex.RED;

import gus.game5.core.play1.Player1;

public class PlayerComputerRandom extends Player1 {
	
	private GameHex game;
	
	public PlayerComputerRandom(GameHex game) {
		this.game = game;
	}

	public boolean play() {
		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilHex.randomPlay(game.boardData());
		if(play==null) throw new RuntimeException("PlayerComputerRandom found no play");
		
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
		return "Random";
	}
}
