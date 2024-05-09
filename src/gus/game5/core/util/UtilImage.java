package gus.game5.core.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UtilImage {
	
	/*
	 * READ
	 */
	
	public static BufferedImage readImg(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static BufferedImage readImg(String path) {
		try {
			InputStream is = UtilImage.class.getResourceAsStream(path);
			if(is==null) return null;
			return ImageIO.read(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static ImageIcon readIcon(String path) {
		URL url = UtilImage.class.getResource(path);
		return new ImageIcon(url);
	}
	
	/*
	 * WRITE
	 */
	
	public static void writeJpeg(BufferedImage image, File file) {
		try {
			ImageIO.write(image,"jpg",file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * PAINT
	 */

	public static void paint(Graphics2D g2, int x0, int y0, int width, int height, Image img) {
		if(img==null) return;
		int imageW = img.getWidth(null);
		int imageH = img.getHeight(null);
		if (imageH <= 0)
			return;

		double cx = (double) width / (double) imageW;
		double cy = (double) height / (double) imageH;

		if (cx >= cy) {
			int a = (int) ((width - imageW * cy) / 2);
			int dx = (int) (imageW * cy);
			int dy = height;
			g2.drawImage(img, x0 + a, y0, dx, dy, null);
		} else {
			int a = (int) ((height - imageH * cx) / 2);
			int dx = width;
			int dy = (int) (imageH * cx);
			g2.drawImage(img, x0, y0 + a, dx, dy, null);
		}
	}

	public static void paint(Graphics2D g2, int x0, int y0, int width, int height, RenderedImage img) {
		if(img==null) return;
		int imageW = img.getWidth();
		int imageH = img.getHeight();
		if (imageH <= 0)
			return;

		double cx = (double) width / (double) imageW;
		double cy = (double) height / (double) imageH;

		if (cx >= cy) {
			int a = (int) ((width - imageW * cy) / 2);
			AffineTransform af = AffineTransform.getTranslateInstance(x0 + a, y0);
			af.scale(cy, cy);
			g2.drawRenderedImage(img, af);
		} else {
			int a = (int) ((height - imageH * cx) / 2);
			AffineTransform af = AffineTransform.getTranslateInstance(x0, y0 + a);
			af.scale(cx, cx);
			g2.drawRenderedImage(img, af);
		}
	}
}
