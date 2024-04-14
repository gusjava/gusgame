package gus.game5.core.alter;

import gus.game5.core.features.g.G;
import gus.game5.core.point.point0.Point0;

public class AlterD implements Alter {
	
	private G<Alter> gAlter;
	
	public AlterD(G<Alter> gAlter) {
		this.gAlter = gAlter;
	}

	public Point0 alterPoint(Point0 p) {
		Alter alter = gAlter.g();
		return alter!=null ? alter.alterPoint(p) : p;
	}

	public double alterDistance(double dist) {
		Alter alter = gAlter.g();
		return alter!=null ? alter.alterDistance(dist) : dist;
	}

	public Point0 revPoint(Point0 p) {
		Alter alter = gAlter.g();
		return alter!=null ? alter.revPoint(p) : p;
	}

	public double revDistance(double dist) {
		Alter alter = gAlter.g();
		return alter!=null ? alter.revDistance(dist) : dist;
	}
}
