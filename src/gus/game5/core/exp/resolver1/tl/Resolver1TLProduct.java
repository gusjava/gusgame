package gus.game5.core.exp.resolver1.tl;

import java.util.List;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL3;
import gus.game5.core.util.UtilParser;

public class Resolver1TLProduct extends ResolverTL3 {
	
	public Resolver1TLProduct(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		double product = 1;
		for(ResolverResult result : results) {
			if(!result.isDataNumber()) throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
			product *= result.asDouble();
		}
		return product;
	}

	protected String defaultDelim() {
		return  UtilParser.C_STAR;
	}
}
