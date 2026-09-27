package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLMod;

public class Resolver2TLMod extends Resolver1TLMod {
	
	public Resolver2TLMod(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(r1.isResolved() && r2.isResolved())
			return super.computeData(r1, r2);
		
		if(!r1.isTypeNumber()) throw new ExpResolveException(r1.getSequence(), "Invalid data type: "+r1.getType());
		if(!r2.isTypeNumber()) throw new ExpResolveException(r2.getSequence(), "Invalid data type: "+r2.getType());
		
		return ResolverResult.Type.DOUBLE;
	}
}
