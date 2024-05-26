package gus.game5.core.shape.board;

import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;

public class ShapeCell extends ShapeRound {
	protected int i;
	protected int j;
	
	protected double cellSize;
	protected int x;
	protected int y;
	
	public ShapeCell(int i, int j) {
		super(p(0,0), 0);
		this.i = i;
		this.j = j;
	}
	
	public void initCell(double cellSize, int x, int y) {
		this.cellSize = cellSize;
		this.x = x;
		this.y = y;
		
		initRadius();
		initAnchor();
		setOriginFromAnchor();
	}
	
	protected void initRadius() {
		setRadius(cellSize*0.5);
	}
	
	protected void initAnchor() {
		setAnchor(new Point2(cellSize*(j+0.5), cellSize*(i+0.5)));
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
	
	public int[] getIJ() {
		return new int[] {i,j};
	}
	
	public boolean isI(int i) {
		return this.i==i;
	}
	
	public boolean isJ(int j) {
		return this.j==j;
	}
	
	public boolean isIJ(int i, int j) {
		return this.i==i && this.j==j;
	}
	
	public String toString() {
		return "["+i+","+j+"]";
	}

}
