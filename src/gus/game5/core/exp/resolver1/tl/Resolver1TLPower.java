package gus.game5.core.exp.resolver1.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL2;

public class Resolver1TLPower extends ResolverTL2 {
	
	public Resolver1TLPower(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException {
		if(r1.isDataNumber() && r2.isDataNumber()) {
			double d1 = r1.asDouble();
			double d2 = r2.asDouble();
			return Math.pow (d1,d2);
		}
		
		if(r1.isDataBoolean() && r2.isDataBoolean()) {
			boolean b1 = r1.asBoolean();
			boolean b2 = r2.asBoolean();
			return b1^b2;
		}
		
		throw new ExpResolveException(r1.getSequence(), "Invalid data: "+r1.getData()+" & "+r2.getData());
	}

	protected String defaultDelim() {
		return "^";
	}
}
