package gus.game5.main.game.p1.c.sokoban;

import java.util.ArrayDeque;
import java.util.Deque;

import gus.game5.core.util.Dir;

public class Level {

	public static final char WALL = '#';
	public static final char FLOOR = ' ';
	public static final char BOX = '$';
	public static final char TARGET = '.';
	public static final char BOX_ON_TARGET = '*';
	public static final char PLAYER = '@';
	public static final char PLAYER_ON_TARGET = '+';

	private static final String[][] LAYOUTS = {
		{
			"#########",
			"#  .$@  #",
			"#########",
		},
		{
			"###########",
			"#@   $   .#",
			"###########",
		},
		{
			"########",
			"#   .  #",
			"#      #",
			"#  $   #",
			"#      #",
			"#  @   #",
			"########",
		},
		{
			"##########",
			"#.  $    #",
			"#        #",
			"#    $  .#",
			"#   @    #",
			"##########",
		},
		{
			"##########",
			"#.   #   #",
			"#  $    .#",
			"#   ###  #",
			"#    $   #",
			"#  @     #",
			"##########",
		},
		{
			"#########",
			"#  .  ###",
			"#  @  $ #",
			"#  $  $ #",
			"##   # .#",
			"# .     #",
			"#########",
		},
		{
			"###########",
			"#.  #    .#",
			"#  $ ##   #",
			"#    ##   #",
			"# $    $  #",
			"#  #  .   #",
			"#  @      #",
			"###########",
		},
		{
			"##########",
			"# #.##   #",
			"#  .  #  #",
			"#    $   #",
			"# #  $$$##",
			"#.     @ #",
			"#      .##",
			"##########",
		},
		{
			"###########",
			"# *       #",
			"#@$  #    #",
			"#.  $#    #",
			"####      #",
			"# # .   # #",
			"#    #*  ##",
			"###########",
		},
		{
			"############",
			"##     @ ###",
			"#   #*  $ ##",
			"## $.  #   #",
			"#     #    #",
			"# $#       #",
			"#   .  #   #",
			"##.        #",
			"############",
		},
	};

	public static final int COUNT = LAYOUTS.length;
	public static final int MAX_COLS = maxCols();
	public static final int MAX_ROWS = maxRows();

	private static int maxCols() {
		int max = 0;
		for(String[] layout : LAYOUTS)
			for(String row : layout)
				max = Math.max(max, row.length());
		return max;
	}

	private static int maxRows() {
		int max = 0;
		for(String[] layout : LAYOUTS)
			max = Math.max(max, layout.length);
		return max;
	}


	private boolean[][] wall;
	private boolean[][] target;
	private boolean[][] box;
	private int cols;
	private int rows;
	private int playerCol;
	private int playerRow;
	private int targetsLeft;
	private int moves;
	private int pushes;

	private final Deque<Move> history = new ArrayDeque<>();

	private static class Move {
		final Dir dir;
		final boolean pushed;
		Move(Dir dir, boolean pushed) {
			this.dir = dir;
			this.pushed = pushed;
		}
	}


	public Level(int index) {
		load(LAYOUTS[index]);
	}

	public Level(String[] layout) {
		load(layout);
	}

	private void load(String[] layout) {
		rows = layout.length;
		cols = 0;
		for(String row : layout) cols = Math.max(cols, row.length());

		wall = new boolean[cols][rows];
		target = new boolean[cols][rows];
		box = new boolean[cols][rows];
		moves = 0;
		pushes = 0;
		history.clear();

		for(int row=0; row<rows; row++) {
			String line = layout[row];
			for(int col=0; col<cols; col++) {
				char c = col<line.length() ? line.charAt(col) : FLOOR;
				apply(col, row, c);
			}
		}

		targetsLeft = 0;
		for(int col=0; col<cols; col++)
			for(int row=0; row<rows; row++)
				if(target[col][row] && !box[col][row]) targetsLeft++;
	}

	private void apply(int col, int row, char c) {
		switch(c) {
			case WALL: wall[col][row] = true; break;
			case TARGET: target[col][row] = true; break;
			case BOX: box[col][row] = true; break;
			case BOX_ON_TARGET: box[col][row] = true; target[col][row] = true; break;
			case PLAYER: playerCol = col; playerRow = row; break;
			case PLAYER_ON_TARGET: playerCol = col; playerRow = row; target[col][row] = true; break;
			default: break;
		}
	}


	/*
	 * MOVE
	 */

	public boolean move(Dir dir) {
		int nc = playerCol+dir.getDx();
		int nr = playerRow+dir.getDy();
		if(isWall(nc, nr)) return false;

		boolean pushed = false;
		if(isBox(nc, nr)) {
			int bc = nc+dir.getDx();
			int br = nr+dir.getDy();
			if(isWall(bc, br) || isBox(bc, br)) return false;
			moveBox(nc, nr, bc, br);
			pushed = true;
		}

		playerCol = nc;
		playerRow = nr;
		moves++;
		if(pushed) pushes++;
		history.push(new Move(dir, pushed));
		return true;
	}

	public boolean undo() {
		if(history.isEmpty()) return false;
		Move m = history.pop();
		int dx = m.dir.getDx();
		int dy = m.dir.getDy();

		if(m.pushed) moveBox(playerCol+dx, playerRow+dy, playerCol, playerRow);

		playerCol -= dx;
		playerRow -= dy;
		moves--;
		if(m.pushed) pushes--;
		return true;
	}

	private void moveBox(int fromCol, int fromRow, int toCol, int toRow) {
		box[fromCol][fromRow] = false;
		box[toCol][toRow] = true;
		if(target[fromCol][fromRow]) targetsLeft++;
		if(target[toCol][toRow]) targetsLeft--;
	}


	/*
	 * STATE
	 */

	public boolean isSolved() {
		return targetsLeft==0;
	}

	public boolean canUndo() {
		return !history.isEmpty();
	}

	public int getMoves() {
		return moves;
	}

	public int getPushes() {
		return pushes;
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public int getPlayerCol() {
		return playerCol;
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public boolean isWall(int col, int row) {
		if(col<0 || col>=cols || row<0 || row>=rows) return true;
		return wall[col][row];
	}

	public boolean isTarget(int col, int row) {
		if(col<0 || col>=cols || row<0 || row>=rows) return false;
		return target[col][row];
	}

	public boolean isBox(int col, int row) {
		if(col<0 || col>=cols || row<0 || row>=rows) return false;
		return box[col][row];
	}
}
