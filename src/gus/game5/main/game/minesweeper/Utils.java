package gus.game5.main.game.minesweeper;

import static gus.game5.core.util.UtilRandom.randomInt;

import java.awt.Color;

public class Utils {

	public static boolean[][] buildRandomGrid(int x, int y, int mineNb) {
		boolean[][] grid = new boolean[x][y];
		
		int securedNb = x*y - mineNb;
		if(securedNb>=mineNb) {
			for(int i=0;i<x;i++)
			for(int j=0;j<y;j++)
			grid[i][j] = false;
			
			for(int k=0;k<mineNb;k++) {
				int i = randomInt(x);
				int j = randomInt(y);
				while(grid[i][j]) {
					i = randomInt(x);
					j = randomInt(y);
				}
				grid[i][j] = true;
			}
			return grid;
		}
		
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
		grid[i][j] = true;
		
		for(int k=0;k<securedNb;k++) {
			int i = randomInt(x);
			int j = randomInt(y);
			while(!grid[i][j]) {
				i = randomInt(x);
				j = randomInt(y);
			}
			grid[i][j] = false;
		}
		return grid;
	}
	
	
	
	public static Color colorFor(int nb) {
		switch(nb) {
			case 1:return Color.BLUE;
			case 2:return new Color(0,123,0);//VERT
			case 3:return Color.RED;
			case 4:return new Color(0,0,123);	//BLEU MARINE
			case 5:return new Color(131,22,22); //BORDEAU
			case 6:return new Color(0,123,123);	//VERT FONCE
			case 7:return Color.BLACK;
			case 8:return Color.GRAY;
			default: return null;
		}
	}
}
