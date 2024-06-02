package gus.game5.main.game.p2.c.board.chess.v2;

import static gus.game5.main.game.p2.c.board.chess.v2.UtilChess.*;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArray;


public class Engine {

	private int winner;
	private int player;
	
	private int[][] data;
	private List<int[][]> history;
	private boolean[][] changed;
	private EState whiteState;
	private EState blackState;
	
	public Engine() {
		winner = -1;
		
		data = UtilArray.clone(INIT_STATE);
		history = new ArrayList<>();
		history.add(UtilArray.clone(data));
		
		changed = UtilArray.boolArray2(8, false);
		whiteState = EState.SAFE;
		blackState = EState.SAFE;
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
		return player==WHITE ? whiteChecked() : blackChecked();
	}

	public boolean isPlayerMate() {
		return player==WHITE ? whiteMate() : blackMate();
	}
	
	public int getWinner() {
		return winner;
	}
	
	public boolean attemptToPlay(int player, int[] start, int[] end) {
		this.player = player;
		
		final int[][] data0 = historyDataAt(0);
		final int[][] data1 = historyDataAt(1);
		
		EMove move = new MoveBuilder(data0, data1, start, end).build();
		if(move==null) return false;
		boolean done = new MoveExecutor(data, changed, move, start, end).execute();
		if(!done) return false;

		if(player==WHITE) {
			if(whiteIsChecked(data)) {
				data = UtilArray.clone(data0);
				return false;
			}
			whiteState = EState.SAFE;
			blackState = new CheckerB(data, data0).check();
			if(blackState.isMate()) winner = player;
		}
		else {
			if(blackIsChecked(data)) {
				data = UtilArray.clone(data0);
				return false;
			}
			blackState = EState.SAFE;
			whiteState = new CheckerW(data, data0).check();
			if(whiteState.isMate()) winner = player;
		}
		
		if(UtilArray.count(data, 0)==62) {
			//only 2 kings left
			winner = 0;
		}

		updateChanged(data0);
		history.add(UtilArray.clone(data));
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
