package gus.game5.core.exp.resolver1.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL2;

public class Resolver1TLDiv extends ResolverTL2 {
	
	public Resolver1TLDiv(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(!r1.isDataNumber()) throw new ExpResolveException(r1.getSequence(), "Invalid data: "+r1.getData());
		if(!r2.isDataNumber()) throw new ExpResolveException(r2.getSequence(), "Invalid data: "+r2.getData());
		
		double d1 = r1.asDouble();
		double d2 = r2.asDouble();
		
		if(d2==0) throw new ExpResolveException(r2.getSequence(), "Invalid division by zero");
		return d1/d2;
	}

	protected String defaultDelim() {
		return "/";
	}
}
