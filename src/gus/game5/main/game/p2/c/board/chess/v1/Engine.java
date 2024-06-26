package gus.game5.main.game.p2.c.board.chess.v1;

import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.INIT_STATE;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.blackIsChecked;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.whiteIsChecked;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArrayBoolean;
import gus.game5.core.util.UtilArrayInt;


public class Engine {

	private GameOver gameOver;
	private EPlayer player;
	
	private int[][] data;
	private List<int[][]> history;
	private boolean[][] changed;
	private EState whiteState;
	private EState blackState;
	
	public Engine() {
		gameOver = null;
		player = EPlayer.WHITE;
		
		data = UtilArrayInt.clone2(INIT_STATE);
		history = new ArrayList<>();
		history.add(UtilArrayInt.clone2(data));
		
		changed = UtilArrayBoolean.build2(8, false);
		whiteState = EState.SAFE;
		blackState = EState.SAFE;
	}
	
	public EPlayer getPlayer() {
		return player;
	}
	
	public int[][] getData() {
		return data;
	}
	
	public boolean whiteChecked() {
		return whiteState.isChecked();
	}
	
	public boolean blackChecked() {
		return blackState.isChecked();
	}
	
	public boolean whiteMate() {
		return whiteState.isMate();
	}
	
	public boolean blackMate() {
		return blackState.isMate();
	}

	public boolean isPlayerChecked() {
		return player.isWhite() ? whiteChecked() : blackChecked();
	}

	public boolean isPlayerMate() {
		return player.isWhite() ? whiteMate() : blackMate();
	}
	
	public GameOver getGameOver() {
		return gameOver;
	}
	
	public void shiftPlayer() {
		player = player.opposite();
	}
	
	public boolean attemptToPlay(int[] start, int[] end) {
		final int[][] data0 = historyDataAt(0);
		final int[][] data1 = historyDataAt(1);
		
		EMove move = new MoveBuilder(data0, data1, start, end).build();
		if(move==null) return false;
		boolean done = new MoveExecutor(data, changed, move, start, end).execute();
		if(!done) return false;

		if(player.isWhite()) {
			if(whiteIsChecked(data)) {
				data = UtilArrayInt.clone2(data0);
				return false;
			}
			whiteState = EState.SAFE;
			blackState = new CheckerB(data, data0).check();
			if(blackState.isMate()) gameOver = new GameOver(player);
		}
		else {
			if(blackIsChecked(data)) {
				data = UtilArrayInt.clone2(data0);
				return false;
			}
			blackState = EState.SAFE;
			whiteState = new CheckerW(data, data0).check();
			if(whiteState.isMate()) gameOver = new GameOver(player);
		}
		
		if(UtilArrayInt.count(data, 0)==62) {
			//only 2 kings left
			gameOver = new GameOver(null);
		}

		updateChanged(data0);
		history.add(UtilArrayInt.clone2(data));
		return true;
	}
	
	private int[][] historyDataAt(int p) {
		int size = history.size();
		return p<size ? history.get(size-p-1) : null;
	}
	
	private void updateChanged(int[][] state0) {
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			if(data[i][j]!=state0[i][j]) changed[i][j] = true;
		}
	}
}
