package gus.game5.core.exp.resolver1.tl;

import java.util.List;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL3;

public class Resolver1TLOr extends ResolverTL3 {
	
	public Resolver1TLOr(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		for(ResolverResult result : results) {
			if(!result.isDataBoolean()) throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
			if(result.asBoolean()) return true;
		}
		return false;
	}

	protected String defaultDelim() {
		return  "||";
	}
}
