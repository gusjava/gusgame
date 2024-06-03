package gus.game5.core.exp.resolver4.tl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilParser;

public class Resolver4TLSharp implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver4TLSharp(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	@SuppressWarnings("unchecked")
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut3(list, getDelim());
		if(parts==null) return null;
		
		Set<String> set = new HashSet<>();
		set.addAll((Set<String>) mainResolver.resolveTL(parts.get(0)).getData());
		
		for (int i=1;i<parts.size();i++) {
			TokenList part = parts.get(i);
			set.addAll(compute(part));
		}
		return new ResolverResult(list, set);
	}

	@SuppressWarnings("unchecked")
	protected Set<String> compute(TokenList tokenList) throws ExpException {
		if(tokenList.size()!=1) throw new ExpResolveException(tokenList, "Invalid tokenList size: "+tokenList.size());
		return (Set<String>) mainResolver.resolveTL(tokenList).getData();
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
		return UtilParser.C_SHARP;
	}
}
