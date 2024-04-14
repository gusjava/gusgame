package gus.game5.core.shape.control;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.Shape;
import gus.game5.core.shape.ShapeList;

public class ShapeControlBlockRebound<E extends Shape> implements ShapeControl {

	private ShapeList<E> blocs;
	
	private boolean top = true;
	private boolean bottom = true;
	private boolean right = true;
	private boolean left = true;
	
	public ShapeControlBlockRebound(ShapeList<E> blocs) {
		this.blocs = blocs;
	}

	public void handle(Shape shape) {
		if(shape==null) return;
		
		Point0 center = shape.getPc();
		
		Point0 p0 = shape.getP0();
		Point0 p1 = shape.getP1();
		
		
		
		for(E bloc : blocs) {
			if(bloc.contains(center)) {
				
			}
		}
		
//		if(x0<0 && right) {
//			anchor.subX(2*x0);
//			anchor.initDerived().invX();
//		}
//		else if(x1>gameW && left) {
//			anchor.subX(2*(x1-gameW));
//			anchor.initDerived().invX();
//		}
//		
//		if(y0<0 && top) {
//			anchor.subY(2*y0);
//			anchor.initDerived().invY();
//		}
//		else if(y1>gameH && bottom) {
//			anchor.subY(2*(y1-gameH));
//			anchor.initDerived().invY();
//		}
	}
	
	

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
}
