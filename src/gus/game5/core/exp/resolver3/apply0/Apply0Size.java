package gus.game5.core.exp.resolver3.apply0;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult.Type;
import gus.game5.core.exp.resolver3.apply.Apply0;
import gus.game5.core.exp.token.TokenSequence;

public class Apply0Size extends Apply0 {

	public Object apply(TokenSequence sequence, Context context, Object data) throws ExpException {
		if(data==null) return null;
		
		if(data==Type.STRING) return Type.INTEGER;
		if(data==Type.LIST) return Type.INTEGER;
		if(data==Type.SET) return Type.INTEGER;
		if(data==Type.MAP) return Type.INTEGER;
		
		if(data instanceof String) return ((String) data).length();
		if(data instanceof List) return ((List<?>) data).size();
		if(data instanceof Set) return ((Set<?>) data).size();
		if(data instanceof Map) return ((Map<?,?>) data).size();
		
		throw new ExpResolveException(sequence, "Could not apply size for data type: "+data.getClass().getName());
	}
}
