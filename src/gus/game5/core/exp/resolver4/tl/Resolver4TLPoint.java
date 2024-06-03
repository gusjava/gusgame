package gus.game5.core.exp.resolver4.tl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver3.apply.Apply;
import gus.game5.core.exp.resolver3.function.ApplyFunction;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;

public class Resolver4TLPoint implements ResolverTL {
	
	private ResolverTL mainResolver;
	private Map<String,Apply> applyMap;
	
	public Resolver4TLPoint(ResolverTL mainResolver, Map<String,Apply> applyMap) {
		this.mainResolver = mainResolver;
		this.applyMap = applyMap;
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
	
	
	protected Set<String> compute(TokenList tokenList) throws ExpException {
		if(tokenList.size()==1) return compute1(tokenList);
		if(tokenList.size()==2) return compute2(tokenList);
		
		throw new ExpResolveException(tokenList, "Invalid tokenList size: "+tokenList.size());
	}
	
	
	protected Set<String> compute1(TokenList tokenList) throws ExpException {
		Token token = tokenList.get(0);
		if(!token.isElement()) throw new ExpResolveException(tokenList, "Invalid token type: "+token.getType());
		
		String rule = (String) token.getValue();
		return apply(tokenList, rule, new ArrayList<>());
	}
	
	
	protected Set<String> compute2(TokenList tokenList) throws ExpException {
		Token token1 = tokenList.get(0);
		Token token2 = tokenList.get(1);
		
		if(!token1.isElement()) throw new ExpResolveException(tokenList, "Invalid token type: "+token2.getType());
		if(!token2.isGroup1()) throw new ExpResolveException(tokenList, "Invalid token type: "+token2.getType());
		
		String rule = (String) token1.getValue();
		TokenList children = token2.getChildren();
		
		List<TokenList> params = Cut.cut3(children, UtilParser.C_COMMA);
		if(params==null) params = UtilList.asList(children);
		
		return apply(tokenList, rule, params);
	}
	

	@SuppressWarnings("unchecked")
	private Set<String> apply(TokenList tokenList, String rule, List<TokenList> params) throws ExpException {
		if(!applyMap.containsKey(rule)) throw new ExpResolveException(tokenList, "Applied rule not found: "+rule);
		Apply apply = applyMap.get(rule);
		
		Set<String> set = new HashSet<>();
		if(apply instanceof ApplyFunction) {
			ApplyFunction function = (ApplyFunction) apply;
			TokenList funcTokenList = function.getTokenList();
			
			Set<String> funcDepSet = (Set<String>) mainResolver.resolveTL(funcTokenList).getData();
			funcDepSet.remove(function.getVar());
			funcDepSet.remove(ApplyFunction.VAR_PP);
			for(int i=0;i<params.size();i++)
			funcDepSet.remove(ApplyFunction.VAR_P+(i+1));
			
			set.addAll(funcDepSet);
		}
		
		for(int i=0;i<params.size();i++)
		set.addAll((Set<String>) mainResolver.resolveTL(params.get(i)).getData());
		
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
		return UtilParser.C_POINT;
	}
}
