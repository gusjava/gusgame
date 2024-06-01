package gus.game5.main.game.p2.board.chess2;

import static gus.game5.main.game.p2.board.chess1.UtilChess.*;

import gus.game5.core.util.UtilArray;

public class CheckerB {
	
	private int[][] data;
	private int[][] data0;

	public CheckerB(int[][] data, int[][] data0) {
		this.data = data;
		this.data0 = data0;
	}
	
	public EState check() {
		int[] king = UtilArray.find(data, BKI);
		if(!whiteCanAttack(data, king)) return EState.SAFE;
		
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			// black king
			if(data[i][j]==BKI) {
				if(resolveWithKing(i, j, 1, -1)) return checked();
				if(resolveWithKing(i, j, 1, 0)) return checked();
				if(resolveWithKing(i, j, 1, 1)) return checked();
				if(resolveWithKing(i, j, 0, -1)) return checked();
				if(resolveWithKing(i, j, 0, 1)) return checked();
				if(resolveWithKing(i, j, -1, -1)) return checked();
				if(resolveWithKing(i, j, -1, 0)) return checked();
				if(resolveWithKing(i, j, -1, 1)) return checked();
			}
			// black pawn
			else if(data[i][j]==BP) {
				if(isEmpty(data, i+1, j)) {
					if(!whiteCanAttack(newData(i, j, i+1, j), king)) return checked();
					if(i==1 && isEmpty(data, 3, j)) {
						if(!whiteCanAttack(newData(1, j, 3, j), king)) return checked();
					}
				}
				if(isWhite(data, i+1, j-1)) {
					if(!whiteCanAttack(newData(i, j, i+1, j-1), king)) return checked();
				}
				if(isWhite(data, i+1, j+1)) {
					if(!whiteCanAttack(newData(i, j, i+1, j+1), king)) return checked();
				}
				if(i==3) {
					// en passant
					if(is(data, 3, j-1, WP) && isEmpty(data, 1, j-1) && isEmpty(data0, 3, j-1) && is(data0, 1, j-1, WP)) {
						int[][] newData = data(newData(3, j, 4, j-1), 3, j-1, 0);
						if(!whiteCanAttack(newData, king)) return checked();
					}
					// en passant
					if(is(data, 3, j+1, WP) && isEmpty(data, 1, j+1) && isEmpty(data0, 3, j+1) && is(data0, 1, j+1, WP)) {
						int[][] newData = data(newData(3, j, 4, j+1), 3, j+1, 0);
						if(!whiteCanAttack(newData, king)) return checked();
					}
				}
			}
			// black knight
			else if(data[i][j]==BK) {
				if(isEmptyOrWhite(data, i+1, j+2)) {
					if(!whiteCanAttack(newData(i, j, i+1, j+2), king)) return checked();
				}
				if(isEmptyOrWhite(data, i+1, j-2)) {
					if(!whiteCanAttack(newData(i, j, i+1, j-2), king)) return checked();
				}
				if(isEmptyOrWhite(data, i-1, j+2)) {
					if(!whiteCanAttack(newData(i, j, i-1, j+2), king)) return checked();
				}
				if(isEmptyOrWhite(data, i-1, j-2)) {
					if(!whiteCanAttack(newData(i, j, i-1, j-2), king)) return checked();
				}
				if(isEmptyOrWhite(data, i+2, j+1)) {
					if(!whiteCanAttack(newData(i, j, i+2, j+1), king)) return checked();
				}
				if(isEmptyOrWhite(data, i+2, j-1)) {
					if(!whiteCanAttack(newData(i, j, i+2, j-1), king)) return checked();
				}
				if(isEmptyOrWhite(data, i-2, j+1)) {
					if(!whiteCanAttack(newData(i, j, i-2, j+1), king)) return checked();
				}
				if(isEmptyOrWhite(data, i-2, j-1)) {
					if(!whiteCanAttack(newData(i, j, i-2, j-1), king)) return checked();
				}
			}
			// black bishop
			else if(data[i][j]==BB) {
				int m = 1;
				while(isEmpty(data, i+m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j+m), king)) return checked();
				}
			}
			// black rook
			else if(data[i][j]==BR) {
				int m = 1;
				while(isEmpty(data, i+m, j)) {
					if(!whiteCanAttack(newData(i, j, i+m, j), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j)) {
					if(!whiteCanAttack(newData(i, j, i+m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j-m)) {
					if(!whiteCanAttack(newData(i, j, i, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i, j-m)) {
					if(!whiteCanAttack(newData(i, j, i, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j)) {
					if(!whiteCanAttack(newData(i, j, i-m, j), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j)) {
					if(!whiteCanAttack(newData(i, j, i-m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j+m)) {
					if(!whiteCanAttack(newData(i, j, i, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i, j+m)) {
					if(!whiteCanAttack(newData(i, j, i, j+m), king)) return checked();
				}
			}
			// black queen
			else if(data[i][j]==BQ) {
				int m = 1;
				while(isEmpty(data, i+m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i+m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j-m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j+m)) {
					if(!whiteCanAttack(newData(i, j, i-m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j)) {
					if(!whiteCanAttack(newData(i, j, i+m, j), king)) return checked();
					m++;
				}
				if(isWhite(data, i+m, j)) {
					if(!whiteCanAttack(newData(i, j, i+m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j-m)) {
					if(!whiteCanAttack(newData(i, j, i, j-m), king)) return checked();
					m++;
				}
				if(isWhite(data, i, j-m)) {
					if(!whiteCanAttack(newData(i, j, i, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j)) {
					if(!whiteCanAttack(newData(i, j, i-m, j), king)) return checked();
					m++;
				}
				if(isWhite(data, i-m, j)) {
					if(!whiteCanAttack(newData(i, j, i-m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j+m)) {
					if(!whiteCanAttack(newData(i, j, i, j+m), king)) return checked();
					m++;
				}
				if(isWhite(data, i, j+m)) {
					if(!whiteCanAttack(newData(i, j, i, j+m), king)) return checked();
				}
			}
		}
		return EState.MATE;
	}
	
	
	private boolean resolveWithKing(int i1, int j1, int di, int dj) {
		int i2 = i1+di;
		int j2 = j1+dj;
		if(!isValid(i2, j2) || isBlack(data, i2, j2)) return false;
		int[][] newData = newData(i1, j1, i2, j2);
		return !whiteCanAttack(newData, i2, j2);
	}
	
	private int[][] newData(int i1, int j1, int i2, int j2){
		int[][] newData = new int[8][8];
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			newData[i][j] = (i==i1 && j==j1) ? 0 : (i==i2 && j==j2) ? data[i1][j1] : data[i][j];
		}
		return newData;
	}
	
	private int[][] data(int[][] data, int i, int j, int v) {
		data[i][j] = v;
		return data;
	}
	
	private EState checked() {
		return EState.CHECKED;
	}
}
