package gus.game5.core.exp.resolver3;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver2.Resolver2Data;
import gus.game5.core.exp.resolver3.tl.Resolver3TLDollar;
import gus.game5.core.exp.resolver3.tl.Resolver3TLPoint;
import gus.game5.core.exp.resolver3.tl.Resolver3TLSharp;

public class Resolver3 extends Resolver2Data {
	
	protected Context context;
	
	public Resolver3(Context context) {
		super(context.getDataMap());
		this.context = context;

		// A # A # A
		addOpTL(getRevolverTLSharp());
		// A . A . A
		addOpTL(getRevolverTLPoint());
		// A $ A $ A
		addOpTL(getRevolverTLDollar());
	}
	
	protected ResolverTL getRevolverTLSharp() {
		return new Resolver3TLSharp(context);
	}
	
	protected ResolverTL getRevolverTLPoint() {
		return new Resolver3TLPoint(context);
	}
	
	protected ResolverTL getRevolverTLDollar() {
		return new Resolver3TLDollar(context);
	}
}

