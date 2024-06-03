package gus.game5.core.exp.resolver4;

import java.util.List;
import java.util.Set;

import gus.game5.core.exp.cut.Cut;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilSet;

public abstract class Resolver4TL2 implements ResolverTL {
	
	protected ResolverTL mainResolver;
	
	public Resolver4TL2(ResolverTL mainResolver) {
		this.mainResolver = mainResolver;
	}

	public ResolverResult resolveTL(TokenList list) throws ExpException {
		List<TokenList> parts = Cut.cut2(list, getDelim());
		if(parts==null) return null;
		
		if(parts.size()!=2) throw new ExpResolveException(list,"Invalid part number: "+parts.size());
		TokenList part1 = parts.get(0);
		TokenList part2 = parts.get(1);
		
		Set<String> dep = computeParts(part1, part2);
		return new ResolverResult(list, dep);
	}
	
	/*
	 * COMPUTE
	 */
	
	@SuppressWarnings("unchecked")
	protected Set<String> computeParts(TokenList part1, TokenList part2) throws ExpException {
		ResolverResult r1 = mainResolver.resolveTL(part1);
		ResolverResult r2 = mainResolver.resolveTL(part2);
		
		Set<String> dep1 = (Set<String>) r1.getData();
		Set<String> dep2 = (Set<String>) r2.getData();
		return UtilSet.asSet(dep1, dep2);
	}
	
	/*
	 * DELIM
	 */
	
	private String delim;
	
	public void setDelim(String delim) {
		this.delim = delim;
	}
	
	public String getDelim() {
		return delim!=null ? delim : defaultDelim();
	}
	
	protected abstract String defaultDelim();
}
