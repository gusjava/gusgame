package gus.game5.main.game.p2.board.ragus1;

import gus.game5.main.game.p2.board.ragus1.GameRagus1.Cell;

public class PlayerRagusHuman extends PlayerRagus {
	
	public PlayerRagusHuman(GameRagus1 game, int value) {
		super(game, value);
	}

	public boolean play() {
		Cell pressedCell = game.getPressedCell();
		if(pressedCell!=null) {
			if(UtilRagus.isPlayable(getValue(), game.boardData(), pressedCell.getIJ())) 
				game.setDragged(pressedCell);
			return false;
		}
		Cell releasedCell = game.getReleasedCell();
		if(releasedCell!=null) {
			Cell startCell = game.getDragged();
			Cell endCell = releasedCell;
			
			game.setDragged(null);
			if(startCell==null) return false;
			
			int[] start = startCell.getIJ();
			int[] end = endCell.getIJ();
			
			int[][] newData = UtilRagus.attemptToPlay(getValue(), game.boardData(), start, end);
			if(newData==null) return false;
			
			for(int j=0;j<8;j++) {
				if(newData[getTargetIndex()][j]*getValue()>0) {
					newData[getTargetIndex()][j] = 0;
					incrScore();
				}
			}
			
			game.updateBoard(newData);
			return true;
		}
		return false;
	}
	
	public String getType() {
		return "Human";
	}
}
