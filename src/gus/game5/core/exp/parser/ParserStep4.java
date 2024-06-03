package gus.game5.core.exp.parser;

import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.exp.token.Token.Type;
/**
 * 4ème et dernière étape du parsing : 
 * - identifie les groupements délimités par des symboles ouvrants et fermants
 * -> on crée des tokens de type GROUP1, GROUP2, GROUP3
 *   - GROUP1 correspond à (...)
 *   - GROUP2 correspond à [...]
 *   - GROUP3 correspond à {...}
 */
public class ParserStep4 {

	public static TokenList compute(TokenList input) throws ExpParseException {
		TokenList output = new TokenList();

		while(!input.isEmpty()) {
			Token m = buildNext(input);
			if(m!=null) output.add(m);
		}
		return output;
	}
	

	private static Token buildNext(TokenList input) throws ExpParseException {
		Token m = input.removeFirst();

		if (m.isSymbol("(")) return buildGroup(input, m, Token.Type.GROUP1, ")");
		if (m.isSymbol("[")) return buildGroup(input, m, Token.Type.GROUP2, "]");
		if (m.isSymbol("{")) return buildGroup(input, m, Token.Type.GROUP3, "}");

		return m;
	}

	
	private static Token buildGroup(TokenList input, Token startToken, Type type, String closingChar) throws ExpParseException {
		TokenList children = new TokenList();
		StringBuilder b = new StringBuilder(startToken.getText());

		boolean endFound = false;
		while(!input.isEmpty() && !endFound) {
			Token m = buildNext(input);
			b.append(m.getText());
			
			if(m.isSymbol(closingChar)) endFound = true;
			else children.add(m);
		}
		
		String text = b.toString();
		int start = startToken.getStart();
		return new Token(type, start, text, children);
	}
}
