package gus.game5.core.point.point1;

import java.util.ArrayList;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;

public class Point1List extends ArrayList<Point1> {
	private static final long serialVersionUID = 1L;
	
	/*
	 * ADD
	 */
	
	public void addX(double x1) {
		for(Point1 point : Point1List.this) point.addX(x1);
	}
	
	public void addY(double y1) {
		for(Point1 point : Point1List.this) point.addY(y1);
	}
	
	public void addXY(double val) {
		for(Point1 point : Point1List.this) point.addXY(val);
	}
	
	public void addXY(double x1, double y1) {
		for(Point1 point : Point1List.this) point.addXY(x1, y1);
	}
	
	public void addXY(Point0 p) {
		if(p==null) return;
		for(Point1 point : Point1List.this) point.addXY(p);
	}
	
	
	
	/*
	 * SUB
	 */
	
	public void subX(double x1) {
		for(Point1 point : Point1List.this) point.subX(x1);
	}
	
	public void subY(double y1) {
		for(Point1 point : Point1List.this) point.subY(y1);
	}
	
	public void subXY(double val) {
		for(Point1 point : Point1List.this) point.subXY(val);
	}
	
	public void subXY(double x1, double y1) {
		for(Point1 point : Point1List.this) point.subXY(x1, y1);
	}
	
	public void subXY(Point0 p) {
		if(p==null) return;
		for(Point1 point : Point1List.this) point.subXY(p);
	}
	
	/*
	 * MULT
	 */
	
	public void multX(double x1) {
		for(Point1 point : Point1List.this) point.multX(x1);
	}
	
	public void multY(double y1) {
		for(Point1 point : Point1List.this) point.multY(y1);
	}
	
	public void multXY(double val) {
		for(Point1 point : Point1List.this) point.multXY(val);
	}
	
	public void multXY(double x1, double y1) {
		for(Point1 point : Point1List.this) point.multXY(x1, y1);
	}
	
	public void multXY(Point0 p) {
		if(p==null) return;
		for(Point1 point : Point1List.this) point.multXY(p);
	}
	
	/*
	 * DIV
	 */
	
	public void divX(double x1) {
		for(Point1 point : Point1List.this) point.divX(x1);
	}
	
	public void divY(double y1) {
		for(Point1 point : Point1List.this) point.divY(y1);
	}
	
	public void divXY(double val) {
		for(Point1 point : Point1List.this) point.divXY(val);
	}
	
	public void divXY(double x1, double y1) {
		for(Point1 point : Point1List.this) point.divXY(x1, y1);
	}
	
	public void divXY(Point0 p) {
		if(p==null) return;
		for(Point1 point : Point1List.this) point.divXY(p);
	}
	
	/*
	 * INV
	 */
	
	public void invX() {
		for(Point1 point : Point1List.this) point.invX();
	}
	
	public void invY() {
		for(Point1 point : Point1List.this) point.invY();
	}
	
	public void invXY() {
		for(Point1 point : Point1List.this) point.invXY();
	}
	
	public void invXY(Point0 origin) {
		for(Point1 point : Point1List.this) point.invXY(origin);
	}
	
	
	/*
	 * SET DIST
	 */
	
	public void setDist(double value) {
		for(Point1 point : Point1List.this) point.setDist(value);
	}
	
	public void setDist(Point0 origin, double value) {
		for(Point1 point : Point1List.this) point.setDist(origin, value);
	}
	
	/*
	 * ADD DIST
	 */
	
	public void addDist(double value) {
		for(Point1 point : Point1List.this) point.addDist(value);
	}
	
	public void addDist(Point0 origin, double value) {
		for(Point1 point : Point1List.this) point.addDist(origin, value);
	}
	
	/*
	 * SUB DIST
	 */
	
	public void subDist(double value) {
		for(Point1 point : Point1List.this) point.subDist(value);
	}
	
	public void subDist(Point0 origin, double value) {
		for(Point1 point : Point1List.this) point.subDist(origin, value);
	}
	
	/*
	 * ROTATE
	 */
	
	public void rotate(Angle angle) {
		if(angle==null) return;
		for(Point1 point : Point1List.this) point.rotate(angle);
	}
	
	public void rotate(Point0 origin, Angle angle) {
		if(angle==null) return;
		for(Point1 point : Point1List.this) point.rotate(origin, angle);
	}
	
	/*
	 * DILATE
	 */
	
	public void dilate(Point0 factor) {
		if(factor==null) return;
		for(Point1 point : Point1List.this) point.dilate(factor);
	}
	
	public void dilate(Point0 origin, Point0 factor) {
		if(factor==null) return;
		for(Point1 point : Point1List.this) point.dilate(origin, factor);
	}
}
