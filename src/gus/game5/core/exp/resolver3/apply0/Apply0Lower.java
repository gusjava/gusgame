package gus.game5.core.exp.resolver3.apply0;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult.Type;
import gus.game5.core.exp.resolver3.apply.Apply0;
import gus.game5.core.exp.token.TokenSequence;

public class Apply0Lower extends Apply0 {

	public Object apply(TokenSequence sequence, Context context, Object data) throws ExpException {
		if(data==null) return null;
		if(data==Type.STRING) return Type.STRING;
		if(data instanceof String) return ((String) data).toLowerCase();
		throw new ExpResolveException(sequence, "Could not apply lower for data type: "+data.getClass().getName());
	}

}
