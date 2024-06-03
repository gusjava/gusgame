package gus.game5.main.game.p2.c.board.chess.v2;

import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.BP;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WB;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WK;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WKI;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WP;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WQ;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.WR;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.blackCanAttack;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.is;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.isBlack;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.isEmpty;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.isEmptyOrBlack;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.isValid;
import static gus.game5.main.game.p2.c.board.chess.v1.UtilChess.isWhite;

import gus.game5.core.util.UtilArrayInt;

public class CheckerW {
	
	private int[][] data;
	private int[][] data0;

	public CheckerW(int[][] data, int[][] data0) {
		this.data = data;
		this.data0 = data0;
	}
	
	public EState check() {
		int[] king = UtilArrayInt.find(data, WKI);
		if(!blackCanAttack(data, king)) return EState.SAFE;
		
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			// white king
			if(data[i][j]==WKI) {
				if(resolveWithKing(i, j, 1, -1)) return checked();
				if(resolveWithKing(i, j, 1, 0)) return checked();
				if(resolveWithKing(i, j, 1, 1)) return checked();
				if(resolveWithKing(i, j, 0, -1)) return checked();
				if(resolveWithKing(i, j, 0, 1)) return checked();
				if(resolveWithKing(i, j, -1, -1)) return checked();
				if(resolveWithKing(i, j, -1, 0)) return checked();
				if(resolveWithKing(i, j, -1, 1)) return checked();
			}
			// white pawn
			else if(data[i][j]==WP) {
				if(isEmpty(data, i-1, j)) {
					if(!blackCanAttack(newData(i, j, i-1, j), king)) return checked();
					if(i==6 && isEmpty(data, 4, j)) {
						if(!blackCanAttack(newData(6, j, 4, j), king)) return checked();
					}
				}
				if(isWhite(data, i-1, j-1)) {
					if(!blackCanAttack(newData(i, j, i-1, j-1), king)) return checked();
				}
				if(isWhite(data, i-1, j+1)) {
					if(!blackCanAttack(newData(i, j, i-1, j+1), king)) return checked();
				}
				if(i==4) {
					// en passant
					if(is(data, 4, j-1, WP) && isEmpty(data, 6, j-1) && isEmpty(data0, 4, j-1) && is(data0, 6, j-1, BP)) {
						int[][] newData = data(newData(4, j, 3, j-1), 4, j-1, 0);
						if(!blackCanAttack(newData, king)) return checked();
					}
					// en passant
					if(is(data, 4, j+1, WP) && isEmpty(data, 6, j+1) && isEmpty(data0, 4, j+1) && is(data0, 6, j+1, BP)) {
						int[][] newData = data(newData(4, j, 3, j+1), 4, j+1, 0);
						if(!blackCanAttack(newData, king)) return checked();
					}
				}
			}
			// white knight
			else if(data[i][j]==WK) {
				if(isEmptyOrBlack(data, i+1, j+2)) {
					if(!blackCanAttack(newData(i, j, i+1, j+2), king)) return checked();
				}
				if(isEmptyOrBlack(data, i+1, j-2)) {
					if(!blackCanAttack(newData(i, j, i+1, j-2), king)) return checked();
				}
				if(isEmptyOrBlack(data, i-1, j+2)) {
					if(!blackCanAttack(newData(i, j, i-1, j+2), king)) return checked();
				}
				if(isEmptyOrBlack(data, i-1, j-2)) {
					if(!blackCanAttack(newData(i, j, i-1, j-2), king)) return checked();
				}
				if(isEmptyOrBlack(data, i+2, j+1)) {
					if(!blackCanAttack(newData(i, j, i+2, j+1), king)) return checked();
				}
				if(isEmptyOrBlack(data, i+2, j-1)) {
					if(!blackCanAttack(newData(i, j, i+2, j-1), king)) return checked();
				}
				if(isEmptyOrBlack(data, i-2, j+1)) {
					if(!blackCanAttack(newData(i, j, i-2, j+1), king)) return checked();
				}
				if(isEmptyOrBlack(data, i-2, j-1)) {
					if(!blackCanAttack(newData(i, j, i-2, j-1), king)) return checked();
				}
			}
			// white bishop
			else if(data[i][j]==WB) {
				int m = 1;
				while(isEmpty(data, i+m, j+m)) {
					if(!blackCanAttack(newData(i, j, i+m, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j+m)) {
					if(!blackCanAttack(newData(i, j, i+m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j-m)) {
					if(!blackCanAttack(newData(i, j, i+m, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j-m)) {
					if(!blackCanAttack(newData(i, j, i+m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j-m)) {
					if(!blackCanAttack(newData(i, j, i-m, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j-m)) {
					if(!blackCanAttack(newData(i, j, i-m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j+m)) {
					if(!blackCanAttack(newData(i, j, i-m, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j+m)) {
					if(!blackCanAttack(newData(i, j, i-m, j+m), king)) return checked();
				}
			}
			// white rook
			else if(data[i][j]==WR) {
				int m = 1;
				while(isEmpty(data, i+m, j)) {
					if(!blackCanAttack(newData(i, j, i+m, j), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j)) {
					if(!blackCanAttack(newData(i, j, i+m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j-m)) {
					if(!blackCanAttack(newData(i, j, i, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i, j-m)) {
					if(!blackCanAttack(newData(i, j, i, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j)) {
					if(!blackCanAttack(newData(i, j, i-m, j), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j)) {
					if(!blackCanAttack(newData(i, j, i-m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j+m)) {
					if(!blackCanAttack(newData(i, j, i, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i, j+m)) {
					if(!blackCanAttack(newData(i, j, i, j+m), king)) return checked();
				}
			}
			// white queen
			else if(data[i][j]==WQ) {
				int m = 1;
				while(isEmpty(data, i+m, j+m)) {
					if(!blackCanAttack(newData(i, j, i+m, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j+m)) {
					if(!blackCanAttack(newData(i, j, i+m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j-m)) {
					if(!blackCanAttack(newData(i, j, i+m, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j-m)) {
					if(!blackCanAttack(newData(i, j, i+m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j-m)) {
					if(!blackCanAttack(newData(i, j, i-m, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j-m)) {
					if(!blackCanAttack(newData(i, j, i-m, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j+m)) {
					if(!blackCanAttack(newData(i, j, i-m, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j+m)) {
					if(!blackCanAttack(newData(i, j, i-m, j+m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i+m, j)) {
					if(!blackCanAttack(newData(i, j, i+m, j), king)) return checked();
					m++;
				}
				if(isBlack(data, i+m, j)) {
					if(!blackCanAttack(newData(i, j, i+m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j-m)) {
					if(!blackCanAttack(newData(i, j, i, j-m), king)) return checked();
					m++;
				}
				if(isBlack(data, i, j-m)) {
					if(!blackCanAttack(newData(i, j, i, j-m), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i-m, j)) {
					if(!blackCanAttack(newData(i, j, i-m, j), king)) return checked();
					m++;
				}
				if(isBlack(data, i-m, j)) {
					if(!blackCanAttack(newData(i, j, i-m, j), king)) return checked();
				}
				m = 1;
				while(isEmpty(data, i, j+m)) {
					if(!blackCanAttack(newData(i, j, i, j+m), king)) return checked();
					m++;
				}
				if(isBlack(data, i, j+m)) {
					if(!blackCanAttack(newData(i, j, i, j+m), king)) return checked();
				}
			}
		}
		return EState.MATE;
	}
	
	
	private boolean resolveWithKing(int i1, int j1, int di, int dj) {
		int i2 = i1+di;
		int j2 = j1+dj;
		if(!isValid(i2, j2) || isWhite(data, i2, j2)) return false;
		int[][] newData = newData(i1, j1, i2, j2);
		return !blackCanAttack(newData, i2, j2);
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
