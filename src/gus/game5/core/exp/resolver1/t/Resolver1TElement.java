package gus.game5.core.exp.resolver1.t;

import java.util.Map;

import gus.game5.core.exp.dataprovider.DataProvider;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.token.Token;

public class Resolver1TElement implements ResolverT {
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String NULL = "null";
	
	
	
	private Map<String,Object> dataMap;
	
	public Resolver1TElement(Map<String,Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	public ResolverResult resolveT(Token token) throws ExpException {
		return new ResolverResult(token, findValue(token));
	}
	
	private Object findValue(Token token) throws ExpException {
		Object value = token.getValue();
		if(!(value instanceof String)) throw new ExpResolveException(token, "Invalid value type: "+value.getClass().getName());
		
		String name = (String) value;
		
		if(name.equals(TRUE)) return true;
		if(name.equals(FALSE)) return true;
		if(name.equals(NULL)) return null;
		
		if(!dataMap.containsKey(name)) 
			throw new ExpResolveException(token, "Unknown variable'session_ name: "+name);
			
		Object data = dataMap.get(name);
		if(data instanceof DataProvider)
			return ((DataProvider) data).provideData();
		return data;
	}
}
