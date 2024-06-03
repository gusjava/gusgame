package gus.game5.core.exp.resolver3.apply;

import java.util.List;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.TokenSequence;

public abstract class Apply2 implements Apply {
	
	public Object apply(TokenSequence sequence, Context context, Object data, List<TokenList> params_) throws ExpException {
		if(params_==null) throw new ExpResolveException(sequence, "params needed");
		if(params_.size()!=2) throw new ExpResolveException(sequence, "invalid params number: "+params_.size());
		
		TokenList param1 = params_.get(0);
		TokenList param2 = params_.get(1);
		return apply(sequence, context, data, param1, param2);
	}
	
	protected abstract Object apply(TokenSequence sequence, Context context, Object data, TokenList param1, TokenList param2) throws ExpException;
}
