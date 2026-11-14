package gus.game5.main.game.p1.o.frogger;

public class Homes {

	public static final double TOLERANCE = 0.5;

	private int[] columns;
	private boolean[] filled;

	public Homes(int[] columns) {
		this.columns = columns;
		this.filled = new boolean[columns.length];
	}

	public int size() {return columns.length;}
	public int column(int slot) {return columns[slot];}
	public boolean isFilled(int slot) {return filled[slot];}

	public void clear() {
		for(int i=0;i<filled.length;i++) filled[i] = false;
	}

	public int slotAt(double x) {
		for(int i=0;i<columns.length;i++)
			if(Math.abs(x-columns[i])<=TOLERANCE) return i;
		return -1;
	}

	public boolean fill(int slot) {
		if(slot<0 || filled[slot]) return false;
		filled[slot] = true;
		return true;
	}

	public int countFilled() {
		int n = 0;
		for(boolean f : filled) if(f) n++;
		return n;
	}

	public boolean isComplete() {
		return countFilled()==filled.length;
	}
}
