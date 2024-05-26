package gus.game5.core.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class UtilFile {

	public static File getUserHomeDir() {
		return new File(System.getProperty("user.home"));
	}

	public static File getGusGameDir() {
		return new File(getUserHomeDir(), ".gusgame");
	}
	
	public static File mkdir(File dir) {
		dir.mkdirs();
		return dir;
	}
	
	public static File mkdir(File dir, String name) {
		return mkdir(new File(dir,name));
	}

	/*
	 * READ
	 */
	
	public static String readTextFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);  
		char[] a= new char[(int) file.length()];
		isr.read(a,0,(int) file.length());
		isr.close();
		return new String(a);
	}
	
	public static Properties readPropFile(File file) throws IOException {
		Properties p = new Properties();
		if(file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			p.load(fis);
			fis.close();
		}
		return p;
	}
	
	/*
	 * WRITE
	 */
	
	public static void write(File file, byte[] data) throws IOException {
		File parent = file.getParentFile();
		if(!parent.exists()) parent.mkdirs();
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(data);
		}
	}
	
	public static void write(File file, Properties p) throws IOException {
		File parent = file.getParentFile();
		if(!parent.exists()) parent.mkdirs();
		FileOutputStream fos = new FileOutputStream(file);
		p.store(fos, "");
		fos.close();
	}
	
	public static void write(File file, InputStream is) throws IOException {
		File parent = file.getParentFile();
		if(!parent.exists()) parent.mkdirs();
		try(BufferedInputStream in = new BufferedInputStream(is); 
				FileOutputStream fos = new FileOutputStream(file)){
			byte dataBuffer[] = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer)) != -1) {
				fos.write(dataBuffer, 0, bytesRead);
			}
		}
	}
}
