package gus.game5.core.util;

import gus.game5.core.features.f.F;
import gus.game5.core.features.p.P;

public class UtilArray {
	
	/*
	 * INDEX AT
	 */
	
	public static <U> int indexAt(U[] array, int index) {
		if(array==null || array.length==0) return -1;
		int size = array.length;
		if(index<0) index+=size;
		return index>=0 && index<size ? index : -1;
	}
	
	/*
	 * GET
	 */
	
	public static <U> U get(U[] array, int index) {
		if(array==null) return null;
		int n = indexAt(array,index);
		if(n==-1) return null;
		return array[n];
	}
	
	/*
	 * SET
	 */
	
	public static <U> void set(U[] array, int index, U u) {
		if(array==null) return;
		int n = indexAt(array,index);
		if(n==-1) return;
		array[n] = u;
	}
	
    /*
     * AS ARRAY
     */

	@SafeVarargs
    public static <U> U[] asArray(U... us) {
    	return us;
    }

	/*
	 * EACH
	 */
	
	public static <U> void each(U[] array, P<? super U> p) {
		if(array==null || p==null) return;
		for(U u : array) p.p(u);
	}
	
	@SafeVarargs
	public static <U> void each(P<? super U> p, U... array) {
		each(array,p);
	}
	
	/*
	 * NONE ALL ANY MANY
	 */
	
	public static <U> boolean none(U[] array, F<? super U> f) {
		for(U u : array) if(f.f(u)) return false;
		return true;
	}

	@SafeVarargs
	public static <U> boolean none(F<? super U> f, U... array) {
		return none(array,f);
	}
	
	public static <U> boolean all(U[] array, F<? super U> f) {
		for(U u : array) if(!f.f(u)) return false;
		return true;
	}

	@SafeVarargs
	public static <U> boolean all(F<? super U> f, U... array) {
		return all(array,f);
	}
	
	public static <U> boolean any(U[] array, F<? super U> f) {
		for(U u : array) if(f.f(u)) return true;
		return false;
	}
	
}
