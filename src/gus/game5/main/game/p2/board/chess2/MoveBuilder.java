package gus.game5.main.game.p2.board.chess2;

import static gus.game5.main.game.p2.board.chess1.UtilChess.*;

public class MoveBuilder {
	
	private int[][] data;
	private int[][] data0;

	private int i0;
	private int j0;
	private int v0;
	
	private int i1;
	private int j1;
	private int v1;

	public MoveBuilder(int[][] data, int[][] data0, int[] start, int[] end) {
		this.data = data;
		this.data0 = data0;
		
		i0 = start[0];
		j0 = start[1];
		
		i1 = end[0];
		j1 = end[1];
		
		v0 = data[i0][j0];
		v1 = data[i1][j1];
	}
	
	public EMove build() {
		if(v0==0) return null;
		if(i0==i1 && j0==j1) return null;
		
		switch(v0) {
		case WP:return computeWP(); // White Pawn
		case WR:return computeWR(); // White Rook
		case WB:return computeWB(); // White Bishop
		case WK:return computeWK(); // White Knight
		case WQ:return computeWQ(); // White Queen
		case WKI:return computeWKI(); // White KIng
		
		case BP:return computeBP(); // Black Pawn
		case BR:return computeBR(); // Black Rook
		case BB:return computeBB(); // Black Bishop
		case BK:return computeBK(); // Black Knight
		case BQ:return computeBQ(); // Black Knight
		case BKI:return computeBKI(); // Black KIng
		}
		return null;
	}
	
	/*
	 * WHITE
	 */

	private EMove computeWP() {
		if (v1 > 0)
			return null;

		// straight move
		if (j1 == j0) {
			if (i0 == 6 && i1 == 4) {
				if (data[5][j0] == 0 && data[4][j0] == 0)
					return EMove.EAT;
				return null;
			}
			if (i1 - i0 == -1) {
				if (v1 == 0)
					return i1==0 ? EMove.PROMOTION : EMove.EAT;
				return null;
			}
			return null;
		}
		// oblique move
		if (j1 == j0 + 1 || j1 == j0 - 1) {
			if (i1 - i0 == -1) {
				if (v1 < 0)
					return i1==0 ? EMove.PROMOTION : EMove.EAT;
				// en passant
				if (i0 == 3 && data[3][j1]==BP && data[1][j1]==0 && data0[3][j1]==0 && data0[1][j1]==BP)
					return EMove.EN_PASSANT;
				return null;
			}
			return null;
		}
		return null;
	}

	private EMove computeWR() {
		if (v1 > 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj == 0) {
			int i_min = Math.min(i1, i0);
			for (int i = 1; i < di; i++)
				if (data[i_min + i][j0] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == 0) {
			int j_min = Math.min(j1, j0);
			for (int i = 1; i < dj; i++)
				if (data[i0][j_min + i] != 0)
					return null;
			return EMove.EAT;
		}
		return null;
	}

	private EMove computeWB() {
		if (v1 > 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj != di)
			return null;

		int ci = di / (i1 - i0);
		int cj = dj / (j1 - j0);

		for (int i = 1; i < di; i++)
			if (data[i0 + ci * i][j0 + cj * i] != 0)
				return null;
		return EMove.EAT;
	}

	private EMove computeWK() {
		if (v1 > 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (di == 1 && dj == 2)
			return EMove.EAT;
		if (di == 2 && dj == 1)
			return EMove.EAT;
		return null;
	}

	private EMove computeWQ() {
		if (v1 > 0)
			return null;
		
		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj == 0) {
			int i_min = Math.min(i1, i0);
			for (int i = 1; i < di; i++)
				if (data[i_min + i][j0] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == 0) {
			int j_min = Math.min(j1, j0);
			for (int i = 1; i < dj; i++)
				if (data[i0][j_min + i] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == dj) {
			int ci = di / (i1 - i0);
			int cj = dj / (j1 - j0);

			for (int i = 1; i < di; i++)
				if (data[i0 + ci * i][j0 + cj * i] != 0)
					return null;
			return EMove.EAT;
		}
		return null;
	}

	private EMove computeWKI() {
		if (v1 > 0)
			return null;
		
		if(i0==7 && j0==4) {
			if(i1==7 && j1==6 && data[7][5]==0 && data[7][6]==0 && data[7][7]==WR) {
				return EMove.CASTLING; //petit roque
			}
			if(i1==7 && j1==2 && data[7][3]==0 && data[7][2]==0 && data[7][1]==0 && data[7][0]==WR) {
				return EMove.CASTLING; //grand roque
			}
		}
		
		int di = Math.abs(i1-i0);
		int dj = Math.abs(j1-j0);
		
		if(di<2 && dj<2) return EMove.EAT;
		return null;
	}

	/*
	 * BLACK
	 */

	private EMove computeBP() {
		if (v1 < 0)
			return null;

		// straight move
		if (j1 == j0) {
			if (i0 == 1 && i1 == 3) {
				if (data[2][j0] == 0 && data[3][j0] == 0)
					return EMove.EAT;
				return null;
			}
			if (i1 - i0 == 1) {
				if (v1 == 0)
					return i1==7 ? EMove.PROMOTION : EMove.EAT;
				return null;
			}
			return null;
		}
		// oblique move
		if (j1 == j0 + 1 || j1 == j0 - 1) {
			if (i1 - i0 == 1) {
				if (v1 > 0)
					return i1==7 ? EMove.PROMOTION : EMove.EAT;
				// en passant
				if (i0 == 4 && data[4][j1]==WP && data[6][j1]==0 && data0[4][j1]==0 && data0[6][j1]==WP)
					return EMove.EN_PASSANT;
				return null;
			}
			return null;
		}
		return null;
	}

	private EMove computeBR() {
		if (v1 < 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj == 0) {
			int i_min = Math.min(i1, i0);
			for (int i = 1; i < di; i++)
				if (data[i_min + i][j0] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == 0) {
			int j_min = Math.min(j1, j0);
			for (int i = 1; i < dj; i++)
				if (data[i0][j_min + i] != 0)
					return null;
			return EMove.EAT;
		}
		return null;
	}

	private EMove computeBB() {
		if (v1 < 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj != di)
			return null;

		int ci = di / (i1 - i0);
		int cj = dj / (j1 - j0);

		for (int i = 1; i < di; i++)
			if (data[i0 + ci * i][j0 + cj * i] != 0)
				return null;
		return EMove.EAT;
	}

	private EMove computeBK() {
		if (v1 < 0)
			return null;

		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (di == 1 && dj == 2)
			return EMove.EAT;
		if (di == 2 && dj == 1)
			return EMove.EAT;
		return null;
	}

	private EMove computeBQ() {
		if (v1 < 0)
			return null;
		
		int di = Math.abs(i1 - i0);
		int dj = Math.abs(j1 - j0);

		if (dj == 0) {
			int i_min = Math.min(i1, i0);
			for (int i = 1; i < di; i++)
				if (data[i_min + i][j0] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == 0) {
			int j_min = Math.min(j1, j0);
			for (int i = 1; i < dj; i++)
				if (data[i0][j_min + i] != 0)
					return null;
			return EMove.EAT;
		}
		if (di == dj) {
			int ci = di / (i1 - i0);
			int cj = dj / (j1 - j0);

			for (int i = 1; i < di; i++)
				if (data[i0 + ci * i][j0 + cj * i] != 0)
					return null;
			return EMove.EAT;
		}
		return null;
	}

	private EMove computeBKI() {
		if (v1 < 0)
			return null;
		
		if(i0==0 && j0==4) {
			if(i1==0 && j1==6 && data[0][5]==0 && data[0][6]==0 && data[0][7]==BR) {
				return EMove.CASTLING; //petit roque
			}
			if(i1==0 && j1==2 && data[0][3]==0 && data[0][2]==0 && data[0][1]==0 && data[0][0]==WR) {
				return EMove.CASTLING; //grand roque
			}
		}
		
		int di = Math.abs(i1-i0);
		int dj = Math.abs(j1-j0);
		
		if(di<2 && dj<2) return EMove.EAT;
		return null;
	}
}
