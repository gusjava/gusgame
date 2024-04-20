package gus.game5.core.point.point1;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;

public class Point1 extends Point0 {

	private double x;
	private double y;
	
	public Point1(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point1(double dist, Angle angle) {
		this(dist*angle.cos(), dist*angle.sin());
	}
	
	public Point1(Angle angle) {
		this(angle.cos(), angle.sin());
	}
	
	public Point1(Point0 p) {
		this(p.getX(), p.getY());
	}
	
	public Point1(double val) {
		this(val, val);
	}
	
	public Point1() {
		this(0,0);
	}
	
	
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	
	
	/*
	 * SET XY
	 */
	
	public void setXY(double val) {
		setX(val);
		setY(val);
	}
	
	public void setXY(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public void setXY(double dist, Angle angle) {
		setX(dist*angle.cos());
		setY(dist*angle.sin());
	}
	
	public void setXY(Point0 p) {
		if(p==null) return;
		setX(p.getX());
		setY(p.getY());
	}
	
	/*
	 * ADD
	 */
	
	public void addX(double x1) {
		setX(getX()+x1);
	}
	
	public void addY(double y1) {
		setY(getY()+y1);
	}
	
	public void addXY(double val) {
		addX(val);
		addY(val);
	}
	
	public void addXY(double x1, double y1) {
		addX(x1);
		addY(y1);
	}
	
	public void addXY(double dist, Angle angle) {
		addX(dist*angle.cos());
		addY(dist*angle.sin());
	}
	
	public void addXY(Point0 p) {
		if(p==null) return;
		addX(p.getX());
		addY(p.getY());
	}
	
	
	
	/*
	 * SUB
	 */
	
	public void subX(double x1) {
		setX(getX()-x1);
	}
	
	public void subY(double y1) {
		setY(getY()-y1);
	}
	
	public void subXY(double val) {
		subX(val);
		subY(val);
	}
	
	public void subXY(double x1, double y1) {
		subX(x1);
		subY(y1);
	}
	
	public void subXY(double dist, Angle angle) {
		subX(dist*angle.cos());
		subY(dist+angle.sin());
	}
	
	public void subXY(Point0 p) {
		if(p==null) return;
		subX(p.getX());
		subY(p.getY());
	}
	
	/*
	 * MULT
	 */
	
	public void multX(double x1) {
		setX(getX()*x1);
	}
	
	public void multY(double y1) {
		setY(getY()*y1);
	}
	
	public void multXY(double val) {
		multX(val);
		multY(val);
	}
	
	public void multXY(double x1, double y1) {
		multX(x1);
		multY(y1);
	}
	
	public void multXY(Point0 p) {
		if(p==null) return;
		multX(p.getX());
		multY(p.getY());
	}
	
	/*
	 * DIV
	 */
	
	public void divX(double x1) {
		setX(getX()/x1);
	}
	
	public void divY(double y1) {
		setY(getY()/y1);
	}
	
	public void divXY(double val) {
		divX(val);
		divY(val);
	}
	
	public void divXY(double x1, double y1) {
		divX(x1);
		divY(y1);
	}
	
	public void divXY(Point0 p) {
		if(p==null) return;
		divX(p.getX());
		divY(p.getY());
	}
	
	/*
	 * INV
	 */
	
	public void invX() {
		setX(getX()*-1);
	}
	
	public void invY() {
		setY(getY()*-1);
	}
	
	public void invXY() {
		invX();
		invY();
	}
	
	public void invXY(Point0 origin) {
		subXY(origin);
		invXY();
		addXY(origin);
	}
	
	/*
	 * SET ANGLE
	 */
	
	public void setAngle(Angle angle) {
		setXY(dist(), angle);
	}
	
	public void setAngle(Point0 origin, Angle angle) {
		if(is(origin)) return;
		subXY(origin);
		setAngle(angle);
		addXY(origin);
	}
	
	/*
	 * SET DIST
	 */
	
	public void setDist(double value) {
		if(is(0,0)) return;
		multXY(value/dist());
	}
	
	public void setDist(Point0 origin, double value) {
		if(is(origin)) return;
		subXY(origin);
		setDist(value);
		addXY(origin);
	}
	
	/*
	 * ADD DIST
	 */
	
	public void addDist(double value) {
		if(is(0,0)) return;
		double d = dist();
		multXY((d+value)/d);
	}
	
	public void addDist(Point0 origin, double value) {
		if(is(origin)) return;
		subXY(origin);
		addDist(value);
		addXY(origin);
	}
	
	/*
	 * SUB DIST
	 */
	
	public void subDist(double value) {
		if(is(0,0)) return;
		double d = dist();
		multXY((d-value)/d);
	}
	
	public void subDist(Point0 origin, double value) {
		if(is(origin)) return;
		subXY(origin);
		subDist(value);
		addXY(origin);
	}
	
	/*
	 * ROTATE
	 */
	
	public void rotate(Angle angle) {
		if(angle==null) return;
		if(is(0,0)) return;
		
		double x = getX();
		double y = getY();

		setX(angle.rotationX(x,y));
		setY(angle.rotationY(x,y));
	}
	
	public void rotate(Point0 origin, Angle angle) {
		if(angle==null) return;
		if(is(origin)) return;
		
		subXY(origin);
		rotate(angle);
		addXY(origin);
	}
	
	/*
	 * DILATE
	 */
	
	public void dilate(Point0 factor) {
		if(is(0,0)) return;
		multXY(factor);
	}
	
	public void dilate(Point0 origin, Point0 factor) {
		if(is(origin)) return;
		subXY(origin);
		dilate(factor);
		addXY(origin);
	}
}
