package gus.game5.main.game.p1.c.puyo;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int COLS = 6;
	public static final int ROWS = 12;
	public static final int HIDDEN = 1;
	public static final int MIN_GROUP = 4;

	private static final int[][] DIRECTIONS = {{1,0},{-1,0},{0,1},{0,-1}};

	private Puyo[][] cells = new Puyo[ROWS+HIDDEN][COLS];

	public boolean isInside(int col, int row) {
		return col>=0 && col<COLS && row>=-HIDDEN && row<ROWS;
	}

	public Puyo at(int col, int row) {
		if(!isInside(col, row)) return null;
		return cells[row+HIDDEN][col];
	}

	public boolean isFree(int col, int row) {
		if(!isInside(col, row)) return false;
		return cells[row+HIDDEN][col]==null;
	}

	public void put(int col, int row, Puyo puyo) {
		if(!isInside(col, row)) return;
		cells[row+HIDDEN][col] = puyo;
	}

	public boolean collapse() {
		boolean moved = false;
		for(int col=0;col<COLS;col++) {
			int target = ROWS-1;
			for(int row=ROWS-1;row>=-HIDDEN;row--) {
				Puyo puyo = at(col, row);
				if(puyo==null) continue;
				if(target!=row) {
					put(col, target, puyo);
					put(col, row, null);
					moved = true;
				}
				target--;
			}
		}
		return moved;
	}

	public void clear(Group group) {
		for(int row=0;row<ROWS;row++)
			for(int col=0;col<COLS;col++)
				if(group.contains(col, row)) put(col, row, null);
	}

	public Group findGroups() {
		Group group = new Group();
		boolean[][] seen = new boolean[ROWS][COLS];
		for(int row=0;row<ROWS;row++)
			for(int col=0;col<COLS;col++)
				explore(group, seen, col, row);
		return group;
	}

	private void explore(Group group, boolean[][] seen, int col, int row) {
		Puyo puyo = at(col, row);
		if(puyo==null || seen[row][col]) return;

		List<int[]> found = new ArrayList<>();
		seen[row][col] = true;
		found.add(new int[]{col, row});

		for(int i=0;i<found.size();i++) {
			int[] cell = found.get(i);
			for(int[] direction : DIRECTIONS) {
				int c = cell[0]+direction[0];
				int r = cell[1]+direction[1];
				if(c<0 || c>=COLS || r<0 || r>=ROWS) continue;
				if(seen[r][c] || at(c, r)!=puyo) continue;
				seen[r][c] = true;
				found.add(new int[]{c, r});
			}
		}

		if(found.size()<MIN_GROUP) return;
		for(int[] cell : found) group.add(cell[0], cell[1]);
		group.addRun(found.size(), puyo);
	}
}
