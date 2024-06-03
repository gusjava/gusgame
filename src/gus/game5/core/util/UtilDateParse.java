package gus.game5.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import gus.game5.core.exception.TechnicalException;

public class UtilDateParse {
	
	
	/*
	 * PARSE LENIENT
	 * attempts to parse string or returns null
	 */
	
	public static Date[] parseLenient(String[] ss) {
		Date[] dates = new Date[ss.length];
		for(int i=0;i<ss.length;i++) dates[i] = parseLenient(ss[i]);
		return dates;
	}
	
	public static Date parseLenient(String s) {
		if(s==null) return null;
		if(s.equals("now")) return new Date();
		
		s = s.trim().replaceAll("[_ \t]+"," ");
		SimpleDateFormat[] sdfArray = findSdfArray(s);

		if(sdfArray!=null) for(SimpleDateFormat sdf : sdfArray) {
			Date d = parseLenient(s, sdf);
			if(d!=null) return d;
		}

		try{return new Date(Long.parseLong(s));}
		catch(NumberFormatException e){}
		return null;
	}

	private static Date parseLenient(String s, SimpleDateFormat sdf) {
		try{return sdf.parse(s);}
		catch(Exception e) {return null;}
	}
	
	
	/*
	 * PARSE STRICT
	 * attempts to parse string or throws RuntimeException
	 */
	
	public static Date[] parseStrict(String[] ss) {
		Date[] dates = new Date[ss.length];
		for(int i=0;i<ss.length;i++) dates[i] = parseStrict(ss[i]);
		return dates;
	}
	
	public static Date parseStrict(String s) {
		Date date = parseLenient(s);
		if(date!=null) return date;
		throw new TechnicalException("Unknown date syntax: "+s);
	}

	public static Date parseStrict(String s, SimpleDateFormat sdf) {
		try{return sdf.parse(s);}
		catch(Exception e) {
			throw new TechnicalException("Failed to parse date: "+s,e);
		}
	}
	

	
	/*
	 * FIND SDF ARRAY
	 */
	
	private static SimpleDateFormat[] findSdfArray(String s) {
		switch(s.length()) {
			case 19:return sdf19;
			case 18:return sdf18;
			case 17:return sdf17;
			case 16:return sdf16;
			case 15:return sdf15;
			case 14:return sdf14;
			case 13:return sdf13;
			case 12:return sdf12;
			case 11:return sdf11;
			case 10:return sdf10;
			case 9:return sdf9;
			case 8:return sdf8;
			case 7:return sdf7;
			case 6:return sdf6;
			case 5:return sdf5;
			case 4:return sdf4;
			default:return null;
		}
	}

	private static SimpleDateFormat[] sdf19 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy MM dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy.MM.dd HH:mm:ss"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
	};

	private static SimpleDateFormat[] sdf18 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy MM dd HH:mm:session_"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:session_"),
			new SimpleDateFormat("yyyy.MM.dd HH:mm:session_"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm:session_"),

			new SimpleDateFormat("yyyy MM dd HH:m:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:m:ss"),
			new SimpleDateFormat("yyyy.MM.dd HH:m:ss"),
			new SimpleDateFormat("dd/MM/yyyy HH:m:ss"),

			new SimpleDateFormat("yyyy MM dd H:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd H:mm:ss"),
			new SimpleDateFormat("yyyy.MM.dd H:mm:ss"),
			new SimpleDateFormat("dd/MM/yyyy H:mm:ss"),

			new SimpleDateFormat("yyyy MM d HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-d HH:mm:ss"),
			new SimpleDateFormat("yyyy.MM.d HH:mm:ss"),
			new SimpleDateFormat("d/MM/yyyy HH:mm:ss"),

			new SimpleDateFormat("yyyy M dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-M-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy.M.dd HH:mm:ss"),
			new SimpleDateFormat("dd/M/yyyy HH:mm:ss")
	};

	private static SimpleDateFormat[] sdf17 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyyMMdd HHmmssSS"),
			
			new SimpleDateFormat("yyyy MM dd HH:m:session_"),
			new SimpleDateFormat("yyyy-MM-dd HH:m:session_"),
			new SimpleDateFormat("yyyy.MM.dd HH:m:session_"),
			new SimpleDateFormat("dd/MM/yyyy HH:m:session_"),

			new SimpleDateFormat("yyyy MM dd H:mm:session_"),
			new SimpleDateFormat("yyyy-MM-dd H:mm:session_"),
			new SimpleDateFormat("yyyy.MM.dd H:mm:session_"),
			new SimpleDateFormat("dd/MM/yyyy H:mm:session_"),

			new SimpleDateFormat("yyyy MM d HH:mm:session_"),
			new SimpleDateFormat("yyyy-MM-d HH:mm:session_"),
			new SimpleDateFormat("yyyy.MM.d HH:mm:session_"),
			new SimpleDateFormat("d/MM/yyyy HH:mm:session_"),

			new SimpleDateFormat("yyyy M dd HH:mm:session_"),
			new SimpleDateFormat("yyyy-M-dd HH:mm:session_"),
			new SimpleDateFormat("yyyy.M.dd HH:mm:session_"),
			new SimpleDateFormat("dd/M/yyyy HH:mm:session_"),
			
			new SimpleDateFormat("yyyy MM dd H:m:ss"),
			new SimpleDateFormat("yyyy-MM-dd H:m:ss"),
			new SimpleDateFormat("yyyy.MM.dd H:m:ss"),
			new SimpleDateFormat("dd/MM/yyyy H:m:ss"),

			new SimpleDateFormat("yyyy MM d HH:m:ss"),
			new SimpleDateFormat("yyyy-MM-d HH:m:ss"),
			new SimpleDateFormat("yyyy.MM.d HH:m:ss"),
			new SimpleDateFormat("d/MM/yyyy HH:m:ss"),

			new SimpleDateFormat("yyyy M dd HH:m:ss"),
			new SimpleDateFormat("yyyy-M-dd HH:m:ss"),
			new SimpleDateFormat("yyyy.M.dd HH:m:ss"),
			new SimpleDateFormat("dd/M/yyyy HH:m:ss"),
			
			new SimpleDateFormat("yyyy MM d H:mm:ss"),
			new SimpleDateFormat("yyyy-MM-d H:mm:ss"),
			new SimpleDateFormat("yyyy.MM.d H:mm:ss"),
			new SimpleDateFormat("dd/M/yyyy H:mm:ss"),

			new SimpleDateFormat("yyyy M dd H:mm:ss"),
			new SimpleDateFormat("yyyy-M-dd H:mm:ss"),
			new SimpleDateFormat("yyyy.M.dd H:mm:ss"),
			new SimpleDateFormat("dd/M/yyyy H:mm:ss"),
			
			new SimpleDateFormat("yyyy M d HH:mm:ss"),
			new SimpleDateFormat("yyyy-M-d HH:mm:ss"),
			new SimpleDateFormat("yyyy.M.d HH:mm:ss"),
			new SimpleDateFormat("d/M/yyyy HH:mm:ss"),
	};

	private static SimpleDateFormat[] sdf16 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy MM d H:m:ss"),
			new SimpleDateFormat("yyyy-MM-d H:m:ss"),
			new SimpleDateFormat("yyyy.MM.d H:m:ss"),
			new SimpleDateFormat("d/MM/yyyy H:m:ss"),

			new SimpleDateFormat("yyyy M dd H:m:ss"),
			new SimpleDateFormat("yyyy-M-dd H:m:ss"),
			new SimpleDateFormat("yyyy.M.dd H:m:ss"),
			new SimpleDateFormat("dd/M/yyyy H:m:ss"),

			new SimpleDateFormat("yyyy MM d H:mm:session_"),
			new SimpleDateFormat("yyyy-MM-d H:mm:session_"),
			new SimpleDateFormat("yyyy.MM.d H:mm:session_"),
			new SimpleDateFormat("d/MM/yyyy H:mm:session_"),

			new SimpleDateFormat("yyyy M dd H:mm:session_"),
			new SimpleDateFormat("yyyy-M-dd H:mm:session_"),
			new SimpleDateFormat("yyyy.M.dd H:mm:session_"),
			new SimpleDateFormat("dd/M/yyyy H:mm:session_"),

			new SimpleDateFormat("yyyy MM d HH:m:session_"),
			new SimpleDateFormat("yyyy-MM-d HH:m:session_"),
			new SimpleDateFormat("yyyy.MM.d HH:m:session_"),
			new SimpleDateFormat("d/MM/yyyy HH:m:session_"),

			new SimpleDateFormat("yyyy M dd HH:m:session_"),
			new SimpleDateFormat("yyyy-M-dd HH:m:session_"),
			new SimpleDateFormat("yyyy.M.dd HH:m:session_"),
			new SimpleDateFormat("dd/M/yyyy HH:m:session_"),

			new SimpleDateFormat("yyyy MM dd H:m:session_"),
			new SimpleDateFormat("yyyy-MM-dd H:m:session_"),
			new SimpleDateFormat("yyyy.MM.dd H:m:session_"),
			new SimpleDateFormat("dd/MM/yyyy H:m:session_"),

			new SimpleDateFormat("yyyy MM dd HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),
			new SimpleDateFormat("yyyy.MM.dd HH:mm"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm")
	};

	private static SimpleDateFormat[] sdf15 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyyMMdd HHmmss"),

			new SimpleDateFormat("yyyy M dd H:m:session_"),
			new SimpleDateFormat("yyyy-M-dd H:m:session_"),
			new SimpleDateFormat("yyyy.M.dd H:m:session_"),
			new SimpleDateFormat("dd/M/yyyy H:m:session_"),

			new SimpleDateFormat("yyyy MM d H:m:session_"),
			new SimpleDateFormat("yyyy-MM-d H:m:session_"),
			new SimpleDateFormat("yyyy.MM.d H:m:session_"),
			new SimpleDateFormat("d/MM/yyyy H:m:session_"),
			
			new SimpleDateFormat("yyyy-M-d H:mm:session_"),
			new SimpleDateFormat("yyyy.M.d H:mm:session_"),
			new SimpleDateFormat("d/M/yyyy H:mm:session_"),

			new SimpleDateFormat("yyyy M d H:m:ss"),
			new SimpleDateFormat("yyyy-M-d H:m:ss"),
			new SimpleDateFormat("yyyy.M.d H:m:ss"),
			new SimpleDateFormat("d/M/yyyy H:m:ss"),

			new SimpleDateFormat("yyyy MM d HH:m:session_"),
			new SimpleDateFormat("yyyy-MM-d HH:m:session_"),
			new SimpleDateFormat("yyyy.MM.d HH:m:session_"),
			new SimpleDateFormat("d/MM/yyyy HH:m:session_"),
			
			new SimpleDateFormat("yyyy M dd HH:mm"),
			new SimpleDateFormat("yyyy-M-dd HH:mm"),
			new SimpleDateFormat("yyyy.M.dd HH:mm"),
			new SimpleDateFormat("dd/M/yyyy HH:mm"),

			new SimpleDateFormat("yyyy MM d HH:mm"),
			new SimpleDateFormat("yyyy-MM-d HH:mm"),
			new SimpleDateFormat("yyyy.MM.d HH:mm"),
			new SimpleDateFormat("d/MM/yyyy HH:mm")
	};

	private static SimpleDateFormat[] sdf14 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy M d H:m:session_"),
			new SimpleDateFormat("yyyy-M-d H:m:session_"),
			new SimpleDateFormat("yyyy.M.d H:m:session_"),
			new SimpleDateFormat("d/M/yyyy H:m:session_"),

			new SimpleDateFormat("yyyy M d HH:mm"),
			new SimpleDateFormat("yyyy-M-d HH:mm"),
			new SimpleDateFormat("yyyy.M.d HH:mm"),
			new SimpleDateFormat("d/M/yyyy HH:mm"),

			new SimpleDateFormat("yyyy M dd H:mm"),
			new SimpleDateFormat("yyyy-M-dd H:mm"),
			new SimpleDateFormat("yyyy.M.dd H:mm"),
			new SimpleDateFormat("dd/M/yyyy H:mm"),

			new SimpleDateFormat("yyyy MM d H:mm"),
			new SimpleDateFormat("yyyy-MM-d H:mm"),
			new SimpleDateFormat("yyyy.MM.d H:mm"),
			new SimpleDateFormat("d/MM/yyyy H:mm"),

			new SimpleDateFormat("yyyy M dd HH:m"),
			new SimpleDateFormat("yyyy-M-dd HH:m"),
			new SimpleDateFormat("yyyy.M.dd HH:m"),
			new SimpleDateFormat("dd/M/yyyy HH:m"),

			new SimpleDateFormat("yyyy MM d HH:m"),
			new SimpleDateFormat("yyyy-MM-d HH:m"),
			new SimpleDateFormat("yyyy.MM.d HH:m"),
			new SimpleDateFormat("d/MM/yyyy HH:m")
	};

	private static SimpleDateFormat[] sdf13 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyyMMdd HHmm"),

			new SimpleDateFormat("yyyy MM dd HH"),
			new SimpleDateFormat("yyyy-MM-dd HH"),
			new SimpleDateFormat("yyyy.MM.dd HH"),
			new SimpleDateFormat("dd/MM/yyyy HH"),

			new SimpleDateFormat("yyyy M d H:mm"),
			new SimpleDateFormat("yyyy-M-d H:mm"),
			new SimpleDateFormat("yyyy.M.d H:mm"),
			new SimpleDateFormat("d/M/yyyy H:mm"),

			new SimpleDateFormat("yyyy M d HH:m"),
			new SimpleDateFormat("yyyy-M-d HH:m"),
			new SimpleDateFormat("yyyy.M.d HH:m"),
			new SimpleDateFormat("d/M/yyyy HH:m"),

			new SimpleDateFormat("yyyy M dd H:m"),
			new SimpleDateFormat("yyyy-M-dd H:m"),
			new SimpleDateFormat("yyyy.M.dd H:m"),
			new SimpleDateFormat("dd/M/yyyy H:m"),

			new SimpleDateFormat("yyyy MM d H:m"),
			new SimpleDateFormat("yyyy-MM-d H:m"),
			new SimpleDateFormat("yyyy.MM.d H:m"),
			new SimpleDateFormat("d/MM/yyyy H:m")
	};

	private static SimpleDateFormat[] sdf12 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy M dd HH"),
			new SimpleDateFormat("yyyy-M-dd HH"),
			new SimpleDateFormat("yyyy.M.dd HH"),
			new SimpleDateFormat("dd/M/yyyy HH"),

			new SimpleDateFormat("yyyy MM d HH"),
			new SimpleDateFormat("yyyy-MM-d HH"),
			new SimpleDateFormat("yyyy.MM.d HH"),
			new SimpleDateFormat("d/MM/yyyy HH"),

			new SimpleDateFormat("yyyy MM dd H"),
			new SimpleDateFormat("yyyy-MM-dd H"),
			new SimpleDateFormat("yyyy.MM.dd H"),
			new SimpleDateFormat("dd/MM/yyyy H"),

			new SimpleDateFormat("yyyy M d H:m"),
			new SimpleDateFormat("yyyy-M-d H:m"),
			new SimpleDateFormat("yyyy.M.d H:m"),
			new SimpleDateFormat("d/M/yyyy H:m")
	};

	private static SimpleDateFormat[] sdf11 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyyMMdd HH"),

			new SimpleDateFormat("yyyy M d HH"),
			new SimpleDateFormat("yyyy-M-d HH"),
			new SimpleDateFormat("yyyy.M.d HH"),
			new SimpleDateFormat("d/M/yyyy HH"),

			new SimpleDateFormat("yyyy M dd H"),
			new SimpleDateFormat("yyyy-M-dd H"),
			new SimpleDateFormat("yyyy.M.dd H"),
			new SimpleDateFormat("dd/M/yyyy H"),

			new SimpleDateFormat("yyyy MM d H"),
			new SimpleDateFormat("yyyy-MM-d H"),
			new SimpleDateFormat("yyyy.MM.d H"),
			new SimpleDateFormat("d/MM/yyyy H")
	};

	private static SimpleDateFormat[] sdf10 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy MM dd"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy.MM.dd"),
			new SimpleDateFormat("yyyy MM dd"),
			new SimpleDateFormat("dd/MM/yyyy"),
			new SimpleDateFormat("dd MM yyyy"),

			new SimpleDateFormat("yyyyMMdd H"),
			new SimpleDateFormat("yyyy M d H"),
			new SimpleDateFormat("yyyy-M-d H"),
			new SimpleDateFormat("yyyy.M.d H"),
			new SimpleDateFormat("d/M/yyyy H"),
			new SimpleDateFormat("d M yyyy H")
	};

	private static SimpleDateFormat[] sdf9 = new SimpleDateFormat[]{

			new SimpleDateFormat("dd M yyyy"),
			new SimpleDateFormat("d MM yyyy"),
			new SimpleDateFormat("yyyy MM d"),
			new SimpleDateFormat("yyyy M dd"),

			new SimpleDateFormat("yyyy-M-dd"),
			new SimpleDateFormat("yyyy-MM-d"),

			new SimpleDateFormat("yyyy.M.dd"),
			new SimpleDateFormat("yyyy.MM.d"),

			new SimpleDateFormat("dd/M/yyyy"),
			new SimpleDateFormat("d/MM/yyyy")
	};

	private static SimpleDateFormat[] sdf8 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyyMMdd"),
			new SimpleDateFormat("yy-MM-dd"),
			new SimpleDateFormat("yy.MM.dd"),
			new SimpleDateFormat("dd/MM/yy")
	};

	private static SimpleDateFormat[] sdf7 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy MM"),
			new SimpleDateFormat("MM yyyy"),

			new SimpleDateFormat("yyyy-MM"),
			new SimpleDateFormat("yy-MM-d"),
			new SimpleDateFormat("yy-M-dd"),

			new SimpleDateFormat("yyyy.MM"),
			new SimpleDateFormat("yy.MM.d"),
			new SimpleDateFormat("yy.M.dd"),

			new SimpleDateFormat("MM/yyyy"),
			new SimpleDateFormat("d/MM/yy"),
			new SimpleDateFormat("dd/M/yy")
	};

	private static SimpleDateFormat[] sdf6 = new SimpleDateFormat[]{

			new SimpleDateFormat("yyyy M"),
			new SimpleDateFormat("M yyyy"),

			new SimpleDateFormat("yyyy-M"),
			new SimpleDateFormat("yy-M-d"),

			new SimpleDateFormat("yyyy.M"),
			new SimpleDateFormat("yy.M.d"),

			new SimpleDateFormat("M/yyyy"),
			new SimpleDateFormat("d/M/yy")
	};

	private static SimpleDateFormat[] sdf5 = new SimpleDateFormat[]{
			
			new SimpleDateFormat("yy-MM"),
			new SimpleDateFormat("yy.MM"),
			new SimpleDateFormat("MM/yy")
	};

	private static SimpleDateFormat[] sdf4 = new SimpleDateFormat[]{
			
			new SimpleDateFormat("yyyy"),
			
			new SimpleDateFormat("yy-M"),
			new SimpleDateFormat("yy.M"),
			new SimpleDateFormat("M/yy")
	};
}
