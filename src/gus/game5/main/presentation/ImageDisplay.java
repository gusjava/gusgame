package gus.game5.main.presentation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.RenderedImage;

import javax.swing.JPanel;

import gus.game5.core.util.UtilImage;

public class ImageDisplay extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	private RenderedImage image;
	
	public ImageDisplay(String path) {
		image = UtilImage.readImg(path);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		UtilImage.paint(g2, 0, 0, getWidth(), getHeight(), image);
	}
}
