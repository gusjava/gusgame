package gus.game5.core.exp.resolver4.t;

import java.util.Set;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.token.Token;
import gus.game5.core.util.UtilSet;

public class Resolver4TElement implements ResolverT {
	
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String NULL = "null";

	public Resolver4TElement() {
	}
	
	public ResolverResult resolveT(Token token) throws ExpException {
		return new ResolverResult(token, findDep(token));
	}
	
	private Set<String> findDep(Token token) throws ExpException {
		Object value = token.getValue();
		if(!(value instanceof String)) throw new ExpResolveException(token, "Invalid value type: "+value.getClass().getName());
		
		String name = (String) value;
		
		if(name.equals(TRUE)) return null;
		if(name.equals(FALSE)) return null;
		if(name.equals(NULL)) return null;
		
		return UtilSet.asSet(name);
	}
}
