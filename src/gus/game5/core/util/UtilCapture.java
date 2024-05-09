package gus.game5.core.util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;

import javax.swing.JFrame;

public class UtilCapture {

	public static void captureFrame(JFrame frame) {
		try {
			Rectangle rect = frame.getBounds();
			rect.setLocation(frame.getLocationOnScreen());
			
			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(rect);
			
			File file = new File(captureDir(), "capture_"+UtilDateFormat.format6(new Date())+".jpg");
			UtilImage.writeJpeg(image, file);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static File appLocation() {
		try {
			return new File(UtilCapture.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static File captureDir() {
		return appLocation().getParentFile();
	}
}
