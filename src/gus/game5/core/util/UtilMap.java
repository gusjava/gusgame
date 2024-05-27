package gus.game5.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import gus.game5.core.features.g.G;
import gus.game5.core.features.p.P;
import gus.game5.core.features.t.T;
import gus.game5.core.util.map.Map1;

public class UtilMap {
	
	/*
	 * HAS
	 */

	public static <K, V> boolean has(Map<K, V> map, K key) {
		return map!=null && key!=null && map.containsKey(key);
	}
	
	/*
	 * GET
	 */
	
	public static <K,V> V get(Map<K,V> map, K key, V defaultValue) {
		return has(map,key) ? map.get(key) : defaultValue;
	}
	
	public static <K,V> V get(Map<K,V> map, K key) {
		return get(map, key, null);
	}
	
	/*
	 * GET STRICT
	 */
	
	public static <K,V> V getStrict(Map<K,V> map, K key) {
		if(!has(map,key)) throw new RuntimeException("Key not found inside map: "+key);
		return map.get(key);
	}
	
	/*
	 * GET LIST
	 */

	public static <K, U> List<U> getList(Map<K, List<U>> map, K key) {
		return get(map, key, new ArrayList<>());
	}
	
	/*
	 * GET SET
	 */

	public static <K, U> Set<U> getSet(Map<K, Set<U>> map, K key) {
		return get(map, key, new HashSet<>());
	}

	/*
	 * INIT
	 */

	public static <K, V> V init(Map<K, V> map, K key, V initValue) {
		if (!map.containsKey(key)) map.put(key, initValue);
		return map.get(key);
	}
	
	public static <K,V> V init(Map<K,V> map, K key, G<? extends V> g) {
		if(!map.containsKey(key)) map.put(key, g.g());
		return map.get(key);
	}
	
	public static <K,V> V init(Map<K,V> map, K key, T<? super K,? extends V> t) {
		if(!map.containsKey(key)) map.put(key, t.t(key));
		return map.get(key);
	}
	
	/*
	 * INIT LIST
	 */

	public static <K, V> List<V> initList(Map<K, List<V>> map, K key) {
		return init(map, key, ()->new ArrayList<>());
	}
	
	/*
	 * INIT SET
	 */

	public static <K, V> Set<V> initSet(Map<K, Set<V>> map, K key) {
		return init(map, key, ()->new HashSet<>());
	}
	
	/*
	 * PUT ALL
	 */
	
	@SafeVarargs
	public static <K,V> Map1<K,V> putAll(Map<K,V>... mm) {
		Map1<K,V> map = new Map1<>();
		for(Map<K,V> m : mm) map.putAll(m);
		return map;
	}
	
	/*
	 * ADD TO SET
	 */
	
	public static <K,V> boolean addToSet(Map<K,Set<V>> map, K key, V value) {
		Set<V> set = initSet(map, key);
		if(set.contains(value)) return true;
		set.add(value);
		return false;
	}
	
	/*
	 * ADD TO LIST
	 */
	
	public static <K,V> boolean addToList(Map<K,List<V>> map, K key, V value) {
		List<V> list = initList(map, key);
		if(list.contains(value)) return true;
		list.add(value);
		return false;
	}
	
	/*
	 * VALUES FOR KEYS
	 */
	
	public static <K,V> List<V> valuesForKeys(Map<K,V> map, List<K> keys) {
		List<V> values = new ArrayList<>();
		for(K key : keys) if(has(map,key)) values.add(map.get(key));
		return values;
	}
	
	
	/*
	 * EACH KEY
	 */
	
	public static <K,V> void eachKey(Map<K,V> map, P<? super K> p) {
		if(map==null || p==null) return;
		for(K key : map.keySet()) p.p(key);
	}
	
	/*
	 * EACH VALUE
	 */
	
	public static <K,V> void eachValue(Map<K,V> map, P<? super V> p) {
		if(map==null || p==null) return;
		for(V value : map.values()) p.p(value);
	}


	/*
	 * MAP KEYS AND VALUES
	 */

	public static <K, V, U> Map1<K, V> mapKV(Collection<U> col, T<? super U, ? extends K> k, T<? super U, ? extends V> v) {
		if (col == null || k == null || v == null) return null;
		Map1<K, V> map = new Map1<>();
		for (U elem : col) if(elem!=null) {
			map.put(k.t(elem), v.t(elem));
		}
		return map;
	}
	
	/*
	 * MAP KEYS
	 */

	public static <K, U> Map1<K, U> mapK(Collection<U> col, T<? super U, ? extends K> k) {
		if (col == null || k == null) return null;
		return mapKV(col, k, o->o);
	}

	/*
	 * MAP VALUES
	 */

	public static <V, U> Map1<U, V> mapV(Collection<U> col, T<? super U, ? extends V> v) {
		if (col == null || v == null) return null;
		return mapKV(col, o->o, v);
	}
	

	
	
	/*
	 * MAP_LIST KEYS AND VALUES
	 */

	public static <K, V, U> Map1<K, List<V>> mapListKV(Collection<U> col, T<? super U, ? extends K> k, T<? super U, ? extends V> v) {
		if (col == null || k == null || v == null) return null;
		Map1<K, List<V>> map = new Map1<>();
		for (U elem : col) if(elem!=null) {
			K key = k.t(elem);
			V value = v.t(elem);
			if(key!=null && value!=null) initList(map,key).add(value);
		}
		return map;
	}
	
	/*
	 * MAP_SET KEYS AND VALUES
	 */

	public static <K, V, U> Map1<K, Set<V>> mapSetKV(Collection<U> col, T<? super U, ? extends K> k, T<? super U, ? extends V> v) {
		if (col == null || k == null || v == null) return null;
		Map1<K, Set<V>> map = new Map1<>();
		for (U elem : col) if(elem!=null) {
			K key = k.t(elem);
			V value = v.t(elem);
			if(key!=null && value!=null) initSet(map,key).add(value);
		}
		return map;
	}
	
	/*
	 * MAP_COUNT VALUES
	 */

	public static <V, U> Map1<V, Integer> mapCountValue(Collection<U> col, T<? super U, ? extends V> v) {
		if (col == null || v == null) return null;
		Map1<V, Integer> map = new Map1<>();
		for (U elem : col) if(elem!=null) {
			V value = v.t(elem);
			if(value!=null) map.put(value, init(map,value,0)+1);
		}
		return map;
	}
	
	public static <U> Map1<String, Integer> mapCountValue(Collection<U> col) {
		return mapCountValue(col, o->o.toString());
	}
	
	/*
	 * MAP_LIST KEYS
	 */

	public static <K, U> Map1<K, List<U>> mapListK(Collection<U> col, T<? super U, ? extends K> k) {
		if (col == null || k == null) return null;
		return mapListKV(col, k, o->o);
	}
	
	/*
	 * MAP_SET KEYS
	 */

	public static <K, U> Map1<K, Set<U>> mapSetK(Collection<U> col, T<? super U, ? extends K> k) {
		if (col == null || k == null) return null;
		return mapSetKV(col, k, o->o);
	}
	
	/*
	 * GROUP BY  (=MAP_LIST KEYS)
	 */

	public static <K, V> Map1<K, List<V>> groupBy(List<V> list, T<? super V, ? extends K> k) {
		return mapListK(list,k);
	}
	
	/*
	 * GROUP BY  (=MAP_LIST KEYS VALUES)
	 */

	public static <K, V, U> Map1<K, List<V>> groupBy(List<U> list, T<? super U, ? extends K> k, T<? super U, ? extends V> v) {
		return mapListKV(list,k,v);
	}
	
	
	/*
	 * COLLECT VALUES
	 */
	
	public static <K, V1, V2> Map1<K,V2> collectValues(Map<K,V1> map, T<? super V1, ? extends V2> k) {
		if (map == null || k == null) return null;
		Map1<K, V2> map2 = new Map1<>();
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()) {
			K key = it.next();
			V1 value1 = map.get(key);
			if(value1!=null) {
				V2 value2 = k.t(value1);
				if(value2!=null) map2.put(key, value2);
			}
		}
		return map2;
	}
	
	
	/*
	 * COLLECT KEYS
	 */
	
	public static <K1, K2, V> Map1<K2,V> collectkeys(Map<K1,V> map, T<? super K1, ? extends K2> k) {
		if (map == null || k == null) return null;
		Map1<K2, V> map2 = new Map1<>();
		Iterator<K1> it = map.keySet().iterator();
		while(it.hasNext()) {
			K1 key1 = it.next();
			V value = map.get(key1);
			K2 key2 = k.t(key1);
			if(key2!=null) map2.put(key2, value);
		}
		return map2;
	}
	
	
	/*
	 * KEYS
	 */
	
	public static <K,V> List<K> keys(Map<K,V> map) {
		if(map==null) return null;
		return new ArrayList<>(map.keySet());
	}
	
	/*
	 * SORTED KEYS
	 */
	
	public static <K extends Comparable<? super K>,V> List<K> sortedKeys(Map<K,V> map) {
		if(map==null) return null;
		List<K> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		return keys;
	}
	
	public static <K,V> List<K> sortedKeys(Map<K,V> map, Comparator<? super K> comparator) {
		if(map==null) return null;
		List<K> keys = new ArrayList<>(map.keySet());
		if(comparator!=null) Collections.sort(keys, comparator);
		return keys;
	}
	
	
	/*
	 * SORT VALUES
	 */
	
	public static <K,V extends Comparable<? super V>> List<V> sortedValues(Map<K,V> map) {
		if(map==null) return null;
		List<V> values = new ArrayList<>(map.values());
		Collections.sort(values);
		return values;
	}
	
	public static <K,V> List<V> sortedValues(Map<K,V> map, Comparator<? super V> comparator) {
		if(map==null) return null;
		List<V> values = new ArrayList<>(map.values());
		if(comparator!=null) Collections.sort(values, comparator);
		return values;
	}
	
	/*
	 * AS MAP
	 */
	
	@SafeVarargs
	public static <U> Map1<U,U> asMap(U... elements) {
		Map1<U,U> map = new Map1<>();
		List<U> buff = new ArrayList<>();
		for(U element : elements) {
			buff.add(element);
			if(buff.size()==2) {
				map.put(buff.get(0), buff.get(1));
				buff.clear();
			}
		}
		return map;
	}
	
	public static <K,V> Map1<K,V> asMap(K key, V value) {
		Map1<K,V> map = new Map1<>();
		map.put(key,value);
		return map;
	}
	
	public static <K,V> Map1<K,V> asMap(K key1, V value1, K key2, V value2) {
		Map1<K,V> map = new Map1<>();
		map.put(key1,value1);
		map.put(key2,value2);
		return map;
	}
	
	public static <K,V> Map1<K,V> asMap(K key1, V value1, K key2, V value2, K key3, V value3) {
		Map1<K,V> map = new Map1<>();
		map.put(key1,value1);
		map.put(key2,value2);
		map.put(key3,value3);
		return map;
	}
	
	public static <K,V> Map1<K,V> asMap(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
		Map1<K,V> map = new Map1<>();
		map.put(key1,value1);
		map.put(key2,value2);
		map.put(key3,value3);
		map.put(key4,value4);
		return map;
	}
	
	/*
	 * OCC
	 */
	
	public static <K> void increaseOcc(Map<K,Integer> occMap, K key) {
		if(!occMap.containsKey(key)) occMap.put(key, 1);
		else occMap.put(key, occMap.get(key)+1);
	}
	
	/*
	 * PROP TO MAP
	 */
	
	public static Map<String,String> propToMap(Properties prop) {
		Map<String,String> map = new HashMap<>();
		Iterator<Object> it = prop.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = prop.getProperty(key);
			map.put(key, value);
		}
		return map;
	}
	
	/*
	 * INV MAP
	 */
	
	public static <K,V> Map<V,K> invMap0(Map<K,V> map) {
		Map<V,K> m = new HashMap<>();
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()) {
			K key = it.next();
			V value = map.get(key);
			m.put(value, key);
		}
		return m;
	}
	
	public static <K,V> Map<V,K> invMap0s(Map<K,V> map) {
		Map<V,K> m = new HashMap<>();
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()) {
			K key = it.next();
			V value = map.get(key);
			if(m.containsKey(value))
				throw new RuntimeException("Value found many times inside map: "+value);
			m.put(value, key);
		}
		return m;
	}
	
	public static <K,V> Map<V,List<K>> invMap1(Map<K,V> map) {
		Map<V,List<K>> m = new HashMap<>();
		Iterator<K> it = map.keySet().iterator();
		while(it.hasNext()) {
			K key = it.next();
			V value = map.get(key);
			addToList(m, value, key);
		}
		return m;
	}
}
