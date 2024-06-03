package gus.game5.core.features.o;

import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;

public class O2s<U> extends O2<U,U> {
	
	public O2s()				{ super(); }
	public O2s(U u1)			{ super(u1); }
	public O2s(U u1, U u2)		{ super(u1,u2); }
	
	public O2s(O2s<U> o)		{ super(o.u1,o.u2); }
	
	
	/*
	 * APPEND
	 */
	
	public boolean append(U u) {
		if(u1==null) {u1 = u;return false;}
		u2 = u;
		return true;
	}
	
	/*
	 * REVERSE
	 */
	
	public void reverse() {
		U u = u2;
		u2 = u1;
		u1 = u;
	}
	
	/*
	 * GET US
	 */
	
	public U[] getUs() {
		return UtilArray.asArray(u1, u2);
	}
	
	/*
	 * EACH
	 */
	
	public void each(P<U> p) {
		UtilArray.each(p, u1, u2);
	}
	
	/*
	 * AS LIST
	 */
	
	public List<U> asList() {
		return UtilList.asList(u1, u2);
	}
}
