package gus.game5.core.exp.parser;

import static gus.game5.core.util.UtilParser.*;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;

public class ParserStep3 {

	public static TokenList compute(TokenList input) throws ExpParseException {
		int number = input.size();

		// on commence par convertir tous les tokens ELEMENT correspondant   à des entiers en token INT
		for(int i=0; i<number; i++) {
			Token m = input.get(i);
			if (!m.isElement()) continue;

			Object value = m.getValue();
			Number n = toIntOrLongObj(value);
			
			if(n!=null) {
				m.setType(Token.Type.INT);
				m.setValue(n);
			}
		}

		// on aggrège toutes les séquences composées de tokens "point" ou INT
		TokenList output = new TokenList();
		List<Token> buff = new ArrayList<>();

		for(int i=0; i<number; i++) {
			Token m = input.get(i);
			if (m.isPoint() || m.isInt()) {
				buff.add(m); 
				continue;
			}

			handleBuff(buff, output);
			output.add(m);
		}

		handleBuff(buff, output);
		return output;
	}

	
	private static void handleBuff(List<Token> buff, TokenList output) throws ExpParseException {
		if(buff.isEmpty()) return;

		if(buff.size() == 3) {
			Token m1 = buff.get(0);
			Token m2 = buff.get(1);
			Token m3 = buff.get(2);

			if(m1.isInt() && m2.isPoint() && m3.isInt()) {
				//ici, on reconnait le pattern correspondant à un DOUBLE : INT . INT
				addDouble(m1, m3, output);
				buff.clear();
				return;
			}
		}

		//dans les autres cas, on passe le contenu du buffer vers la sortie
		output.addAll(buff);
		buff.clear();
	}
	
	
	private static void addDouble(Token m1, Token m2, TokenList output) throws ExpParseException {
		int start = m1.getStart();
		String text = m1.getText()+"."+m2.getText();
		
		try {
			Double v = Double.valueOf(text);
			output.add(new Token(Token.Type.DOUBLE, start, text, v));
		}
		catch(NumberFormatException e) {
			throw new ExpParseException(m1.getStart(), m2.getEnd(), "Failed to parse double value: "+text);
		}
	}
}