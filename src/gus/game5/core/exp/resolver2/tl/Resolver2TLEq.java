package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLEq;

public class Resolver2TLEq extends Resolver1TLEq {
	
	public Resolver2TLEq(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(r1.isResolved() && r2.isResolved())
			return super.computeData(r1, r2);
		
		if(r1.isTypeBoolean() &&  r2.isTypeBoolean())
			return ResolverResult.Type.BOOLEAN;
		
		if(r1.isTypeNumber() &&  r2.isTypeNumber())
			return ResolverResult.Type.BOOLEAN;
		
		if(r1.isTypeDate() &&  r2.isTypeDate())
			return ResolverResult.Type.BOOLEAN;
		
		if(r1.isTypeString() &&  r2.isTypeString())
			return ResolverResult.Type.BOOLEAN;

		throw new ExpResolveException(r1.getSequence(), "Invalid data types: "+r1.getType()+" & "+r2.getType());
	}
}
