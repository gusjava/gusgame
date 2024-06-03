package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import gus.game5.core.features.f.F;
import gus.game5.core.features.g.G;
import gus.game5.core.features.p.P;
import gus.game5.core.features.t.T;
import gus.game5.core.features.t.Tdouble;
import gus.game5.core.features.t.Tint;

public class UtilList {
	
	/*
	 * FIRST
	 */
	
	public static <U> U first(List<U> list) {
		if(list==null || list.isEmpty()) return null;
		return list.get(0);
	}
	
	public static <U> U first(List<U> list, F<? super U> f) {
		if(list==null || f==null) return null;
		for(U u : list) if(f.f(u)) return u;
		return null;
	}
	
	/*
	 * LAST
	 */
	
	public static <U> U last(List<U> list) {
		if(list==null || list.isEmpty()) return null;
		return list.get(list.size()-1);
	}
	
	public static <U> U last(List<U> list, F<? super U> f) {
		if(list==null || f==null) return null;
		int nb = list.size();
		for(int i=0;i<nb;i++) {
			U u = list.get(nb-1-i);
			if(f.f(u)) return u;
		}
		return null;
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
	 * REMOVE
	 */
	
	public static <U> int remove(List<U> list, F<? super U> f) {
		if(f==null || list==null) return 0;
		Iterator<U> it = list.iterator();
		int removed = 0;
		while(it.hasNext()) {
			if(f.f(it.next())) {
				removed++;
				it.remove();
			}
		}
		return removed;
    }
	
	/*
	 * FIND ALL
	 */
	
	public static <U> List<U> findAll(List<U> list, F<? super U> f) {
		List<U> list1 = newList(list);
		keep(list1,f);
		return list1;
	}
	
	public static <U> List<U> findAll(Set<U> set, F<? super U> f) {
		List<U> list1 = newList(set);
		keep(list1,f);
		return list1;
	}
	
	public static <U> List<U> findAll(Collection<U> col, F<? super U> f) {
		List<U> list1 = newList(col);
		keep(list1,f);
		return list1;
	}
	
	public static <U> List<U> findAll(U[] array, F<? super U> f) {
		List<U> list1 = newList(array);
		keep(list1,f);
		return list1;
	}
	
	
	/*
	 * EXCLUDE ALL
	 */
	
	public static <U> List<U> excludeAll(List<U> list, F<? super U> f) {
		List<U> list1 = newList(list);
		remove(list1,f);
		return list1;
	}
	
	public static <U> List<U> excludeAll(Set<U> set, F<? super U> f) {
		List<U> list1 = newList(set);
		remove(list1,f);
		return list1;
	}
	
	public static <U> List<U> excludeAll(Collection<U> col, F<? super U> f) {
		List<U> list1 = newList(col);
		remove(list1,f);
		return list1;
	}
	
	public static <U> List<U> excludeAll(U[] array, F<? super U> f) {
		List<U> list1 = newList(array);
		remove(list1,f);
		return list1;
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
	 * COLLECT G
	 */

	public static <V> List<V> collectG(List<G<V>> list) {
		if(list==null) return null;
		List<V> list1 = new ArrayList<>();
		for(G<V> u : list) list1.add(u.g());
		return list1;
	}
	
	/*
	 * COLLECT DOUBLE
	 */
	
	public static <U> List<Double> collectDouble(List<U> list, Tdouble<? super U> t) {
		if(list==null || t==null) return null;
		List<Double> list1 = new ArrayList<>();
		for(U u : list) list1.add(t.t(u));
		return list1;
	}
	
	public static <U> List<Double> collectDouble(U[] array, Tdouble<? super U> t) {
		if(array==null || t==null) return null;
		List<Double> list1 = new ArrayList<>();
		for(U u : array) list1.add(t.t(u));
		return list1;
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
	 * COLLECT WHERE
	 */

	public static <U,V> List<V> collect(List<U> list, T<? super U,? extends V> t, F<? super U> f) {
		if(list==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : list) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}

	public static <U,V> List<Double> collect(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		if(list==null || t==null) return null;
		List<Double> list1 = new ArrayList<>();
		for(U u : list) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <U,V> List<V> collect(Set<U> set, T<? super U,? extends V> t, F<? super U> f) {
		if(set==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : set) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <U,V> List<V> collect(Collection<U> col, T<? super U,? extends V> t, F<? super U> f) {
		if(col==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : col) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <U,V> List<V> collect(Iterator<U> it, T<? super U,? extends V> t, F<? super U> f) {
		if(it==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		while(it.hasNext()) {
			U u = it.next();
			if(f!=null && f.f(u)) list1.add(t.t(u));
		}
		return list1;
	}
	
	public static <U,V> List<V> collect(Iterable<U> iter, T<? super U,? extends V> t, F<? super U> f) {
		if(iter==null || t==null) return null;
		return collect(iter.iterator(), t, f);
	}
	
	public static <U,V> List<V> collect(U[] array, T<? super U,? extends V> t, F<? super U> f) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(U u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <U> List<Double> collect(U[] array, Tdouble<? super U> t, F<? super U> f) {
		if(array==null || t==null) return null;
		List<Double> list1 = new ArrayList<>();
		for(U u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	

	public static <V> List<V> collect(boolean[] array, T<Boolean,? extends V> t, F<Boolean> f) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(boolean u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <V> List<V> collect(double[] array, T<Double,? extends V> t, F<Double> f) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(double u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <V> List<V> collect(int[] array, T<Integer,? extends V> t, F<Integer> f) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(int u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
		return list1;
	}
	
	public static <V> List<V> collect(long[] array, T<Long,? extends V> t, F<Long> f) {
		if(array==null || t==null) return null;
		List<V> list1 = new ArrayList<>();
		for(long u : array) if(f!=null && f.f(u)) list1.add(t.t(u));
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
	
	/*
	 * NONE
	 */
	
	public static <U> boolean none(List<U> list, F<? super U> f) {
		for(U u : list) if(f.f(u)) return false;
		return true;
	}
	
	public static <U> boolean none(List<U> list, U elem) {
		for(U u : list) if(Objects.equals(u, elem)) return false;
		return true;
	}
	
	/*
	 * ALL
	 */
	
	public static <U> boolean all(List<U> list, F<? super U> f) {
		for(U u : list) if(!f.f(u)) return false;
		return true;
	}
	
	/*
	 * ALL EQ
	 */
	
	public static <U> boolean allEq(List<U> list, U elem) {
		for(U u : list) if(!Objects.equals(u, elem)) return false;
		return true;
	}
	
	/*
	 * ANY
	 */
	
	public static <U> boolean any(List<U> list, F<? super U> f) {
		for(U u : list) if(f.f(u)) return true;
		return false;
	}
	
	/*
	 * ANY EQ
	 */
	
	public static <U> boolean anyEq(List<U> list, U elem) {
		for(U u : list) if(Objects.equals(u, elem)) return true;
		return false;
	}
	
	/*
	 * MANY
	 */
	
	public static <U> boolean many(List<U> list, F<? super U> f) {
		int n = 0;
		for(U u : list) {
			if(f.f(u)) n++;
			if(n>1) return true;
		}
		return false;
	}
	
	/*
	 * MANY EQ
	 */
	
	public static <U> boolean manyEq(List<U> list, U elem) {
		int n = 0;
		for(U u : list) {
			if(Objects.equals(u, elem)) n++;
			if(n>1) return true;
		}
		return false;
	}
	
	/*
	 * NOTALL
	 */
	
	public static <U> boolean notAll(List<U> list, F<? super U> f) {
		for(U u : list) if(!f.f(u)) return true;
		return false;
	}
	
	/*
	 * NOTALL EQ
	 */
	
	public static <U> boolean notAllEq(List<U> list, U elem) {
		for(U u : list) if(!Objects.equals(u, elem)) return true;
		return false;
	}
	
	/*
	 * SORT
	 */
	
	public static <U extends Comparable<? super U>> List<U> sort(List<U> list) {
		if(list==null) return null;
		List<U> list1 = newList(list);
		Collections.sort(list1);
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sort(Set<U> set) {
		if(set==null) return null;
		List<U> list1 = newList(set);
		Collections.sort(list1);
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sort(Collection<U> col) {
		if(col==null) return null;
		List<U> list1 = newList(col);
		Collections.sort(list1);
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sort(U[] array) {
		if(array==null) return null;
		List<U> list1 = newList(array);
		Collections.sort(list1);
		return list1;
	}
	

	
	public static <U> List<U> sort(List<U> list, Comparator<? super U> comparator) {
		if(list==null) return null;
		List<U> list1 = newList(list);
		if(comparator!=null) Collections.sort(list1,comparator);
		return list1;
	}
	
	public static <U> List<U> sort(Set<U> set, Comparator<? super U> comparator) {
		if(set==null) return null;
		List<U> list1 = newList(set);
		if(comparator!=null) Collections.sort(list1,comparator);
		return list1;
	}
	
	public static <U> List<U> sort(Collection<U> col, Comparator<? super U> comparator) {
		if(col==null) return null;
		List<U> list1 = newList(col);
		if(comparator!=null) Collections.sort(list1,comparator);
		return list1;
	}
	
	public static <U> List<U> sort(U[] array, Comparator<? super U> comparator) {
		if(array==null) return null;
		List<U> list1 = newList(array);
		if(comparator!=null) Collections.sort(list1,comparator);
		return list1;
	}
	
	
	/*
	 * SORT INV
	 */
	
	public static <U extends Comparable<? super U>> List<U> sortInv(List<U> list) {
		if(list==null) return null;
		List<U> list1 = newList(list);
		Collections.sort(list1, Collections.reverseOrder());
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sortInv(Set<U> set) {
		if(set==null) return null;
		List<U> list1 = newList(set);
		Collections.sort(list1, Collections.reverseOrder());
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sortInv(Collection<U> col) {
		if(col==null) return null;
		List<U> list1 = newList(col);
		Collections.sort(list1, Collections.reverseOrder());
		return list1;
	}
	
	public static <U extends Comparable<? super U>> List<U> sortInv(U[] array) {
		if(array==null) return null;
		List<U> list1 = newList(array);
		Collections.sort(list1, Collections.reverseOrder());
		return list1;
	}
	

	
	public static <U> List<U> sortInv(List<U> list, Comparator<? super U> comparator) {
		if(list==null) return null;
		List<U> list1 = newList(list);
		if(comparator!=null) Collections.sort(list1, Collections.reverseOrder(comparator));
		return list1;
	}
	
	public static <U> List<U> sortInv(Set<U> set, Comparator<? super U> comparator) {
		if(set==null) return null;
		List<U> list1 = newList(set);
		if(comparator!=null) Collections.sort(list1, Collections.reverseOrder(comparator));
		return list1;
	}
	
	public static <U> List<U> sortInv(Collection<U> col, Comparator<? super U> comparator) {
		if(col==null) return null;
		List<U> list1 = newList(col);
		if(comparator!=null) Collections.sort(list1, Collections.reverseOrder(comparator));
		return list1;
	}
	
	public static <U> List<U> sortInv(U[] array, Comparator<? super U> comparator) {
		if(array==null) return null;
		List<U> list1 = newList(array);
		if(comparator!=null) Collections.sort(list1, Collections.reverseOrder(comparator));
		return list1;
	}
	
	
	/*
	 * SORT AS P
	 */
	
	public static <U> P<List<U>> sortAsP(final Comparator<? super U> comparator) {
		return list->sort(list,comparator);
	}
}
