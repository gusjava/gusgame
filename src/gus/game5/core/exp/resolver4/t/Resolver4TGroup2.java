package gus.game5.core.exp.resolver4.t;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;

public class Resolver4TGroup2 implements ResolverT {
	
	private ResolverTL mainResolver;
	
	public Resolver4TGroup2(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveT(Token token) throws ExpException {
		List<TokenList> parts = cut(token);
		Set<String> set = compute(parts);
		return new ResolverResult(token, set);
	}
	
	/*
	 * CUT
	 */
	
	private List<TokenList> cut(Token token) throws ExpException {
		TokenList tokenList = token.getChildren();
		if(tokenList.size()==0) return new ArrayList<>();
		if(tokenList.size()==1) return UtilList.asList(tokenList);
		return Cut.cut3(tokenList, getDelim());
	}
	
	/*
	 * COMPUTE
	 */

	@SuppressWarnings("unchecked")
	protected Set<String> compute(List<TokenList> parts) throws ExpException {
		Set<String> set = new HashSet<>();
		for(TokenList part : parts) {
			set.addAll((Set<String>) mainResolver.resolveTL(part).getData());
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
	
	protected String defaultDelim() {
		return ",";
	}
}
