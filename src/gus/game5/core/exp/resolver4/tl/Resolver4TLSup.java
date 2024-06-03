package gus.game5.core.exp.resolver4.tl;

import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver4.Resolver4TL2;

public class Resolver4TLSup extends Resolver4TL2 {
	
	public Resolver4TLSup(ResolverTL mainResolver) {
		super(mainResolver);
	}

	protected String defaultDelim() {
		return ">";
	}
}
