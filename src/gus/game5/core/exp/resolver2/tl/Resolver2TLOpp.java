package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLOpp;

public class Resolver2TLOpp extends Resolver1TLOpp {
	
	public Resolver2TLOpp(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult result) throws ExpException {
		if(result.isResolved())
			return super.computeData(result);
		
		if(result.isTypeInteger())
			return ResolverResult.Type.INTEGER;
		
		if(result.isTypeDouble())
			return ResolverResult.Type.DOUBLE;
		
		throw new ExpResolveException(result.getSequence(), "Invalid data type: "+result.getType());
	}
}
