package gus.game5.core.exp.resolver1.t;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilParser;

public class Resolver1TGroup2 implements ResolverT {
	
	private ResolverTL mainResolver;
	
	public Resolver1TGroup2(ResolverTL mainResolver) {
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
		List<ResolverResult> results = new ArrayList<>();
		for(TokenList part : parts) results.add(mainResolver.resolveTL(part));
		
		return computeData(results);
	}
	
	protected Object computeData(List<ResolverResult> results) throws ExpException {
		List<Object> objectList = new ArrayList<>();
		for(ResolverResult result : results) {
			objectList.add(result.getData());
		}
		return objectList;
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
}
