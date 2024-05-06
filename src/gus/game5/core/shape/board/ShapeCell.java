package gus.game5.core.shape.board;

import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeSquare;

public class ShapeCell extends ShapeSquare {
	protected int i;
	protected int j;
	
	public ShapeCell(int i, int j) {
		super(p(0,0), 0);
		this.i = i;
		this.j = j;
	}
	
	public void initCell(double cellSize) {
		setLength(cellSize);
		setAnchor(new Point2(cellSize*(j+0.5), cellSize*(i+0.5)));
		setOriginFromAnchor();
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

}
