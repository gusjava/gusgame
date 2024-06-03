package gus.game5.core.exp.resolver4.tl;

import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver4.Resolver4TL3;

public class Resolver4TLOr extends Resolver4TL3 {
	
	public Resolver4TLOr(ResolverTL mainResolver) {
		super(mainResolver);
	}

	protected String defaultDelim() {
		return  "||";
	}
}
