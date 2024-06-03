package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLPower;

public class Resolver2TLPower extends Resolver1TLPower {
	
	public Resolver2TLPower(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(r1.isResolved() && r2.isResolved())
			return super.computeData(r1, r2);
		
		if(r1.isTypeNumber() && r2.isTypeNumber()) 
			return ResolverResult.Type.DOUBLE;
		
		if(r1.isTypeBoolean() && r2.isTypeBoolean()) 
			return ResolverResult.Type.BOOLEAN;
		
		throw new ExpResolveException(r1.getSequence(), "Invalid data types: "+r1.getType()+" & "+r2.getType());
	}
}
