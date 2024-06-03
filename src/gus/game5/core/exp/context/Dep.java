package gus.game5.core.exp.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.exp.dataprovider.DataProviderExp;
import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.exp.exception.ExpResolveException;
import gus.game5.core.exp.resolver4.Resolver4;
import gus.game5.core.exp.token.TokenList;
import gus.game5.core.util.UtilMap;

public class Dep {
	
	private Map<String, Set<String>> map1;
	private Map<String, Set<String>> map2;
	
	public Dep() {
		map1 = new HashMap<>();
		map2 = new HashMap<>();
	}
	
	public Dep(Dep parent) {
		map1 = new HashMap<>(parent.map1);
		map2 = new HashMap<>(parent.map2);
	}
	
	public Map<String, Set<String>> getMap1() {
		return map1;
	}
	
	public Map<String, Set<String>> getMap2() {
		return map2;
	}

	
	/*
	 * UP
	 */
	
	public Set<String> up(String key) {
		if(!map1.containsKey(key)) throw new TechnicalException("Variable not found: "+key);
		return map1.get(key);
	}
	
	public Set<String> upDeep(String key) {
		if(!map1.containsKey(key)) throw new TechnicalException("Variable not found: "+key);
		
		List<String> pending = new ArrayList<>();
		Set<String> done = new HashSet<>();
		pending.add(key);
		
		Set<String> results = new HashSet<>();
		
		while(!pending.isEmpty()) {
			String var = pending.remove(0);
			if(map1.containsKey(var)) {
				Set<String> set = map1.get(var);
				if(done.contains(var)) throw new TechnicalException("Invalid dependency state : cycle found with var="+var);
				
				done.add(var);
				pending.addAll(set);
				results.addAll(set);
			}
		}
		return results;
	}
	
	public Set<String> missing(String key) {
		if(!map1.containsKey(key)) throw new TechnicalException("Variable not found: "+key);
		
		Set<String> results = upDeep(key);
		results.removeAll(map1.keySet());
		return results;
	}
	
	
	
	/*
	 * DOWN
	 */
	
	public Set<String> down(String key) {
		if(!map2.containsKey(key)) return new HashSet<>();
		return map2.get(key);
	}
	
	
	public Set<String> downDeep(String key) {
		
		List<String> pending = new ArrayList<>();
		Set<String> done = new HashSet<>();
		pending.add(key);
		
		Set<String> results = new HashSet<>();
		
		while(!pending.isEmpty()) {
			String var = pending.remove(0);
			if(map2.containsKey(var)) {
				Set<String> set = map2.get(var);
				if(done.contains(var)) throw new TechnicalException("Invalid dependency state : cycle found with var="+var);
				
				done.add(var);
				pending.addAll(set);
				results.addAll(set);
			}
		}
		return results;
	}
	
	
	
	/*
	 * HANDLE
	 */
	
	@SuppressWarnings("unchecked")
	public void handle(Context context, String key, DataProviderExp dataProvider) throws ExpException {
		TokenList tokenList = dataProvider.getTokenList();
		Resolver4 resolver = new Resolver4(context);
		Set<String> set = (Set<String>) resolver.resolveTL(tokenList).getData();
		
		map1.put(key, set);
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String dep = it.next();
			UtilMap.addToSet(map2, dep, key);
		}
		
		String recycledVar = searchCycle(key);
		if(recycledVar!=null) throw new ExpResolveException(tokenList, "cyclic dependency found for var="+recycledVar+" when setting data key="+key);
	}
	
		
	private String searchCycle(String key) {
		List<String> pending = new ArrayList<>();
		Set<String> done = new HashSet<>();
		pending.add(key);
		
		while(!pending.isEmpty()) {
			String var = pending.remove(0);
			if(map1.containsKey(var)) {
				Set<String> set = map1.get(var);
				if(done.contains(var)) return var;
				done.add(var);
				pending.addAll(set);
			}
		}
		return null;
	}
}
