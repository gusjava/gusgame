package gus.game5.core.features.o;

import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;

public class O3s<U> extends O3<U,U,U> {
	
	public O3s()					{ super(); }
	public O3s(U u1)				{ super(u1); }
	public O3s(U u1, U u2)			{ super(u1,u2); }
	public O3s(U u1, U u2, U u3)	{ super(u1,u2,u3); }
	
	public O3s(O2s<U> o)			{ super(o.u1,o.u2); }
	public O3s(O3s<U> o)			{ super(o.u1,o.u2,o.u3); }

	public O3s(O2s<U> o, U u3)		{ this(o.u1,o.u2,u3); }
	public O3s(U u1, O2s<U> o)		{ this(u1,o.u1,o.u2); }
	
	
	
	/*
	 * APPEND
	 */
	
	public boolean append(U u) {
		if(u1==null) {u1 = u;return false;}
		if(u2==null) {u2 = u;return false;}
		u3 = u;
		return true;
	}
	
	/*
	 * REVERSE
	 */
	
	public void reverse() {
		U u = u3;
		u3 = u1;
		u1 = u;
	}
	
	/*
	 * GET US
	 */
	
	public U[] getUs() {
		return UtilArray.asArray(u1,u2,u3);
	}
	
	/*
	 * EACH
	 */
	
	public void each(P<U> p) {
		UtilArray.each(p,u1,u2,u3);
	}
	
	/*
	 * AS LIST
	 */
	
	public List<U> asList() {
		return UtilList.asList(u1,u2,u3);
	}
}
