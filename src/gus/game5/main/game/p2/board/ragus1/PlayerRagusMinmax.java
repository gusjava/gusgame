package gus.game5.main.game.p2.board.ragus1;

import gus.game5.core.util.UtilArray;

public class PlayerRagusMinmax extends PlayerRagus {
	
	public PlayerRagusMinmax(GameRagus1 game, int value) {
		super(game, value);
	}

	public boolean play() {
//		if(game.getCount() < game.getLastPlayCount() + 50) return false;
		int[] play = UtilRagusMinmax.computePlay(getValue(), game.boardData());
		if(play==null) throw new RuntimeException("PlayerRagusMinmax found no play");
		
		int[] start = new int[] {play[0],play[1]};
		int[] end = new int[] {play[2],play[3]};

		int[][] newData = UtilRagus.attemptToPlay(getValue(), game.boardData(), start, end);
		if(newData==null) throw new RuntimeException("PlayerRagusMinmax found invalid play: "+UtilArray.toString(play));
		
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
		return "Min-Max";
	}
}
