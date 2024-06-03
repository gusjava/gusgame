package gus.game5.core.exp.resolver1.tl;

import java.util.List;

import gus.game5.core.exp.cut.CutTernary;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public class Resolver1TLTernary implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver1TLTernary(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

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
		
		Object data = computeData(r1, r2, r3);
		return new ResolverResult(list, data);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2, ResolverResult r3) throws ExpException {
		if(!r1.isDataBoolean()) throw new ExpResolveException(r1.getSequence(), "Invalid data: "+r1.getData());
		return r1.asBoolean() ? r2.getData() : r3.getData();
	}
}
