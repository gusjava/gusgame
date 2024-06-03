package gus.game5.core.exp.resolver;

import java.util.List;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.TokenList;

public abstract class ResolverTL2 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public ResolverTL2(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut2(list, getDelim());
		if(parts==null) return null;
		
		if(parts.size()!=2) throw new ExpResolveException(list,"Invalid part number: "+parts.size());
		TokenList part1 = parts.get(0);
		TokenList part2 = parts.get(1);
		
		Object data = computeParts(part1, part2);
		return new ResolverResult(list, data);
	}
	
	/*
	 * COMPUTE
	 */
	
	protected Object computeParts(TokenList part1, TokenList part2) throws ExpException {
		ResolverResult r1 = mainResolver.resolveTL(part1);
		ResolverResult r2 = mainResolver.resolveTL(part2);
		
		return computeData(r1, r2);
	}
	
	protected abstract Object computeData(ResolverResult r1, ResolverResult r2) throws ExpException;
	
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
