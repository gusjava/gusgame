package gus.game5.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;

public class UtilDate {

	/*
	 * CHECK
	 */
	
	public static Date check(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Date) return (Date) obj;
		throw new RuntimeException("Invalid data type: "+obj.getClass().getName());
	}
	
	
	public static Date objToDate(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Date) 			return (Date) obj;
		if(obj instanceof LocalDateTime) 	return ldtToDate((LocalDateTime) obj);
		if(obj instanceof Long) 			return new Date((Long) obj);
		if(obj instanceof Calendar) 		return ((Calendar) obj).getTime();
		if(obj instanceof String) 			return UtilDateParse.parseStrict((String) obj);
		
		throw new RuntimeException("Failed to convert data into Date (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	/*
	 * TO DATE
	 */
	
	public static Date toDate(int y, int M) {
		return toDate(y,M,0);
	}
	
	public static Date toDate(int y, int M, int d) {
		return toDate(y,M,d,0,0);
	}
	
	public static Date toDate(int y, int M, int d, int h, int m) {
		return toDate(y,M,d,h,m,0);
	}
	
	public static Date toDate(int y, int M, int d, int h, int m, int s) {
		return toDate(y,M,d,h,m,s,0);
	}
	
	public static Date toDate(int y, int M, int d, int h, int m, int s, int milli) {
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.YEAR, y);
		c.set(Calendar.MONTH, M-1);
		c.set(Calendar.DAY_OF_MONTH,d);
		c.set(Calendar.HOUR_OF_DAY,h);
		c.set(Calendar.MINUTE,m);
		c.set(Calendar.SECOND,s);
		c.set(Calendar.MILLISECOND,milli);
		
		return c.getTime();
	}
	
	
	/*
	 * DISPLAY
	 */
	
	public static String display(Date date) {
		if(date==null) return "";
		return  UtilDateFormat.formatFr3(date);
	}
	
	public static String displayFull(Date date) {
		if(date==null) return "";
		return UtilDateFormat.formatFr6(date);
	}
	
	/*
	 * DISPLAY BUILDER
	 */
	
	public static T<Date,String> displayBuilder(String pattern) {
		return displayBuilder(new SimpleDateFormat(pattern));
	}
	
	public static T<Date,String> displayBuilder(SimpleDateFormat sdf) {
		return date->sdf.format(date);
	}
	
	
	/*
	 * BUILD DATE
	 */
	
	public static Date buildDate(String timeStamp, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(timeStamp);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date buildDate(Calendar calendar) {
		return calendar!=null ? calendar.getTime() : null;
	}
	
	
	/*
	 * YEAR
	 */
	
	public static int year(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int thisYear() {
		return year(new Date());
	}
	
	/*
	 * MONTH (1 ... 12)
	 */
	
	public static int month(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}

	public static int thisMonth() {
		return month(new Date());
	}
	
	/*
	 * DAY OF MONTH (1 ... )
	 */
	
	public static int dayOfM(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int thisDayOfM() {
		return dayOfM(new Date());
	}
	
	/*
	 * YEAR, MONTH, DAY
	 */
	
	public static int[] yearMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new int[] {year, month, day};
	}

	public static int[] thisYearMonthDay() {
		return yearMonthDay(new Date());
	}
	
	/*
	 * DAY OF WEEK (SUNDAY = 1 ... )
	 */
	
	public static int dayOfW(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int thisDayOfW() {
		return dayOfW(new Date());
	}
	
	/*
	 * HOUR
	 */
	
	public static int hour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int thisHour() {
		return hour(new Date());
	}
	
	/*
	 * MINUTE
	 */
	
	public static int minute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	public static int thisMinute() {
		return minute(new Date());
	}
	
	/*
	 * SECOND
	 */
	
	public static int second(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	public static int thisSecond() {
		return second(new Date());
	}
	
	/*
	 * HOUR, MINUTE
	 */
	
	public static int[] hourMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return new int[] {hour, minute};
	}

	public static int[] thisHourMinute() {
		return hourMinute(new Date());
	}
	
	/*
	 * HOUR, MINUTE, SECOND
	 */
	
	public static int[] hourMinuteSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return new int[] {hour, minute, second};
	}

	public static int[] thisHourMinuteSecond() {
		return hourMinuteSecond(new Date());
	}
	
	
	/*
	 * PAST / FUTURE
	 */
	
	public static boolean isPast(Date date) {
		return date!=null && date.before(new Date());
	}
	
	public static boolean isFuture(Date date) {
		return date!=null && date.after(new Date());
	}
	
	/*
	 * SAME DAY, MONTH, YEAR, HOUR, MINUTE, SECOND
	 */
	
	public static boolean isSameSecond(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format7(date1).equals(UtilDateFormat.format7(date2));
	}
	
	public static boolean isSameMinute(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format6(date1).equals(UtilDateFormat.format6(date2));
	}
	
	public static boolean isSameHour(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format4(date1).equals(UtilDateFormat.format4(date2));
	}
	
	public static boolean isSameDay(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format3(date1).equals(UtilDateFormat.format3(date2));
	}
	
	public static boolean isSameMonth(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format2(date1).equals(UtilDateFormat.format2(date2));
	}
	
	public static boolean isSameYear(Date date1, Date date2) {
		if(date1==null || date2==null) return false;
		return UtilDateFormat.format1(date1).equals(UtilDateFormat.format1(date2));
	}
	
	/*
	 * TODAY, THIS MONTH, THIS YEAR
	 */
	
	public static boolean isThisSecond(Date date) {
		return isSameSecond(date, new Date());
	}
	
	public static boolean isThisMinute(Date date) {
		return isSameMinute(date, new Date());
	}
	
	public static boolean isThisHour(Date date) {
		return isSameHour(date, new Date());
	}
	
	public static boolean isToday(Date date) {
		return isSameDay(date, new Date());
	}
	
	public static boolean isThisMonth(Date date) {
		return isSameMonth(date, new Date());
	}
	
	public static boolean isThisYear(Date date) {
		return isSameYear(date, new Date());
	}
	
	
	/*
	 * ADD
	 */
	
	public static Date addMilli(Date date, int nb) {
		if (date == null) return null;
		return ldtToDate(dateToLdt(date).plusNanos(nb*1000));
	}
	
	public static Date addSeconds(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusSeconds(nb));
	}
	
	public static Date addMinutes(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusMinutes(nb));
	}
	
	public static Date addHours(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusHours(nb));
	}
	
	public static Date addDays(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusDays(nb));
	}
	
	public static Date addWeeks(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusDays(nb*7));
	}
	
	public static Date addMonths(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusMonths(nb));
	}
	
	public static Date addYears(Date date, int nb) {
		return ldtToDate(dateToLdt(date).plusYears(nb));
	}
	
	/*
	 * SET
	 */
	
	public static Date setMilli(Date date, int nb) {
		if (date == null) return null;
		return ldtToDate(dateToLdt(date).withNano(nb*1000));
	}
	
	public static Date setSeconds(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withSecond(nb));
	}
	
	public static Date setMinutes(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withMinute(nb));
	}
	
	public static Date setHours(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withHour(nb));
	}
	
	public static Date setDays(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withDayOfMonth(nb));
	}
	
	public static Date setMonths(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withMonth(nb));
	}
	
	public static Date setYears(Date date, int nb) {
		return ldtToDate(dateToLdt(date).withYear(nb));
	}

	
	public static Date setHM(Date date, int hours, int minutes) {
		return ldtToDate(dateToLdt(date).withHour(hours).withMinute(minutes));
	}
	
	public static Date setHMS(Date date, int hours, int minutes, int seconds) {
		return ldtToDate(dateToLdt(date).withHour(hours).withMinute(minutes).withSecond(seconds));
	}
	
	public static Date setHMSmilli(Date date, int hours, int minutes, int seconds, int milli) {
		return ldtToDate(dateToLdt(date).withHour(hours).withMinute(minutes).withSecond(seconds).withNano(milli*1000));
	}
	
	/*
	 * TRUNCATE / WIDEN
	 */
	
	public static Date truncateTime(Date date) {
		if (date == null) return null;
		return setHMS(date,0,0,0);
	}
	
	public static Date widenTime(Date date) {
		if (date == null) return null;
		return setHMS(date,23,59,59);
	}
	
	/*
	 * DURATION
	 */
	
	public static long duration(Date date1, Date date2) {
		return date2.getTime() - date1.getTime();
	}
	
	/*
	 * BEFORE
	 */

	public static boolean isBeforeOrSameDate(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		return truncateTime(d1).before(widenTime(d2));
	}

	public static boolean isBeforeDate(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		return widenTime(d1).before(truncateTime(d2));
	}

	/*
	 * AFTER
	 */

	public static boolean isAfterOrSameDate(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		return widenTime(d1).after(truncateTime(d2));
	}

	public static boolean isAfterDate(Date d1, Date d2) {
		if (d1 == null || d2 == null) return false;
		return truncateTime(d1).after(widenTime(d2));
	}

	/*
	 * PAST
	 */

	public static boolean isPastOrTodayDate(Date d) {
		return isBeforeOrSameDate(d, new Date());
	}

	public static boolean isPastDate(Date d) {
		return isBeforeDate(d, new Date());
	}

	/*
	 * FUTURE
	 */

	public static boolean isFutureOrTodayDate(Date d) {
		return isAfterOrSameDate(d, new Date());
	}

	public static boolean isFutureDate(Date d) {
		return isAfterDate(d, new Date());
	}
	
	
	/*
	 * CONVERT
	 */
	
	public static Date ldtToDate(LocalDateTime ldt) {
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static LocalDateTime dateToLdt(Date  date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/*
	 * MIN
	 */
	
	public static Date min(Date... array) {
		if(array==null || array.length==0) return null;

		Date min = null;
		for(Date date : array) {
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static <U> Date min(U[] array, T<? super U, Date> t) {
		if (array == null || t == null) return null;

		Date min = null;
		for (U u : array) {
			Date date = t.t(u);
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static Date min(List<Date> list) {
		if(list==null || list.isEmpty()) return null;

		Date min = null;
		for(Date date : list) {
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static <U> Date min(List<U> list, T<? super U, Date> t) {
		if (list == null || t == null) return null;

		Date min = null;
		for (U u : list) {
			Date date = t.t(u);
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	/*
	 * MIN (WHERE)
	 */
	
	public static Date min(Date[] array, F<? super Date> f) {
		if(array==null || array.length==0) return null;

		Date min = null;
		for(Date date : array) if (f==null || f.f(date)) {
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static <U> Date min(U[] array, T<? super U, Date> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Date min = null;
		for (U u : array) if (f==null || f.f(u)) {
			Date date = t.t(u);
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static Date min(List<Date> dates, F<? super Date> f) {
		if(dates==null || dates.isEmpty()) return null;

		Date min = null;
		for(Date date : dates) if (f==null || f.f(date)) {
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	public static <U> Date min(List<U> list, T<? super U, Date> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Date min = null;
		for (U u : list) if (f==null || f.f(u)) {
			Date date = t.t(u);
			if (min == null || (date != null && date.before(min))) min = date;
		}
		return min;
	}
	
	/*
	 * MAX
	 */
	
	public static Date max(Date... array) {
		if(array==null || array.length==0) return null;

		Date max = null;
		for(Date date : array) {
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static <U> Date max(U[] array, T<? super U, Date> t) {
		if (array == null || t == null) return null;

		Date max = null;
		for (U u : array) {
			Date date = t.t(u);
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static Date max(List<Date> list) {
		if(list==null || list.isEmpty()) return null;

		Date max = null;
		for(Date date : list) {
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static <U> Date max(List<U> list, T<? super U, Date> t) {
		if (list == null || t == null) return null;

		Date max = null;
		for (U u : list) {
			Date date = t.t(u);
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	/*
	 * MAX (WHERE)
	 */
	
	public static Date max(Date[] array, F<? super Date> f) {
		if(array==null || array.length==0) return null;

		Date max = null;
		for(Date date : array) if (f==null || f.f(date)) {
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static <U> Date max(U[] array, T<? super U, Date> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Date max = null;
		for (U u : array) if (f==null || f.f(u)) {
			Date date = t.t(u);
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static Date max(List<Date> list, F<? super Date> f) {
		if(list==null || list.isEmpty()) return null;

		Date max = null;
		for(Date date : list) if (f==null || f.f(date)) {
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
	
	public static <U> Date max(List<U> list, T<? super U, Date> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Date max = null;
		for (U u : list) if (f==null || f.f(u)) {
			Date date = t.t(u);
			if (max == null || (date != null && date.after(max))) max = date;
		}
		return max;
	}
}
