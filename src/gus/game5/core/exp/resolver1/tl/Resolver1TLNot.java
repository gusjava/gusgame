package gus.game5.core.exp.resolver1.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL1;

public class Resolver1TLNot extends ResolverTL1 {
	
	public Resolver1TLNot(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult result) throws ExpException {
		if(!result.isDataBoolean()) throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
		return !result.asBoolean();
	}
	
	protected String defaultDelim() {
		return "!";
	}
}
