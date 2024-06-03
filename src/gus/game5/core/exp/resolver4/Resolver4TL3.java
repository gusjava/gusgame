package gus.game5.core.exp.resolver4;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public abstract class Resolver4TL3 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public Resolver4TL3(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}
	
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut3(list, getDelim());
		if(parts==null) return null;
		
		Set<String> dep = computeParts(parts);
		return new ResolverResult(list, dep);
	}
	
	/*
	 * COMPUTE
	 */
	
	@SuppressWarnings("unchecked")
	protected Set<String> computeParts(List<TokenList> parts) throws ExpException {
		Set<String> set = new HashSet<>();
		for(TokenList part : parts) {
			ResolverResult r = mainResolver.resolveTL(part);
			Set<String> dep = (Set<String>) r.getData();
			set.addAll(dep);
		}
		return set;
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
