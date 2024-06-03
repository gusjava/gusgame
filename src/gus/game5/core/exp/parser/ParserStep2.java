package gus.game5.core.exp.parser;

import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilParser;

public class ParserStep2 {

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

		if(m.isQuote() || m.isDQuote()) return buildString(input, m);
		if(m.isOther()) return null;
		return m;
	}

	private static Token buildString(TokenList input, Token startToken) throws ExpParseException {
		
		String meta = startToken.getText();
		StringBuilder bValue = new StringBuilder();
		StringBuilder bText = new StringBuilder();
		
		boolean escapeFound = false;
		boolean endFound = false;

		while(!input.isEmpty() && !endFound) {
			Token m = input.removeFirst();
			bText.append(m.getText());
			
			if(escapeFound) {
				if(m.isEscape())
					bValue.append(UtilParser.C_ESCAPE);
				else if(m.isOfValue(meta))
					bValue.append(meta);
				else if(startsWith(m,"t"))
					bValue.append("\t"+strValue(m).substring(1));
				else if(startsWith(m,"b"))
					bValue.append("\b"+strValue(m).substring(1));
				else if(startsWith(m,"n"))
					bValue.append("\n"+strValue(m).substring(1));
				else if(startsWith(m,"r"))
					bValue.append("\r"+strValue(m).substring(1));
				else if(startsWith(m,"f"))
					bValue.append("\f"+strValue(m).substring(1));
				else if(startsWith(m,"u"))
					handleHexa(bValue, bText, input, m);
				else throw new ExpParseException(m,"Invalid string escape character");

				escapeFound = false;
			} else {
				if(m.isEscape())
					escapeFound = true;
				else if(m.isOfValue(meta))
					endFound = true;
				else bValue.append(strValue(m));
			}
		}

		String value = bValue.toString();
		String text = meta+bText.toString()+meta;
		int start = startToken.getStart();
		
		return new Token(Token.Type.STRING, start, text, value);
	}

	

	private static void handleHexa(StringBuilder bValue, StringBuilder bText, TokenList input, Token m) throws ExpParseException {
		StringBuffer h = new StringBuffer();
		h.append(strValue(m).substring(1));

		while(h.length()<4 && !input.isEmpty()) {
			m = input.removeFirst();
			bText.append(m.getText());
			h.append(strValue(m));
		}
		if(h.length()<4) throw new ExpParseException(m,"Invalid \\u hexa value: " + h);

		try{
			int codePoint = Integer.parseInt(h.substring(0,4),16);
			bValue.append(Character.toChars(codePoint));
			bValue.append(h.substring(4));
		}
		catch(NumberFormatException e) {
			int end = m.getEnd();
			int start = end-4;
			throw new ExpParseException(start,end,"Invalid \\u hexa value: " + h);
		}
	}
	
	
	
	
	private static boolean startsWith(Token m, String value) {
		return strValue(m).startsWith(value);
	}
	
	private static String strValue(Token m) {
		return (String) m.getValue();
	}
}