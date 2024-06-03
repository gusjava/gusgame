package gus.game5.core.exp.resolver1.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver.ResolverTL1;

public class Resolver1TLOpp extends ResolverTL1 {
	
	public Resolver1TLOpp(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult result) throws ExpException {
		if(!result.isDataNumber()) throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
		
		Object data = result.getData();
		if (data instanceof Integer)  return Integer.valueOf(-1 * ((Integer) data));
		if (data instanceof Long)  return Long.valueOf(-1 * ((Long) data));
		if (data instanceof Double)  return Double.valueOf(-1 * ((Double) data));
		if (data instanceof Float)  return Float.valueOf(-1 * ((Float) data));
		
		throw new ExpResolveException(result.getSequence(), "Invalid data: "+result.getData());
	}
	
	protected String defaultDelim() {
		return "-";
	}
}
