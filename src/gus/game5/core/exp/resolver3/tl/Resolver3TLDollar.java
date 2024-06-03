package gus.game5.core.exp.resolver3.tl;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilExp;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;
import gus.game5.core.util.UtilReflection;

public class Resolver3TLDollar implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver3TLDollar(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut3(list, getDelim());
		if(parts==null) return null;
		
		Object base = mainResolver.resolveTL(parts.get(0)).getData();
		
		for (int i=1;i<parts.size();i++) {
			TokenList part = parts.get(i);
			base = compute(base, part);
		}
		return new ResolverResult(list, base);
	}
	
	
	protected Object compute(Object base, TokenList tokenList) throws ExpException {
		if(tokenList.size()==1) return compute1(base, tokenList);
		if(tokenList.size()==2) return compute2(base, tokenList);
		
		throw new ExpResolveException(tokenList, "Invalid tokenList size: "+tokenList.size());
	}
	
	
	protected Object compute1(Object base, TokenList tokenList) throws ExpException {
		Token token = tokenList.get(0);
		if(!token.isElement()) throw new ExpResolveException(tokenList, "Invalid token type: "+token.getType());
		
		String rule = (String) token.getValue();
		return callMethod(tokenList, rule, base, new ArrayList<>());
	}
	
	
	protected Object compute2(Object base, TokenList tokenList) throws ExpException {
		Token token1 = tokenList.get(0);
		Token token2 = tokenList.get(1);
		
		if(!token1.isElement()) throw new ExpResolveException(tokenList, "Invalid token type: "+token2.getType());
		if(!token2.isGroup1()) throw new ExpResolveException(tokenList, "Invalid token type: "+token2.getType());
		
		String rule = (String) token1.getValue();
		TokenList children = token2.getChildren();
		
		List<TokenList> params = Cut.cut3(children, UtilParser.C_COMMA);
		if(params==null) params = UtilList.asList(children);
		
		return callMethod(tokenList, rule, base, params);
	}
	
	
	
	private Object callMethod(TokenList tokenList, String methodName, Object base, List<TokenList> params) throws ExpException {
		List<Object> paramValues = UtilExp.resolveAll(mainResolver, params);
		
		try {
			return UtilReflection.callMethod(base, methodName, paramValues);
		} catch (Exception e) {
			throw new ExpResolveException(tokenList, e);
		}
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
		return UtilParser.C_DOLLAR;
	}
}
