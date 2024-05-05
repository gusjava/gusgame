package gus.game5.core.util.image;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

import gus.game5.core.util.UtilImage;

public class IconLoader {

	private String base;
	private Map<String, Icon> map;
	
	public IconLoader(String base) {
		this.base = base;
		map = new HashMap<>();
	}
	
	public Icon get(String relPath) {
		if(!map.containsKey(relPath)) loadImage(relPath);
		return map.get(relPath);
	}
	
	private void loadImage(String relPath) {
		map.put(relPath, UtilImage.readIcon(base + relPath));
	}
}
