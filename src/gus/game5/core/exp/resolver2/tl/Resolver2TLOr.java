package gus.game5.core.exp.resolver2.tl;

import java.util.List;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLOr;

public class Resolver2TLOr extends Resolver1TLOr {
	
	public Resolver2TLOr(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		int trueFound = 0;
		int falseFound = 0;
		
		for(ResolverResult result : results) {
			if(result.isDataTrue()) trueFound++;
			else if(result.isDataFalse()) falseFound++;
			else if(!result.isTypeBoolean()) throw new ExpResolveException(result.getSequence(), "Invalid data type: "+result.getType());
		}
		
		if(trueFound>0) return true;
		if(falseFound==results.size()) return false;
		return ResolverResult.Type.BOOLEAN;
	}
}
