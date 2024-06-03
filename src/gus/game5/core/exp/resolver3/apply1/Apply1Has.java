package gus.game5.core.exp.resolver3.apply1;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult.Type;
import gus.game5.core.exp.resolver3.apply.Apply1;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.TokenSequence;

public class Apply1Has extends Apply1 {

	public Object apply(TokenSequence sequence, Context context, Object data, TokenList param) throws ExpException {
		
		if(data==Type.NULL) return false;
		if(data==null) return false;
		
		if(data==Type.LIST) return Type.BOOLEAN;
		if(data==Type.SET) return Type.BOOLEAN;
		if(data==Type.MAP) return Type.BOOLEAN;
		
		Object paramValue = context.resolveTL(param).getData();
		
		if(data instanceof List) return ((List<?>) data).contains(paramValue);
		if(data instanceof Set) return ((Set<?>) data).contains(paramValue);
		if(data instanceof Map) return ((Map<?,?>) data).containsKey(paramValue);
		
		throw new ExpResolveException(sequence, "Could not Apply1Has has for data type: "+data.getClass().getName());
	}
}
