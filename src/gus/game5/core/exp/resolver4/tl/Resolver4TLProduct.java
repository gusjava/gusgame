package gus.game5.core.exp.resolver4.tl;

import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver4.Resolver4TL3;
import gus.game5.core.util.UtilParser;

public class Resolver4TLProduct extends Resolver4TL3 {
	
	public Resolver4TLProduct(ResolverTL mainResolver) {
		super(mainResolver);
	}

	protected String defaultDelim() {
		return  UtilParser.C_STAR;
	}
}
