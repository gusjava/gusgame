package gus.game5.core.shape;

import java.awt.image.BufferedImage;

import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point2.Point2;

public class ShapeImg extends Shape0 {
	
	// ANCHOR P0

	public ShapeImg(Point0 anchor, double width, double height, AnchorType type, BufferedImage img) {
		super(anchor, width, height, type);
		this.img = img;
	}

	public ShapeImg(Point0 anchor, double width, double height, BufferedImage img) {
		super(anchor, width, height);
		this.img = img;
	}

	public ShapeImg(Point0 anchor, AnchorType type, BufferedImage img) {
		super(anchor, img.getWidth(), img.getHeight(), type);
		this.img = img;
	}

	public ShapeImg(Point0 anchor, BufferedImage img) {
		super(anchor, img.getWidth(), img.getHeight());
		this.img = img;
	}
	
	// ANCHOR P2

	public ShapeImg(Point2 anchor, double width, double height, AnchorType type, BufferedImage img) {
		super(anchor, width, height, type);
		this.img = img;
	}

	public ShapeImg(Point2 anchor, double width, double height, BufferedImage img) {
		super(anchor, width, height);
		this.img = img;
	}

	public ShapeImg(Point2 anchor, AnchorType type, BufferedImage img) {
		super(anchor, img.getWidth(), img.getHeight(), type);
		this.img = img;
	}

	public ShapeImg(Point2 anchor, BufferedImage img) {
		super(anchor, img.getWidth(), img.getHeight());
		this.img = img;
	}
	
	/*
	 * DRAW SHAPE
	 */

	protected void drawShape() {
		paintRenderedImageC(getWidth(), getHeight(), img);
	}
	
	/*
	 * IMAGE
	 */
	
	private BufferedImage img;
	
	public BufferedImage getImg() {
		return img;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
