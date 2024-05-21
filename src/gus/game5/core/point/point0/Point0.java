package gus.game5.core.point.point0;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.util.UtilDisplay;
import gus.game5.core.util.UtilDistance;

public abstract class Point0 {
	
	public abstract double getX();
	public abstract double getY();
	
	public String toString() {
		return "["+UtilDisplay.dec2(getX())+":"+UtilDisplay.dec2(getY())+"]";
	}
	
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(!(obj instanceof Point0)) return false;
		return is((Point0) obj);
	}
	
	/*
	 * ANGLE
	 */
	
	public Angle angle() {
		return Angle.angleXY(getX(), getY());
	}
	
	/*
	 * SLOPE
	 */
	
	public Double slope() {
		double x = getX();
		if(x==0) return null;
		return getY()/x;
	}
	
	/*
	 * DIST
	 */
	
	public double dist() {
		return UtilDistance.dist(getX(), getY());
	}
	
	public double dist(double x1, double y1) {
		return UtilDistance.dist(getX(), getY(), x1, y1);
	}
	
	public double dist(Point0 p) {
		return UtilDistance.dist(this, p);
	}
	
	/*
	 * DIST 2
	 */
	
	public double dist2() {
		return UtilDistance.dist2(getX(), getY());
	}
	
	public double dist2(double x1, double y1) {
		return UtilDistance.dist2(getX(), getY(), x1, y1);
	}
	
	public double dist2(Point0 p) {
		return UtilDistance.dist2(this, p);
	}
	
	/*
	 * IS
	 */
	
	public boolean isX(double x) {
		return getX()==x;
	}
	
	public boolean isY(double y) {
		return getY()==y;
	}
	
	public boolean is(double x, double y) {
		return isX(x) && isY(y);
	}
	
	public boolean is(Point0 p) {
		if(p==null) return false;
		return isX(p.getX()) && isY(p.getY());
	}
	
	/*
	 * BETWEEN
	 */
	
	public boolean xBetween(double xMin, double xMax) {
		double x = getX();
		return x>=xMin && x<=xMax;
	}
	
	public boolean yBetween(double yMin, double yMax) {
		double y = getY();
		return y>=yMin && y<=yMax;
	}
	
	/*
	 * NEAR
	 */
	
	public boolean near(Point0 p, double limit) {
		return dist2(p)<=limit*limit;
	}
	
	/*
	 * P1 P2
	 */
	
	public Point1 p1() {
		return new Point1(this);
	}
	
	public Point2 p2() {
		return new Point2(this);
	}
	
	/*
	 * P SET
	 */
	
	public Point1 pSetX(double x) {
		return new Point1(x, getY());
	}
	
	public Point1 pSetY(double y) {
		return new Point1(getX(), y);
	}
	
	/*
	 * P ADD
	 */
	
	public Point1 pAdd(double value) {
		return new Point1(getX()+value, getY()+value);
	}
	
	public Point1 pAdd(double x1, double y1) {
		return new Point1(getX()+x1, getY()+y1);
	}
	
	public Point1 pAdd(double dist, Angle angle) {
		return new Point1(getX()+dist*angle.cos(), getY()+dist*angle.sin());
	}
	
	public Point1 pAdd(Point0 p) {
		if(p==null) return p1();
		return new Point1(getX()+p.getX(), getY()+p.getY());
	}
	
	/*
	 * P SUB
	 */
	
	public Point1 pSub(double value) {
		return new Point1(getX()-value, getY()-value);
	}
	
	public Point1 pSub(double x1, double y1) {
		return new Point1(getX()-x1, getY()-y1);
	}
	
	public Point1 pSub(double dist, Angle angle) {
		return new Point1(getX()-dist*angle.cos(), getY()-dist+angle.sin());
	}
	
	public Point1 pSub(Point0 p) {
		if(p==null) return p1();
		return new Point1(getX()-p.getX(), getY()-p.getY());
	}
	
	/*
	 * P MULT
	 */
	
	public Point1 pMult(double value) {
		return new Point1(getX()*value, getY()*value);
	}
	
	public Point1 pMult(double x1, double y1) {
		return new Point1(getX()*x1, getY()*y1);
	}
	
	public Point1 pMult(Point0 p) {
		if(p==null) return p1();
		return new Point1(getX()*p.getX(), getY()*p.getY());
	}
	
	/*
	 * P DIV
	 */
	
	public Point1 pDiv(double value) {
		if(value==0) return null;
		return new Point1(getX()/value, getY()/value);
	}
	
	public Point1 pDiv(double x1, double y1) {
		if(x1==0 || y1==0) return null;
		return new Point1(getX()/x1, getY()/y1);
	}
	
	public Point1 pDiv(Point0 p) {
		if(p==null) return p1();
		if(p.getX()==0 || p.getY()==0) return null;
		return new Point1(getX()/p.getX(), getY()/p.getY());
	}
	
	/*
	 * P INV
	 */
	
	public Point1 pInv() {
		return new Point1(-getX(), -getY());
	}
	
	public Point1 pInv(Point0 origin) {
		if(origin==null) return pInv();
		return pSub(origin).pInv().pAdd(origin);
	}
	
	/*
	 * P DIST SET
	 */
	
	public Point1 pDistSet(double value) {
		if(is(0,0)) return null;
		double d = dist();
		return pMult(value/d);
	}
	
	public Point1 pDistSet(Point0 origin, double val) {
		if(origin==null) return pDistSet(val);
		return pSub(origin).pDistSet(val).pAdd(origin);
	}
	
	/*
	 * P DIST ADD
	 */
	
	public Point1 pDistAdd(double value) {
		double d = dist();
		return pMult(d!=0 ? (d+value)/d : 0);
	}
	
	public Point1 pDistAdd(Point0 origin, double value) {
		if(origin==null) return pDistAdd(value);
		return pSub(origin).pDistAdd(value).pAdd(origin);
	}
	
	/*
	 * P DIST SUB
	 */
	
	public Point1 pDistSub(double value) {
		double d = dist();
		return pMult(d!=0 ? (d+value)/d : 0);
	}
	
	public Point1 pDistSub(Point0 origin, double value) {
		if(origin==null) return pDistSub(value);
		return pSub(origin).pDistSub(value).pAdd(origin);
	}
	
	/*
	 * P ROTATE
	 */
	
	public Point1 pRotate(Angle angle) {
		if(angle==null) return p1();
		
		double x = getX();
		double y = getY();

		double x1 = angle.rotationX(x,y);
		double y1 = angle.rotationY(x,y);
		return new Point1(x1, y1);
	}
	
	public Point1 pRotate(Point0 origin, Angle angle) {
		if(origin==null) return pRotate(angle);
		return pSub(origin).pRotate(angle).pAdd(origin);
	}
	
	/*
	 * P DILATE
	 */
	
	public Point1 pDilate(double factor) {
		return pMult(factor);
	}
	
	public Point1 pDilate(Point0 factor) {
		return pMult(factor);
	}
	
	public Point1 pDilate(Point0 origin, double factor) {
		if(origin==null) return pDilate(factor);
		return pSub(origin).pDilate(factor).pAdd(origin);
	}
	
	public Point1 pDilate(Point0 origin, Point0 factor) {
		if(origin==null) return pDilate(factor);
		return pSub(origin).pDilate(factor).pAdd(origin);
	}
}
