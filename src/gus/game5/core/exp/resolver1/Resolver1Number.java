package gus.game5.core.exp.resolver1;

import gus.game5.core.exp.resolver.ResolverMain;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.t.Resolver1TGroup1;
import gus.game5.core.exp.resolver1.t.Resolver1TValue;
import gus.game5.core.exp.resolver1.tl.Resolver1TLAnd;
import gus.game5.core.exp.resolver1.tl.Resolver1TLDiff;
import gus.game5.core.exp.resolver1.tl.Resolver1TLDiv;
import gus.game5.core.exp.resolver1.tl.Resolver1TLEq;
import gus.game5.core.exp.resolver1.tl.Resolver1TLInf;
import gus.game5.core.exp.resolver1.tl.Resolver1TLInfEq;
import gus.game5.core.exp.resolver1.tl.Resolver1TLNot;
import gus.game5.core.exp.resolver1.tl.Resolver1TLOpp;
import gus.game5.core.exp.resolver1.tl.Resolver1TLOr;
import gus.game5.core.exp.resolver1.tl.Resolver1TLPower;
import gus.game5.core.exp.resolver1.tl.Resolver1TLProduct;
import gus.game5.core.exp.resolver1.tl.Resolver1TLSum;
import gus.game5.core.exp.resolver1.tl.Resolver1TLSup;
import gus.game5.core.exp.resolver1.tl.Resolver1TLSupEq;
import gus.game5.core.exp.resolver1.tl.Resolver1TLTernary;
import gus.game5.core.exp.token.Token;

public class Resolver1Number extends ResolverMain {
	
	public Resolver1Number() {
		super();

		// A ? B : C
		addOpTL(getRevolverTLTernary());
		// A || A || A ||
		addOpTL(getResolverTLOr());
		// A && A && A &&
		addOpTL(getRevolverTLAnd());
		// !A
		addOpTL(getRevolverTLNot());
		
		// A <= A
		addOpTL(getRevolverTLInfEq());
		// A >= A
		addOpTL(getRevolverTLSupEq());
		// A != A
		addOpTL(getRevolverTLDiff());
		// A = A
		addOpTL(getRevolverTLEq());
		// A < A
		addOpTL(getRevolverTLInf());
		// A > A
		addOpTL(getRevolverTLSup());
		
		// A +- A +- A
		addOpTL(getRevolverTLSum());
		// A * A * A
		addOpTL(getRevolverTLProduct());
		// A / A
		addOpTL(getRevolverTLDiv());
		// A ^ A
		addOpTL(getRevolverTLPower());
		// - A
		addOpTL(getRevolverTLOpp());
		
		// (....)
		putOpT(Token.Type.GROUP1, getRevolverTGroup1());
		// 5
		putOpT(Token.Type.INT, getRevolverTInt());
		// 2.6
		putOpT(Token.Type.DOUBLE, getRevolverTDouble());
	}
	
	
	
	protected ResolverTL getRevolverTLTernary() {
		return new Resolver1TLTernary(this);
	}
	protected ResolverTL getResolverTLOr() {
		return new Resolver1TLOr(this);
	}
	protected ResolverTL getRevolverTLAnd() {
		return new Resolver1TLAnd(this);
	}
	protected ResolverTL getRevolverTLNot() {
		return new Resolver1TLNot(this);
	}
	protected ResolverTL getRevolverTLInfEq() {
		return new Resolver1TLInfEq(this);
	}
	protected ResolverTL getRevolverTLSupEq() {
		return new Resolver1TLSupEq(this);
	}
	protected ResolverTL getRevolverTLDiff() {
		return new Resolver1TLDiff(this);
	}
	protected ResolverTL getRevolverTLEq() {
		return new Resolver1TLEq(this);
	}
	protected ResolverTL getRevolverTLInf() {
		return new Resolver1TLInf(this);
	}
	protected ResolverTL getRevolverTLSup() {
		return new Resolver1TLSup(this);
	}
	protected ResolverTL getRevolverTLSum() {
		return new Resolver1TLSum(this);
	}
	protected ResolverTL getRevolverTLProduct() {
		return new Resolver1TLProduct(this);
	}
	protected ResolverTL getRevolverTLDiv() {
		return new Resolver1TLDiv(this);
	}
	protected ResolverTL getRevolverTLPower() {
		return new Resolver1TLPower(this);
	}
	protected ResolverTL getRevolverTLOpp() {
		return new Resolver1TLOpp(this);
	}
	
	
	
	protected ResolverT getRevolverTGroup1() {
		return new Resolver1TGroup1(this);
	}
	protected ResolverT getRevolverTInt() {
		return new Resolver1TValue();
	}
	protected ResolverT getRevolverTDouble() {
		return new Resolver1TValue();
	}
}
