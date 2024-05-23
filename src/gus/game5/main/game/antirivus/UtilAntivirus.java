package gus.game5.main.game.antirivus;

import java.awt.Color;

public class UtilAntivirus {
	
	public static final int PIECE0 = 0;//RED (2)
	public static final int PIECE1 = 1;//BLUE (2)
	public static final int PIECE2 = 2;//ORANGE (3)
	public static final int PIECE3 = 3;//PINK (2)
	public static final int PIECE4 = 4;//DARK GREEN (2)
	public static final int PIECE5 = 5;//DARK BLUE (3)
	public static final int PIECE6 = 6;//VIOLET (3)
	public static final int PIECE7 = 7;//GREEN (3)
	public static final int PIECE8 = 8;//YELLOW (3)

	public static final int BLOCKED = 9;
	public static final int EMPTY = -1;
	
	public static final int OUTPUT_I = 0;
	public static final int OUTPUT_J = 3;
	public static final int LEVEL_NUMBER = 10;
	
	public static final Color[] COLORS = new Color[] {
			Color.RED,
			Color.BLUE,
			Color.ORANGE,
			Color.PINK,
			Color.GREEN.darker(),
			Color.BLUE.darker(),
			Color.MAGENTA.darker(),
			Color.GREEN,
			Color.YELLOW,
			Color.WHITE
	};
	

	public static int[][] dataForLevel(int level) {
		switch(level) {
			case 0:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9,-1,-1,-1,-1,-1, 9},
				{-1,-1,-1,-1,-1,-1,-1},
				{ 9,-1,-1,-1,-1,-1, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 1:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1, 6,-1, 9, 9},
				{ 9,-1, 6, 0, 6,-1, 9},
				{-1,-1,-1, 0,-1,-1,-1},
				{ 9,-1,-1, 9,-1,-1, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 2:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9,-1, 9,-1,-1,-1, 9},
				{-1,-1,-1,-1, 8, 8,-1},
				{ 9, 9,-1, 8, 0,-1, 9},
				{ 9, 9,-1,-1, 0, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 3:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9, 0, 9,-1,-1,-1, 9},
				{-1, 0,-1, 9, 3,-1,-1},
				{ 9,-1,-1, 3,-1,-1, 9},
				{ 9, 9, 1, 1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 4:return new int[][] {
				{ 9, 9, 9, 5, 9, 9, 9},
				{ 9, 9,-1,-1, 5, 9, 9},
				{ 9,-1,-1, 6,-1, 5, 9},
				{-1, 0,-1,-1, 6,-1,-1},
				{ 9, 0,-1, 6, 9,-1, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 5:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9, 3,-1,-1, 9, 9},
				{ 9,-1,-1, 3, 5,-1, 9},
				{-1, 0,-1, 5,-1, 9,-1},
				{ 9, 0, 5,-1,-1,-1, 9},
				{ 9, 9, 9,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 6:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1, 6,-1, 9, 9},
				{ 9,-1, 9,-1, 6,-1, 9},
				{-1,-1,-1, 6, 7, 0,-1},
				{ 9,-1,-1,-1, 7, 0, 9},
				{ 9, 9,-1, 7,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 7:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9, 7, 7,-1, 9, 9},
				{ 9, 9,-1, 8, 7, 9, 9},
				{-1, 8, 8,-1,-1, 0,-1},
				{ 9,-1,-1,-1,-1, 0, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 8:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1,-1, 3, 9, 9},
				{ 9,-1, 5, 3,-1,-1, 9},
				{-1,-1, 0, 5,-1,-1,-1},
				{ 9,-1, 0,-1, 5,-1, 9},
				{ 9, 9,-1,-1, 9, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
			case 9:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9, 8,-1, 9, 9, 9},
				{ 9,-1, 9, 8, 0,-1, 9},
				{-1,-1,-1, 8, 0,-1,-1},
				{ 9,-1,-1,-1,-1,-1, 9},
				{ 9, 9, 6,-1, 6, 9, 9},
				{ 9, 9, 9, 6, 9, 9, 9},
			};
			case 10:return new int[][] {
				{ 9, 9, 9,-1, 9, 9, 9},
				{ 9, 9,-1,-1,-1, 9, 9},
				{ 9,-1, 6,-1, 6,-1, 9},
				{-1, 9, 2, 6, 0,-1,-1},
				{ 9,-1, 2, 2, 0,-1, 9},
				{ 9, 9, 9,-1,-1, 9, 9},
				{ 9, 9, 9,-1, 9, 9, 9},
			};
		}
		return null;
	}
}
