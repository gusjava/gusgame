package gus.game5.main.game.minesweeper;

public class Data {
	
	private int x;
	private int y;
	private int nb;

	private boolean[][] grid;
	private int[][] mineC;
	private int[][] hiddenC;
	
	private int revealedNb = 0;
	private int safeNb = 0;
	private int iDead = -1;
	private int jDead = -1;
	
	
	public Data(int x, int y, int nb) {
		this.x = x;
		this.y = y;
		this.nb = nb;
		
		revealedNb = 0;
		safeNb = x*y - nb;
		if(safeNb<1) throw new RuntimeException("Invalid params: x="+x+" y="+y+" nb="+nb);
		
		iDead = -1;
		jDead = -1;
		
		mineC = new int[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
		mineC[i][j] = -1;
		
		hiddenC = new int[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++)
		hiddenC[i][j] = initHiddenCFor(i,j);
		
		grid = Utils.buildRandomGrid(x,y,nb);
	}
	
	
	
	
	private int initHiddenCFor(int i, int j) {
		if(i==0 && j==0) return 3;
		if(i==0 && j==y-1) return 3;
		if(i==x-1 && j==0) return 3;
		if(i==x-1 && j==y-1) return 3;
		
		if(i==0) return 5;
		if(i==x-1) return 5;
		if(j==0) return 5;
		if(j==y-1) return 5;
		
		return 8;
	}
	
	
	
	public void revealCell(int i, int j) {
		if(!isHiddenCell(i,j)) return;
		
		int c = countAround(i,j);
		mineC[i][j] = c;
		revealedNb++;
		
		decreaseHiddenCAround(i,j);
		
		if(c==0) {
			revealCell(i-1, j-1);
			revealCell(i-1, j);
			revealCell(i-1, j+1);
			
			revealCell(i, j-1);
			revealCell(i, j+1);
			
			revealCell(i+1, j-1);
			revealCell(i+1, j);
			revealCell(i+1, j+1);
		}
	}
	
	
	private int countAround(int i, int j) {
		int c = 0;
		
		if(isMineCell(i-1,j-1)) c++;
		if(isMineCell(i-1,j)) c++;
		if(isMineCell(i-1,j+1)) c++;
		
		if(isMineCell(i,j-1)) c++;
		if(isMineCell(i,j+1)) c++;
		
		if(isMineCell(i+1,j-1)) c++;
		if(isMineCell(i+1,j)) c++;
		if(isMineCell(i+1,j+1)) c++;
		
		return c;
	}
	
	
	
	private void decreaseHiddenCAround(int i, int j) {
		decreaseHiddenC(i-1,j-1);
		decreaseHiddenC(i-1,j);
		decreaseHiddenC(i-1,j+1);

		decreaseHiddenC(i,j-1);
		decreaseHiddenC(i,j+1);

		decreaseHiddenC(i+1,j-1);
		decreaseHiddenC(i+1,j);
		decreaseHiddenC(i+1,j+1);
	}
	
	private void decreaseHiddenC(int i, int j) {
		if(isDefined(i,j)) hiddenC[i][j]--;
	}
	
	public void setDead(int i, int j) {
		iDead = i;
		jDead = j;
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getNb() {
		return nb;
	}
	
	public int getMineC(int i, int j) {
		return mineC[i][j];
	}
	
	public int getHiddenC(int i, int j) {
		return hiddenC[i][j];
	}
	
	public boolean isDefined(int i, int j) {
		return i>=0 && j>=0 && i<x && j<y;
	}
	
	public boolean isMineCell(int i, int j) {
		return isDefined(i,j) && grid[i][j];
	}
	
	public boolean isHiddenCell(int i, int j) {
		return isDefined(i,j) && mineC[i][j]==-1;
	}
	
	public boolean isDead(int i, int j) {
		return i==iDead && j==jDead;
	}
	
	public boolean hasRevealedAll() {
		return revealedNb==safeNb;
	}
	
	public boolean hasRevealedMany() {
		return revealedNb>1;
	}
}
