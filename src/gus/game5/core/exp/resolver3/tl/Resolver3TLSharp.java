package gus.game5.core.exp.resolver3.tl;

import java.util.List;
import java.util.Map;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;

public class Resolver3TLSharp implements ResolverTL {
	
	private ResolverTL mainResolver;
	
	public Resolver3TLSharp(ResolverTL mainResolver) {
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
		if(tokenList.size()!=1) throw new ExpResolveException(tokenList, "Invalid tokenList size: "+tokenList.size());
		Object data = mainResolver.resolveTL(tokenList).getData();
		
		if(base==null) return null;
		
		if(base instanceof Map) {
			Map<?,?> map = (Map<?,?>) base;
			if(!map.containsKey(data)) return null;
			return map.get(data);
		}
		
		if(base instanceof List) {
			if(!(data instanceof Integer)) throw new ExpResolveException(tokenList, "Invalid data type: "+data.getClass().getName());
			List<?> list = (List<?>) base;
			return UtilList.get(list, (Integer) data);
		}
		
		if(base instanceof Object[]) {
			if(!(data instanceof Integer)) throw new ExpResolveException(tokenList, "Invalid data type: "+data.getClass().getName());
			Object[] array = (Object[]) base;
			return UtilArray.get(array, (Integer) data);
		}
		
		throw new ExpResolveException(tokenList, "Invalid data type: "+base.getClass().getName());
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
