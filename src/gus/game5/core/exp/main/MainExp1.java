package gus.game5.core.exp.main;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver1.Resolver1Number;
import gus.game5.core.exp.token.TokenList;

public class MainExp1 {


	public static void main(String[] args) {
		
		try {
			String expression = "(4-3)*2";
			System.out.println("Expression: "+expression);
			
			TokenList list = Parser.parse(expression);
			list.printPretty();
			
			Resolver1Number resolver = new Resolver1Number();
			resolver.resolveTL(list).printData();
		}
		catch (ExpException e) {
			e.printStackTrace();
		}
	}
}
