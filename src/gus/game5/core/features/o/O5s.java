package gus.game5.core.features.o;

import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;

public class O5s<U> extends O5<U,U,U,U,U> {
	
	public O5s()						{ super(); }
	public O5s(U u1)					{ super(u1); }
	public O5s(U u1, U u2)				{ super(u1,u2); }
	public O5s(U u1, U u2, U u3)		{ super(u1,u2,u3); }
	public O5s(U u1, U u2, U u3, U u4)	{ super(u1,u2,u3,u4); }
	public O5s(U u1, U u2, U u3, U u4, U u5)	{ super(u1,u2,u3,u4,u5); }
	
	public O5s(O2s<U> o)				{ super(o.u1,o.u2); }
	public O5s(O3s<U> o)				{ super(o.u1,o.u2,o.u3); }
	public O5s(O4s<U> o)				{ super(o.u1,o.u2,o.u3,o.u4); }
	public O5s(O5s<U> o)				{ super(o.u1,o.u2,o.u3,o.u4,o.u5); }
	
	
	
	/*
	 * APPEND
	 */
	
	public boolean append(U u) {
		if(u1==null) {u1 = u;return false;}
		if(u2==null) {u2 = u;return false;}
		if(u3==null) {u3 = u;return false;}
		if(u4==null) {u4 = u;return false;}
		u5 = u;
		return true;
	}
	
	/*
	 * GET US
	 */
	
	public U[] getUs() {
		return UtilArray.asArray(u1,u2,u3,u4,u5);
	}
	
	/*
	 * EACH
	 */
	
	public void each(P<U> p) {
		UtilArray.each(p,u1,u2,u3,u4,u5);
	}
	
	/*
	 * AS LIST
	 */
	
	public List<U> asList() {
		return UtilList.asList(u1,u2,u3,u4,u5);
	}
}
