package gus.game5.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import gus.game5.core.features.t.T;

public class UtilDateFormat {
	
	/*
	 * TECHNICAL FORMATS
	 */
	
	public final static String yyyy = "yyyy";
	public final static String yyyyMM = "yyyyMM";
	public final static String yyyyMMdd = "yyyyMMdd";
	public final static String yyyyMMdd_HH = "yyyyMMdd_HH";
	public final static String yyyyMMdd_HHmm = "yyyyMMdd_HHmm";
	public final static String yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";
	public final static String yyyyMMdd_HHmmss_SSS = "yyyyMMdd_HHmmss_SSS";
	
	/*
	 * FRENCH FORMATS
	 */
	
	public final static String MM_yyyy = "MM/yyyy";
	public final static String dd_MM_yyyy = "dd/MM/yyyy";
	public final static String dd_MM_yyyy_HH = "dd/MM/yyyy HH";
	public final static String dd_MM_yyyy_HH_mm = "dd/MM/yyyy HH:mm";
	public final static String dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";
	public final static String dd_MM_yyyy_HH_mm_ss_SSS = "dd/MM/yyyy HH:mm:ss SSS";
	
	/*
	 * ENGLISH FORMATS
	 */
	
	public final static String yyyy_MM = "yyyy-MM";
	public final static String yyyy_MM_dd = "yyyy-MM-dd";
	public final static String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
	public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss SSS";
	
	/*
	 * HOUR FORMATS
	 */
	
	public final static String HH = "HH";
	public final static String HH_mm = "HH:mm";
	public final static String HH_mm_ss = "HH:mm:ss";
	public final static String HH_mm_ss_SSS = "HH:mm:ss SSS";
	
	
	
	
	
	/*
	 * FORMAT
	 */
	
	public static String format(Date date, String pattern) {
		if(date==null || pattern==null) return "";
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String format(Date date, SimpleDateFormat sdf) {
		if(date==null || sdf==null) return "";
		return sdf.format(date);
	}
	
	/*
	 * BUILD DISPLAYER
	 */
	
	public static T<Date,String> buildDisplayer(String pattern) {
		return date->format(date, pattern);
	}
	
	public static T<Date,String> buildDisplayer(SimpleDateFormat sdf) {
		return date->format(date, sdf);
	}
	
	
	/*
	 * FORMAT : TECHNICAL
	 */
	
	public static String format1(Date date) {
		return format(date, yyyy);
	}
	
	public static String format2(Date date) {
		return format(date, yyyyMM);
	}
	
	public static String format3(Date date) {
		return format(date, yyyyMMdd);
	}
	
	public static String format4(Date date) {
		return format(date, yyyyMMdd_HH);
	}

	public static String format5(Date date) {
		return format(date, yyyyMMdd_HHmm);
	}

	public static String format6(Date date) {
		return format(date, yyyyMMdd_HHmmss);
	}
	
	public static String format7(Date date) {
		return format(date, yyyyMMdd_HHmmss_SSS);
	}
	
	
	/*
	 * FORMAT : FRENCH
	 */
	
	public static String formatFr2(Date date) {
		return format(date, MM_yyyy);
	}
	
	public static String formatFr3(Date date) {
		return format(date, dd_MM_yyyy);
	}
	
	public static String formatFr4(Date date) {
		return format(date, dd_MM_yyyy_HH);
	}
	
	public static String formatFr5(Date date) {
		return format(date, dd_MM_yyyy_HH_mm);
	}

	public static String formatFr6(Date date) {
		return format(date, dd_MM_yyyy_HH_mm_ss);
	}
	
	public static String formatFr7(Date date) {
		return format(date, dd_MM_yyyy_HH_mm_ss_SSS);
	}
	
	
	/*
	 * FORMAT : ENGLISH
	 */
	
	public static String formatEn2(Date date) {
		return format(date, yyyy_MM);
	}
	
	public static String formatEn3(Date date) {
		return format(date, yyyy_MM_dd);
	}
	
	public static String formatEn4(Date date) {
		return format(date, yyyy_MM_dd_HH);
	}

	public static String formatEn5(Date date) {
		return format(date, yyyy_MM_dd_HH_mm);
	}

	public static String formatEn6(Date date) {
		return format(date, yyyy_MM_dd_HH_mm_ss);
	}
	
	public static String formatEn7(Date date) {
		return format(date, yyyy_MM_dd_HH_mm_ss_SSS);
	}
	
	
	
	/*
	 * FORMAT : HOUR
	 */
	
	public static String formatH1(Date date) {
		return format(date, HH);
	}
	
	public static String formatH2(Date date) {
		return format(date, HH_mm);
	}
	
	public static String formatH3(Date date) {
		return format(date, HH_mm_ss);
	}
	
	public static String formatH4(Date date) {
		return format(date, HH_mm_ss_SSS);
	}
	
	
	
	/*
	 * FORMAT NOW
	 */
	
	public static String formatNow(String pattern) {
		return format(new Date(), pattern);
	}
	
	public static String formatNow1() { return format1(new Date()); }
	public static String formatNow2() { return format2(new Date()); }
	public static String formatNow3() { return format3(new Date()); }
	public static String formatNow4() { return format4(new Date()); }
	public static String formatNow5() { return format5(new Date()); }
	public static String formatNow6() { return format6(new Date()); }
	public static String formatNow7() { return format7(new Date()); }
	
	public static String formatNowFr2() { return formatFr2(new Date()); }
	public static String formatNowFr3() { return formatFr3(new Date()); }
	public static String formatNowFr4() { return formatFr4(new Date()); }
	public static String formatNowFr5() { return formatFr5(new Date()); }
	public static String formatNowFr6() { return formatFr6(new Date()); }
	public static String formatNowFr7() { return formatFr7(new Date()); }
	
	public static String formatNowEn2() { return formatEn2(new Date()); }
	public static String formatNowEn3() { return formatEn3(new Date()); }
	public static String formatNowEn4() { return formatEn4(new Date()); }
	public static String formatNowEn5() { return formatEn5(new Date()); }
	public static String formatNowEn6() { return formatEn6(new Date()); }
	public static String formatNowEn7() { return formatEn7(new Date()); }
	
	public static String formatNowH1() { return formatH1(new Date()); }
	public static String formatNowH2() { return formatH2(new Date()); }
	public static String formatNowH3() { return formatH3(new Date()); }
	public static String formatNowH4() { return formatH4(new Date()); }

}
