package gus.game5.core.util;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.t.T;

public class UtilList {

	@SuppressWarnings("unchecked")
	public static <U> List<U> asList(U... us) {
		List<U> list = new ArrayList<>();
		for(U u : us) list.add(u);
		return list;
	}

	public static <U,V> List<V> collect(List<U> list, T<? super U,? extends V> t) {
		if(list==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : list) list1.add(t.t(u));
		return list1;
	}
	
	public static <U> U get(List<U> list, int index) {
		if(index<0 || index>=list.size()) return null;
		return list.get(index);
	}
}
