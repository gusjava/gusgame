package gus.game5.main.game.chess;

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
	public static int[][] INIT_STATE_ = {
			{BR,BB,BK,BQ,BKI,BK,BB,BR},
			{BP,BP,BP,0,BP,BP,BP,BP},
			{0,0,0,WP,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{WP,WP,WP,WP,WP,WP,WP,WP},
			{WR,WB,WK,WQ,WKI,WK,WB,WR}
	};
	
	
	public static Move computeMove(int[][] data, int[][] data0, int[] start, int[] end) {
		
		int x0 = start[0];
		int y0 = start[1];
		
		int x1 = end[0];
		int y1 = end[1];
		
		int value0 = data[x0][y0];
		int value1 = data[x1][y1];

		if(value0==0) return null;
		if(x0==x1 && y0==y1) return null;
		
		switch(value0) {
		case WP:return computeWhitePawn(data, data0, value1, x0, y0, x1, y1);
		case WR:return computeWhiteRook(data, value1, x0, y0, x1, y1);
		case WB:return computeWhiteBishop(data, value1, x0, y0, x1, y1);
		case WK:return computeWhiteKnight(data, value1, x0, y0, x1, y1);
		case WQ:return computeWhiteQueen(data, value1, x0, y0, x1, y1);
		case WKI:return computeWhiteKing(data, value1, x0, y0, x1, y1);
		
		case BP:return computeBlackPawn(data, data0, value1, x0, y0, x1, y1);
		case BR:return computeBlackRook(data, value1, x0, y0, x1, y1);
		case BB:return computeBlackBishop(data, value1, x0, y0, x1, y1);
		case BK:return computeBlackKnight(data, value1, x0, y0, x1, y1);
		case BQ:return computeBlackQueen(data, value1, x0, y0, x1, y1);
		case BKI:return computeBlackKing(data, value1, x0, y0, x1, y1);
		}
		return null;
	}
	
	/*
	 * WHITE
	 */

	private static Move computeWhitePawn(int[][] data, int[][] data0, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;
		

		// straight move
		if (y1 == y0) {
			if (x0 == 6 && x1 == 4) {
				if (data[5][y0] == 0 && data[4][y0] == 0)
					return Move.EAT;
				return null;
			}
			if (x1 - x0 == -1) {
				if (value1 == 0)
					return x1==0 ? Move.PROMOTION : Move.EAT;
				return null;
			}
			return null;
		}
		// oblique move
		if (y1 == y0 + 1 || y1 == y0 - 1) {
			if (x1 - x0 == -1) {
				if (value1 < 0)
					return x1==0 ? Move.PROMOTION : Move.EAT;
				// en passant
				if (x0 == 3 && data[3][y1]==BP && data[1][y1]==0 && data0[3][y1]==0 && data0[1][y1]==BP)
					return Move.EN_PASSANT;
				return null;
			}
			return null;
		}
		return null;
	}

	private static Move computeWhiteRook(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy == 0) {
			int x_min = Math.min(x1, x0);
			for (int i = 1; i < dx; i++)
				if (data[x_min + i][y0] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == 0) {
			int y_min = Math.min(y1, y0);
			for (int i = 1; i < dy; i++)
				if (data[x0][y_min + i] != 0)
					return null;
			return Move.EAT;
		}
		return null;
	}

	private static Move computeWhiteBishop(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy != dx)
			return null;

		int cx = dx / (x1 - x0);
		int cy = dy / (y1 - y0);

		for (int i = 1; i < dx; i++)
			if (data[x0 + cx * i][y0 + cy * i] != 0)
				return null;
		return Move.EAT;
	}

	private static Move computeWhiteKnight(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dx == 1 && dy == 2)
			return Move.EAT;
		if (dx == 2 && dy == 1)
			return Move.EAT;
		return null;
	}

	private static Move computeWhiteQueen(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy == 0) {
			int x_min = Math.min(x1, x0);
			for (int i = 1; i < dx; i++)
				if (data[x_min + i][y0] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == 0) {
			int y_min = Math.min(y1, y0);
			for (int i = 1; i < dy; i++)
				if (data[x0][y_min + i] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == dy) {
			int cx = dx / (x1 - x0);
			int cy = dy / (y1 - y0);

			for (int i = 1; i < dx; i++)
				if (data[x0 + cx * i][y0 + cy * i] != 0)
					return null;
			return Move.EAT;
		}
		return null;
	}

	private static Move computeWhiteKing(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 > 0)
			return null;
		
		if(x0==7 && y0==4) {
			if(x1==7 && y1==6 && data[7][5]==0 && data[7][6]==0 && data[7][7]==WR) {
				return Move.CASTLING; //petit roque
			}
			if(x1==7 && y1==2 && data[7][3]==0 && data[7][2]==0 && data[7][1]==0 && data[7][0]==WR) {
				return Move.CASTLING; //grand roque
			}
		}
		
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		
		if(dx<2 && dy<2) return Move.EAT;
		return null;
	}

	/*
	 * BLACK
	 */

	private static Move computeBlackPawn(int[][] data, int[][] data0, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;

		// straight move
		if (y1 == y0) {
			if (x0 == 1 && x1 == 3) {
				if (data[2][y0] == 0 && data[3][y0] == 0)
					return Move.EAT;
				return null;
			}
			if (x1 - x0 == 1) {
				if (value1 == 0)
					return x1==7 ? Move.PROMOTION : Move.EAT;
				return null;
			}
			return null;
		}
		// oblique move
		if (y1 == y0 + 1 || y1 == y0 - 1) {
			if (x1 - x0 == 1) {
				if (value1 > 0)
					return x1==7 ? Move.PROMOTION : Move.EAT;
				// en passant
				if (x0 == 4 && data[4][y1]==WP && data[6][y1]==0 && data0[4][y1]==0 && data0[6][y1]==WP)
					return Move.EN_PASSANT;
				return null;
			}
			return null;
		}
		return null;
	}

	private static Move computeBlackRook(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy == 0) {
			int x_min = Math.min(x1, x0);
			for (int i = 1; i < dx; i++)
				if (data[x_min + i][y0] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == 0) {
			int y_min = Math.min(y1, y0);
			for (int i = 1; i < dy; i++)
				if (data[x0][y_min + i] != 0)
					return null;
			return Move.EAT;
		}
		return null;
	}

	private static Move computeBlackBishop(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy != dx)
			return null;

		int cx = dx / (x1 - x0);
		int cy = dy / (y1 - y0);

		for (int i = 1; i < dx; i++)
			if (data[x0 + cx * i][y0 + cy * i] != 0)
				return null;
		return Move.EAT;
	}

	private static Move computeBlackKnight(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dx == 1 && dy == 2)
			return Move.EAT;
		if (dx == 2 && dy == 1)
			return Move.EAT;
		return null;
	}

	private static Move computeBlackQueen(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		if (dy == 0) {
			int x_min = Math.min(x1, x0);
			for (int i = 1; i < dx; i++)
				if (data[x_min + i][y0] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == 0) {
			int y_min = Math.min(y1, y0);
			for (int i = 1; i < dy; i++)
				if (data[x0][y_min + i] != 0)
					return null;
			return Move.EAT;
		}
		if (dx == dy) {
			int cx = dx / (x1 - x0);
			int cy = dy / (y1 - y0);

			for (int i = 1; i < dx; i++)
				if (data[x0 + cx * i][y0 + cy * i] != 0)
					return null;
			return Move.EAT;
		}
		return null;
	}

	private static Move computeBlackKing(int[][] data, int value1, int x0, int y0, int x1, int y1) {
		if (value1 < 0)
			return null;
		
		if(x0==0 && y0==4) {
			if(x1==0 && y1==6 && data[0][5]==0 && data[0][6]==0 && data[0][7]==BR) {
				return Move.CASTLING; //petit roque
			}
			if(x1==0 && y1==2 && data[0][3]==0 && data[0][2]==0 && data[0][1]==0 && data[0][0]==WR) {
				return Move.CASTLING; //grand roque
			}
		}
		
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		
		if(dx<2 && dy<2) return Move.EAT;
		return null;
	}
	
	/*
	 * KING IS CHECKED
	 */
	
	public static boolean whiteKingIsChecked(int[][] data) {
		int[] pos = UtilArray.find(data, WKI);
		return blackCanAttack(data, pos[0], pos[1]);
	}
	
	public static boolean blackKingIsChecked(int[][] data) {
		int[] pos = UtilArray.find(data, BKI);
		return whiteCanAttack(data, pos[0], pos[1]);
	}
	
	/*
	 * CAN ATTACK
	 */

	public static boolean whiteCanAttack(int[][] data, int x, int y) {

		// attacked by white pawns
		if (is(data, x + 1, y - 1, WP)) return true;
		if (is(data, x + 1, y + 1, WP)) return true;

		// attacked by white knights
		if (is(data, x - 1, y - 2, WK)) return true;
		if (is(data, x - 1, y + 2, WK)) return true;
		if (is(data, x + 1, y - 2, WK)) return true;
		if (is(data, x + 1, y + 2, WK)) return true;
		if (is(data, x - 2, y - 1, WK)) return true;
		if (is(data, x - 2, y + 1, WK)) return true;
		if (is(data, x + 2, y - 1, WK)) return true;
		if (is(data, x + 2, y + 1, WK)) return true;

		// attacked from diagonal NW
		int x0 = x;
		int y0 = y;
		while(x0>0 && y0>0) {
			x0--;
			y0--;
			if(data[x0][y0]==WB || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal NE
		x0 = x;
		y0 = y;
		while(x0>0 && y0<7) {
			x0--;
			y0++;
			if(data[x0][y0]==WB || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal SW
		x0 = x;
		y0 = y;
		while(x0<7 && y0>0) {
			x0++;
			y0--;
			if(data[x0][y0]==WB || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal SE
		x0 = x;
		y0 = y;
		while(x0<7 && y0<7) {
			x0++;
			y0++;
			if(data[x0][y0]==WB || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row N
		x0 = x;
		y0 = y;
		while(x0>0) {
			x0--;
			if(data[x0][y0]==WR || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row S
		x0 = x;
		y0 = y;
		while(x0<7) {
			x0++;
			if(data[x0][y0]==WR || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row W
		x0 = x;
		y0 = y;
		while(y0>0) {
			y0--;
			if(data[x0][y0]==WR || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row E
		x0 = x;
		y0 = y;
		while(y0<7) {
			y0++;
			if(data[x0][y0]==WR || data[x0][y0]==WQ) return true;
			if(data[x0][y0]!=0) break;
		}
		
		return false;
	}

	public static boolean blackCanAttack(int[][] data, int x, int y) {

		// attacked by black pawns
		if (is(data, x - 1, y - 1, BP)) return true;
		if (is(data, x - 1, y + 1, BP)) return true;

		// attacked by black knights
		if (is(data, x - 1, y - 2, BK)) return true;
		if (is(data, x - 1, y + 2, BK)) return true;
		if (is(data, x + 1, y - 2, BK)) return true;
		if (is(data, x + 1, y + 2, BK)) return true;
		if (is(data, x - 2, y - 1, BK)) return true;
		if (is(data, x - 2, y + 1, BK)) return true;
		if (is(data, x + 2, y - 1, BK)) return true;
		if (is(data, x + 2, y + 1, BK)) return true;

		// attacked from diagonal SW
		int x0 = x;
		int y0 = y;
		while(x0>0 && y0>0) {
			x0--;
			y0--;
			if(data[x0][y0]==BB || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal SE
		x0 = x;
		y0 = y;
		while(x0>0 && y0<7) {
			x0--;
			y0++;
			if(data[x0][y0]==BB || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal NW
		x0 = x;
		y0 = y;
		while(x0<7 && y0>0) {
			x0++;
			y0--;
			if(data[x0][y0]==BB || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from diagonal NE
		x0 = x;
		y0 = y;
		while(x0<7 && y0<7) {
			x0++;
			y0++;
			if(data[x0][y0]==BB || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row S
		x0 = x;
		y0 = y;
		while(x0>0) {
			x0--;
			if(data[x0][y0]==BR || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row N
		x0 = x;
		y0 = y;
		while(x0<7) {
			x0++;
			if(data[x0][y0]==BR || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row W
		x0 = x;
		y0 = y;
		while(y0>0) {
			y0--;
			if(data[x0][y0]==BR || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}

		// attacked from row E
		x0 = x;
		y0 = y;
		while(y0<7) {
			y0++;
			if(data[x0][y0]==BR || data[x0][y0]==BQ) return true;
			if(data[x0][y0]!=0) break;
		}
		
		return false;
	}

	public static boolean is(int[][] data, int x, int y, int value) {
		if (x < 0 || x > 7 || y < 0 || y > 7)
			return false;
		return data[x][y] == value;
	}
}
