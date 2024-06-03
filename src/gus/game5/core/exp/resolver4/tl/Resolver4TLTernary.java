package gus.game5.core.exp.resolver4.tl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.CutTernary;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public class Resolver4TLTernary implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver4TLTernary(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	@SuppressWarnings("unchecked")
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = CutTernary.cut(list);
		if(parts==null) return null;
		
		if(parts.size()!=3) throw new ExpResolveException(list, "Invalid tokenList size: "+parts.size());
		
		TokenList part1 = parts.get(0);
		TokenList part2 = parts.get(1);
		TokenList part3 = parts.get(2);
		
		ResolverResult r1 = mainResolver.resolveTL(part1);
		ResolverResult r2 = mainResolver.resolveTL(part2);
		ResolverResult r3 = mainResolver.resolveTL(part3);
		
		Set<String> set = new HashSet<>();
		set.addAll((Set<String>) r1.getData());
		set.addAll((Set<String>) r2.getData());
		set.addAll((Set<String>) r3.getData());
		
		return new ResolverResult(list, set);
	}
}
