package gus.game5.main.game.board2.hex1;

import gus.game5.core.play1.Player1;
import gus.game5.main.game.board2.hex1.GameHex.Cell;
import static gus.game5.main.game.board2.hex1.UtilHex.*;

public class PlayerHuman extends Player1 {
	
	private GameHex game;
	
	public PlayerHuman(GameHex game) {
		this.game = game;
	}

	public boolean play() {
		Cell c = game.getPressedCell();
		if(c==null) return false;
		
		int[] play = findPlay(game.boardData(), c.getIJ());
		if(play==null) return false;
		
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
		return "Human";
	}
}
