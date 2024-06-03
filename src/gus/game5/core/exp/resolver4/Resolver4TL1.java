package gus.game5.core.exp.resolver4;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public abstract class Resolver4TL1 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public Resolver4TL1(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut1(list, getDelim());
		if(parts==null) return null;
		
		if(parts.size()!=1) throw new ExpResolveException(list,"Invalid part number: "+parts.size());
		TokenList part = parts.get(0);
		
		Set<String> dep = computePart(part);
		return new ResolverResult(list, dep);
	}
	
	/*
	 * COMPUTE
	 */
	
	@SuppressWarnings("unchecked")
	protected Set<String> computePart(TokenList part) throws ExpException {
		ResolverResult r = mainResolver.resolveTL(part);
		
		return new HashSet<>((Set<String>) r.getData());
	}
	
	
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
