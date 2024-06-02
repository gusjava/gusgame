package gus.game5.core.shape.board;

import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.ShapeRound;

public class ShapeCell extends ShapeRound {
	protected int i;
	protected int j;
	
	protected int index;
	protected int x;
	protected int y;
	
	public ShapeCell(int i, int j) {
		super(p(0,0), 0);
		this.i = i;
		this.j = j;
	}
	
	public void initCell(double width, double height, int index, int x, int y) {
		setWidth(width);
		setHeight(height);
		
		this.index = index;
		this.x = x;
		this.y = y;
		
		initAnchor();
		setOriginFromAnchor();
	}
	
	protected void initAnchor() {
		setAnchor(new Point2(getWidth()*(j+0.5), getHeight()*(i+0.5)));
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
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		return index+":["+i+","+j+"]";
	}

}
