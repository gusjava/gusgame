package gus.game5.core.exp.cut;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilParser;

public class CutTernary extends UtilParser {

	
	public static List<TokenList> cut(TokenList tokens) {
		int number = tokens.size();
		if (number < 5) return null;

		TokenList k1 = new TokenList();
		TokenList k2 = new TokenList();
		TokenList k3 = new TokenList();
		
		int step = 0;

		for (int i = 0; i < number; i++) {
			Token m = (Token) tokens.get(i);
			
			if(step==0) {
				if(m.isSymbol(C_QUESTION)) step = 1;
				else k1.add(m);
			}
			else if(step==1) {
				if(m.isSymbol(C_COLON)) step = 2;
				else k2.add(m);
			}
			else {
				k3.add(m);
			}
		}
		
		if (k1.isEmpty() || k2.isEmpty() || k3.isEmpty()) return null;
		
		List<TokenList> kk = new ArrayList<>();
		kk.add(k1);
		kk.add(k2);
		kk.add(k3);
		
		return kk;
	}
}