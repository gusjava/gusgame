package gus.game5.main.game.chess;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArray;
import static gus.game5.main.game.chess.UtilChess.*;


public class Engine {

	private GameOver gameOver;
	private Player player;
	private boolean[][] moved;
	private boolean wkiChecked;
	private boolean bkiChecked;
	
	private int[][] data;
	private List<int[][]> history;
	
	public Engine() {
		gameOver = null;
		player = Player.WHITE;
		
		data = UtilArray.clone(INIT_STATE);
		history = new ArrayList<>();
		history.add(UtilArray.clone(data));
		
		moved = UtilArray.boolArray2(8, false);
		wkiChecked = false;
		bkiChecked = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int[][] getData() {
		return data;
	}
	
	public boolean wkiChecked() {
		return wkiChecked;
	}
	
	public boolean bkiChecked() {
		return bkiChecked;
	}

	public boolean isPlayerChecked() {
		return player.isWhite() ? wkiChecked : bkiChecked;
	}
	
	public boolean isGameOver() {
		return gameOver!=null;
	}
	
	public void shiftPlayer() {
		player = player.opposite();
	}
	
	public boolean attemptToPlay(int[] start, int[] end) {
		final int[][] data0 = historyDataAt(0);
		final int[][] data1 = historyDataAt(1);
		
		Move move = computeMove(data0, data1, start, end);
		if(move==null) return false;
		
		boolean done = performMove(move, start, end);
		if(!done) return false;

		if(player.isWhite()) {
			if(whiteKingIsChecked(data)) {
				data = UtilArray.clone(data0);
				return false;
			}
			wkiChecked = false;
			bkiChecked = blackKingIsChecked(data);
		}
		else {
			if(blackKingIsChecked(data)) {
				data = UtilArray.clone(data0);
				return false;
			}
			bkiChecked = false;
			wkiChecked = whiteKingIsChecked(data);
		}

		checkMoved(data0);
		history.add(UtilArray.clone(data));
		return true;
	}
	
	private boolean performMove(Move move, int[] start, int[] end) {
		switch(move) {
		case EAT:
			setValueAt(end, valueAt(start));
			setValueAt(start,0);
			return true;
			
		case EN_PASSANT:
			setValueAt(end, valueAt(start));
			setValueAt(start,0);
			setValueAt(start[0], end[1], 0);
			return true;
			
		case CASTLING:
			if(is(end,7,6)) {
				if(moved[7][7] || moved[7][4]) return false;
				if(blackCanAttack(data, 7, 4)) return false;
				if(blackCanAttack(data, 7, 5)) return false;
				if(blackCanAttack(data, 7, 6)) return false;
				if(blackCanAttack(data, 7, 7)) return false;
				setValueAt(7,7,0);
				setValueAt(7,5,WR);
				setValueAt(end, valueAt(start));
				setValueAt(start,0);
				return true;
			}
			if(is(end,7,2)) {
				if(moved[7][0] || moved[7][4]) return false;
				if(blackCanAttack(data, 7, 0)) return false;
				if(blackCanAttack(data, 7, 1)) return false;
				if(blackCanAttack(data, 7, 2)) return false;
				if(blackCanAttack(data, 7, 3)) return false;
				if(blackCanAttack(data, 7, 4)) return false;
				setValueAt(7,0,0);
				setValueAt(7,3,WR);
				setValueAt(end, valueAt(start));
				setValueAt(start,0);
				return true;
			}
			if(is(end,0,6)) {
				if(moved[0][7] || moved[0][4]) return false;
				if(whiteCanAttack(data, 0, 4)) return false;
				if(whiteCanAttack(data, 0, 5)) return false;
				if(whiteCanAttack(data, 0, 6)) return false;
				if(whiteCanAttack(data, 0, 7)) return false;
				setValueAt(0,7,0);
				setValueAt(0,5,WR);
				setValueAt(end, valueAt(start));
				setValueAt(start,0);
				return true;
			}
			if(is(end,0,2)) {
				if(moved[0][0] || moved[0][4]) return false;
				if(whiteCanAttack(data, 0, 0)) return false;
				if(whiteCanAttack(data, 0, 1)) return false;
				if(whiteCanAttack(data, 0, 2)) return false;
				if(whiteCanAttack(data, 0, 3)) return false;
				if(whiteCanAttack(data, 0, 4)) return false;
				setValueAt(0,0,0);
				setValueAt(0,3,WR);
				setValueAt(end, valueAt(start));
				setValueAt(start,0);
				return true;
			}
			return false;
		case PROMOTION:
			setValueAt(end, player.isWhite() ? WQ : BQ);
			setValueAt(start,0);
			return true;
		}
		return false;
	}
	
	private int[][] historyDataAt(int p) {
		int size = history.size();
		return p<size ? history.get(size-p-1) : null;
	}
	
	private void checkMoved(int[][] state0) {
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			if(data[i][j]!=state0[i][j]) moved[i][j] = true;
		}
	}
	
	private void setValueAt(int[] pos, int value) {
		data[pos[0]][pos[1]] = value;
	}
	
	private void setValueAt(int x, int y, int value) {
		data[x][y] = value;
	}
	
	private int valueAt(int[] pos) {
		return data[pos[0]][pos[1]];
	}
	
	private boolean is(int[] pos, int x, int y) {
		return pos[0]==x && pos[1]==y;
	}
}
