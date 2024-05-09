package gus.game5.core.point.point2;

import gus.game5.core.angle.Angle;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;

public class Point2 extends Point1 implements Dyn {

	private Point0 previous;
	private Point2 derived;
	
	public Point2() {
		super();
	}
	
	public Point2(double val) {
		super(val);
	}
	
	public Point2(double x, double y) {
		super(x,y);
	}
	
	public Point2(Point0 p) {
		super(p);
	}
	
	public Point2(Angle angle) {
		super(angle);
	}
	
	public Point2(double dist, Angle angle) {
		super(dist, angle);
	}
	
	/*
	 * PREVIOUS
	 */
	
	public Point0 getPrevious() {
		return previous;
	}
	
	public void setPrevious(Point0 previous) {
		this.previous = previous;
	}
	
	/*
	 * DERIVED
	 */
	
	public Point2 getDerived() {
		return derived;
	}
	
	public Point2 initDerived() {
		if(derived==null) derived = new Point2();
		return derived;
	}
	
	public void setDerived(Point2 derived) {
		this.derived = derived;
	}
	
	public void setDerived(Point0 p) {
		this.derived = p!=null ? new Point2(p) : null;
	}
	
	public void setDerived(double x, double y) {
		this.derived = new Point2(x,y);
	}
	
	public void setDerived(double dist, Angle angle) {
		this.derived = new Point2(dist,angle);
	}
	
	public void setDerived(Point0 target, double speed) {
		if(target==null) setDerived(null);
		else setDerived(target.pSub(this).pDistSet(speed));
	}

	/*
	 * GO NEXT
	 */
	
	public void goNext() {
		if(derived==null) return;
		
		previous = p1();
		derived.goNext();
		addXY(derived);
	}
	
	/*
	 * GO BACK
	 */
	
	public void goBack() {
		if(derived==null) return;
		if(previous==null) return;

		derived.goBack();
		setXY(previous);
		previous = null;
	}
}
