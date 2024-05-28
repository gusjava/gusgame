package gus.game5.core.game.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.swing.JPanel;

import gus.game5.core.util.UtilImage;
import gus.game5.core.util.UtilThread;

public class ImageDisplay extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	private RenderedImage image;
	
	public ImageDisplay(String path) {
		UtilThread.run(()->{
			image = UtilImage.readImg(path);
		});
	}
	
	public ImageDisplay(File file) {
		UtilThread.run(()->{
			image = UtilImage.readImg(file);
		});
	}
	
	public ImageDisplay(RenderedImage image) {
		this.image = image;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		UtilImage.paint(g2, 0, 0, getWidth(), getHeight(), image);
	}
}
