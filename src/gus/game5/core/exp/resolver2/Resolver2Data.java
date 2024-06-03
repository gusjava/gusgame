package gus.game5.core.exp.resolver2;

import java.util.Map;

import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.Resolver1Data;
import gus.game5.core.exp.resolver2.tl.Resolver2TLAnd;
import gus.game5.core.exp.resolver2.tl.Resolver2TLDiff;
import gus.game5.core.exp.resolver2.tl.Resolver2TLDiv;
import gus.game5.core.exp.resolver2.tl.Resolver2TLEq;
import gus.game5.core.exp.resolver2.tl.Resolver2TLInf;
import gus.game5.core.exp.resolver2.tl.Resolver2TLInfEq;
import gus.game5.core.exp.resolver2.tl.Resolver2TLNot;
import gus.game5.core.exp.resolver2.tl.Resolver2TLOpp;
import gus.game5.core.exp.resolver2.tl.Resolver2TLOr;
import gus.game5.core.exp.resolver2.tl.Resolver2TLPower;
import gus.game5.core.exp.resolver2.tl.Resolver2TLProduct;
import gus.game5.core.exp.resolver2.tl.Resolver2TLSum;
import gus.game5.core.exp.resolver2.tl.Resolver2TLSup;
import gus.game5.core.exp.resolver2.tl.Resolver2TLSupEq;
import gus.game5.core.exp.resolver2.tl.Resolver2TLTernary;

public class Resolver2Data extends Resolver1Data {
	
	public Resolver2Data(Map<String,Object> dataMap) {
		super(dataMap);
	}
	
	
	protected ResolverTL getRevolverTLTernary() {
		return new Resolver2TLTernary(this);
	}
	protected ResolverTL getResolverTLOr() {
		return new Resolver2TLOr(this);
	}
	protected ResolverTL getRevolverTLAnd() {
		return new Resolver2TLAnd(this);
	}
	protected ResolverTL getRevolverTLNot() {
		return new Resolver2TLNot(this);
	}
	protected ResolverTL getRevolverTLInfEq() {
		return new Resolver2TLInfEq(this);
	}
	protected ResolverTL getRevolverTLSupEq() {
		return new Resolver2TLSupEq(this);
	}
	protected ResolverTL getRevolverTLDiff() {
		return new Resolver2TLDiff(this);
	}
	protected ResolverTL getRevolverTLEq() {
		return new Resolver2TLEq(this);
	}
	protected ResolverTL getRevolverTLInf() {
		return new Resolver2TLInf(this);
	}
	protected ResolverTL getRevolverTLSup() {
		return new Resolver2TLSup(this);
	}
	protected ResolverTL getRevolverTLSum() {
		return new Resolver2TLSum(this);
	}
	protected ResolverTL getRevolverTLProduct() {
		return new Resolver2TLProduct(this);
	}
	protected ResolverTL getRevolverTLDiv() {
		return new Resolver2TLDiv(this);
	}
	protected ResolverTL getRevolverTLPower() {
		return new Resolver2TLPower(this);
	}
	protected ResolverTL getRevolverTLOpp() {
		return new Resolver2TLOpp(this);
	}
}

