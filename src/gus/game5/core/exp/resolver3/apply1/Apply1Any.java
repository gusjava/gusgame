package gus.game5.core.exp.resolver3.apply1;

import java.util.List;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult.Type;
import gus.game5.core.exp.resolver3.apply.Apply1;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.TokenSequence;
import gus.game5.core.util.UtilMap;

public class Apply1Any extends Apply1 {

	public Object apply(TokenSequence sequence, Context context, Object data, TokenList param) throws ExpException {

		if(data==Type.NULL) return false;
		if(data==null) return false;
		
		if(data==Type.LIST) return Type.BOOLEAN;

		if(data instanceof List) {
			List<?> list = (List<?>) data;
			ExpFilter filter = new ExpFilter(context, param);
			for(Object elem : list) if(filter.filter(elem)) return true;
			return false;
		}
		
		throw new ExpResolveException(sequence, "Could not apply Apply1Any for data type: "+data.getClass().getName());
	}
	
	
	
	
	
	public class ExpFilter {
		private Context context;
		private TokenList param;
		
		public ExpFilter(Context context, TokenList param) {
			this.context = context;
			this.param = param;
		}
		
		public boolean filter(Object obj) throws ExpException {
			return context.resolveTLWith(param, UtilMap.asMap("o",obj)).asBoolean();
		}
	}
}
