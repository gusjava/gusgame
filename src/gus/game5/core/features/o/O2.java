package gus.game5.core.features.o;

import java.util.Objects;

public class O2<U1,U2> {

	public U1 u1;
	public U2 u2;
	
	
	/*
	 * CONSTRUCTORS
	 */
	
	public O2() {
	}
	public O2(U1 u1) {
		this.u1 = u1;
	}
	public O2(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
	}
	
	public O2(O2<U1,U2> o){ 
		this(o.u1,o.u2);
	}
	
	
	/*
	 * SET US
	 */
	
	public void setUs(U1 u1) {
		this.u1 = u1;
		this.u2 = null;
	}
	
	public void setUs(U1 u1, U2 u2) {
		this.u1 = u1;
		this.u2 = u2;
	}
	
	public void setUs(O2<U1,U2> o) {
		setUs(o.u1,o.u2);
	}
	
	/*
	 * GET U1 U2
	 */
	
	public U1 getU1() {return u1;}
	public U2 getU2() {return u2;}
	
	/*
	 * SET U1 U2
	 */
	
	public void setU1(U1 u1) {this.u1 = u1;}
	public void setU2(U2 u2) {this.u2 = u2;}
	
	/*
	 * IS / IS NOT
	 */
	
	public boolean u1Is(Object o) { return Objects.equals(u1,o); }
	public boolean u2Is(Object o) { return Objects.equals(u2,o); }
	
	public boolean u1IsNot(Object o) { return !u1Is(o); }
	public boolean u2IsNot(Object o) { return !u2Is(o); }
	
	public boolean is(Object o1, Object o2) { 
		return u1Is(o1) && u2Is(o2); 
	}
	public boolean isNot(Object o1, Object o2) { 
		return !is(o1,o2); 
	}
	
	/*
	 * CLEAR
	 */
	
	public void clear() {
		u1 = null;
		u2 = null;
	}
	
	/*
	 * EMPTY
	 */
	
	public boolean isEmpty() {
		return u1==null && u2==null;
	}
	
	/*
	 * TO STRING
	 */
	
	public String toString() {
		return u1+" - "+u2;
	}
	
	/*
	 * EQUALS
	 */
	
	public boolean equals(Object o) {
		if(o==null) return false;
		if(!(o instanceof O2)) return false;
		return ((O2<?,?>) o).is(u1,u2);
	}
}
