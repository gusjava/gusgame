package gus.game5.main.game.p1.c.pacman;

import java.util.ArrayDeque;
import java.util.Deque;

public class Maze {
	
	public static final char WALL = '#';
	public static final char BLANK = ' ';
	public static final char PELLET = '.';
	public static final char POWER = 'o';
	public static final char FREE = '-';
	public static final char DOOR = '=';
	
	private static final String[] LAYOUT = {
		"############################",
		"#............##............#",
		"#.####.#####.##.#####.####.#",
		"#o####.#####.##.#####.####o#",
		"#.####.#####.##.#####.####.#",
		"#..........................#",
		"#.####.##.########.##.####.#",
		"#.####.##.########.##.####.#",
		"#......##....##....##......#",
		"######.##### ## #####.######",
		"######.##### ## #####.######",
		"######.--------------.######",
		"######.   ###==###   .######",
		"######.   #------#   .######",
		"----------#------#----------",
		"######.   #------#   .######",
		"######.   ########   .######",
		"######.              .######",
		"######.   ########   .######",
		"######.   ########   .######",
		"#............##............#",
		"#.####.#####.##.#####.####.#",
		"#.####.#####.##.#####.####.#",
		"#o..##................##..o#",
		"###.##.##.########.##.##.###",
		"###.##.##.########.##.##.###",
		"#......##....##....##......#",
		"#.##########.##.##########.#",
		"#.##########.##.##########.#",
		"#..........................#",
		"############################"};
	
	public static final int CELL = 20;
	public static final int COLS = LAYOUT[0].length();
	public static final int ROWS = LAYOUT.length;
	
	public static final int HOME_COL = 13;
	public static final int HOME_ROW = 14;
	public static final int GATE_ROW = 11;
	
	public static final int PACMAN_COL = 13;
	public static final int PACMAN_ROW = 23;
	
	
	private char[][] grid;
	private int pelletsLeft;
	private int pelletsTotal;
	
	public Maze() {
		reset();
	}
	
	public void reset() {
		grid = new char[COLS][ROWS];
		pelletsLeft = 0;
		for(int row=0;row<ROWS;row++)
			for(int col=0;col<COLS;col++) {
				char c = LAYOUT[row].charAt(col);
				grid[col][row] = c;
				if(c==PELLET || c==POWER) pelletsLeft++;
			}
		pelletsTotal = pelletsLeft;
	}
	
	public int getPelletsLeft() {
		return pelletsLeft;
	}
	
	public int getPelletsEaten() {
		return pelletsTotal-pelletsLeft;
	}
	
	public int wrapCol(int col) {
		return Math.floorMod(col, COLS);
	}
	
	public char at(int col, int row) {
		if(row<0 || row>=ROWS) return WALL;
		return grid[wrapCol(col)][row];
	}
	
	public boolean isWall(int col, int row) {
		char c = at(col, row);
		return c==WALL || c==BLANK;
	}
	
	public boolean isDoor(int col, int row) {
		return at(col, row)==DOOR;
	}
	
	public boolean canWalk(int col, int row, boolean doorOpen) {
		if(isWall(col, row)) return false;
		return doorOpen || !isDoor(col, row);
	}
	
	public char eat(int col, int row) {
		char c = at(col, row);
		if(c!=PELLET && c!=POWER) return FREE;
		grid[wrapCol(col)][row] = FREE;
		pelletsLeft--;
		return c;
	}
	
	public Dir firstStepTo(int fromCol, int fromRow, int toCol, int toRow, boolean doorOpen) {
		if(fromCol==toCol && fromRow==toRow) return null;
		
		Dir[][] first = new Dir[COLS][ROWS];
		boolean[][] seen = new boolean[COLS][ROWS];
		Deque<int[]> queue = new ArrayDeque<>();
		seen[wrapCol(fromCol)][fromRow] = true;
		queue.add(new int[]{wrapCol(fromCol), fromRow});
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			for(Dir d : Dir.values()) {
				int nc = wrapCol(cur[0]+d.getDx());
				int nr = cur[1]+d.getDy();
				if(nr<0 || nr>=ROWS) continue;
				if(seen[nc][nr]) continue;
				if(!canWalk(nc, nr, doorOpen)) continue;
				seen[nc][nr] = true;
				first[nc][nr] = first[cur[0]][cur[1]]==null ? d : first[cur[0]][cur[1]];
				if(nc==toCol && nr==toRow) return first[nc][nr];
				queue.add(new int[]{nc, nr});
			}
		}
		return null;
	}
	
	public static int centerX(int col) {
		return col*CELL + CELL/2;
	}
	
	public static int centerY(int row) {
		return row*CELL + CELL/2;
	}
}
