package gus.game5.core.exp.resolver2.tl;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.tl.Resolver1TLTernary;

public class Resolver2TLTernary extends Resolver1TLTernary {
	
	public Resolver2TLTernary(ResolverTL mainResolver) {
		super(mainResolver);
	}
	
	protected Object computeData(ResolverResult r1, ResolverResult r2, ResolverResult r3) throws ExpException {
		if(r1.isResolved() && r2.isResolved() && r3.isResolved())
			return super.computeData(r1, r2, r3);
		
		if(r1.isDataTrue()) return r2.getType();
		if(r1.isDataFalse()) return r3.getType();
		
		if(!r1.isTypeBoolean()) throw new ExpResolveException(r1.getSequence(), "Invalid data type: "+r1.getType());
		
		if(r2.isTypeBoolean() &&  r3.isTypeBoolean()) return ResolverResult.Type.BOOLEAN;
		if(r2.isTypeInteger() &&  r3.isTypeInteger()) return ResolverResult.Type.INTEGER;
		if(r2.isTypeNumber() &&  r3.isTypeNumber()) return ResolverResult.Type.DOUBLE;
		if(r2.isTypeDate() &&  r3.isTypeDate()) return ResolverResult.Type.DATE;
		if(r2.isTypeString() &&  r3.isTypeString()) return ResolverResult.Type.STRING;	
		
		throw new ExpResolveException(r1.getSequence(), "Invalid data types: "+r2.getType()+" & "+r3.getType());
	}
}
