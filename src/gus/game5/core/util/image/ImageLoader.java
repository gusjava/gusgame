package gus.game5.core.util.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import gus.game5.core.game.Game;
import gus.game5.core.util.UtilImage;
import gus.game5.core.util.UtilResource;

public class ImageLoader {

	private String base;
	private Map<String, BufferedImage> map;
	
	public ImageLoader(Game game) {
		this(game.getClass());
	}
	
	public ImageLoader(Class<?> clss) {
		this(UtilResource.resourceAt(clss, ""));
	}
	
	public ImageLoader(String base) {
		this.base = base;
		map = new HashMap<>();
	}
	
	public BufferedImage get(String relPath) {
		if(!map.containsKey(relPath)) loadImage(relPath);
		return map.get(relPath);
	}
	
	private void loadImage(String relPath) {
		map.put(relPath, UtilImage.readImg(base + relPath));
	}
}
