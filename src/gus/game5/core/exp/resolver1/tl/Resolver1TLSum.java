package gus.game5.core.exp.resolver1.tl;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.cut.CutPlusMinus;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;

public class Resolver1TLSum implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver1TLSum(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		
		List<TokenList> parts = CutPlusMinus.cut(list);
		if(parts==null) return null;
		
		List<ResolverResult> results = new ArrayList<>();
		for(TokenList part : parts) results.add(mainResolver.resolveTL(part));
		
		Object data = computeData(results);
		return new ResolverResult(list, data);
	}
	
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		if(UtilList.any(results, ResolverResult::isDataString))
			return computeDataAsString(results);
		
		if(UtilList.all(results, ResolverResult::isDataInteger))
			return computeDataAsInteger(results);
		
		double sum = 0;
		for(ResolverResult result : results) {
			if(!result.isDataNumber()) throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
			sum += result.asDouble();
		}
		return sum;
	}
	
	private String computeDataAsString(List<ResolverResult> results) {
		StringBuilder b = new StringBuilder();
		for(ResolverResult result : results) {
			b.append(result.asString());
		}
		return b.toString();
	}
	
	private int computeDataAsInteger(List<ResolverResult> results) {
		int sum = 0;
		for(ResolverResult result : results) {
			sum += result.asInteger();
		}
		return sum;
	}
}
