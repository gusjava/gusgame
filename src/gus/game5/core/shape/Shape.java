package gus.game5.core.shape;

import java.awt.Color;

import gus.game5.core.alter.Alter;
import gus.game5.core.draw.Draw;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public interface Shape extends Draw, Dyn {

	public Point2 getAnchor();
	
	public double getWidth();
	public double getHeight();
	
	public double getWidth2();
	public double getHeight2();
	
	public Point0 getPc();
	public Point0 getP0();
	public Point0 getP1();

	public boolean contains(Point0 p);
	public boolean containsX(double x);
	public boolean containsY(double y);
	public boolean includes(Shape p);
	public boolean intersects(Shape p);
	public boolean isOver();
	
	public void addAlter(Alter alter);
	public void removeAlter(Alter alter);
	public void debugFrame(Color c);
	public void debugOrigin(Color c);
	public void debugAnchor(Color c);
}
