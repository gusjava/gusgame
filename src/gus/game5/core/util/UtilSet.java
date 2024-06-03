package gus.game5.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;

public class UtilSet {
	

	
	/*
	 * NEW SET
	 */
	
	public static <U> Set<U> newSet(List<U> list) {
		if(list==null) return null;
		return new HashSet<>(list);
	}
	
	public static <U> Set<U> newSet(Set<U> set) {
		if(set==null) return null;
		return new HashSet<>(set);
	}
	
	public static <U> Set<U> newSet(Collection<U> col) {
		if(col==null) return null;
		return new HashSet<>(col);
	}
	
	public static <U> Set<U> newSet(U[] array) {
		if(array==null) return null;
		Set<U> set = new HashSet<>();
		for(U u : array) set.add(u);
		return set;
	}
	

	
	
	/*
	 * FIND
	 */
	
	public static <U> U find(Set<U> set, F<? super U> f) {
		if(set==null || f==null) return null;
		for(U u : set) if(f.f(u)) return u;
		return null;
	}
	
	
	/*
	 * AS SET
	 */
	
	@SafeVarargs
	public static <U> Set<U> asSet(U... elements) {
		if(elements==null) return null;
		return new HashSet<>(Arrays.asList(elements));
	}
	
	@SafeVarargs
	public static <U> Set<U> asSet(Collection<U>... cc) {
		if(cc==null) return null;
		Set<U> set = new HashSet<>();
		for(Collection<U> c : cc) set.addAll(c);
		return set;
	}
	
	public static <U> Set<U> asSet(List<Collection<U>> cc) {
		if(cc==null) return null;
		Set<U> set = new HashSet<>();
		for(Collection<U> c : cc) set.addAll(c);
		return set;
	}
	
	@SafeVarargs
	public static <U> Set<U> asSet(Collection<U> col, U... elements) {
		Set<U> set = new HashSet<>(col);
		if(elements!=null) for(U element : elements) set.add(element);
		return set;
	}
	
	@SafeVarargs
	public static <U> Set<U> asSet(U[] array0, U... elements) {
		Set<U> set = new HashSet<>();
		if(array0!=null) for(U element : array0) set.add(element);
		if(elements!=null) for(U element : elements) set.add(element);
		return set;
	}
	
	public static <U> Set<U> asSet(Iterator<U> it) {
		if(it==null) return null;
		Set<U> set = new HashSet<>();
		while(it.hasNext()) set.add(it.next());
		return set;
	}
	
	/*
	 * GROUP
	 */
	
	public static <U,V> Set<V> group(List<U> cc, T<U, Collection<V>> t) {
		if(cc==null) return null;
		Set<V> set = new HashSet<>();
		for(U c : cc) set.addAll(t.t(c));
		return set;
	}
	
	
	/*
	 * NONE ALL ANY MANY NOTALL
	 */
	
	public static <U> boolean none(Set<U> set, F<? super U> f) {
		for(U u : set) if(f.f(u)) return false;
		return true;
	}
	
	public static <U> boolean all(Set<U> set, F<? super U> f) {
		for(U u : set) if(!f.f(u)) return false;
		return true;
	}
	
	public static <U> boolean any(Set<U> set, F<? super U> f) {
		for(U u : set) if(f.f(u)) return true;
		return false;
	}
	
	public static <U> boolean many(Set<U> set, F<? super U> f) {
		int n = 0;
		for(U u : set) {
			if(f.f(u)) n++;
			if(n>1) return true;
		}
		return false;
	}
	
	public static <U> boolean notAll(Set<U> set, F<? super U> f) {
		for(U u : set) if(!f.f(u)) return true;
		return false;
	}
	
	
	/*
	 * COUNT
	 */
	
	public static <U> int count(Set<U> set, F<? super U> f) {
		if(set==null || f==null) return 0;
		int n = 0;
		for(U u : set) if(f.f(u)) n++;
		return n;
	}
	
	public static <U> int countNot(Set<U> set, F<? super U> f) {
		if(set==null || f==null) return 0;
		int n = 0;
		for(U u : set) if(!f.f(u)) n++;
		return n;
	}
	
	
	/*
	 * IS UNIQUE DATA
	 */
	
	public static <U,V> boolean isUniqueData(Set<U> set, T<? super U,? extends V> t) {
		Set<V> found = new HashSet<>();
		for(U u : set) {
			V data = t.t(u);
			if(found.contains(data)) return false;
			found.add(data);
		}
		return true;
	}
	
	
	/*
	 * COLLECT
	 */

	public static <U,V> Set<V> collect(List<U> list, T<? super U,? extends V> t) {
		if(list==null || t==null) return null;
		Set<V> set1 = new HashSet<>();
		for(U u : list) set1.add(t.t(u));
		return set1;
	}
	
	public static <U,V> Set<V> collect(Set<U> set, T<? super U,? extends V> t) {
		if(set==null || t==null) return null;
		Set<V> set1 = new HashSet<>();
		for(U u : set) set1.add(t.t(u));
		return set1;
	}
	
	public static <U,V> Set<V> collect(Collection<U> col, T<? super U,? extends V> t) {
		if(col==null || t==null) return null;
		Set<V> set1 = new HashSet<>();
		for(U u : col) set1.add(t.t(u));
		return set1;
	}
	
	public static <U,V> Set<V> collect(Iterator<U> it, T<? super U,? extends V> t) {
		if(it==null || t==null) return null;
		Set<V> set1 = new HashSet<>();
		while(it.hasNext()) set1.add(t.t(it.next()));
		return set1;
	}
	
	public static <U,V> Set<V> collect(Iterable<U> iter, T<? super U,? extends V> t) {
		if(iter==null || t==null) return null;
		return collect(iter.iterator(), t);
	}
	
	public static <U,V> Set<V> collect(U[] array, T<? super U,? extends V> t) {
		if(array==null || t==null) return null;
		Set<V> set1 = new HashSet<>();
		for(U u : array) set1.add(t.t(u));
		return set1;
	}
	
	/*
	 * SAME
	 */
	
	public static <U> boolean isSame(Set<U> set1, Set<U> set2) {
		if(set1==null && set2==null) return true;
		if(set1==null || set2==null) return false;
		Set<U> set2_ = newSet(set2);
		Iterator<U> it = set1.iterator();
		while(it.hasNext()) {
			U elem = it.next();
			if(!set2_.remove(elem)) return false;
		}
		return set2_.isEmpty();
	}
}
