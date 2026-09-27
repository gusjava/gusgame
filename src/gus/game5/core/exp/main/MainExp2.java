package gus.game5.core.exp.main;

import java.util.HashMap;
import java.util.Map;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver2.Resolver2Data;
import gus.game5.core.exp.token.TokenList;

public class MainExp2 {


	public static void main(String[] args) {
		
		try {
			String expression = "(4-3)*2+a";
			System.out.println("Expression: "+expression);
			
			TokenList list = Parser.parse(expression);
			
			Map<String,Object> map = new HashMap<>();
			map.put("a", ResolverResult.Type.DOUBLE);
			
			Resolver2Data resolver = new Resolver2Data(map);
			resolver.resolveTL(list).printData();
		}
		catch (ExpException e) {
			e.printStackTrace();
		}
	}
}
