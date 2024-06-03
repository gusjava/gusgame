package gus.game5.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.f.F;
import gus.game5.core.features.t.Tdouble;
import gus.game5.core.features.t.Ts;

public class UtilDouble {
	
	/*
	 * CHECK
	 */
	
	public static Double check(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Double) return (Double) obj;
		throw new TechnicalException("Invalid data type: "+obj.getClass().getName());
	}
	
	/*
	 * TO DOUBLE
	 */

	public static Double objToDouble(Object obj) {
		if(obj==null) return null;
		
		if(obj instanceof Double) return (Double) obj;
		if(obj instanceof Number) return ((Number) obj).doubleValue();
		if(obj instanceof String) return stringToDouble((String) obj);
		if(obj instanceof BigDecimal) return ((BigDecimal) obj).doubleValue();
		
		throw new TechnicalException("Failed to convert data into Double (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	public static Double stringToDouble(String s) {
		if(s==null) return null;
		
		s = s.trim();
		if(s.equals("")) return null;
		if(s.equals("null")) return null;
		
		s = s.replace(",",".");
		try{return Double.parseDouble(s);}
		catch(NumberFormatException e) {}
		throw new TechnicalException("Failed to convert string data into double");
	}
	
	public static String doubleToString(Double value) {
		return Double.toString(value);
	}
	
	public static boolean isDouble(String s) {
		try{Double.parseDouble(s);return true;}
		catch(NumberFormatException e) {return false;}
	}
	
	/*
	 * FORMAT DOUBLE
	 */
	
	public static String format2decComma(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}
	
	public static String format2decPoint(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d).replace(",",".");
	}
	
	/*
	 * ROUND DOUBLE
	 */
	
	public static Double round2dec(Double val) {
		if (val == null) return null;
		return Math.round(val*100.0)/100.0;
	}
	
	/*
	 * SUM LENIENT
	 */
	
	public static double sumLenient(Double... array) {
		double total = 0;
		if(array!=null) for(Double n : array) 
			if(n!=null) total += n;
		return total;
	}
	
	public static <U> double sumLenient(U[] array, Tdouble<? super U> t) {
		double total = 0;
		if(array!=null) for (U u : array) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static double sumLenient(List<Double> list) {
		double total = 0;
		if(list!=null) for (Double n : list) 
			if(n!=null) total += n;
		return total;
	}
	
	public static <U> double sumLenient(List<U> list, Tdouble<? super U> t) {
		double total = 0;
		if(list!=null) for (U u : list) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static double sumLenient(Double value0, Double[] array) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for(Double n : array) 
			if(n!=null) total += n;
		return total;
	}
	
	public static <U> double sumLenient(Double value0, U[] array, Tdouble<? super U> t) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for (U u : array) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static double sumLenient(Double value0, List<Double> list) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (Double n : list) 
			if(n!=null) total += n;
		return total;
	}
	
	public static <U> double sumLenient(Double value0, List<U> list, Tdouble<? super U> t) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (U u : list) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	
	/*
	 * SUM LENIENT (WHERE)
	 */
	
	public static double sumLenient(Double[] array, F<Double> f) {
		double total = 0;
		if(array!=null) for (Double n : array) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}
	
	public static <U> double sumLenient(U[] array, Tdouble<? super U> t, F<? super U> f) {
		double total = 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sumLenient(List<Double> list, F<Double> f) {
		double total = 0;
		if(list!=null) for (Double n : list) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}

	public static <U> double sumLenient(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		double total = 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sumLenient(Double value0, Double[] array, F<Double> f) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for (Double n : array) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}
	
	public static <U> double sumLenient(Double value0, U[] array, Tdouble<? super U> t, F<? super U> f) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sumLenient(Double value0, List<Double> list, F<Double> f) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (Double n : list) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}

	public static <U> double sumLenient(Double value0, List<U> list, Tdouble<? super U> t, F<? super U> f) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				Double value = t.t(u);
				if(value!=null) total += value;
			}
		}
		return total;
	}
	
	
	/*
	 * SUM
	 */
	
	public static double sum(Double... array) {
		return UtilBigDecimal.sum(array, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double sum(U[] array, Tdouble<? super U> t) {
		return UtilBigDecimal.sum(array, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double sum(List<Double> list) {
		return UtilBigDecimal.sum(list, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double sum(List<U> list, Tdouble<? super U> t) {
		return UtilBigDecimal.sum(list, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double sum(Double value0, Double[] array) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), array, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double sum(Double value0, U[] array, Tdouble<? super U> t) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), array, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double sum(Double value0, List<Double> list) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), list, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double sum(Double value0, List<U> list, Tdouble<? super U> t) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), list, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	/*
	 * SUM (WHERE)
	 */
	
	public static double sum(Double[] array, F<Double> f) {
		return UtilBigDecimal.sum(array, UtilBigDecimal::bg, f).doubleValue();
	}
	
	public static <U> double sum(U[] array, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.sum(array, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double sum(List<Double> list, F<Double> f) {
		return UtilBigDecimal.sum(list, UtilBigDecimal::bg, f).doubleValue();
	}

	public static <U> double sum(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.sum(list, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double sum(Double value0, Double[] array, F<Double> f) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), array, UtilBigDecimal::bg, f).doubleValue();
	}
	
	public static <U> double sum(Double value0, U[] array, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), array, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double sum(Double value0, List<Double> list, F<Double> f) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), list, UtilBigDecimal::bg, f).doubleValue();
	}

	public static <U> double sum(Double value0, List<U> list, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.sum(UtilBigDecimal.bg(value0), list, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	/*
	 * MULT
	 */
	
	public static double mult(Double... array) {
		return UtilBigDecimal.mult(array, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double mult(U[] array, Tdouble<? super U> t) {
		return UtilBigDecimal.mult(array, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double mult(List<Double> list) {
		return UtilBigDecimal.mult(list, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double mult(List<U> list, Tdouble<? super U> t) {
		return UtilBigDecimal.mult(list, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double mult(Double value1, Double[] array) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), array, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double mult(Double value1, U[] array, Tdouble<? super U> t) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), array, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}
	
	public static double mult(Double value1, List<Double> list) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), list, UtilBigDecimal::bg).doubleValue();
	}
	
	public static <U> double mult(Double value1, List<U> list, Tdouble<? super U> t) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), list, n->UtilBigDecimal.bg(t.t(n))).doubleValue();
	}

	
	/*
	 * MULT (WHERE)
	 */
	
	public static double mult(Double[] array, F<Double> f) {
		return UtilBigDecimal.mult(array, UtilBigDecimal::bg, f).doubleValue();
	}
	
	public static <U> double mult(U[] array, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.mult(array, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double mult(List<Double> list, F<Double> f) {
		return UtilBigDecimal.mult(list, UtilBigDecimal::bg, f).doubleValue();
	}

	public static <U> double mult(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.mult(list, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double mult(Double value1, Double[] array, F<Double> f) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), array, UtilBigDecimal::bg, f).doubleValue();
	}
	
	public static <U> double mult(Double value1, U[] array, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), array, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	public static double mult(Double value1, List<Double> list, F<Double> f) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), list, UtilBigDecimal::bg, f).doubleValue();
	}

	public static <U> double mult(Double value1, List<U> list, Tdouble<? super U> t, F<? super U> f) {
		return UtilBigDecimal.mult(UtilBigDecimal.bg(value1), list, n->UtilBigDecimal.bg(t.t(n)), f).doubleValue();
	}
	
	/*
	 * DIV
	 */

	public static <U> double div(double n1, int n2) {
		return UtilBigDecimal.div(n1, n2).doubleValue();
	}

	public static <U> double div(double n1, long n2) {
		return UtilBigDecimal.div(n1, n2).doubleValue();
	}

	public static <U> double div(double n1, double n2) {
		return UtilBigDecimal.div(n1, n2).doubleValue();
	}
	
	/*
	 * AVG
	 */
	
	public static Double avg(Double... array) {
		return div(sum(array), array.length);
	}
	
	public static Double avg(List<Double> list) {
		return div(sum(list), list.size());
	}
	
	public static <U> Double avg(U[] array, Tdouble<? super U> t) {
		return div(sum(array, t), array.length);
	}
	
	public static <U> Double avg(List<U> list, Tdouble<? super U> t) {
		return div(sum(list, t), list.size());
	}
	
	/*
	 * AVG (WHERE)
	 */
	
	public static Double avg(Double[] array, F<Double> f) {
		List<Double> list1 = UtilList.findAll(array, f);
		return div(sum(list1), list1.size());
	}

	public static <U> Double avg(U[] array, Tdouble<? super U> t, F<? super U> f) {
		List<Double> list1 = UtilList.collect(array, t, f);
		return div(sum(list1), list1.size());
	}
	
	public static Double avg(List<Double> list, F<Double> f) {
		List<Double> list1 = UtilList.findAll(list, f);
		return div(sum(list1), list1.size());
	}

	public static <U> Double avg(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		List<Double> list1 = UtilList.collect(list, t, f);
		return div(sum(list1), list1.size());
	}
	
	/*
	 * POW
	 */
	
	public static Double pow(Double value, int exp) {
		return UtilBigDecimal.pow(UtilBigDecimal.bg(value), exp).doubleValue();
	}
	
	/*
	 * SQRT
	 */
	
	public static Double sqrt(Double value) {
		return UtilBigDecimal.sqrt(UtilBigDecimal.bg(value)).doubleValue();
	}
	
	/*
	 * MIN
	 */
	
	public static Double min(Double... array) {
		if(array==null || array.length==0) return null;

		Double min = null;
		for(Double n : array) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Double min(U[] array, Tdouble<? super U> t) {
		if (array == null || t == null) return null;

		Double min = null;
		for (U u : array) {
			double n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	public static Double min(List<Double> list) {
		if(list==null || list.isEmpty()) return null;

		Double min = null;
		for(Double n : list) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Double min(List<U> list, Tdouble<? super U> t) {
		if (list == null || t == null) return null;

		Double min = null;
		for (U u : list) {
			double n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	/*
	 * MIN (WHERE)
	 */
	
	public static Double min(Double[] array, F<Double> f) {
		if(array==null || array.length==0) return null;

		Double min = null;
		for(Double n : array) if (f==null || f.f(n)) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Double min(U[] array, Tdouble<? super U> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Double min = null;
		for (U u : array) if (f==null || f.f(u)) {
			double n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	public static Double min(List<Double> list, F<Double> f) {
		if(list==null || list.isEmpty()) return null;

		Double min = null;
		for(Double n : list) if (f==null || f.f(n)) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Double min(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Double min = null;
		for (U u : list) if (f==null || f.f(u)) {
			double n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	/*
	 * MAX
	 */
	
	public static Double max(Double... array) {
		if(array==null || array.length==0) return null;

		Double max = null;
		for(Double n : array) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Double max(U[] array, Tdouble<? super U> t) {
		if (array == null || t == null) return null;

		Double max = null;
		for (U u : array) {
			double n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	public static Double max(List<Double> list) {
		if(list==null || list.isEmpty()) return null;

		Double max = null;
		for(Double n : list) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Double max(List<U> list, Tdouble<? super U> t) {
		if (list == null || t == null) return null;

		Double max = null;
		for (U u : list) {
			double n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	/*
	 * MAX (WHERE)
	 */
	
	public static Double max(Double[] array, F<Double> f) {
		if(array==null || array.length==0) return null;

		Double max = null;
		for(Double n : array) if (f==null || f.f(n)) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Double max(U[] array, Tdouble<? super U> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Double max = null;
		for (U u : array) if (f==null || f.f(u)) {
			double n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	public static Double max(List<Double> list, F<Double> f) {
		if(list==null || list.isEmpty()) return null;

		Double max = null;
		for(Double n : list) if (f==null || f.f(n)) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Double max(List<U> list, Tdouble<? super U> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Double max = null;
		for (U u : list) if (f==null || f.f(u)) {
			double n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	/*
	 * T
	 */
	
	public static Ts<Double> tAdd(double d)  {
		return n->sum(n,d);
	}
}
