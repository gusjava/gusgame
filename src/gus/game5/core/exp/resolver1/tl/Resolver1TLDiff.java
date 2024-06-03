package gus.game5.core.exp.resolver1.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL2;

public class Resolver1TLDiff extends ResolverTL2 {
	
	public Resolver1TLDiff(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(r1.isDataBoolean() &&  r2.isDataBoolean())
			return r1.asBoolean() != r2.asBoolean();
		
		if(r1.isDataNumber() &&  r2.isDataNumber())
			return r1.asDouble() != r2.asDouble();
		
		if(r1.isDataDate() &&  r2.isDataDate())
			return r1.asDate().getTime() != r2.asDate().getTime();
		
		if(r1.isDataString() &&  r2.isDataString())
			return !r1.asString().equals(r2.asString());
		
		throw new ExpResolveException(r1.getSequence(), "Invalid data: "+r2.getData());
	}

	protected String defaultDelim() {
		return "!=";
	}
}
