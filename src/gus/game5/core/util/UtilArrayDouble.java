package gus.game5.core.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gus.game5.core.features.g.GDouble;

public class UtilArrayDouble {
	
	/*
	 * COLLECT
	 */
	
	public static double[] collectDouble2(GDouble... dd) {
		double[] data = new double[dd.length];
		for(int i=0;i<data.length;i++) data[i] = dd[i].gDouble();
		return data;
	}
	
	public static double[] collectDouble2(List<Double> list) {
		double[] data = new double[list.size()];
		for(int i=0;i<data.length;i++) data[i] = list.get(i);
		return data;
	}
	
	public static double[] collectDouble2(Set<Double> set) {
		double[] data = new double[set.size()];
		Iterator<Double> it = set.iterator();
		for(int i=0;i<data.length;i++) data[i] = it.next();
		return data;
	}
}
