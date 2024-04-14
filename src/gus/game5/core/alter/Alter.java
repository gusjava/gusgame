package gus.game5.core.alter;

import gus.game5.core.point.point0.Point0;

public interface Alter  {

	public Point0 alterPoint(Point0 p);
	public double alterDistance(double dist);

	public Point0 revPoint(Point0 p);
	public double revDistance(double dist);
}
