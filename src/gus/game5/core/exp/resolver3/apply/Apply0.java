package gus.game5.core.exp.resolver3.apply;

import java.util.List;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.TokenSequence;

public abstract class Apply0 implements Apply {
	
	public Object apply(TokenSequence sequence, Context context, Object data, List<TokenList> params) throws ExpException {
		if(params.size()>0) throw new ExpResolveException(sequence, "params not supported");
		return apply(sequence, context, data);
	}
	
	protected abstract Object apply(TokenSequence sequence, Context context, Object data) throws ExpException;
}
