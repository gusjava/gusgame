package gus.game5.main.game.p1.c.tetris;

import java.awt.Color;

public enum Tetromino {

	I(3, new Color(0,240,240), new int[][][] {
		{{0,1},{1,1},{2,1},{3,1}},
		{{2,0},{2,1},{2,2},{2,3}},
		{{0,2},{1,2},{2,2},{3,2}},
		{{1,0},{1,1},{1,2},{1,3}}}),

	O(4, new Color(240,240,0), new int[][][] {
		{{0,0},{1,0},{0,1},{1,1}},
		{{0,0},{1,0},{0,1},{1,1}},
		{{0,0},{1,0},{0,1},{1,1}},
		{{0,0},{1,0},{0,1},{1,1}}}),

	T(3, new Color(170,0,240), new int[][][] {
		{{1,0},{0,1},{1,1},{2,1}},
		{{1,0},{1,1},{2,1},{1,2}},
		{{0,1},{1,1},{2,1},{1,2}},
		{{1,0},{0,1},{1,1},{1,2}}}),

	S(3, new Color(0,240,0), new int[][][] {
		{{1,0},{2,0},{0,1},{1,1}},
		{{1,0},{1,1},{2,1},{2,2}},
		{{1,1},{2,1},{0,2},{1,2}},
		{{0,0},{0,1},{1,1},{1,2}}}),

	Z(3, new Color(240,0,0), new int[][][] {
		{{0,0},{1,0},{1,1},{2,1}},
		{{2,0},{1,1},{2,1},{1,2}},
		{{0,1},{1,1},{1,2},{2,2}},
		{{1,0},{0,1},{1,1},{0,2}}}),

	J(3, new Color(0,0,240), new int[][][] {
		{{0,0},{0,1},{1,1},{2,1}},
		{{1,0},{2,0},{1,1},{1,2}},
		{{0,1},{1,1},{2,1},{2,2}},
		{{1,0},{1,1},{0,2},{1,2}}}),

	L(3, new Color(240,160,0), new int[][][] {
		{{2,0},{0,1},{1,1},{2,1}},
		{{1,0},{1,1},{1,2},{2,2}},
		{{0,1},{1,1},{2,1},{0,2}},
		{{0,0},{1,0},{1,1},{1,2}}});


	private int spawnX;
	private Color color;
	private int[][][] cells;

	private Tetromino(int spawnX, Color color, int[][][] cells) {
		this.spawnX = spawnX;
		this.color = color;
		this.cells = cells;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public Color getColor() {
		return color;
	}

	public int[][] getCells(int rotation) {
		return cells[rotation];
	}
}
