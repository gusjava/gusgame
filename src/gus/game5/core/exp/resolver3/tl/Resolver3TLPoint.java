package gus.game5.core.exp.resolver3.tl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver3.apply.Apply;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;

public class Resolver3TLPoint implements ResolverTL {
	
	private Context context;
	
	public Resolver3TLPoint(Context context) {
		this.context = context;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut3(list, getDelim());
		if(parts==null) return null;
		
		Object base = context.resolveTL(parts.get(0)).getData();
		
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
		return apply(tokenList, rule, base, new ArrayList<>());
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
		
		return apply(tokenList, rule, base, params);
	}
	
	
	
	private Object apply(TokenList tokenList, String rule, Object base, List<TokenList> params) throws ExpException {
		Map<String,Apply> applyMap = context.getApplyMap();
		if(!applyMap.containsKey(rule)) throw new ExpResolveException(tokenList, "Applied rule not found: "+rule);
		return applyMap.get(rule).apply(tokenList, context, base, params);
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
