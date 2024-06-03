 package gus.game5.core.exp.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.Token.Type;

public class ResolverMain implements ResolverTL, ResolverT {
	
	private List<ResolverTL> listOpTL = new ArrayList<>();
	private Map<Type, ResolverT> mapOpT = new HashMap<>();
	
	public void addOpTL(ResolverTL op) {
		listOpTL.add(op);
	}
	
	public void putOpT(Type type, ResolverT op) {
		mapOpT.put(type, op);
	}
	
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		
		if(list.size()==0) throw new ExpResolveException(0,0,"Invalid empty list");
		if(list.size()==1) return resolveT(list.get(0));
		
		for(ResolverTL op : listOpTL) {
			ResolverResult result = op.resolveTL(list);
			if(result!=null) return result;
		}
		throw new ExpResolveException(list, "No operator available to resolve list: "+list);
	}

	public ResolverResult resolveT(Token token) throws ExpException {
		Type type = token.getType();
		if(!mapOpT.containsKey(type)) throw new ExpResolveException(token, "No operator available to resolve token type="+type);
		return mapOpT.get(type).resolveT(token);
	}
}
