package gus.game5.core.exp.resolver1.t;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;

public class Resolver1TGroup3 implements ResolverT {
	
	private ResolverTL mainResolver;
	
	public Resolver1TGroup3(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveT(Token token) throws ExpException {
		List<TokenList> parts = cut(token);
		Object data = computeParts(parts);
		return new ResolverResult(token, data);
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
	
	protected Object computeParts(List<TokenList> parts) throws ExpException {
		if(parts.isEmpty()) return new HashSet<>();
		if(isUniqueSymbolDelim2(parts)) return new HashMap<>();
		
		Map<Object,Object> map = attemptToBuildMap(parts);
		if(map!=null) return map;
		return attemptToBuildSet(parts);
	}
	
	
	
	protected boolean isUniqueSymbolDelim2(List<TokenList> parts) {
		if(parts.size()!=1) return false;
		List<Token> children = parts.get(0).getList();
		if(children.size()!=1) return false;
		return children.get(0).isSymbol(getDelim2());
	}
	
	
	
	protected Map<Object,Object> attemptToBuildMap(List<TokenList> parts) throws ExpException {
		Map<Object,Object> map = new HashMap<>();
		for(TokenList part : parts) {
			List<TokenList> parts2 = Cut.cut3(part, getDelim2());
			if(parts2==null) return null;
			if(parts2.size()!=2) throw new ExpParseException(part,"Failed to build map");
			
			Object key = mainResolver.resolveTL(parts2.get(0)).getData();
			if(map.containsKey(key)) throw new ExpParseException(part,"Duplicated key found for map: "+key);
			
			Object value = mainResolver.resolveTL(parts2.get(1)).getData();
			map.put(key, value);
		}
		return map;
	}
	
	
	protected Set<Object> attemptToBuildSet(List<TokenList> parts) throws ExpException {
		Set<Object> set = new HashSet<>();
		for(TokenList part : parts) {
			Object element = mainResolver.resolveTL(part).getData();
			set.add(element);
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
		return UtilParser.C_COMMA;
	}
	
	/*
	 * DELIM2
	 */
	
	private String delim2;
	
	public void setDelim2(String delim2) {
		this.delim2 = delim2;
	}
	
	public String getDelim2() {
		return delim2!=null ? delim2 : defaultDelim2();
	}
	
	protected String defaultDelim2() {
		return UtilParser.C_COLON;
	}
}
