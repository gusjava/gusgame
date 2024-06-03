package gus.game5.core.exp.cut;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;

public class CutDoubleSymbol {

	/*
	 * exp = <SYMBOL1> <SYMBOL2> <PART>
	 */
	public static List<TokenList> cut1(TokenList tokens, String symbol1, String symbol2) {
		if (tokens.size() < 3) return null;

		if(!tokens.get(0).isSymbol(symbol1)) return null;
		if(!tokens.get(1).isSymbol(symbol2)) return null;
		
		List<TokenList> kk = new ArrayList<>();
		TokenList k = new TokenList();

		for (int i = 2; i < tokens.size(); i++) {
			k.add(tokens.get(i));
		}
		kk.add(k);
		return kk;
	}
	
	
	/*
	 * exp = <PART1> <SYMBOL1> <SYMBOL2> <PART2>
	 */
	public static List<TokenList> cut2(TokenList tokens, String symbol1, String symbol2) throws ExpResolveException {
		List<TokenList> cut = cut3(tokens, symbol1, symbol2);
		if(cut==null) return null;
		
		if(cut.size()!=2) throw new ExpResolveException(tokens,"Invalid cutting with symbols: " + symbol1 + symbol2);
		return cut;
	}
	
	
	/*
	 * exp = <PART> <SYMBOL1> <SYMBOL2> ...n times... <PART>
	 */
	public static List<TokenList> cut3(TokenList tokens, String symbol1, String symbol2) throws ExpResolveException {
		if (tokens.size() < 4) return null;

		List<TokenList> kk = new ArrayList<>();
		TokenList k = new TokenList();

		int step = 0;
		for (int i = 0; i < tokens.size(); i++) {
			Token m = tokens.get(i);
			k.add(m);

			if (step == 0) {
				if (m.isSymbol(symbol1)) step = 1;
			} else if (step == 1) {
				if (m.isSymbol(symbol2)) {
					k.remove(k.size() - 1);
					k.remove(k.size() - 1);

					if (k.isEmpty()) throw new ExpResolveException(tokens,"Invalid cutting with symbols: " + symbol1 + symbol2);
					kk.add(k);
					k = new TokenList();
				} else
					step = 0;
			}
		}
		if (step > 0 || k.isEmpty()) throw new ExpResolveException(tokens,"Invalid cutting with symbols: " + symbol1 + symbol2);
		kk.add(k);

		if (kk.size() == 1) return null;
		return kk;
	}
}