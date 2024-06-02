package gus.game5.main.game.p2.c.board.chess.v2;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilRandom;

public class UtilChess {
	
	// COLORS
	
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	
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
		if (is(data, i-1, j-1, WKI)) return true;
		if (is(data, i-1, j+1, WKI)) return true;
		if (is(data, i+1, j-1, WKI)) return true;
		if (is(data, i+1, j+1, WKI)) return true;
		if (is(data, i-1, j, WKI)) return true;
		if (is(data, i+1, j, WKI)) return true;
		if (is(data, i, j-1, WKI)) return true;
		if (is(data, i, j+1, WKI)) return true;

		// attacked by white pawns
		if (is(data, i+1, j-1, WP)) return true;
		if (is(data, i+1, j+1, WP)) return true;

		// attacked by white knights
		if (is(data, i-1, j-2, WK)) return true;
		if (is(data, i-1, j+2, WK)) return true;
		if (is(data, i+1, j-2, WK)) return true;
		if (is(data, i+1, j+2, WK)) return true;
		if (is(data, i-2, j-1, WK)) return true;
		if (is(data, i-2, j+1, WK)) return true;
		if (is(data, i+2, j-1, WK)) return true;
		if (is(data, i+2, j+1, WK)) return true;

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
		if (is(data, i-1, j-1, BKI)) return true;
		if (is(data, i-1, j+1, BKI)) return true;
		if (is(data, i+1, j-1, BKI)) return true;
		if (is(data, i+1, j+1, BKI)) return true;
		if (is(data, i-1, j, BKI)) return true;
		if (is(data, i+1, j, BKI)) return true;
		if (is(data, i, j-1, BKI)) return true;
		if (is(data, i, j+1, BKI)) return true;

		// attacked by black pawns
		if (is(data, i-1, j-1, BP)) return true;
		if (is(data, i-1, j+1, BP)) return true;

		// attacked by black knights
		if (is(data, i-1, j-2, BK)) return true;
		if (is(data, i-1, j+2, BK)) return true;
		if (is(data, i+1, j-2, BK)) return true;
		if (is(data, i+1, j+2, BK)) return true;
		if (is(data, i-2, j-1, BK)) return true;
		if (is(data, i-2, j+1, BK)) return true;
		if (is(data, i+2, j-1, BK)) return true;
		if (is(data, i+2, j+1, BK)) return true;

		// attacked from diagonal SE
		int i0 = i;
		int j0 = j;
		while(i0>0 && j0>0) {
			i0--;
			j0--;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal SW
		i0 = i;
		j0 = j;
		while(i0>0 && j0<7) {
			i0--;
			j0++;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal NE
		i0 = i;
		j0 = j;
		while(i0<7 && j0>0) {
			i0++;
			j0--;
			if(data[i0][j0]==BB || data[i0][j0]==BQ) return true;
			if(data[i0][j0]!=0) break;
		}

		// attacked from diagonal NW
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
	
	
	
	public static boolean whiteIsCheckedDebug(int[][] data) throws Exception {
		int[] king = UtilArray.find(data, WKI);
		if(king==null) throw new Exception("White king has been eaten !!!");
		return blackCanAttack(data, king);
	}
	
	public static boolean blackIsCheckedDebug(int[][] data) throws Exception {
		int[] king = UtilArray.find(data, BKI);
		if(king==null) throw new Exception("Black king has been eaten !!!");
		return whiteCanAttack(data, king);
	}
	
	/*
	 * POSSIBLE PLAYS
	 */

	public static int[] randomPlay(int player, int[][] data) {
		return UtilRandom.randomElement(findPossiblePlays(player, data));
	}
	
	public static List<int[]> findPossiblePlays(int player, int[][] data) {
		return player==WHITE ? findPossiblePlaysForWhite(data) : findPossiblePlaysForBlack(data);
	}

	private static List<int[]> findPossiblePlaysForWhite(int[][] data) {
		List<int[]> playList = new ArrayList<>();
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			int value = data[i][j];
			switch(value) {
			case WKI:
				if(isEmptyOrBlack(data, i-1, j-1)) add(playList, i,j,i-1,j-1);
				if(isEmptyOrBlack(data, i-1, j+1)) add(playList, i,j,i-1,j+1);
				if(isEmptyOrBlack(data, i+1, j-1)) add(playList, i,j,i+1,j-1);
				if(isEmptyOrBlack(data, i+1, j+1)) add(playList, i,j,i+1,j+1);
				if(isEmptyOrBlack(data, i-1, j)) add(playList, i,j,i-1,j);
				if(isEmptyOrBlack(data, i+1, j)) add(playList, i,j,i+1,j);
				if(isEmptyOrBlack(data, i, j-1)) add(playList, i,j,i,j-1);
				if(isEmptyOrBlack(data, i, j+1)) add(playList, i,j,i,j+1);
				//TODO roque
				break;
			case WP: 
				if(isEmpty(data, i-1, j)) {
					add(playList, i,j,i-1,j);
					if(i==6 && isEmpty(data, 4, j)) add(playList, i,j,4,j);
				}
				if(isBlack(data, i-1, j-1)) add(playList, i,j,i-1,j-1);
				if(isBlack(data, i-1, j+1)) add(playList, i,j,i-1,j+1);
				//TODO prise en passant ...
				break;
			case WK:
				if(isEmptyOrBlack(data, i-2, j-1)) add(playList, i,j,i-2,j-1);
				if(isEmptyOrBlack(data, i-2, j+1)) add(playList, i,j,i-2,j+1);
				if(isEmptyOrBlack(data, i-1, j-2)) add(playList, i,j,i-1,j-2);
				if(isEmptyOrBlack(data, i-1, j+2)) add(playList, i,j,i-1,j+2);
				if(isEmptyOrBlack(data, i+1, j-2)) add(playList, i,j,i+1,j-2);
				if(isEmptyOrBlack(data, i+1, j+2)) add(playList, i,j,i+1,j+2);
				if(isEmptyOrBlack(data, i+2, j-1)) add(playList, i,j,i+2,j-1);
				if(isEmptyOrBlack(data, i+2, j+1)) add(playList, i,j,i+2,j+1);
				break;
			case WB:
				// move NW
				int i0 = i;
				int j0 = j;
				while(i0>0 && j0>0) {
					i0--;
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move NE
				i0 = i;
				j0 = j;
				while(i0>0 && j0<7) {
					i0--;
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SW
				i0 = i;
				j0 = j;
				while(i0<7 && j0>0) {
					i0++;
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SE
				i0 = i;
				j0 = j;
				while(i0<7 && j0<7) {
					i0++;
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			case WR:
				// move N
				i0 = i;
				j0 = j;
				while(i0>0) {
					i0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move S
				i0 = i;
				j0 = j;
				while(i0<7) {
					i0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move W
				i0 = i;
				j0 = j;
				while(j0>0) {
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move E
				i0 = i;
				j0 = j;
				while(j0<7) {
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			case WQ:
				// move NW
				i0 = i;
				j0 = j;
				while(i0>0 && j0>0) {
					i0--;
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move NE
				i0 = i;
				j0 = j;
				while(i0>0 && j0<7) {
					i0--;
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SW
				i0 = i;
				j0 = j;
				while(i0<7 && j0>0) {
					i0++;
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SE
				i0 = i;
				j0 = j;
				while(i0<7 && j0<7) {
					i0++;
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move N
				i0 = i;
				j0 = j;
				while(i0>0) {
					i0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move S
				i0 = i;
				j0 = j;
				while(i0<7) {
					i0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move W
				i0 = i;
				j0 = j;
				while(j0>0) {
					j0--;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move E
				i0 = i;
				j0 = j;
				while(j0<7) {
					j0++;
					if(data[i0][j0]<=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			}
		}
		playList.removeIf(play->isDangerousWhitePlay(data, play));
		return playList;
	}

	private static List<int[]> findPossiblePlaysForBlack(int[][] data) {
		List<int[]> playList = new ArrayList<>();
		for(int i=0;i<8;i++) for(int j=0;j<8;j++) {
			int value = data[i][j];
			switch(value) {
			case BKI:
				if(isEmptyOrWhite(data, i-1, j-1)) add(playList, i,j,i-1,j-1);
				if(isEmptyOrWhite(data, i-1, j+1)) add(playList, i,j,i-1,j+1);
				if(isEmptyOrWhite(data, i+1, j-1)) add(playList, i,j,i+1,j-1);
				if(isEmptyOrWhite(data, i+1, j+1)) add(playList, i,j,i+1,j+1);
				if(isEmptyOrWhite(data, i-1, j)) add(playList, i,j,i-1,j);
				if(isEmptyOrWhite(data, i+1, j)) add(playList, i,j,i+1,j);
				if(isEmptyOrWhite(data, i, j-1)) add(playList, i,j,i,j-1);
				if(isEmptyOrWhite(data, i, j+1)) add(playList, i,j,i,j+1);
				//TODO roque
				break;
			case BP: 
				if(isEmpty(data, i+1, j)) {
					add(playList, i,j,i+1,j);
					if(i==1 && isEmpty(data, 3, j)) add(playList, i,j,3,j);
				}
				if(isWhite(data, i+1, j-1)) add(playList, i,j,i+1,j-1);
				if(isWhite(data, i+1, j+1)) add(playList, i,j,i+1,j+1);
				//TODO prise en passant ...
				break;
			case BK:
				if(isEmptyOrWhite(data, i-2, j-1)) add(playList, i,j,i-2,j-1);
				if(isEmptyOrWhite(data, i-2, j+1)) add(playList, i,j,i-2,j+1);
				if(isEmptyOrWhite(data, i-1, j-2)) add(playList, i,j,i-1,j-2);
				if(isEmptyOrWhite(data, i-1, j+2)) add(playList, i,j,i-1,j+2);
				if(isEmptyOrWhite(data, i+1, j-2)) add(playList, i,j,i+1,j-2);
				if(isEmptyOrWhite(data, i+1, j+2)) add(playList, i,j,i+1,j+2);
				if(isEmptyOrWhite(data, i+2, j-1)) add(playList, i,j,i+2,j-1);
				if(isEmptyOrWhite(data, i+2, j+1)) add(playList, i,j,i+2,j+1);
				break;
			case BB:
				// move NW
				int i0 = i;
				int j0 = j;
				while(i0>0 && j0>0) {
					i0--;
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move NE
				i0 = i;
				j0 = j;
				while(i0>0 && j0<7) {
					i0--;
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SW
				i0 = i;
				j0 = j;
				while(i0<7 && j0>0) {
					i0++;
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SE
				i0 = i;
				j0 = j;
				while(i0<7 && j0<7) {
					i0++;
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			case BR:
				// move N
				i0 = i;
				j0 = j;
				while(i0>0) {
					i0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move S
				i0 = i;
				j0 = j;
				while(i0<7) {
					i0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move W
				i0 = i;
				j0 = j;
				while(j0>0) {
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move E
				i0 = i;
				j0 = j;
				while(j0<7) {
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			case BQ:
				// move NW
				i0 = i;
				j0 = j;
				while(i0>0 && j0>0) {
					i0--;
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move NE
				i0 = i;
				j0 = j;
				while(i0>0 && j0<7) {
					i0--;
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SW
				i0 = i;
				j0 = j;
				while(i0<7 && j0>0) {
					i0++;
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move SE
				i0 = i;
				j0 = j;
				while(i0<7 && j0<7) {
					i0++;
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move N
				i0 = i;
				j0 = j;
				while(i0>0) {
					i0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move S
				i0 = i;
				j0 = j;
				while(i0<7) {
					i0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move W
				i0 = i;
				j0 = j;
				while(j0>0) {
					j0--;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				// move E
				i0 = i;
				j0 = j;
				while(j0<7) {
					j0++;
					if(data[i0][j0]>=0) add(playList,i,j,i0,j0);
					if(data[i0][j0]!=0) break;
				}
				break;
			}
		}
		playList.removeIf(play->isDangerousBlackPlay(data, play));
		return playList;
	}
	
	/*
	 * IS DANGEROUS PLAY
	 */
	
	private static boolean isDangerousWhitePlay(int[][] data, int[] play) {
		try {
			return whiteIsCheckedDebug(simulatePlay(data,play));
		} catch (Exception e) {
			int val1 = data[play[0]][play[1]];
			int val2 = data[play[2]][play[3]];
			System.out.println("Invalid white play: "+play[0]+":"+play[1]+" ("+val1+") -> "+play[2]+":"+play[3]+" ("+val2+")");
			e.printStackTrace();
			return true;
		}
	}
	
	private static boolean isDangerousBlackPlay(int[][] data, int[] play) {
		try {
			return blackIsCheckedDebug(simulatePlay(data,play));
		} catch (Exception e) {
			int val1 = data[play[0]][play[1]];
			int val2 = data[play[2]][play[3]];
			System.out.println("Invalid black play: "+play[0]+":"+play[1]+" ("+val1+") -> "+play[2]+":"+play[3]+" ("+val2+")");
			e.printStackTrace();
			return true;
		}
	}
	
	private static int[][] simulatePlay(int[][] data, int[] play){
		int i1 = play[0];
		int j1 = play[1];
		int i2 = play[2];
		int j2 = play[3];
		int val = data[i1][j1];
		int[][] newData = UtilArray.clone(data);
		newData[i1][j1] = 0;
		newData[i2][j2] = val;
		return newData;
	}
	
	/*
	 * IS
	 */
	
	public static void add(List<int[]> playList, int i1, int j1, int i2, int j2) {
		playList.add(new int[]{i1,j1,i2,j2});
	}
	
	public static boolean isValid(int i, int j) {
		return i>=0 && i <= 7 && j>=0 && j <= 7;
	}
	
	public static boolean is(int[][] data, int i, int j, int value) {
		return isValid(i,j) && data[i][j] == value;
	}
	
	public static boolean isWhite(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j]>0;
	}
	
	public static boolean isBlack(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j]<0;
	}
	
	public static boolean isEmpty(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j]==0;
	}
	
	public static boolean isEmptyOrWhite(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j]>=0;
	}
	
	public static boolean isEmptyOrBlack(int[][] data, int i, int j) {
		return isValid(i,j) && data[i][j]<=0;
	}
}
