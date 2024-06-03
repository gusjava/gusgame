package gus.game5.core.features.o;

import java.util.Objects;

public class O3<U1,U2,U3> {

	public U1 u1;
	public U2 u2;
	public U3 u3;
	
	/*
	 * CONSTRUCTORS
	 */
	
	public O3() {
	}
	public O3(U1 u1) {
		this.u1 = u1;
	}
	public O3(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
	}
	public O3(U1 u1, U2 u2, U3 u3) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
	}
	
	public O3(O2<U1,U2> o)				{ this(o.u1,o.u2); }
	public O3(O3<U1,U2,U3> o)			{ this(o.u1,o.u2,o.u3); }
	
	public O3(O2<U1,U2> o, U3 u3)		{ this(o.u1,o.u2,u3); }
	public O3(U1 u1, O2<U2,U3> o)		{ this(u1,o.u1,o.u2); }
	
	
	/*
	 * SET US
	 */
	
	public void setUs(U1 u1) {
		this.u1 = u1;
		this.u2 = null;
		this.u3 = null;
	}
	
	public void setUs(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = null;
	}
	
	public void setUs(U1 u1, U2 u2, U3 u3) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
	}
	
	public void setUs(O2<U1,U2> o) 			{setUs(o.u1,o.u2);}
	public void setUs(O3<U1,U2,U3> o) 		{setUs(o.u1,o.u2,o.u3);}
	
	public void setUs(O2<U1,U2> o, U3 u3)	{ setUs(o.u1,o.u2,u3); }
	public void setUs(U1 u1, O2<U2,U3> o)	{ setUs(u1,o.u1,o.u2); }
	
	/*
	 * GET U1 U2 U3
	 */
	
	public U1 getU1() {return u1;}
	public U2 getU2() {return u2;}
	public U3 getU3() {return u3;}
	
	/*
	 * SET U1 U2 U3
	 */
	
	public void setU1(U1 u1) {this.u1 = u1;}
	public void setU2(U2 u2) {this.u2 = u2;}
	public void setU3(U3 u3) {this.u3 = u3;}
	
	/*
	 * IS / IS NOT
	 */
	
	public boolean u1Is(Object o) { return Objects.equals(u1,o); }
	public boolean u2Is(Object o) { return Objects.equals(u2,o); }
	public boolean u3Is(Object o) { return Objects.equals(u3,o); }
	
	public boolean u1IsNot(Object o) { return !u1Is(o); }
	public boolean u2IsNot(Object o) { return !u2Is(o); }
	public boolean u3IsNot(Object o) { return !u3Is(o); }
	
	public boolean is(Object o1, Object o2, Object o3) { 
		return u1Is(o1) && u2Is(o2) && u3Is(o3); 
	}
	public boolean isNot(Object o1, Object o2, Object o3) { 
		return !is(o1,o2,o3); 
	}
	
	/*
	 * CLEAR
	 */
	
	public void clear() {
		u1 = null;
		u2 = null;
		u3 = null;
	}
	
	/*
	 * EMPTY
	 */
	
	public boolean isEmpty() {
		return u1==null && u2==null && u3==null;
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		return u1+" - "+u2+" - "+u3;
	}
	
	/*
	 * EQUALS
	 */
	
	public boolean equals(Object o) {
		if(o==null) return false;
		if(!(o instanceof O3)) return false;
		return ((O3<?,?,?>) o).is(u1,u2,u3);
	}
}
