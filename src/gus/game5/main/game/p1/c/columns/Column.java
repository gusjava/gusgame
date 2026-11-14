package gus.game5.main.game.p1.c.columns;

public class Column {

	public static final int SIZE = 3;

	private Field field;
	private Gem[] gems;
	private int col;
	private int row;

	public Column(Field field, Gem[] gems, int col) {
		this.field = field;
		this.gems = new Gem[SIZE];
		for(int i=0;i<SIZE;i++) this.gems[i] = gems[i];
		this.col = col;
		this.row = 0;
	}

	public Gem gemAt(int index) {
		return gems[index];
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public boolean fits(int col, int row) {
		for(int i=0;i<SIZE;i++) if(!field.isFree(col, row+i)) return false;
		return true;
	}

	public boolean isBlocked() {
		return !fits(col, row);
	}

	public boolean isLanded() {
		return !fits(col, row+1);
	}

	public int dropDistance() {
		int distance = 0;
		while(fits(col, row+distance+1)) distance++;
		return distance;
	}

	public boolean move(int dx, int dy) {
		if(!fits(col+dx, row+dy)) return false;
		col += dx;
		row += dy;
		return true;
	}

	public void cycle() {
		Gem bottom = gems[SIZE-1];
		for(int i=SIZE-1;i>0;i--) gems[i] = gems[i-1];
		gems[0] = bottom;
	}

	public void lock() {
		for(int i=0;i<SIZE;i++) field.put(col, row+i, gems[i]);
	}
}
