package gus.game5.core.util;

public class UtilResource {

	public static String resourceAt(Class<?> clss, String fileName) {
		String className = clss.getName();
		String[] n = className.split("\\.");
		StringBuffer b = new StringBuffer("/");
		for(int i=0;i<n.length-1;i++) b.append(n[i]+"/");
		b.append(fileName);
		return b.toString();
	}
}
