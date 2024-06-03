package gus.game5.core.exp.parser;

import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.token.TokenList;

public class Parser {

	public static TokenList parse(String s) throws ExpParseException {
		
		TokenList list = null;
		
		list = ParserStep1.compute(s);		//identification des séquences alphanumériques, des symboles et des blancs
		list = ParserStep2.compute(list);	//regroupement des chaînes de caractères "..." ou '...'
		list = ParserStep3.compute(list);	//identification des nombres INT et DOUBLE
		list = ParserStep4.compute(list);	//regroupements hiérarchiques selon les parenthèses (...(..)...(..).) et {} []
		
		return list;
	}
}
