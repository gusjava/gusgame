package gus.game5.core.util.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Map1<K,V> implements Iterable<K>, Map<K,V> {
	
	protected Map<K,V> map;
	
	public Map1() {
		map = new HashMap<>();
	}
	
	/*
	 * MAP
	 */

	public int size() {
		return map.size();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void clear() {
		map.clear();
	}
	
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	
	public Collection<V> values() {
		return map.values();
	}

	public V get(Object key) {
		return map.get(key);
	}
	
	public V remove(Object key) {
		return map.remove(key);
	}
	
	public V put(K key, V value) {
		return map.put(key, value);
	}
	
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	public Set<K> keySet() {
		return map.keySet();
	}
	
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}
	
	/*
	 * ITERATOR
	 */

	public Iterator<K> iterator() {
		return map.keySet().iterator();
	}
	
	/*
	 * VAL
	 */
	
	public V rmVal(K key) {
		return map.remove(key);
	}
	
	public V getVal(K key) {
		return map.get(key);
	}
	
	public boolean hasVal(K key) {
		return map.containsKey(key);
	}
}
