package gus.game5.core.exp.resolver;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.token.TokenList;

public abstract class ResolverTL3 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public ResolverTL3(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}
	
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut3(list, getDelim());
		if(parts==null) return null;
		
		Object data = computeParts(parts);
		return new ResolverResult(list, data);
	}
	
	/*
	 * COMPUTE
	 */
	
	protected Object computeParts(List<TokenList> parts) throws ExpException {
		List<ResolverResult> results = new ArrayList<>();
		for(TokenList part : parts) results.add(mainResolver.resolveTL(part));
		
		return computeData(results);
	}
	
	protected abstract Object computeData(List<ResolverResult> results) throws ExpException;
	
	
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
