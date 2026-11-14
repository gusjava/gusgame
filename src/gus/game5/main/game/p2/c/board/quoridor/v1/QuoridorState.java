package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN2;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.SIZE;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.WALL_COUNT;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.WALL_SIZE;

import java.util.Arrays;

public class QuoridorState {

	private int[] pawn1;
	private int[] pawn2;
	private boolean[][] wallH;
	private boolean[][] wallV;
	private int wallsLeft1;
	private int wallsLeft2;

	public QuoridorState() {
		pawn1 = new int[] {SIZE-1, SIZE/2};
		pawn2 = new int[] {0, SIZE/2};
		wallH = new boolean[WALL_SIZE][WALL_SIZE];
		wallV = new boolean[WALL_SIZE][WALL_SIZE];
		wallsLeft1 = WALL_COUNT;
		wallsLeft2 = WALL_COUNT;
	}

	public QuoridorState copy() {
		QuoridorState state = new QuoridorState();
		state.pawn1 = new int[] {pawn1[0], pawn1[1]};
		state.pawn2 = new int[] {pawn2[0], pawn2[1]};
		for(int i=0;i<WALL_SIZE;i++) {
			state.wallH[i] = Arrays.copyOf(wallH[i], WALL_SIZE);
			state.wallV[i] = Arrays.copyOf(wallV[i], WALL_SIZE);
		}
		state.wallsLeft1 = wallsLeft1;
		state.wallsLeft2 = wallsLeft2;
		return state;
	}

	public int[] pawn(int player) {
		return player==PAWN1 ? pawn1 : pawn2;
	}

	public void setPawn(int player, int i, int j) {
		int[] pawn = pawn(player);
		pawn[0] = i;
		pawn[1] = j;
	}

	public int pawnAt(int i, int j) {
		if(pawn1[0]==i && pawn1[1]==j) return PAWN1;
		if(pawn2[0]==i && pawn2[1]==j) return PAWN2;
		return 0;
	}

	public boolean wallH(int i, int j) {
		return inWallBoard(i, j) && wallH[i][j];
	}

	public boolean wallV(int i, int j) {
		return inWallBoard(i, j) && wallV[i][j];
	}

	public void setWall(int i, int j, boolean horizontal) {
		if(horizontal) wallH[i][j] = true;
		else wallV[i][j] = true;
	}

	public int wallsLeft(int player) {
		return player==PAWN1 ? wallsLeft1 : wallsLeft2;
	}

	public void useWall(int player) {
		if(player==PAWN1) wallsLeft1--;
		else wallsLeft2--;
	}

	public int goalRow(int player) {
		return player==PAWN1 ? 0 : SIZE-1;
	}

	private boolean inWallBoard(int i, int j) {
		return i>=0 && i<WALL_SIZE && j>=0 && j<WALL_SIZE;
	}
}
