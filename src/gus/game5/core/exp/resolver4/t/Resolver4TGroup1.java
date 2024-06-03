package gus.game5.core.exp.resolver4.t;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;

public class Resolver4TGroup1 implements ResolverT {
	
	private ResolverTL mainResolver;
	
	public Resolver4TGroup1(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveT(Token token) throws ExpException {
		TokenList children = token.getChildren();
		if(children.isEmpty()) throw new ExpResolveException(token,"Empty group1 detected");
		return mainResolver.resolveTL(children);
	}
}
