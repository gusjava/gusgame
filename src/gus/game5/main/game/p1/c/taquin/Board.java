package gus.game5.main.game.p1.c.taquin;

import java.util.Random;

public class Board {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public static final int SHUFFLE_STEPS = 400;

	private final int size;
	private int[][] cells;
	private int blankCol;
	private int blankRow;
	private int moveCount;

	public Board(int size) {
		this.size = size;
		this.cells = new int[size][size];
		reset();
	}

	public int getSize() {
		return size;
	}

	public int at(int col, int row) {
		return cells[row][col];
	}

	public int getMoveCount() {
		return moveCount;
	}


	/*
	 * RESET
	 */

	public void reset() {
		int value = 1;
		for(int row=0;row<size;row++)
			for(int col=0;col<size;col++)
				cells[row][col] = (row==size-1 && col==size-1) ? 0 : value++;
		blankCol = size-1;
		blankRow = size-1;
		moveCount = 0;
	}

	public boolean isSolved() {
		int value = 1;
		for(int row=0;row<size;row++)
			for(int col=0;col<size;col++) {
				int expected = (row==size-1 && col==size-1) ? 0 : value++;
				if(cells[row][col]!=expected) return false;
			}
		return true;
	}


	/*
	 * SHUFFLE
	 */

	public void shuffle(Random random) {
		do {
			reset();
			int forbidden = -1;
			for(int k=0;k<SHUFFLE_STEPS;k++) {
				int dir = randomLegalDirection(random, forbidden);
				moveBlank(dir);
				forbidden = opposite(dir);
			}
		} while(isSolved());
		moveCount = 0;
	}

	private int randomLegalDirection(Random random, int forbidden) {
		int dir;
		do {
			dir = random.nextInt(4);
		} while(dir==forbidden || !canMove(dir));
		return dir;
	}

	private int opposite(int dir) {
		if(dir==LEFT) return RIGHT;
		if(dir==RIGHT) return LEFT;
		if(dir==UP) return DOWN;
		return UP;
	}


	/*
	 * MOVE
	 */

	public boolean canMove(int dir) {
		if(dir==LEFT) return blankCol>0;
		if(dir==RIGHT) return blankCol<size-1;
		if(dir==UP) return blankRow>0;
		return blankRow<size-1;
	}

	public boolean moveBlank(int dir) {
		if(!canMove(dir)) return false;
		int col = blankCol + (dir==LEFT ? -1 : dir==RIGHT ? 1 : 0);
		int row = blankRow + (dir==UP ? -1 : dir==DOWN ? 1 : 0);
		cells[blankRow][blankCol] = cells[row][col];
		cells[row][col] = 0;
		blankCol = col;
		blankRow = row;
		moveCount++;
		return true;
	}


	/*
	 * SLIDE
	 */

	public boolean canSlideTo(int col, int row) {
		if(col==blankCol && row==blankRow) return false;
		return col==blankCol || row==blankRow;
	}

	public boolean slideTo(int col, int row) {
		if(!canSlideTo(col, row)) return false;
		int shifted = col==blankCol ? Math.abs(row-blankRow) : Math.abs(col-blankCol);
		if(col==blankCol) slideColumn(row);
		else slideRow(col);
		moveCount += shifted;
		return true;
	}

	private void slideColumn(int row) {
		if(row<blankRow)
			for(int r=blankRow;r>row;r--) cells[r][blankCol] = cells[r-1][blankCol];
		else
			for(int r=blankRow;r<row;r++) cells[r][blankCol] = cells[r+1][blankCol];
		cells[row][blankCol] = 0;
		blankRow = row;
	}

	private void slideRow(int col) {
		if(col<blankCol)
			for(int c=blankCol;c>col;c--) cells[blankRow][c] = cells[blankRow][c-1];
		else
			for(int c=blankCol;c<col;c++) cells[blankRow][c] = cells[blankRow][c+1];
		cells[blankRow][col] = 0;
		blankCol = col;
	}
}
