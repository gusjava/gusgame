package gus.game5.core.exp.cut;

import java.util.List;

import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilParser;

public class Cut extends UtilParser {

	/*
	 * exp = <DELIM> <PART>
	 */
	public static List<TokenList> cut1(TokenList tokens, String delim) {
		
		if(isDoubleSymbol(delim))
			return CutDoubleSymbol.cut1(tokens, stringAt(delim,0), stringAt(delim,1));
		
		if(isSymbol(delim))
			return CutSymbol.cut1(tokens, delim);
		
		return CutKeyword.cut1(tokens, delim);
	}
	
	/*
	 * exp = <PART1> <DELIM> <PART2>
	 */
	public static List<TokenList> cut2(TokenList tokens, String delim) throws ExpResolveException {
		
		if(isDoubleSymbol(delim))
			return CutDoubleSymbol.cut2(tokens, stringAt(delim,0), stringAt(delim,1));
		
		if(isSymbol(delim))
			return CutSymbol.cut2(tokens, delim);
		
		return CutKeyword.cut2(tokens, delim);
	}
	
	/*
	 * exp = <PART> <DELIM> ...n times... <PART>
	 */
	public static List<TokenList> cut3(TokenList tokens, String delim) throws ExpResolveException {
		
		if(isDoubleSymbol(delim))
			return CutDoubleSymbol.cut3(tokens, stringAt(delim, 0), stringAt(delim, 1));
		
		if(isSymbol(delim))
			return CutSymbol.cut3(tokens, delim);
		
		return CutKeyword.cut3(tokens, delim);
	}
}
