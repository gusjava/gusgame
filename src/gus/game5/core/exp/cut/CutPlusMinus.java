package gus.game5.core.exp.cut;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilParser;

public class CutPlusMinus extends UtilParser {

	
	public static List<TokenList> cut(TokenList tokens) throws ExpResolveException {
		if (tokens.size() < 3) return null;

		List<TokenList> kk = new ArrayList<>();
		TokenList k = new TokenList();
		Token minus = null;

		for (int i = 0; i < tokens.size(); i++) {
			Token m = (Token) tokens.get(i);

			if (m.isSymbol(C_PLUS)) {
				if (!k.isEmpty()) {
					kk.add(k);
					k = new TokenList();
				}
			} else if (m.isSymbol(C_MINUS)) {
				if (!k.isEmpty()) {
					kk.add(k);
					k = new TokenList();
				}
				minus = minus == null ? m : null;

			} else {
				if (k.isEmpty() && minus != null) {
					k.add(minus);
					minus = null;
				}
				k.add(m);
			}
		}

		if (k.isEmpty()) throw new ExpResolveException(tokens,"Invalid cutting with symbols: + and -");
		kk.add(k);

		if (kk.size() == 1) return null;
		return kk;
	}
}