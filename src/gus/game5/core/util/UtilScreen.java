package gus.game5.core.util;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import gus.game5.core.exception.TechnicalException;

public class UtilScreen {

	public static Rectangle getScreenRect() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsConfiguration gc = device.getDefaultConfiguration();
		return gc.getBounds();
	}
	
	public static BufferedImage captureScreen() {
		return captureScreen(getScreenRect());
	}
	
	public static BufferedImage captureScreen(Rectangle rect) {
		try {
			Robot robot = new Robot();
			return robot.createScreenCapture(rect);
		} catch (AWTException e) {
			throw new TechnicalException(e);
		}
	}
}
