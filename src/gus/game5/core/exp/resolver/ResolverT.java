package gus.game5.core.exp.resolver;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.token.Token;

public interface ResolverT {

	public ResolverResult resolveT(Token token) throws ExpException;
}
