package gus.game5.main.game.board2.chess2;

import gus.game5.core.play1.Player1;
import static gus.game5.main.game.board2.chess2.UtilChess.*;

public class PlayerComputerRandom extends Player1 {
	
	private GameChess2 game;
	
	public PlayerComputerRandom(GameChess2 game) {
		this.game = game;
	}

	public boolean play() throws Exception {
//		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilChess.randomPlay(getValue(), game.boardData());
		if(play==null) throw new Exception("PlayerComputerRandom found no play");

		int[] start = new int[] {play[0], play[1]};
		int[] end = new int[] {play[2], play[3]};
		
		game.attemptToPlay(getValue(), start, end);
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
