package gus.game5.core.exp.resolver2.tl;

import java.util.List;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLSum;
import gus.game5.core.util.UtilList;

public class Resolver2TLSum extends Resolver1TLSum {
	
	public Resolver2TLSum(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		if(UtilList.all(results, ResolverResult::isResolved))
			return super.computeData(results);
		
		if(UtilList.any(results, ResolverResult::isTypeString))
			return ResolverResult.Type.STRING;
		
		int intNb = 0;
		
		for(ResolverResult result : results) {
			if(!result.isTypeNumber()) throw new ExpResolveException(result.getSequence(), "Invalid data type: "+result.getType());
			if(result.isTypeInteger()) intNb++;
		}
		
		if(intNb==results.size()) 
			return ResolverResult.Type.INTEGER;
		return ResolverResult.Type.DOUBLE;
	}
}
