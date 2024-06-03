package gus.game5.core.util;

import java.util.Map;

import gus.game5.core.features.o.O2;
import gus.game5.core.features.t.T;

public class UtilT {

	/*
	 * TT...
	 */
	
	public static <U1,U2,U3> T<? super U1,U3> tt(final T<? super U1,U2> t1, final T<? super U2,U3> t2) {
		return u->t2.t(t1.t(u));
	}
	public static <U1,U2,U3,U4> T<? super U1,U4> ttt(final T<? super U1,U2> t1, final T<? super U2,U3> t2, final T<? super U3,U4> t3) {
		return u->t3.t(t2.t(t1.t(u)));
	}
	public static <U1,U2,U3,U4,U5> T<? super U1,U5> tttt(final T<? super U1,U2> t1, final T<? super U2,U3> t2, final T<? super U3,U4> t3, final T<? super U4,U5> t4) {
		return u->t4.t(t3.t(t2.t(t1.t(u))));
	}
	public static <U1,U2,U3,U4,U5,U6> T<? super U1,U6> ttttt(final T<? super U1,U2> t1, final T<? super U2,U3> t2, final T<? super U3,U4> t3, final T<? super U4,U5> t4, final T<? super U5,U6> t5) {
		return u->t5.t(t4.t(t3.t(t2.t(t1.t(u)))));
	}
	
	
	/*
	 * AS T
	 */
	
	public static <U,V> T<? super U,V> asT(V defaultValue, Map<U,V> map) {
		return key->UtilMap.get(map, key, defaultValue);
	}
	
	public static <U,V> T<? super U,V> asT(Map<U,V> map) {
		return key->UtilMap.get(map, key);
	}
	
	@SafeVarargs
	public static <U,V> T<? super U,V> asT(V defaultValue, O2<U,V>... oo) {
		return asT(defaultValue, UtilMap.asMap(oo));
	}
	
	@SafeVarargs
	public static <U,V> T<? super U,V> asT(O2<U,V>... oo) {
		return asT(UtilMap.asMap(oo));
	}
	
	
	/*
	 * AS T (STRICT)
	 */
	
	public static <U,V> T<? super U,V> asTStrict(Map<U,V> map) {
		return key->UtilMap.getStrict(map, key);
	}
	
	@SafeVarargs
	public static <U,V> T<? super U,V> asTStrict(O2<U,V>... oo) {
		return asTStrict(UtilMap.asMap(oo));
	}
}
