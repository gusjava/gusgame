package gus.game5.core.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;

import javax.swing.JFrame;

import gus.game5.core.exception.TechnicalException;

public class UtilCapture {

	public static void captureFrame(JFrame frame) {
		Rectangle rect = frame.getBounds();
		rect.setLocation(frame.getLocationOnScreen());
		
		BufferedImage image = UtilScreen.captureScreen(rect);
		
		File file = new File(captureDir(), "capture_"+UtilDateFormat.format6(new Date())+".jpg");
		UtilImage.writeJpeg(image, file);
	}
	
	public static File appLocation() {
		try {
			return new File(UtilCapture.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			throw new TechnicalException(e);
		}
	}
	
	public static File captureDir() {
		return appLocation().getParentFile();
	}
}
