/*
 * Project : Guestadom
 * Company : Akelio
 * Year : 2021
 */
package gus.game5.core.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import gus.game5.core.features.t.T;

public class UtilComparator {

	/*
	 * COMPARE
	 */

	public static <U extends Comparable<? super U>> int compare(U u1, U u2) {
		if (u1 == null && u2 == null) return 0;
		if (u1 == null) return 1;
		if (u2 == null) return -1;
		return u1.compareTo(u2);
	}
	
	public static <U, V extends Comparable<? super V>> int compare(U u1, U u2, T<U, V> t) {
		if (u1 == null && u2 == null) return 0;
		if (u1 == null) return 1;
		if (u2 == null) return -1;
		return compare(t.t(u1), t.t(u2));
	}
	
	public static <U, V extends Comparable<? super V>> int compare(U u1, U u2, Map<U, V> map) {
		if (u1 == null && u2 == null) return 0;
		if (u1 == null) return 1;
		if (u2 == null) return -1;
		
		V v1 = UtilMap.get(map, u1);
		V v2 = UtilMap.get(map, u2);
		return compare(v1, v2);
	}
	
	@SafeVarargs
	public static <U extends Comparable<? super U>> int compare(U u1, U u2, U... values) {
		if (u1 == null && u2 == null) return 0;
		if (u1 == null) return 1;
		if (u2 == null) return -1;
		if (u1.equals(u2)) return 0;
		
		for(U value : values) {
			if(value.equals(u1)) return -1;
			if(value.equals(u2)) return 1;
		}
		return u1.compareTo(u2);
	}

	/*
	 * INV
	 */

	public static <U> Comparator<U> inv(Comparator<U> c) {
		return (u1, u2) -> c.compare(u2, u1);
	}

	/*
	 * MULTI
	 */

	@SafeVarargs
	public static <U> Comparator<U> multi(final Comparator<U>... cc) {
		if (cc == null) return null;
		if (cc.length == 1) return cc[0];

		return (u1, u2) -> {
			for (Comparator<U> c : cc)
				if (c != null) {
					int r = c.compare(u1, u2);
					if (r != 0) return r;
				}
			return 0;
		};
	}
	
	public static <U> Comparator<U> multi(final List<Comparator<U>> cc) {
		if (cc == null) return null;
		if (cc.size() == 1) return cc.get(0);

		return (u1, u2) -> {
			for (Comparator<U> c : cc)
				if (c != null) {
					int r = c.compare(u1, u2);
					if (r != 0) return r;
				}
			return 0;
		};
	}

	/*
	 * BUILD
	 */
	
	public static <U extends Comparable<? super U>> Comparator<U> build() {
		return (u1, u2) -> compare(u1, u2);
	}

	public static <U, V extends Comparable<? super V>> Comparator<U> build(T<U, V> t) {
		return (u1, u2) -> compare(u1, u2, t);
	}

	public static <U, V extends Comparable<? super V>> Comparator<U> build(Map<U, V> map) {
		return (u1, u2) -> compare(u1, u2, map);
	}

	@SafeVarargs
	public static <U, V extends Comparable<? super V>> Comparator<U> build(T<U, V> t, V... values) {
		return (u1, u2) -> compare(t.t(u1), t.t(u2), values);
	}

	public static <U extends Comparable<? super V>, V extends Comparable<? super V>> Comparator<U> build(List<T<U, V>> list) {
		List<Comparator<U>> comps = UtilList.collect(list, UtilComparator::build);
		return multi(comps);
	}

	/*
	 * BUILD INV
	 */

	public static <U extends Comparable<? super U>> Comparator<U> buildInv() {
		return (u1, u2) -> compare(u2, u1);
	}

	public static <U, V extends Comparable<? super V>> Comparator<U> buildInv(T<U, V> t) {
		return (u1, u2) -> compare(t.t(u2), t.t(u1));
	}

	@SafeVarargs
	public static <U, V extends Comparable<? super V>> Comparator<U> buildInv(T<U, V> t, V... values) {
		return (u1, u2) -> compare(t.t(u2), t.t(u1), values);
	}

	/*
	 * STRING LENGTH
	 */
	
	public static Comparator<String> compStrLen() {
		return build(String::length);
	}
}
