package gus.game5.core.persister;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import gus.game5.core.features.f.F;
import gus.game5.core.game.Game;
import gus.game5.core.util.UtilFile;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilMap;

public class Persister1 {
	
	public static final String EXT = "properties";
	public static final String DEFAULT = "default";
	
	private String id;
	private File dir;

	public Persister1(Game game) {
		this(game.getClass().getSimpleName());
	}

	public Persister1(String id) {
		this.id = id;
		dir = UtilFile.mkdir(UtilFile.getGusGameDir(), id);
	}
	
	public String getId() {
		return id;
	}
	
	public File getDir() {
		return dir;
	}
	
	/*
	 * NAMES
	 */
	
	public List<String> names() {
		File[] ff = dir.listFiles();
		List<String> names = new ArrayList<>();
		for(File f : ff) {
			String name = f.getName();
			name = name.substring(0, name.length()-EXT.length()-1);
			names.add(name);
		}
		return names;
	}
	
	public List<String> names(F<String> filter) {
		return UtilList.findAll(names(), filter);
	}
	
	/*
	 * KEYS
	 */
	
	public List<String> keys() {
		return keys(DEFAULT);
	}
	
	public List<String> keys(String name) {
		List<String> keys = new ArrayList<>();
		for(Object key : load(name).keySet()) keys.add((String) key);
		Collections.sort(keys);
		return keys;
	}
	
	/*
	 * GET
	 */
	
	public String get(String key) {
		return get(DEFAULT,key);
	}
	
	public String get(String name, String key) {
		return getValue(load(name),key);
	}
	
	/*
	 * GET INT
	 */
	
	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}
	
	public int getInt(String name, String key) {
		return Integer.parseInt(get(name, key));
	}
	
	/*
	 * PUT
	 */
	
	public void put(String key, String value) {
		put(DEFAULT, key, value);
	}
	
	public void put(String name, String key, String value) {
		Properties p = load(name);
		putValue(p, key, value);
		write(name, p);
	}
	
	/*
	 * REMOVE
	 */
	
	public void remove(String name) {
		File f = file(name);
		if(f!=null && f.exists()) f.delete();
	}
	
	/*
	 * PROPS
	 */
	
	public Map<String, Properties> props() {
		Map<String, Properties> map = new HashMap<>();
		File[] ff = dir.listFiles();
		for(File f : ff) {
			String name = name(f);
			map.put(name, load(f));
		}
		return map;
	}
	
	public Map<String, Properties> props(F<String> filter) {
		Map<String, Properties> map = new HashMap<>();
		File[] ff = dir.listFiles();
		for(File f : ff) {
			String name = name(f);
			if(filter.f(name)) {
				map.put(name, load(f));
			}
		}
		return map;
	}
	
	/*
	 * PROP
	 */
	
	public Properties prop(String name) {
		return load(name);
	}
	
	/*
	 * ALL VALUES
	 */
	
	public Map<String,String> allValuesForKey(String key) {
		Map<String,String> map = new HashMap<>();
		File[] ff = dir.listFiles();
		for(File f : ff) {
			String name = name(f);
			Properties p = load(f);
			if(p.containsKey(key)) {
				map.put(name, p.getProperty(key));
			}
		}
		return map;
	}
	
	public Map<String,String> allValuesForKey(String key, F<String> filter) {
		Map<String,String> map = new HashMap<>();
		File[] ff = dir.listFiles();
		for(File f : ff) {
			String name = name(f);
			if(filter.f(name)) {
				Properties p = load(f);
				if(p.containsKey(key)) {
					map.put(name, p.getProperty(key));
				}
			}
		}
		return map;
	}
	
	/*
	 * PRIVATE
	 */
	
	private File file(String name) {
		return new File(dir, name+"."+EXT);
	}
	
	private String name(File file) {
		String name = file.getName();
		return name.substring(0, name.length()-EXT.length()-1);
	}
	
	private Properties load(String name) {
		return load(file(name));
	}
	
	private Properties load(File file) {
		try {
			return UtilFile.readPropFile(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void write(String name, Properties p) {
		write(file(name), p);
	}
	
	private void write(File file, Properties p) {
		try {
			UtilFile.write(file, p);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getValue(Properties p, String key) {
		if(!p.containsKey(key)) return null;
		return p.getProperty(key);
	}
	
	private void putValue(Properties p, String key, String value) {
		if(value==null) p.remove(key);
		else p.setProperty(key, value);
	}
}
