package gus.game5.core.exp.dataprovider;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;

public class DataProviderExp implements DataProvider {
	
	private ResolverTL resolver;
	private String expression;
	private TokenList tokenList;
	
	private Object data;
	private boolean computed = false;
	
	public DataProviderExp(ResolverTL resolver, String expression) throws ExpParseException {
		this.resolver = resolver;
		this.expression = expression;
		tokenList = Parser.parse(expression);
	}
	
	public String getExpression() {
		return expression;
	}
	
	public TokenList getTokenList() {
		return tokenList;
	}
	
	public Object provideData() throws ExpException {
		if(!computed) compute();
		return data;
	}
	
	public void reset() {
		data = null;
		computed = false;
	}
	
	public void compute() throws ExpException {
		data = resolver.resolveTL(tokenList).getData();
		computed = true;
	}
}
