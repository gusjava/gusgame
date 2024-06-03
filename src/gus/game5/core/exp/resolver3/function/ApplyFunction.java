package gus.game5.core.exp.resolver3.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver3.apply.Apply;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.TokenSequence;
import gus.game5.core.util.UtilExp;

public class ApplyFunction implements Apply {
	
	public static final String VAR_PP = "pp";
	public static final String VAR_P = "p";

	private TokenList tokenList;
	private String expression;
	private String var;
	
	public ApplyFunction(String expression, String var) throws ExpParseException {
		this.expression = expression;
		this.var = var;
		tokenList = Parser.parse(expression);
	}
	
	public ApplyFunction(String expression) throws ExpParseException {
		this(expression,"o");
	}
	
	public String getExpression() {
		return expression;
	}
	
	public TokenList getTokenList() {
		return tokenList;
	}
	
	public String getVar() {
		return var;
	}
	
	public Object apply(TokenSequence sequence, Context context, Object data, List<TokenList> params) throws ExpException {
		List<Object> paramValues = UtilExp.resolveAll(context, params);
		
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put(var, data);
		dataMap.put(VAR_PP, paramValues);
		for(int i=0;i<paramValues.size();i++)
		dataMap.put(VAR_P+(i+1), paramValues.get(i));
		
		return context.resolveWith(expression, dataMap).getData();
	}
}
