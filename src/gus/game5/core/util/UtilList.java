package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;
import gus.game5.core.features.t.Tdouble;
import gus.game5.core.features.t.Tint;

public class UtilList {
	
	/*
	 * FIND ALL
	 */
	
	public static <U> List<U> findAll(List<U> list, F<? super U> f) {
		List<U> list1 = newList(list);
		keep(list1,f);
		return list1;
	}
	
	/*
	 * KEEP
	 */
	
	public static <U> int keep(List<U> list, F<? super U> f) {
		if(f==null || list==null) return 0;
		Iterator<U> it = list.iterator();
		int removed = 0;
		while(it.hasNext()) {
			if(!f.f(it.next())) {
				removed++;
				it.remove();
			}
		}
		return removed;
    }
	
	/*
	 * NEW LIST
	 */
	
	public static <U> List<U> newList(List<U> list) {
		if(list==null) return null;
		return new ArrayList<>(list);
	}
	
	public static <U> List<U> newList(Set<U> set) {
		if(set==null) return null;
		return new ArrayList<>(set);
	}
	
	public static <U> List<U> newList(Collection<U> col) {
		if(col==null) return null;
		return new ArrayList<>(col);
	}
	
	public static <U> List<U> newList(U[] array) {
		if(array==null) return null;
		List<U> list = new ArrayList<>();
		for(U u : array) list.add(u);
		return list;
	}
	
	/*
	 * AS LIST
	 */

	@SafeVarargs
	public static <U> List<U> asList(U... us) {
		List<U> list = new ArrayList<>();
		for(U u : us) list.add(u);
		return list;
	}
	
	@SafeVarargs
	public static <U> List<U> asList(Collection<U>... cc) {
		if(cc==null) return null;
		List<U> list = new ArrayList<>();
		for(Collection<U> c : cc) list.addAll(c);
		return list;
	}
	
	public static <U> List<U> asList(List<Collection<U>> cc) {
		if(cc==null) return null;
		List<U> list = new ArrayList<>();
		for(Collection<U> c : cc) list.addAll(c);
		return list;
	}
	
	@SafeVarargs
	public static <U> List<U> asList(Collection<U> col, U... array) {
		List<U> list = new ArrayList<>(col);
		if(array!=null) for(U element : array) list.add(element);
		return list;
	}
	
	public static <U> List<U> asList(U element, Collection<U> col) {
		List<U> list = asList(element);
		if(col!=null) list.addAll(col);
		return list;
	}
	
	@SafeVarargs
	public static <U> List<U> asList(U[] array0, U... elements) {
		List<U> list = new ArrayList<>();
		if(array0!=null) for(U element : array0) list.add(element);
		if(elements!=null) for(U element : elements) list.add(element);
		return list;
	}
	
	public static <U> List<U> asList(U element, U[] array0) {
		List<U> list = new ArrayList<>();
		if(element!=null) list.add(element);
		if(array0!=null) for(U elem : array0) list.add(elem);
		return list;
	}
	
	public static <U> List<U> asList(Iterator<U> it) {
		if(it==null) return null;
		List<U> list = new ArrayList<>();
		while(it.hasNext()) list.add(it.next());
		return list;
	}
	
	public static <U> List<U> asList(int times, U element) {
		List<U> list = new ArrayList<>();
		for(int i=0;i<times;i++) list.add(element);
		return list;
	}
	
	/*
	 * GET
	 */
	
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
	
	public static <U,V> List<V> collect(Set<U> set, T<? super U,? extends V> t) {
		if(set==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : set) list1.add(t.t(u));
		return list1;
	}
	
	public static <U,V> List<V> collect(Collection<U> col, T<? super U,? extends V> t) {
		if(col==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : col) list1.add(t.t(u));
		return list1;
	}
	
	public static <U,V> List<V> collect(Iterator<U> it, T<? super U,? extends V> t) {
		if(it==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		while(it.hasNext()) list1.add(t.t(it.next()));
		return list1;
	}
	
	public static <U,V> List<V> collect(Iterable<U> iter, T<? super U,? extends V> t) {
		if(iter==null || t==null) return null;
		return collect(iter.iterator(), t);
	}
	
	public static <U,V> List<V> collect(U[] array, T<? super U,? extends V> t) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : array) list1.add(t.t(u));
		return list1;
	}
	
	/*
	 * COLLECT MIN
	 */
	
	public static <U> int collectMinInt(List<U> list, Tint<U> t) {
		int min = Integer.MAX_VALUE;
		for(U u : list) {
			int value = t.t(u);
			if(min>value) {
				min = value;
			}
		}
		return min;
	}
	
	public static <U> double collectMinDouble(List<U> list, Tdouble<U> t) {
		double min = Double.MAX_VALUE;
		for(U u : list) {
			double value = t.t(u);
			if(min>value) {
				min = value;
			}
		}
		return min;
	}
	
	/*
	 * COLLECT MAX
	 */
	
	public static <U> int collectMaxInt(List<U> list, Tint<U> t) {
		int max = Integer.MIN_VALUE;
		for(U u : list) {
			int value = t.t(u);
			if(max<value) {
				max = value;
			}
		}
		return max;
	}
	
	public static <U> double collectMaxDouble(List<U> list, Tdouble<U> t) {
		double max = Double.MIN_VALUE;
		for(U u : list) {
			double value = t.t(u);
			if(max<value) {
				max = value;
			}
		}
		return max;
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
	
	/*
	 * ADD ALL
	 */

	@SafeVarargs
	public static <U> List<U> addAll(List<U> list, U... uu) {
		for(U u : uu) list.add(u);
		return list;
	}

	@SafeVarargs
	public static <U> List<U> addAllNotNull(List<U> list, U... uu) {
		for(U u : uu) if(u!=null) list.add(u);
		return list;
	}
	
	
}
