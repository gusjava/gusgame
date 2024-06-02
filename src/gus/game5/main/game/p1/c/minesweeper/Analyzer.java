package gus.game5.main.game.p1.c.minesweeper;

import java.awt.Color;

public class Analyzer {
	
	public static final int CODE_REVEALED_1 = 1;
	public static final int CODE_REVEALED_2 = 2;
	public static final int CODE_REVEALED_3 = 3;
	public static final int CODE_REVEALED_4 = 4;
	public static final int CODE_REVEALED_5 = 5;
	public static final int CODE_REVEALED_6 = 6;
	public static final int CODE_REVEALED_7 = 7;
	
	public static final int CODE_HIDDEN_1_MINE = -1;
	public static final int CODE_HIDDEN_2_SAFE = -2;
	public static final int CODE_HIDDEN_3_MINE = -3;
	public static final int CODE_HIDDEN_4_SAFE = -4;
	public static final int CODE_HIDDEN_5_MINE = -5;
	public static final int CODE_HIDDEN_6_SAFE = -6;
	public static final int CODE_HIDDEN_7_MINE = -7;
	
	public static final int DEPTH0 = 0;
	public static final int DEPTH1 = 1;
	public static final int DEPTH2 = 2;
	public static final int DEPTH3 = 3;
	public static final int DEPTH4 = 4;
	public static final int DEPTH5 = 5;
	public static final int DEPTH6 = 6;
	public static final int DEPTH7 = 7;
	
	public static final Color COLOR1 = Color.CYAN;
	public static final Color COLOR2 = Color.CYAN.darker();
	public static final Color COLOR3 = Color.ORANGE;
	public static final Color COLOR4 = Color.PINK;
	public static final Color COLOR5 = Color.RED.darker();
	

	private Data data;
	private int[][] analyzed;
	private int depth = DEPTH0;
	
	
	public Analyzer(Data data) {
		this.data = data;
		
		int x = data.getX();
		int y = data.getY();
		
		analyzed = new int[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
		analyzed[i][j] = 0;
	}
	
	
	public void analyzeGrid() {
		int x = data.getX();
		int y = data.getY();
		
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
			analyzed[i][j] = 0;
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			int h = data.getHiddenC(i, j);
			
			if(m>0 && m == h) {
				analyzed[i][j] = CODE_REVEALED_1;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_1)) {
				analyzed[i][j] = CODE_HIDDEN_1_MINE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			if(m>0 && countCodeAround(i, j, CODE_HIDDEN_1_MINE)==m) {
				analyzed[i][j] = CODE_REVEALED_2;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_2)) {
				analyzed[i][j] = CODE_HIDDEN_2_SAFE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			int h = data.getHiddenC(i, j) - countCodeAround(i,j,CODE_HIDDEN_2_SAFE);
			
			if(m>0 && m == h) {
				analyzed[i][j] = CODE_REVEALED_3;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_3)) {
				analyzed[i][j] = CODE_HIDDEN_3_MINE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			if(m>0 && countCodeAround(i, j, CODE_HIDDEN_1_MINE) + countCodeAround(i, j, CODE_HIDDEN_3_MINE)==m) {
				analyzed[i][j] = CODE_REVEALED_4;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_4)) {
				analyzed[i][j] = CODE_HIDDEN_4_SAFE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			int h = data.getHiddenC(i, j) - countCodeAround(i,j,CODE_HIDDEN_2_SAFE) - countCodeAround(i,j,CODE_HIDDEN_4_SAFE);
			
			if(m>0 && m == h) {
				analyzed[i][j] = CODE_REVEALED_5;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_5)) {
				analyzed[i][j] = CODE_HIDDEN_5_MINE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			if(m>0 && countCodeAround(i, j, CODE_HIDDEN_1_MINE) + countCodeAround(i, j, CODE_HIDDEN_3_MINE) + countCodeAround(i, j, CODE_HIDDEN_5_MINE)==m) {
				analyzed[i][j] = CODE_REVEALED_6;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_6)) {
				analyzed[i][j] = CODE_HIDDEN_6_SAFE;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			int m = data.getMineC(i, j);
			int h = data.getHiddenC(i, j) - countCodeAround(i,j,CODE_HIDDEN_2_SAFE) - countCodeAround(i,j,CODE_HIDDEN_4_SAFE) - countCodeAround(i,j,CODE_HIDDEN_6_SAFE);
			
			if(m>0 && m == h) {
				analyzed[i][j] = CODE_REVEALED_7;
			}
		}
		
		for(int i=0;i<x;i++) 
		for(int j=0;j<y;j++)
		if(analyzed[i][j] == 0){
			if(data.isHiddenCell(i, j) && hasCodeAround(i, j, CODE_REVEALED_7)) {
				analyzed[i][j] = CODE_HIDDEN_7_MINE;
			}
		}
	}
	
	
	private boolean hasCodeAround(int i, int j, int code)  {
		if(isCellWithCode(i-1, j-1, code)) return true;
		if(isCellWithCode(i-1, j, code)) return true;
		if(isCellWithCode(i-1, j+1, code)) return true;

		if(isCellWithCode(i, j-1, code)) return true;
		if(isCellWithCode(i, j+1, code)) return true;

		if(isCellWithCode(i+1, j-1, code)) return true;
		if(isCellWithCode(i+1, j, code)) return true;
		if(isCellWithCode(i+1, j+1, code)) return true;
		
		return false;
	}
	
	
	
	private int countCodeAround(int i, int j, int code)  {
		int c = 0;
		if(isCellWithCode(i-1, j-1, code)) c++;
		if(isCellWithCode(i-1, j, code)) c++;
		if(isCellWithCode(i-1, j+1, code)) c++;

		if(isCellWithCode(i, j-1, code)) c++;
		if(isCellWithCode(i, j+1, code)) c++;

		if(isCellWithCode(i+1, j-1, code)) c++;
		if(isCellWithCode(i+1, j, code)) c++;
		if(isCellWithCode(i+1, j+1, code)) c++;
		
		return c;
	}
	
	
	
	private boolean isCellWithCode(int i, int j, int code) {
		return data.isDefined(i,j) && analyzed[i][j]==code;
	}
	
	public int getAnalyzedCode(int i, int j) {
		return analyzed[i][j];
	}
	
	
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	
	
	public boolean hasDepth1() {
		return depth>=DEPTH1;
	}
	
	public boolean hasDepth2() {
		return depth>=DEPTH2;
	}
	
	public boolean hasDepth3() {
		return depth>=DEPTH3;
	}
	
	public boolean hasDepth4() {
		return depth>=DEPTH4;
	}
	
	public boolean hasDepth5() {
		return depth>=DEPTH5;
	}
	
	public boolean hasDepth6() {
		return depth>=DEPTH6;
	}
	
	public boolean hasDepth7() {
		return depth>=DEPTH7;
	}
	
	
	
	public Color getColorRevealed(int i, int j) {
		int code = analyzed[i][j];
		if(hasDepth5() && code==CODE_REVEALED_5) return COLOR5;
		if(hasDepth4() && code==CODE_REVEALED_4) return COLOR4;
		if(hasDepth3() && code==CODE_REVEALED_3) return COLOR3;
		if(hasDepth2() && code==CODE_REVEALED_2) return COLOR2;
		if(hasDepth1() && code==CODE_REVEALED_1) return COLOR1;
		
		return null;
	}
	
	
	public Color getColorHidden(int i, int j) {
		int code = analyzed[i][j];
		if(hasDepth5() && code==CODE_HIDDEN_5_MINE) return COLOR5;
		if(hasDepth4() && code==CODE_HIDDEN_4_SAFE) return COLOR4;
		if(hasDepth3() && code==CODE_HIDDEN_3_MINE) return COLOR3;
		if(hasDepth2() && code==CODE_HIDDEN_2_SAFE) return COLOR2;
		if(hasDepth1() && code==CODE_HIDDEN_1_MINE) return COLOR1;
		
		return null;
	}
	
	public boolean isHiddenMine(int i, int j) {
		int code = analyzed[i][j];
		if(hasDepth5() && code==CODE_HIDDEN_5_MINE) return true;
		if(hasDepth3() && code==CODE_HIDDEN_3_MINE) return true;
		if(hasDepth1() && code==CODE_HIDDEN_1_MINE) return true;
		return false;
	}
	
	public boolean isHiddenSafe(int i, int j) {
		int code = analyzed[i][j];
		if(hasDepth4() && code==CODE_HIDDEN_4_SAFE) return true;
		if(hasDepth2() && code==CODE_HIDDEN_2_SAFE) return true;
		return false;
	}
}
