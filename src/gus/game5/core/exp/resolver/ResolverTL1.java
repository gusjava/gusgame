package gus.game5.core.exp.resolver;

import java.util.List;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.TokenList;

public abstract class ResolverTL1 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public ResolverTL1(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut1(list, getDelim());
		if(parts==null) return null;
		
		if(parts.size()!=1) throw new ExpResolveException(list,"Invalid part number: "+parts.size());
		TokenList part = parts.get(0);
		
		Object data = computePart(part);
		return new ResolverResult(list, data);
	}
	
	/*
	 * COMPUTE
	 */
	
	protected Object computePart(TokenList part) throws ExpException {
		ResolverResult r = mainResolver.resolveTL(part);
		
		return computeData(r);
	}
	
	protected abstract Object computeData(ResolverResult r) throws ExpException;
	
	/*
	 * DELIM
	 */
	
	private String delim;
	
	public void setDelim(String delim) {
		this.delim = delim;
	}
	
	public String getDelim() {
		return delim!=null ? delim : defaultDelim();
	}
	
	protected abstract String defaultDelim();
}
