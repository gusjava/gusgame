package gus.game5.core.exp.resolver4.tl;

import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver4.Resolver4TL1;

public class Resolver4TLOpp extends Resolver4TL1 {
	
	public Resolver4TLOpp(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected String defaultDelim() {
		return "-";
	}
}
