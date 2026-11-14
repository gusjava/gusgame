package gus.game5.main.game.p1.c.puyo;

public class Group {

	public static final int CLEAR_POINTS = 10;

	private static final int[] SIZE_BONUS = {0,0,0,0,0,2,3,4,5,6,7};
	private static final int BIG_BONUS = 10;
	private static final int[] CHAIN_POWER = {
		0,8,16,32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512};
	private static final int[] COLOR_BONUS = {0,0,3,6,12,24};

	private boolean[][] cells = new boolean[Board.ROWS][Board.COLS];
	private boolean[] colors = new boolean[Puyo.values().length];
	private int count;
	private int bonus;

	public void add(int col, int row) {
		if(cells[row][col]) return;
		cells[row][col] = true;
		count++;
	}

	public void addRun(int size, Puyo puyo) {
		colors[puyo.ordinal()] = true;
		bonus += size<SIZE_BONUS.length ? SIZE_BONUS[size] : BIG_BONUS;
	}

	public boolean contains(int col, int row) {
		if(row<0 || row>=Board.ROWS || col<0 || col>=Board.COLS) return false;
		return cells[row][col];
	}

	public boolean isEmpty() {
		return count==0;
	}

	public int getCount() {
		return count;
	}

	public int getBonus() {
		return bonus;
	}

	public int getColorCount() {
		int total = 0;
		for(boolean used : colors) if(used) total++;
		return total;
	}

	public int getMultiplier(int chain) {
		int power = CHAIN_POWER[Math.min(chain-1, CHAIN_POWER.length-1)];
		int colors = COLOR_BONUS[Math.min(getColorCount(), COLOR_BONUS.length-1)];
		return Math.max(1, power + colors + bonus);
	}

	public int getScore(int chain) {
		return CLEAR_POINTS*count*getMultiplier(chain);
	}
}
