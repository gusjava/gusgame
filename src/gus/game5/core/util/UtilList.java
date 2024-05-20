package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.t.T;
import gus.game5.core.features.t.Tdouble;
import gus.game5.core.features.t.Tint;

public class UtilList {

	@SuppressWarnings("unchecked")
	public static <U> List<U> asList(U... us) {
		List<U> list = new ArrayList<>();
		for(U u : us) list.add(u);
		return list;
	}
	
	public static <U> U get(List<U> list, int index) {
		if(index<0 || index>=list.size()) return null;
		return list.get(index);
	}
	
	/*
	 * COLLECT
	 */

	public static <U,V> List<V> collect(List<U> list, T<? super U,? extends V> t) {
		if(list==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : list) list1.add(t.t(u));
		return list1;
	}
	
	/*
	 * FIND FOR
	 */
	
	public static <U> U findForMaxInt(List<U> list, Tint<U> t) {
		int max = Integer.MIN_VALUE;
		U found = null;
		
		for(U u : list) {
			int value = t.t(u);
			if(max<value) {
				found = u;
				max = value;
			}
		}
		return found;
	}
	
	public static <U> U findForMinInt(List<U> list, Tint<U> t) {
		int min = Integer.MAX_VALUE;
		U found = null;
		
		for(U u : list) {
			int value = t.t(u);
			if(min>value) {
				found = u;
				min = value;
			}
		}
		return found;
	}
	
	public static <U> U findForMaxDouble(List<U> list, Tdouble<U> t) {
		double max = Double.MIN_VALUE;
		U found = null;
		
		for(U u : list) {
			double value = t.t(u);
			if(max<value) {
				found = u;
				max = value;
			}
		}
		return found;
	}
	
	public static <U> U findForMinDouble(List<U> list, Tdouble<U> t) {
		double min = Double.MAX_VALUE;
		U found = null;
		
		for(U u : list) {
			double value = t.t(u);
			if(min>value) {
				found = u;
				min = value;
			}
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	public static <U> List<U> addAll(List<U> list, U... uu) {
		for(U u : uu) list.add(u);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <U> List<U> addAllNotNull(List<U> list, U... uu) {
		for(U u : uu) if(u!=null) list.add(u);
		return list;
	}
	
	
}
