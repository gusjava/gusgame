package gus.game5.core.exp.resolver4.t;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class Resolver4TGroup3 implements ResolverT {
	
	private ResolverTL mainResolver;
	
	public Resolver4TGroup3(ResolverTL mainResolver) {
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
	
	protected Set<String> compute(List<TokenList> parts) throws ExpException {
		if(parts.isEmpty()) return new HashSet<>();
		if(isUniqueSymbolDelim2(parts)) return new HashSet<>();
		
		Set<String> mapDep = attemptToBuildMap(parts);
		if(mapDep!=null) return mapDep;
		return attemptToBuildSet(parts);
	}
	
	
	
	protected boolean isUniqueSymbolDelim2(List<TokenList> parts) {
		if(parts.size()!=1) return false;
		List<Token> children = parts.get(0).getList();
		if(children.size()!=1) return false;
		return children.get(0).isSymbol(getDelim2());
	}
	
	

	@SuppressWarnings("unchecked")
	protected Set<String> attemptToBuildMap(List<TokenList> parts) throws ExpException {
		Set<String> set = new HashSet<>();
		for(TokenList part : parts) {
			List<TokenList> parts2 = Cut.cut3(part, getDelim2());
			if(parts2==null) return null;
			if(parts2.size()!=2) throw new ExpParseException(part,"Failed to build map");
			
			Set<String> keyDep = (Set<String>) mainResolver.resolveTL(parts2.get(0)).getData();
			Set<String> valueDep = (Set<String>) mainResolver.resolveTL(parts2.get(1)).getData();

			set.addAll(keyDep);
			set.addAll(valueDep);
		}
		return set;
	}
	

	@SuppressWarnings("unchecked")
	protected Set<String> attemptToBuildSet(List<TokenList> parts) throws ExpException {
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
