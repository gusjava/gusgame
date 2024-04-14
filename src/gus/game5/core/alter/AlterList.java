package gus.game5.core.alter;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.point.point0.Point0;

public class AlterList extends ArrayList<Alter> implements Alter {
	private static final long serialVersionUID = 1L;
	
	public AlterList() {
		super();
	}
	
	public AlterList(List<Alter> alters) {
		super(alters);
	}
	
	public AlterList(Alter... alters) {
		super();
		for(Alter alter : alters) add(alter);
	}
	
	public void setAll(List<Alter> alters) {
		clear();
		addAll(alters);
	}
	
	public Point0 alterPoint(Point0 p) {
		for(Alter alter : this) {
			if(alter!=null) p = alter.alterPoint(p);
		}
		return p;
	}

	public double alterDistance(double dist) {
		for(Alter alter : this) {
			if(alter!=null) dist = alter.alterDistance(dist);
		}
		return dist;
	}

	public Point0 revPoint(Point0 p) {
		int nb = size();
		for(int i=0;i<nb;i++) {
			Alter alter = get(nb-i-1);
			if(alter!=null) p = alter.revPoint(p);
		}
		return p;
	}

	public double revDistance(double dist) {
		int nb = size();
		for(int i=0;i<nb;i++) {
			Alter alter = get(nb-i-1);
			if(alter!=null) dist = alter.revDistance(dist);
		}
		return dist;
	}
}
