package gus.game5.main.game.p1.c.tetris;

public class Kick {

	private static final int[][][] JLSTZ_CW = {
		{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}},
		{{0,0},{1,0},{1,1},{0,-2},{1,-2}},
		{{0,0},{1,0},{1,-1},{0,2},{1,2}},
		{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}}};

	private static final int[][][] JLSTZ_CCW = {
		{{0,0},{1,0},{1,-1},{0,2},{1,2}},
		{{0,0},{1,0},{1,1},{0,-2},{1,-2}},
		{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}},
		{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}}};

	private static final int[][][] I_CW = {
		{{0,0},{-2,0},{1,0},{-2,1},{1,-2}},
		{{0,0},{-1,0},{2,0},{-1,-2},{2,1}},
		{{0,0},{2,0},{-1,0},{2,-1},{-1,2}},
		{{0,0},{1,0},{-2,0},{1,2},{-2,-1}}};

	private static final int[][][] I_CCW = {
		{{0,0},{-1,0},{2,0},{-1,-2},{2,1}},
		{{0,0},{2,0},{-1,0},{2,-1},{-1,2}},
		{{0,0},{1,0},{-2,0},{1,2},{-2,-1}},
		{{0,0},{-2,0},{1,0},{-2,1},{1,-2}}};

	public static int[][] offsets(Tetromino type, int rotation, boolean cw) {
		if(type==Tetromino.I) return cw ? I_CW[rotation] : I_CCW[rotation];
		return cw ? JLSTZ_CW[rotation] : JLSTZ_CCW[rotation];
	}
}
