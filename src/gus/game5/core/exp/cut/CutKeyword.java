package gus.game5.core.exp.cut;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;

public class CutKeyword {

	/*
	 * exp = <KEYWORD> <PART>
	 */
	public static List<TokenList> cut1(TokenList tokens, String element) {
		if (tokens.size() < 2) return null;

		if(!tokens.get(0).isElement(element)) return null;
		
		List<TokenList> kk = new ArrayList<>();
		TokenList k = new TokenList();

		for (int i = 1; i < tokens.size(); i++) {
			k.add(tokens.get(i));
		}
		kk.add(k);
		return kk;
	}
	
	/*
	 * exp = <PART1> <KEYWORD> <PART2>
	 */
	public static List<TokenList> cut2(TokenList tokens, String element) throws ExpResolveException {
		List<TokenList> cut = cut3(tokens, element);
		if(cut==null) return null;
		
		if(cut.size()!=2) throw new ExpResolveException(tokens,"Invalid cutting with element: " + element);
		return cut;
	}
	
	/*
	 * exp = <PART> <KEYWORD> ...n times... <PART>
	 */
	public static List<TokenList> cut3(TokenList tokens, String element) throws ExpResolveException {
		if (tokens.size() < 3) return null;

		List<TokenList> kk = new ArrayList<>();
		TokenList k = new TokenList();

		for (int i = 0; i < tokens.size(); i++) {
			Token m = (Token) tokens.get(i);

			if (m.isElement(element)) {
				if (k.isEmpty()) throw new ExpResolveException(tokens,"Invalid cutting with element: " + element);
				kk.add(k);
				k = new TokenList();
			} else
				k.add(m);
		}
		
		if (k.isEmpty()) throw new ExpResolveException(tokens,"Invalid cutting with element: " + element);
		kk.add(k);

		if (kk.size() == 1) return null;
		return kk;
	}
}