package gus.game5.core.exp.resolver4.t;

import java.util.HashSet;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.token.Token;

public class Resolver4TValue implements ResolverT {

	public ResolverResult resolveT(Token token) throws ExpException {
		return new ResolverResult(token, new HashSet<>());
	}
}
