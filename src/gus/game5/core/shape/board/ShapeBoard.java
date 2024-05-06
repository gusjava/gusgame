package gus.game5.core.shape.board;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import gus.game5.core.draw.Draw;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;
import gus.game5.core.features.t.Tchar;
import gus.game5.core.features.t.Tdouble;
import gus.game5.core.features.t.Tint;
import gus.game5.core.point.point0.Point0;

public class ShapeBoard<U extends ShapeCell> implements Draw, Dyn {
	
	private int x;
	private int y;
	private double cellSize;
	private List<U> data;
	
	public ShapeBoard(double cellSize, int x, ShapeCellBuilder<U> builder) {
		this(cellSize, x, x, builder);
	}
	
	public ShapeBoard(double cellSize, int x, int y, ShapeCellBuilder<U> builder) {
		this.x = x;
		this.y = y;
		this.cellSize = cellSize;
		data = new ArrayList<>();
		for(int i=0;i<x;i++) for(int j=0;j<y;j++) {
			U cell = builder.build(i, j);
			cell.initCell(cellSize);
			data.add(cell);
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getCellSize() {
		return cellSize;
	}
	
	public double getWidth() {
		return x * cellSize;
	}
	
	public double getHeight() {
		return y * cellSize;
	}
	
	public Iterator<U> iterator() {
		return data.iterator();
	}
	
	public List<U> cells() {
		return Collections.unmodifiableList(data);
	}
	
	/*
	 * CELL AT
	 */
	
	public U cellAt(int i, int j) {
		if(i<0 || i>=x) return null;
		if(j<0 || j>=y) return null;
		return get(i, j);
	}
	
	public U cellAt(Point0 p) {
		for(U u : data) if(u.contains(p)) return u;
		return null;
	}
	
	public int count(F<U> f) {
		int count = 0;
		for(U u : data) if(f.f(u)) count++;
		return count;
	}
	
	public boolean any(F<U> f) {
		for(U u : data) if(f.f(u)) return true;
		return false;
	}
	
	public boolean none(F<U> f) {
		return !any(f);
	}
	
	public boolean all(F<U> f) {
		for(U u : data) if(!f.f(u)) return false;
		return true;
	}
	
	public U find(F<U> f) {
		for(U u : data) if(f.f(u)) return u;
		return null;
	}
	
	public List<U> findAll(F<U> f) {
		List<U> list = new ArrayList<>();
		for(U u : data) if(f.f(u)) list.add(u);
		return Collections.unmodifiableList(list);
	}
	
	/*
	 * DYN, DRAW
	 */

	public void goNext() {
		for(U cell : data) cell.goNext();
	}

	public void goBack() {
		for(U cell : data) cell.goBack();
	}

	public void draw(Graphics2D g2) {
		for(U cell : data) cell.draw(g2);
	}
	
	/*
	 * AS
	 */
	
	public boolean[][] asBoolean(F<U> f) {
		boolean[][] data = new boolean[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<x;j++) {
			data[i][j] = f.f(get(i,j));
		}
		return data;
	}
	
	public int[][] asInt(Tint<U> t) {
		int[][] data = new int[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<x;j++) {
			data[i][j] = t.t(get(i,j));
		}
		return data;
	}
	
	public double[][] asDouble(Tdouble<U> t) {
		double[][] data = new double[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<x;j++) {
			data[i][j] = t.t(get(i,j));
		}
		return data;
	}
	
	public char[][] asChar(Tchar<U> t) {
		char[][] data = new char[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<x;j++) {
			data[i][j] = t.t(get(i,j));
		}
		return data;
	}
	
	public String[][] asString(T<U, String> t) {
		String[][] data = new String[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<x;j++) {
			data[i][j] = t.t(get(i,j));
		}
		return data;
	}
	
	/*
	 * PRIVATE
	 */
	
	private U get(int i, int j)  {
		return data.get(i*x+j);
	}
}
