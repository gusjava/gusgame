package gus.game5.core.exp.resolver4.tl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.CutPlusMinus;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public class Resolver4TLSum implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver4TLSum(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = CutPlusMinus.cut(list);
		if(parts==null) return null;
		
		Set<String> dep = computeData(parts);
		return new ResolverResult(list, dep);
	}
	
	/*
	 * COMPUTE
	 */
	
	@SuppressWarnings("unchecked")
	protected Set<String> computeData(List<TokenList> parts) throws ExpException {
		Set<String> set = new HashSet<>();
		for(TokenList part : parts) {
			ResolverResult r = mainResolver.resolveTL(part);
			Set<String> dep = (Set<String>) r.getData();
			set.addAll(dep);
		}
		return set;
	}
}
