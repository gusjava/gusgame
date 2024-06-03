package gus.game5.core.features.o;

import java.util.Objects;

public class O5<U1,U2,U3,U4,U5> {

	public U1 u1;
	public U2 u2;
	public U3 u3;
	public U4 u4;
	public U5 u5;
	
	/*
	 * CONSTRUCTORS
	 */
	
	public O5() {
	}
	public O5(U1 u1) {
		this.u1 = u1;
	}
	public O5(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
	}
	public O5(U1 u1, U2 u2, U3 u3) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
	}
	public O5(U1 u1, U2 u2, U3 u3, U4 u4) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
	}
	public O5(U1 u1, U2 u2, U3 u3, U4 u4, U5 u5) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
		this.u5 = u5;
	}
	
	public O5(O2<U1,U2> o)				{ this(o.u1,o.u2); }
	public O5(O3<U1,U2,U3> o)			{ this(o.u1,o.u2,o.u3); }
	public O5(O4<U1,U2,U3,U4> o)		{ this(o.u1,o.u2,o.u3,o.u4); }
	public O5(O5<U1,U2,U3,U4,U5> o)		{ this(o.u1,o.u2,o.u3,o.u4,o.u5); }
	
	
	/*
	 * SET US
	 */
	
	public void setUs(U1 u1) {
		this.u1 = u1;
		this.u2 = null;
		this.u3 = null;
		this.u4 = null;
		this.u5 = null;
	}
	
	public void setUs(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = null;
		this.u4 = null;
		this.u5 = null;
	}
	
	public void setUs(U1 u1, U2 u2, U3 u3) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = null;
		this.u5 = null;
	}
	
	public void setUs(U1 u1, U2 u2, U3 u3, U4 u4) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
		this.u5 = null;
	}
	
	public void setUs(U1 u1, U2 u2, U3 u3, U4 u4, U5 u5) {
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
		this.u5 = u5;
	}
	
	public void setUs(O2<U1,U2> o) 			{setUs(o.u1,o.u2);}
	public void setUs(O3<U1,U2,U3> o) 		{setUs(o.u1,o.u2,o.u3);}
	public void setUs(O4<U1,U2,U3,U4> o) 	{setUs(o.u1,o.u2,o.u3,o.u4);}
	public void setUs(O5<U1,U2,U3,U4,U5> o) {setUs(o.u1,o.u2,o.u3,o.u4,o.u5);}
	
	/*
	 * GET U1 U2 U3 U4 U5
	 */
	
	public U1 getU1() {return u1;}
	public U2 getU2() {return u2;}
	public U3 getU3() {return u3;}
	public U4 getU4() {return u4;}
	public U5 getU5() {return u5;}
	
	/*
	 * SET U1 U2 U3 U4 U5
	 */
	
	public void setU1(U1 u1) {this.u1 = u1;}
	public void setU2(U2 u2) {this.u2 = u2;}
	public void setU3(U3 u3) {this.u3 = u3;}
	public void setU4(U4 u4) {this.u4 = u4;}
	public void setU5(U5 u5) {this.u5 = u5;}
	
	/*
	 * IS / IS NOT
	 */
	
	public boolean u1Is(Object o) { return Objects.equals(u1,o); }
	public boolean u2Is(Object o) { return Objects.equals(u2,o); }
	public boolean u3Is(Object o) { return Objects.equals(u3,o); }
	public boolean u4Is(Object o) { return Objects.equals(u4,o); }
	public boolean u5Is(Object o) { return Objects.equals(u5,o); }
	
	public boolean u1IsNot(Object o) { return !u1Is(o); }
	public boolean u2IsNot(Object o) { return !u2Is(o); }
	public boolean u3IsNot(Object o) { return !u3Is(o); }
	public boolean u4IsNot(Object o) { return !u4Is(o); }
	public boolean u5IsNot(Object o) { return !u5Is(o); }
	
	public boolean is(Object o1, Object o2, Object o3, Object o4, Object o5) { 
		return u1Is(o1) && u2Is(o2) && u3Is(o3) && u4Is(o4) && u5Is(o5); 
	}
	public boolean isNot(Object o1, Object o2, Object o3, Object o4, Object o5) { 
		return !is(o1,o2,o3,o4,o5); 
	}
	
	/*
	 * CLEAR
	 */
	
	public void clear() {
		u1 = null;
		u2 = null;
		u3 = null;
		u4 = null;
		u5 = null;
	}
	
	/*
	 * EMPTY
	 */
	
	public boolean isEmpty() {
		return u1==null && u2==null && u3==null && u4==null && u5==null;
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		return u1+" - "+u2+" - "+u3+" - "+u4+" - "+u5;
	}
	
	/*
	 * EQUALS
	 */
	
	public boolean equals(Object o) {
		if(o==null) return false;
		if(!(o instanceof O5)) return false;
		return ((O5<?,?,?,?,?>) o).is(u1,u2,u3,u4,u5);
	}
}
