package gus.game5.core.exp.context;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gus.game5.core.exp.dataprovider.DataProviderExp;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpParseException;
import gus.game5.core.exp.parser.Parser;
import gus.game5.core.exp.resolver.ResolverResult;
import gus.game5.core.exp.resolver.ResolverT;
import gus.game5.core.exp.resolver.ResolverTL;
import gus.game5.core.exp.resolver3.Resolver3;
import gus.game5.core.exp.resolver3.apply.Apply;
import gus.game5.core.exp.resolver3.apply0.Apply0Dstring;
import gus.game5.core.exp.resolver3.apply0.Apply0Lower;
import gus.game5.core.exp.resolver3.apply0.Apply0Size;
import gus.game5.core.exp.resolver3.apply0.Apply0Type;
import gus.game5.core.exp.resolver3.apply0.Apply0Upper;
import gus.game5.core.exp.resolver3.apply1.Apply1All;
import gus.game5.core.exp.resolver3.apply1.Apply1Any;
import gus.game5.core.exp.resolver3.apply1.Apply1Collect;
import gus.game5.core.exp.resolver3.apply1.Apply1Find;
import gus.game5.core.exp.resolver3.apply1.Apply1FindAll;
import gus.game5.core.exp.resolver3.apply1.Apply1Has;
import gus.game5.core.exp.resolver3.function.ApplyFunction;
import gus.game5.core.exp.token.Token;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilMap;

public class Context implements ResolverTL, ResolverT {
		
	public Context() {
		dep = new Dep();
		initDataMap();
		initApplyMap();
	}
	
	public Context(Context parent) {
		dep = new Dep(parent.dep);
		this.dataMap = new HashMap<>(parent.dataMap);
		this.applyMap = new HashMap<>(parent.applyMap);
	}
	
	/*
	 * DEPENDENCIES
	 */
	
	private Dep dep;
	
	public Dep getDep() {
		return dep;
	}
	
	
	
	/*
	 * DATA MAP
	 */
	
	private Map<String, Object> dataMap;
	
	public Map<String,Object> getDataMap() {
		return dataMap;
	}
	
	private void initDataMap() {
		dataMap = new HashMap<>();
	}
	
	/*
	 * APPLY MAP
	 */
	
	private Map<String,Apply> applyMap;
	
	public Map<String,Apply> getApplyMap() {
		return applyMap;
	}
	
	private void initApplyMap() {
		applyMap = new HashMap<>();
		
		addApply("dstring", new Apply0Dstring());
		addApply("lower", new Apply0Lower());
		addApply("size", new Apply0Size());
		addApply("type", new Apply0Type());
		addApply("upper", new Apply0Upper());

		addApply("all", new Apply1All());
		addApply("any", new Apply1Any());
		addApply("collect", new Apply1Collect());
		addApply("find", new Apply1Find());
		addApply("findAll", new Apply1FindAll());
		addApply("has", new Apply1Has());
	}
	
	

	
	

	
	/*
	 * ADD DATA
	 */
	
	public void addData(String key, Object value) throws ExpException {
		if(value instanceof DataProviderExp) dep.handle(this, key, (DataProviderExp) value);
		dataMap.put(key, value);
	}
	
	public void addDataMap(Map<String, Object> map) throws ExpException {
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			addData(key,map.get(key));
		}
	}
	
	/*
	 * ADD EXPRESSION
	 */
	
	public void addExpression(String key, String expression) throws ExpException {
		addData(key, new DataProviderExp(this, expression));
	}
	
	/*
	 * ADD APPLY
	 */
	
	public void addApply(String key, Apply apply) {
		applyMap.put(key, apply);
	}
	
	/*
	 * ADD FUNCTION
	 */
	
	public void addFunction(String key, String expression) throws ExpParseException {
		addApply(key, new ApplyFunction(expression));
	}
	
	public void addFunction(String key, String expression, String var) throws ExpParseException {
		addApply(key, new ApplyFunction(expression, var));
	}
	
	/*
	 * RESET ALL
	 */
	
	public void resetAll() {
		UtilMap.eachValue(dataMap, val->{
			if(val instanceof DataProviderExp) {
				((DataProviderExp) val).reset();
			}
		});
	}
	
	/*
	 * RESOLVE
	 */
	
	public ResolverResult resolveTL(TokenList list) throws ExpException {
		return new Resolver3(this).resolveTL(list);
	}
	
	public ResolverResult resolveT(Token token) throws ExpException {
		return new Resolver3(this).resolveT(token);
	}
	
	public ResolverResult resolve(String expression) throws ExpException {
		TokenList list = Parser.parse(expression);
		return new Resolver3(this).resolveTL(list);
	}
	
	/*
	 * RESOLVE WITH
	 */
	
	public ResolverResult resolveTLWith(TokenList list, Map<String, Object> dataMap1) throws ExpException {
		return new Resolver3(newContext(dataMap1)).resolveTL(list);
	}
	
	public ResolverResult resolveTWith(Token token, Map<String, Object> dataMap1) throws ExpException {
		return new Resolver3(newContext(dataMap1)).resolveT(token);
	}
	
	public ResolverResult resolveWith(String expression, Map<String, Object> dataMap1) throws ExpException {
		TokenList list = Parser.parse(expression);
		return new Resolver3(newContext(dataMap1)).resolveTL(list);
	}
	
	
	/*
	 * NEW CONTEXT
	 */
	
	public Context newContext() {
		return new Context(this);
	}

	public Context newContext(Map<String,Object> dataMap1) throws ExpException {
		Context c = newContext();
		c.addDataMap(dataMap1);
		return c;
	}
	
}
