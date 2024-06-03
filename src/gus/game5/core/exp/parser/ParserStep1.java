package gus.game5.core.exp.parser;

import static gus.game5.core.util.UtilParser.isDigit;
import static gus.game5.core.util.UtilParser.isLetter;
import static gus.game5.core.util.UtilParser.isSymbol;

import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;

public class ParserStep1 {
	
	public static TokenList compute(String s) {

		TokenList list = new TokenList();
		StringBuilder b = new StringBuilder();

		for(char c : s.toCharArray()) {
			if(isLetter(c) || isDigit(c) || c == '_') {
				b.append(c);
			} else if(isSymbol(c)) {
				addElement(list,b);
				addSymbol(list,c);
			} else {
				addElement(list,b);
				addOther(list,c);
			}
		}

		addElement(list, b);
		return list;
	}
	
	

	private static void addElement(TokenList list, StringBuilder b) {
		if(b.length() == 0) return;

		String value = b.toString();
		b.delete(0,b.length());
		
		if(value.equals("_")) 
			list.add(new Token(Token.Type.SYMBOL, list.getEnd(), value));
		else list.add(new Token(Token.Type.ELEMENT, list.getEnd(), value));
	}

	private static void addSymbol(TokenList list, char c) {
		String value = String.valueOf(c);
		list.add(new Token(Token.Type.SYMBOL, list.getEnd(), value));
	}

	private static void addOther(TokenList list, char c) {
		String value = String.valueOf(c);
		Token previous = list.last();
		
		if(previous==null) {
			list.add(new Token(Token.Type.OTHER, 0, value));
			return;
		}

		if(!previous.isOther()) {
			int start = previous.getEnd();
			list.add(new Token(Token.Type.OTHER, start, value));
			return;
		}

		String newValue = previous.getValue()+value;
		previous.setText(newValue);
		previous.setValue(newValue);
	}
}
