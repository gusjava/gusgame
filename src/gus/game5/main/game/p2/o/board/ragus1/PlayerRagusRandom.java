package gus.game5.main.game.p2.o.board.ragus1;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.util.UtilArrayInt;

public class PlayerRagusRandom extends PlayerRagus {
	
	public PlayerRagusRandom(GameRagus1 game, int value) {
		super(game, value);
	}

	public boolean play() {
//		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilRagus.randomPlay(getValue(), game.boardData());
		if(play==null) throw new TechnicalException("PlayerRagusRandom found no play");
		
		int[] start = new int[] {play[0],play[1]};
		int[] end = new int[] {play[2],play[3]};

		int[][] newData = UtilRagus.attemptToPlay(getValue(), game.boardData(), start, end);
		if(newData==null) throw new TechnicalException("PlayerRagusRandom found invalid play: "+UtilArrayInt.toString(play));
		
		for(int j=0;j<8;j++) {
			if(newData[getTargetIndex()][j]*getValue()>0) {
				newData[getTargetIndex()][j] = 0;
				incrScore();
			}
		}
		
		game.updateBoard(newData);
		return true;
	}
	
	public String getType() {
		return "Random";
	}
}
