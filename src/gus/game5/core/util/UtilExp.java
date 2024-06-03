package gus.game5.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exp.context.Context;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver1.Resolver1Data;
import gus.game5.core.exp.resolver2.Resolver2Data;
import gus.game5.core.exp.resolver3.Resolver3;
import gus.game5.core.exp.resolver4.Resolver4;
import gus.game5.core.exp.token.TokenList;

public class UtilExp {
	
	/*
	 * RESOLVE 1
	 */
	
	public static ResolverResult resolve1(String expression, Map<String,Object> dataMap) throws ExpException {
		TokenList list = Parser.parse(expression);
		Resolver1Data resolver = new Resolver1Data(dataMap);
		return resolver.resolveTL(list);
	}
	
	/*
	 * RESOLVE 2
	 */
	
	public static ResolverResult resolve2(String expression, Map<String,Object> dataMap) throws ExpException {
		TokenList list = Parser.parse(expression);
		Resolver2Data resolver = new Resolver2Data(dataMap);
		return resolver.resolveTL(list);
	}
	
	/*
	 * RESOLVE 3
	 */
	
	public static ResolverResult resolve3(String expression, Context context) throws ExpException {
		TokenList list = Parser.parse(expression);
		Resolver3 resolver = new Resolver3(context);
		return resolver.resolveTL(list);
	}
	
	/*
	 * RESOLVE 4
	 */
	
	public static ResolverResult resolve4(String expression, Context context) throws ExpException {
		TokenList list = Parser.parse(expression);
		Resolver4 resolver = new Resolver4(context);
		return resolver.resolveTL(list);
	}
	
	
	/*
	 * RESOLVE ALL
	 */
	
	public static List<Object> resolveAll(ResolverTL resolver, List<TokenList> list) throws ExpException {
		List<Object> dataList = new ArrayList<>();
		for(TokenList tokenList : list) {
			ResolverResult r = resolver.resolveTL(tokenList);
			if(!r.isResolved()) throw new ExpResolveException(tokenList, "tokenList not resolved");
			dataList.add(r.getData());
		}
		return dataList;
	}
	
	
	/*
	 * ESTIMATE
	 */
	
	public static Object estimate(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve2(expression, dataMap).getData();
	}
	
	public static boolean isTypeBoolean(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve2(expression, dataMap).isTypeBoolean();
	}
	
	public static boolean isTypeDate(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve2(expression, dataMap).isTypeDate();
	}
	
	public static boolean isTypeNumber(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve2(expression, dataMap).isTypeNumber();
	}
	
	public static boolean isTypeString(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve2(expression, dataMap).isTypeString();
	}
	
	/*
	 * DEPENDENCIES
	 */
	
	@SuppressWarnings("unchecked")
	public static Set<String> getDependencies(String expression, Context context) throws ExpException {
		return (Set<String>) resolve4(expression, context).getData();
	}
	
	public static Set<String> getDependencies(String expression) throws ExpException {
		return getDependencies(expression, new Context());
	}
	
	
	/*
	 * EVALUATE
	 */
	
	public static Object evaluateWith(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve1(expression, dataMap).getData();
	}
	
	public static Object evaluateWith(String expression, String var, Object val) throws ExpException {
		return evaluateWith(expression, UtilMap.asMap(var,val));
	}
	
	public static Object evaluate(String expression) throws ExpException {
		return evaluateWith(expression, new HashMap<>());
	}
	
	

	/*
	 * EVALUATE AS DOUBLE
	 */
	
	public static double evaluateAsDoubleWith(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve1(expression, dataMap).asDouble();
	}
	
	public static double evaluateAsDoubleWith(String expression, String var, Object val) throws ExpException {
		return evaluateAsDoubleWith(expression, UtilMap.asMap(var,val));
	}
	
	public static double evaluateAsDouble(String expression) throws ExpException {
		return evaluateAsDoubleWith(expression, new HashMap<>());
	}
	
	/*
	 * EVALUATE AS BOOLEAN
	 */
	
	public static boolean evaluateAsBooleanWith(String expression, Map<String,Object> dataMap) throws ExpException {
		return resolve1(expression, dataMap).asBoolean();
	}
	
	public static boolean evaluateAsBooleanWith(String expression, String var, Object val) throws ExpException {
		return evaluateAsBooleanWith(expression, UtilMap.asMap(var,val));
	}
	
	public static boolean evaluateAsBoolean(String expression) throws ExpException {
		return evaluateAsBooleanWith(expression, new HashMap<>());
	}
}
