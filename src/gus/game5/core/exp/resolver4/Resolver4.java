package gus.game5.core.exp.resolver4;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver3.Resolver3;
import gus.game5.core.exp.resolver4.t.Resolver4TElement;
import gus.game5.core.exp.resolver4.t.Resolver4TGroup1;
import gus.game5.core.exp.resolver4.t.Resolver4TGroup2;
import gus.game5.core.exp.resolver4.t.Resolver4TGroup3;
import gus.game5.core.exp.resolver4.t.Resolver4TValue;
import gus.game5.core.exp.resolver4.tl.Resolver4TLAnd;
import gus.game5.core.exp.resolver4.tl.Resolver4TLDiff;
import gus.game5.core.exp.resolver4.tl.Resolver4TLDiv;
import gus.game5.core.exp.resolver4.tl.Resolver4TLDollar;
import gus.game5.core.exp.resolver4.tl.Resolver4TLEq;
import gus.game5.core.exp.resolver4.tl.Resolver4TLInf;
import gus.game5.core.exp.resolver4.tl.Resolver4TLInfEq;
import gus.game5.core.exp.resolver4.tl.Resolver4TLNot;
import gus.game5.core.exp.resolver4.tl.Resolver4TLOpp;
import gus.game5.core.exp.resolver4.tl.Resolver4TLOr;
import gus.game5.core.exp.resolver4.tl.Resolver4TLPoint;
import gus.game5.core.exp.resolver4.tl.Resolver4TLPower;
import gus.game5.core.exp.resolver4.tl.Resolver4TLProduct;
import gus.game5.core.exp.resolver4.tl.Resolver4TLSharp;
import gus.game5.core.exp.resolver4.tl.Resolver4TLSum;
import gus.game5.core.exp.resolver4.tl.Resolver4TLSup;
import gus.game5.core.exp.resolver4.tl.Resolver4TLSupEq;
import gus.game5.core.exp.resolver4.tl.Resolver4TLTernary;

public class Resolver4 extends Resolver3 {
	
	
	public Resolver4(Context context) {
		super(context);
	}

	protected ResolverTL getRevolverTLTernary() {
		return new Resolver4TLTernary(this);
	}
	protected ResolverTL getResolverTLOr() {
		return new Resolver4TLOr(this);
	}
	protected ResolverTL getRevolverTLAnd() {
		return new Resolver4TLAnd(this);
	}
	protected ResolverTL getRevolverTLNot() {
		return new Resolver4TLNot(this);
	}
	protected ResolverTL getRevolverTLInfEq() {
		return new Resolver4TLInfEq(this);
	}
	protected ResolverTL getRevolverTLSupEq() {
		return new Resolver4TLSupEq(this);
	}
	protected ResolverTL getRevolverTLDiff() {
		return new Resolver4TLDiff(this);
	}
	protected ResolverTL getRevolverTLEq() {
		return new Resolver4TLEq(this);
	}
	protected ResolverTL getRevolverTLInf() {
		return new Resolver4TLInf(this);
	}
	protected ResolverTL getRevolverTLSup() {
		return new Resolver4TLSup(this);
	}
	protected ResolverTL getRevolverTLSum() {
		return new Resolver4TLSum(this);
	}
	protected ResolverTL getRevolverTLProduct() {
		return new Resolver4TLProduct(this);
	}
	protected ResolverTL getRevolverTLDiv() {
		return new Resolver4TLDiv(this);
	}
	protected ResolverTL getRevolverTLPower() {
		return new Resolver4TLPower(this);
	}
	protected ResolverTL getRevolverTLOpp() {
		return new Resolver4TLOpp(this);
	}
	protected ResolverTL getRevolverTLPoint() {
		return new Resolver4TLPoint(this, context.getApplyMap());
	}
	protected ResolverTL getRevolverTLSharp() {
		return new Resolver4TLSharp(this);
	}
	protected ResolverTL getRevolverTLDollar() {
		return new Resolver4TLDollar(this);
	}


	protected ResolverT getRevolverTGroup1() {
		return new Resolver4TGroup1(this);
	}
	protected ResolverT getRevolverTGroup2() {
		return new Resolver4TGroup2(this);
	}
	protected ResolverT getRevolverTGroup3() {
		return new Resolver4TGroup3(this);
	}
	
	
	protected ResolverT getRevolverTElement() {
		return new Resolver4TElement();
	}
	protected ResolverT getRevolverTInt() {
		return new Resolver4TValue();
	}
	protected ResolverT getRevolverTDouble() {
		return new Resolver4TValue();
	}
	protected ResolverT getRevolverTString() {
		return new Resolver4TValue();
	}
}
