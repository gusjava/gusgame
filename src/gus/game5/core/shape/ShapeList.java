package gus.game5.core.shape;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.alter.Alter;
import gus.game5.core.alter.AlterD;
import gus.game5.core.clean.Clean;
import gus.game5.core.draw.Draw;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.control.ShapeControl;

public class ShapeList<U extends Shape> extends ArrayList<U> implements Draw, Dyn, Clean {
	private static final long serialVersionUID = 1L;
	
	public ShapeList() {
		super();
	}
	
	public ShapeList(List<U> shapes) {
		super(shapes);
	}
	
	@SuppressWarnings("unchecked")
	public ShapeList(U... shapes) {
		super();
		for(U shape : shapes) add(shape);
	}
	
	/*
	 * ALTER
	 */
	
	private Alter alter;
	private AlterD alterD = new AlterD(this::getAlter);
	
	public void setAlter(Alter alter) {
		this.alter = alter;
	}
	
	public Alter getAlter() {
		return alter;
	}
	
	private void initAlter(U shape) {
		shape.addAlter(alterD);
	}
	
	private void resetAlter(U shape) {
		shape.removeAlter(alterD);
	}
	
	/*
	 * ADD
	 */
	
	public boolean add(U shape) {
		initAlter(shape);
		return super.add(shape);
	}
	
	/*
	 * REMOVE
	 */
	
	public boolean remove(U shape) {
		resetAlter(shape);
		return super.remove(shape);
	}
	
	/*
	 * ADD ALL
	 */
	
	public boolean addAll(List<U> shapes) {
		shapes.forEach(this::initAlter);
		return super.addAll(shapes);
	}
	
	/*
	 * REMOVE ALL
	 */
	
	public boolean removeAll(List<U> shapes) {
		shapes.forEach(this::resetAlter);
		return super.removeAll(shapes);
	}
	
	/*
	 * CLEAR
	 */
	
	public void clear() {
		forEach(this::resetAlter);
		super.clear();
	}
	
	/*
	 * DRAW
	 */

	public void draw(Graphics2D g2) {
		for(U element : this) element.draw(g2);
	}
	
	/*
	 * GO NEXT, GO BACK
	 */

	public void goNext() {
		for(U element : this) element.goNext();
	}

	public void goBack() {
		for(U element : this) element.goBack();
	}
	
	/*
	 * CLEAN
	 */
	
	public void clean() {
		removeIf(Shape::isOver);
	}
	
	/*
	 * FILLED
	 */
	
	public boolean isFilled() {
		return !isEmpty();
	}
	
	/*
	 * ANY
	 */
	
	public boolean any(F<U> f) {
		for(U element : this) {
			if(f.f(element)) return true;
		}
		return false;
	}
	
	public boolean anyCo(Point0 p) {
		for(U element : this) {
			if(element.contains(p)) return true;
		}
		return false;
	}
	
	public boolean anyIncl(Shape p) {
		for(U element : this) {
			if(element.includes(p)) return true;
		}
		return false;
	}
	
	public boolean anyInter(Shape p) {
		for(U element : this) {
			if(element.intersects(p)) return true;
		}
		return false;
	}
	
	/*
	 * ALL
	 */
	
	public boolean all(F<U> f) {
		for(U element : this) {
			if(!f.f(element)) return false;
		}
		return true;
	}
	
	public boolean allCo(Point0 p) {
		for(U element : this) {
			if(!element.contains(p)) return false;
		}
		return true;
	}
	
	public boolean allIncl(Shape p) {
		for(U element : this) {
			if(!element.includes(p)) return false;
		}
		return true;
	}
	
	public boolean allInter(Shape p) {
		for(U element : this) {
			if(!element.intersects(p)) return false;
		}
		return true;
	}
	
	/*
	 * NONE
	 */
	
	public boolean none(F<U> f) {
		return !any(f);
	}
	
	public boolean noneCo(Point0 p) {
		return !anyCo(p);
	}
	
	public boolean noneIncl(Shape p) {
		return !anyIncl(p);
	}
	
	public boolean noneInter(Shape p) {
		return !anyInter(p);
	}
	
	/*
	 * FIND
	 */
	
	public U find(F<U> f) {
		for(U element : this) {
			if(f.f(element)) return element;
		}
		return null;
	}
	
	public U findCo(Point0 p) {
		for(U element : this) {
			if(element.contains(p)) return element;
		}
		return null;
	}
	
	public U findIncl(Shape p) {
		for(U element : this) {
			if(element.includes(p)) return element;
		}
		return null;
	}
	
	public U findInter(Shape p) {
		for(U element : this) {
			if(element.intersects(p)) return element;
		}
		return null;
	}
	
	/*
	 * FIND NEAREST
	 */
	
	public U findNearest(Point0 p) {
		U found = null;
		double dist0 = -1;
		
		for(U element : this) {
			double dist = element.getAnchor().dist2(p);
			if(dist0==-1 || dist < dist0) {
				dist0 = dist;
				found = element;
			}
		}
		return found;
	}
	
	public U findNearest(Shape p) {
		U found = null;
		double dist0 = -1;
		Point0 a = p.getAnchor();
		
		for(U element : this) if(element!=p) {
			double dist = element.getAnchor().dist2(a);
			if(dist0==-1 || dist < dist0) {
				dist0 = dist;
				found = element;
			}
		}
		return found;
	}
	
	/*
	 * FIND FAREST
	 */
	
	public U findFarest(Point0 p) {
		U found = null;
		double dist0 = -1;
		
		for(U element : this) {
			double dist = element.getAnchor().dist2(p);
			if(dist0==-1 || dist > dist0) {
				dist0 = dist;
				found = element;
			}
		}
		return found;
	}
	
	public U findFarest(Shape p) {
		U found = null;
		double dist0 = -1;
		Point0 a = p.getAnchor();
		
		for(U element : this) if(element!=p) {
			double dist = element.getAnchor().dist2(a);
			if(dist0==-1 || dist > dist0) {
				dist0 = dist;
				found = element;
			}
		}
		return found;
	}
	
	/*
	 * FIND ALL
	 */
	
	public List<U> findAll(F<U> f) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(f.f(element)) list.add(element);
		}
		return list;
	}
	
	public List<U> findAllCo(Point0 p) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(element.contains(p)) list.add(element);
		}
		return list;
	}
	
	public List<U> findAllCoX(double x) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(element.containsX(x)) list.add(element);
		}
		return list;
	}
	
	public List<U> findAllCoY(double y) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(element.containsY(y)) list.add(element);
		}
		return list;
	}
	
	public List<U> findAllIncl(Shape p) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(element.includes(p)) list.add(element);
		}
		return list;
	}
	
	public List<U> findAllInter(Shape p) {
		List<U> list = new ArrayList<>();
		for(U element : this) {
			if(element.intersects(p)) list.add(element);
		}
		return list;
	}
	
	public List<List<U>> findAllInterGroups() {
		List<List<U>> groups = new ArrayList<>();
		for(U element1 : this) for(U element2 : this) {
			if(element1!=element2) {
				//TODO
			}
		}
		return groups;
	}
	
	/*
	 * COLLECT
	 */
	
	public <V> List<V> collect(T<U,V> t) {
		List<V> list = new ArrayList<>();
		for(U element : this) {
			list.add(t.t(element));
		}
		return list;
	}
	
	/*
	 * SUM
	 */
	
	public int sumInt(T<U,Integer> t) {
		int sum = 0;
		for(U element : this) sum += t.t(element);
		return sum;
	}
	
	public double sumDouble(T<U,Double> t) {
		double sum = 0;
		for(U element : this) sum += t.t(element);
		return sum;
	}
	
	public Point0 sumPoint(T<U,Point0> t) {
		Point1 p = new Point1();
		for(U element : this) p.addXY(t.t(element));
		return p;
	}
	
	/*
	 * AVG
	 */
	
	public double avgDouble(T<U,Double> t) {
		double sum = 0;
		for(U element : this) sum += t.t(element);
		return sum/size();
	}
	
	public Point0 avgPoint(T<U,Point0> t) {
		Point1 p = new Point1();
		for(U element : this) p.addXY(t.t(element));
		p.divXY(size());
		return p;
	}
	
	/*
	 * CONTROL
	 */
	
	public void control(ShapeControl control) {
		for(U element : this) {
			control.handle(element);
		}
	}
}
