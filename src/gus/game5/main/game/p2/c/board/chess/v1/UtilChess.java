package gus.game5.main.game.p2.c.board.chess.v1;

import gus.game5.core.util.UtilArray;

public class UtilChess {
	
	// CHESS PIECES
	
	public static final int WP = 1; //White Pawn
	public static final int WR = 2; //White Rook
	public static final int WB = 3; //White Bishop
	public static final int WK = 4; //White Knight
	public static final int WQ = 5; //White Queen
	public static final int WKI = 6; //White KIng

	public static final int BP = -1; //Black Pawn
	public static final int BR = -2; //Black Rook
	public static final int BB = -3; //Black Bishop
	public static final int BK = -4; //Black Knight
	public static final int BQ = -5; //Black Queen
	public static final int BKI = -6; //Black KIng
	
	public static int[][] INIT_STATE = {
			{BR,BB,BK,BQ,BKI,BK,BB,BR},
			{BP,BP,BP,BP,BP,BP,BP,BP},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{WP,WP,WP,WP,WP,WP,WP,WP},
			{WR,WB,WK,WQ,WKI,WK,WB,WR}
	};
	public static int[][] INIT_STATE_1 = {
			{BR,BB,BK,0,0,BK,BB,BR},
			{BP,BP,BP,BP,BP,BP,BP,BP},
			{0,0,0,0,0,0,0,0},
			{0,BKI,0,0,0,0,0,0},
			{0,0,0,WKI,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{WP,WP,WP,WP,WP,WP,WP,WP},
			{WR,WB,WK,WQ,0,WK,WB,WR}
	};
	
	/*
	 * CAN ATTACK
	 */

	public static boolean whiteCanAttack(int[][] data, int[] pos) {
		return whiteCanAttack(data, pos[0], pos[1]);
	}

	public static boolean whiteCanAttack(int[][] data, int i, int j) {

		// attacked by white king
		if (is(data, i - 1, j - 1, WKI)) return true;
		if (is(data, i - 1, j + 1, WKI)) return true;
		if (is(data, i + 1, j - 1, WKI)) return true;
		if (is(data, i + 1, j + 1, WKI)) return true;
		if (is(data, i - 1, j, WKI)) return true;
		if (is(data, i + 1, j, WKI)) return true;
		if (is(data, i, j - 1, WKI)) return true;
		if (is(data, i, j + 1, WKI)) return true;

		// attacked by white pawns
		if (is(data, i + 1, j - 1, WP)) return true;
		if (is(data, i + 1, j + 1, WP)) return true;

		// attacked by white knights
		if (is(data, i - 1, j - 2, WK)) return true;
		if (is(data, i - 1, j + 2, WK)) return true;
		if (is(data, i + 1, j - 2, WK)) return true;
		if (is(data, i + 1, j + 2, WK)) return true;
		if (is(data, i - 2, j - 1, WK)) return true;
		if (is(data, i - 2, j + 1, WK)) return true;
		if (is(data, i + 2, j - 1, WK)) return true;
		if (is(data, i + 2, j + 1, WK)) return true;

		// attacked from diagonal NW
		int i0 = i;
		int j0 = j;
		while(i0>0 && j0>0) {
			i0--;
			j0--;
			if(data[i0][j0]==WB || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal NE
		i0 = i;
		j0 = j;
		while(i0>0 && j0<7) {
			i0--;
			j0++;
			if(data[i0][j0]==WB || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal SW
		i0 = i;
		j0 = j;
		while(i0<7 && j0>0) {
			i0++;
			j0--;
			if(data[i0][j0]==WB || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal SE
		i0 = i;
		j0 = j;
		while(i0<7 && j0<7) {
			i0++;
			j0++;
			if(data[i0][j0]==WB || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row N
		i0 = i;
		j0 = j;
		while(i0>0) {
			i0--;
			if(data[i0][j0]==WR || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row S
		i0 = i;
		j0 = j;
		while(i0<7) {
			i0++;
			if(data[i0][j0]==WR || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row W
		i0 = i;
		j0 = j;
		while(j0>0) {
			j0--;
			if(data[i0][j0]==WR || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row E
		i0 = i;
		j0 = j;
		while(j0<7) {
			j0++;
			if(data[i0][j0]==WR || data[i0][j0]==WQ) return true;
			if(data[i0][j0]!=0) break;
		}
		
		return false;
	}

	public static boolean blackCanAttack(int[][] data, int[] pos) {
		return blackCanAttack(data, pos[0], pos[1]);
	}

	public static boolean blackCanAttack(int[][] data, int i, int j) {

		// attacked by black king
		if (is(data, i - 1, j - 1, BKI)) return true;
		if (is(data, i - 1, j + 1, BKI)) return true;
		if (is(data, i + 1, j - 1, BKI)) return true;
		if (is(data, i + 1, j + 1, BKI)) return true;
		if (is(data, i - 1, j, BKI)) return true;
		if (is(data, i + 1, j, BKI)) return true;
		if (is(data, i, j - 1, BKI)) return true;
		if (is(data, i, j + 1, BKI)) return true;

		// attacked by black pawns
		if (is(data, i - 1, j - 1, BP)) return true;
		if (is(data, i - 1, j + 1, BP)) return true;

		// attacked by black knights
		if (is(data, i - 1, j - 2, BK)) return true;
		if (is(data, i - 1, j + 2, BK)) return true;
		if (is(data, i + 1, j - 2, BK)) return true;
		if (is(data, i + 1, j + 2, BK)) return true;
		if (is(data, i - 2, j - 1, BK)) return true;
		if (is(data, i - 2, j + 1, BK)) return true;
		if (is(data, i + 2, j - 1, BK)) return true;
		if (is(data, i + 2, j + 1, BK)) return true;

		// attacked from diagonal SW
		int i0 = i;
		int j0 = j;
		while(i0>0 && j0>0) {
			i0--;
			j0--;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal SE
		i0 = i;
		j0 = j;
		while(i0>0 && j0<7) {
			i0--;
			j0++;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal NW
		i0 = i;
		j0 = j;
		while(i0<7 && j0>0) {
			i0++;
			j0--;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal NE
		i0 = i;
		j0 = j;
		while(i0<7 && j0<7) {
			i0++;
			j0++;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row S
		i0 = i;
		j0 = j;
		while(i0>0) {
			i0--;
			if(data[i0][j0]==BR || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row N
		i0 = i;
		j0 = j;
		while(i0<7) {
			i0++;
			if(data[i0][j0]==BR || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row W
		i0 = i;
		j0 = j;
		while(j0>0) {
			j0--;
			if(data[i0][j0]==BR || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from row E
		i0 = i;
		j0 = j;
		while(j0<7) {
			j0++;
			if(data[i0][j0]==BR || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}
		
		return false;
	}
	
	/*
	 * CHECKED
	 */
	
	public static boolean whiteIsChecked(int[][] data) {
		int[] king = UtilArray.find(data, WKI);
		return blackCanAttack(data, king);
	}
	
	public static boolean blackIsChecked(int[][] data) {
		int[] king = UtilArray.find(data, BKI);
		return whiteCanAttack(data, king);
	}
	
	/*
	 * IS
	 */
	
	public static boolean isValid(int i, int j) {
		return i >= 0 && i <= 7 && j >= 0 && j <= 7;
	}
	
	public static boolean is(int[][] data, int i, int j, int value) {
		return isValid(i,j) && data[i][j] == value;
	}
	
	public static boolean isBlack(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j] < 0;
	}
	
	public static boolean isWhite(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j] > 0;
	}
	
	public static boolean isEmpty(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j] == 0;
	}
	
	public static boolean isEmptyOrWhite(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j] >= 0;
	}
	
	public static boolean isEmptyOrBlack(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j] <= 0;
	}
}
