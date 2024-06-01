package gus.game5.core.shape.board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import gus.game5.core.draw.Draw;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.features.f.F;
import gus.game5.core.features.p2.P2int;
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
	private int nb;
	
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
			cell.initCell(cellSize, x, y);
			data.add(cell);
		}
		nb = data.size();
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
	
	public U cellAt(int index) {
		if(index<0 || index>=nb) return null;
		return get(index);
	}
	
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
	
	public boolean[] asBoolean1(F<U> f) {
		boolean[] ouput = new boolean[nb];
		for(int i=0;i<nb;i++) {
			ouput[i] = f.f(get(i));
		}
		return ouput;
	}
	
	public boolean[][] asBoolean2(F<U> f) {
		boolean[][] ouput = new boolean[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			ouput[i][j] = f.f(get(i,j));
		}
		return ouput;
	}
	
	public int[] asInt1(Tint<U> t) {
		int[] ouput = new int[nb];
		for(int i=0;i<nb;i++) {
			ouput[i] = t.t(get(i));
		}
		return ouput;
	}
	
	public int[][] asInt2(Tint<U> t) {
		int[][] ouput = new int[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			ouput[i][j] = t.t(get(i,j));
		}
		return ouput;
	}
	
	public double[] asDouble1(Tdouble<U> t) {
		double[] ouput = new double[nb];
		for(int i=0;i<nb;i++) {
			ouput[i] = t.t(get(i));
		}
		return ouput;
	}
	
	public double[][] asDouble2(Tdouble<U> t) {
		double[][] ouput = new double[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			ouput[i][j] = t.t(get(i,j));
		}
		return ouput;
	}
	
	public char[] asChar1(Tchar<U> t) {
		char[] ouput = new char[nb];
		for(int i=0;i<nb;i++) {
			ouput[i] = t.t(get(i));
		}
		return ouput;
	}
	
	public char[][] asChar2(Tchar<U> t) {
		char[][] ouput = new char[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			ouput[i][j] = t.t(get(i,j));
		}
		return ouput;
	}
	
	public String[] asString1(T<U, String> t) {
		String[] ouput = new String[nb];
		for(int i=0;i<nb;i++) {
			ouput[i] = t.t(get(i));
		}
		return ouput;
	}
	
	public String[][] asString2(T<U, String> t) {
		String[][] ouput = new String[x][y];
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			ouput[i][j] = t.t(get(i,j));
		}
		return ouput;
	}
	
	/*
	 * UPDATE CELLS
	 */
	
	public void updateCells(int[][] data, P2int<U> p) {
		for(int i=0;i<x;i++)
		for(int j=0;j<y;j++) {
			p.p(get(i, j), data[i][j]);
		}
	}
	
	/*
	 * DEBUG
	 */
	
	public void debugCellFrame(Color c) {
		for(U cell : data) cell.debugFrame(c);
	}
	
	public void debugCellOrigin(Color c) {
		for(U cell : data) cell.debugOrigin(c);
	}
	
	public void debugCellAnchor(Color c) {
		for(U cell : data) cell.debugAnchor(c);
	}
	
	/*
	 * PRIVATE
	 */
	
	private U get(int i, int j)  {
		return get(i*y+j);
	}
	
	private U get(int index) {
		return data.get(index);
	}
}
