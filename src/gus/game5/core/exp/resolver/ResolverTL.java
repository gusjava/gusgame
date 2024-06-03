package gus.game5.core.exp.resolver;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.token.TokenList;

public interface ResolverTL {
	
	public ResolverResult resolveTL(TokenList list) throws ExpException;
}
