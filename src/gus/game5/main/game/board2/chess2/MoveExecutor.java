package gus.game5.main.game.board2.chess2;

import static gus.game5.main.game.board2.chess1.UtilChess.*;

public class MoveExecutor {
	
	private int[][] data;
	private boolean[][] changed;
	private EMove move;
	private int[] start;
	private int[] end;

	public MoveExecutor(int[][] data, boolean[][] changed, EMove move, int[] start, int[] end) {
		this.data = data;
		this.changed = changed;
		this.move = move;
		this.start = start;
		this.end = end;
	}
	
	public boolean execute() {
		switch(move) {
		case EAT:
			set(end, get(start));
			set(start,0);
			return true;
			
		case EN_PASSANT:
			set(end, get(start));
			set(start,0);
			set(start[0], end[1], 0);
			return true;
			
		case CASTLING:
			if(is(end,7,6)) {
				if(changed[7][7] || changed[7][4]) return false;
				if(blackCanAttack(data, 7, 4)) return false;
				if(blackCanAttack(data, 7, 5)) return false;
				if(blackCanAttack(data, 7, 6)) return false;
				if(blackCanAttack(data, 7, 7)) return false;
				set(7,7,0);
				set(7,5,WR);
				set(end, get(start));
				set(start,0);
				return true;
			}
			if(is(end,7,2)) {
				if(changed[7][0] || changed[7][4]) return false;
				if(blackCanAttack(data, 7, 0)) return false;
				if(blackCanAttack(data, 7, 1)) return false;
				if(blackCanAttack(data, 7, 2)) return false;
				if(blackCanAttack(data, 7, 3)) return false;
				if(blackCanAttack(data, 7, 4)) return false;
				set(7,0,0);
				set(7,3,WR);
				set(end, get(start));
				set(start,0);
				return true;
			}
			if(is(end,0,6)) {
				if(changed[0][7] || changed[0][4]) return false;
				if(whiteCanAttack(data, 0, 4)) return false;
				if(whiteCanAttack(data, 0, 5)) return false;
				if(whiteCanAttack(data, 0, 6)) return false;
				if(whiteCanAttack(data, 0, 7)) return false;
				set(0,7,0);
				set(0,5,BR);
				set(end, get(start));
				set(start,0);
				return true;
			}
			if(is(end,0,2)) {
				if(changed[0][0] || changed[0][4]) return false;
				if(whiteCanAttack(data, 0, 0)) return false;
				if(whiteCanAttack(data, 0, 1)) return false;
				if(whiteCanAttack(data, 0, 2)) return false;
				if(whiteCanAttack(data, 0, 3)) return false;
				if(whiteCanAttack(data, 0, 4)) return false;
				set(0,0,0);
				set(0,3,BR);
				set(end, get(start));
				set(start,0);
				return true;
			}
			return false;
		case PROMOTION:
			set(end, get(start)==WP ? WQ : BQ);
			set(start,0);
			return true;
		}
		return false;
	}
	
	private void set(int[] pos, int value) {
		data[pos[0]][pos[1]] = value;
	}
	
	private void set(int x, int y, int value) {
		data[x][y] = value;
	}
	
	private int get(int[] pos) {
		return data[pos[0]][pos[1]];
	}
	
	private boolean is(int[] pos, int x, int y) {
		return pos[0]==x && pos[1]==y;
	}
}
