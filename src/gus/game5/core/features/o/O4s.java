package gus.game5.core.features.o;

import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;

public class O4s<U> extends O4<U,U,U,U> {
	
	public O4s()						{ super(); }
	public O4s(U u1)					{ super(u1); }
	public O4s(U u1, U u2)				{ super(u1,u2); }
	public O4s(U u1, U u2, U u3)		{ super(u1,u2,u3); }
	public O4s(U u1, U u2, U u3, U u4)	{ super(u1,u2,u3,u4); }
	
	public O4s(O2s<U> o)				{ super(o.u1,o.u2); }
	public O4s(O3s<U> o)				{ super(o.u1,o.u2,o.u3); }
	public O4s(O4s<U> o)				{ super(o.u1,o.u2,o.u3,o.u4); }
	
	
	
	/*
	 * APPEND
	 */
	
	public boolean append(U u) {
		if(u1==null) {u1 = u;return false;}
		if(u2==null) {u2 = u;return false;}
		if(u3==null) {u3 = u;return false;}
		u4 = u;
		return true;
	}
	
	/*
	 * GET US
	 */
	
	public U[] getUs() {
		return UtilArray.asArray(u1,u2,u3,u4);
	}
	
	/*
	 * EACH
	 */
	
	public void each(P<U> p) {
		UtilArray.each(p,u1,u2,u3,u4);
	}
	
	/*
	 * AS LIST
	 */
	
	public List<U> asList() {
		return UtilList.asList(u1,u2,u3,u4);
	}
}
