package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLNot;

public class Resolver2TLNot extends Resolver1TLNot {
	
	public Resolver2TLNot(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult result) throws ExpException {
		if(result.isResolved())
			return super.computeData(result);
		
		if(result.isTypeBoolean())
			return ResolverResult.Type.BOOLEAN;
		
		throw new ExpResolveException(result.getSequence(), "Invalid data type: "+result.getType());
	}
}
