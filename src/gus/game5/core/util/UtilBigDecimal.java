package gus.game5.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import gus.game5.core.exception.TechnicalException;
import gus.game5.core.features.f.F;
import gus.game5.core.features.t.T;

public class UtilBigDecimal {
	
	public static MathContext CONTEXT = new MathContext(8);
	
	/*
	 * TO BIG DECIMAL
	 */
	
	public static BigDecimal objToBigDecimal(Object obj) {
		if(obj==null) return null;
		
		if(obj instanceof Double) return new BigDecimal((Double) obj);
		if(obj instanceof Number) return new BigDecimal(((Number) obj).doubleValue());
		if(obj instanceof String) return stringToBigDecimal((String) obj);
		
		throw new TechnicalException("Failed to convert data into double (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	public static BigDecimal stringToBigDecimal(String s) {
		if(s==null) return null;
		
		s = s.trim();
		if(s.equals("")) return null;
		if(s.equals("null")) return null;

		s = s.replace(",",".");
		try{return new BigDecimal(Double.parseDouble(s));}
		catch(NumberFormatException e) {}
		throw new TechnicalException("Failed to convert string data into double");
	}
	
	
	/*
	 * BG
	 */
	
	public static BigDecimal bg(double v) {
		return new BigDecimal(v);
	}
	
	public static BigDecimal bg(int v) {
		return new BigDecimal(v);
	}
	
	public static BigDecimal bg(long v) {
		return new BigDecimal(v);
	}
	
	public static BigDecimal bg(String s) {
		if(UtilEmpty.isEmpty(s)) return null;
		try {return new BigDecimal(s.replace(",","."));}
		catch(NumberFormatException e) {return null;}
	}
	
	public static BigDecimal bgLenient(String s) {
		if(UtilEmpty.isEmpty(s)) return bg(0);
		if(s.equals("-")) return bg(0);
		
		try {return new BigDecimal(s.replace(",","."));}
		catch(NumberFormatException e) {return null;}
	}
	
	/*
	 * TO STRING
	 */
	
	public static String toString(BigDecimal b) {
		if(b==null) return "";
		return b.toString().replace(".",",");
	}
	
	/*
	 * POSITIVE
	 */
	
	public static boolean positive(BigDecimal b) {
		return b!=null && b.doubleValue()>0;
	}
	
	public static boolean positiveOrZero(BigDecimal b) {
		return b!=null && b.doubleValue()>=0;
	}
	
	/*
	 * SUM 2
	 */
	
	public static BigDecimal sum(BigDecimal n1, BigDecimal n2) {
		if(n1==null) return n2;
		if(n2==null) return n1;
		return n1.add(n2, CONTEXT);
	}
	
	public static BigDecimal sum(BigDecimal n1, double n2) {
		return sum(n1, bg(n2));
	}
	
	public static BigDecimal sum(BigDecimal n1, int n2) {
		return sum(n1, bg(n2));
	}
	
	public static BigDecimal sum(BigDecimal n1, long n2) {
		return sum(n1, bg(n2));
	}
	
	public static BigDecimal sum(double n1, double n2) {
		return sum(bg(n1), bg(n2));
	}
	
	public static BigDecimal sum(int n1, int n2) {
		return sum(bg(n1), bg(n2));
	}
	
	public static BigDecimal sum(long n1, long n2) {
		return sum(bg(n1), bg(n2));
	}
	
	/*
	 * SUM
	 */
	
	public static BigDecimal sum(BigDecimal... array) {
		BigDecimal total = bg(0);
		if(array!=null) for(BigDecimal n : array) 
			total = sum(total, n);
		return total;
	}

	public static <U> BigDecimal sum(U[] array, T<? super U, BigDecimal> t) {
		BigDecimal total = bg(0);
		if(array!=null) for (U u : array) if(u!=null)
			total = sum(total, t.t(u));
		return total;
	}
	
	public static BigDecimal sum(List<BigDecimal> list) {
		BigDecimal total = bg(0);
		if(list!=null) for(BigDecimal n : list) 
			total = sum(total, n);
		return total;
	}
	
	public static <U> BigDecimal sum(List<U> list, T<? super U, BigDecimal> t) {
		BigDecimal total = bg(0);
		if(list!=null) for(U u : list) if(u!=null) 
			total = sum(total, t.t(u));
		return total;
	}
	
	public static BigDecimal sum(BigDecimal value0, BigDecimal[] array) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(array!=null) for(BigDecimal n : array) 
			total = sum(total, n);
		return total;
	}

	public static <U> BigDecimal sum(BigDecimal value0, U[] array, T<? super U, BigDecimal> t) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(array!=null) for (U u : array) if(u!=null)
			total = sum(total, t.t(u));
		return total;
	}
	
	public static BigDecimal sum(BigDecimal value0, List<BigDecimal> list) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(list!=null) for(BigDecimal n : list) 
			total = sum(total, n);
		return total;
	}
	
	public static <U> BigDecimal sum(BigDecimal value0, List<U> list, T<? super U, BigDecimal> t) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(list!=null) for(U u : list) if(u!=null) 
			total = sum(total, t.t(u));
		return total;
	}
	
	
	/*
	 * SUM (WHERE)
	 */
	
	public static BigDecimal sum(BigDecimal[] array, F<BigDecimal> f) {
		BigDecimal total = bg(0);
		if(array!=null) for(BigDecimal n : array) 
			if(f == null || f.f(n)) total = sum(total, n);
		return total;
	}

	public static <U> BigDecimal sum(U[] array, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal total = bg(0);
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) total = sum(total, t.t(u));
		}
		return total;
	}
	
	public static BigDecimal sum(List<BigDecimal> list, F<BigDecimal> f) {
		BigDecimal total = bg(0);
		if(list!=null) for(BigDecimal n : list) 
			if(f == null || f.f(n)) total = sum(total, n);
		return total;
	}
	
	public static <U> BigDecimal sum(List<U> list, T<U,BigDecimal> t, F<? super U> f) {
		BigDecimal total = bg(0);
		if(list!=null) for(U u : list) if(u!=null) {
			if(f == null || f.f(u)) total = sum(total, t.t(u));
		}
		return total;
	}
	
	public static BigDecimal sum(BigDecimal value0, BigDecimal[] array, F<BigDecimal> f) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(array!=null) for(BigDecimal n : array) 
			if(f == null || f.f(n)) total = sum(total, n);
		return total;
	}

	public static <U> BigDecimal sum(BigDecimal value0, U[] array, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) total = sum(total, t.t(u));
		}
		return total;
	}
	
	public static BigDecimal sum(BigDecimal value0, List<BigDecimal> list, F<BigDecimal> f) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(list!=null) for(BigDecimal n : list) 
			if(f == null || f.f(n)) total = sum(total, n);
		return total;
	}
	
	public static <U> BigDecimal sum(BigDecimal value0, List<U> list, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal total = value0!=null ? value0 : bg(0);
		if(list!=null) for(U u : list) if(u!=null) {
			if(f == null || f.f(u)) total = sum(total, t.t(u));
		}
		return total;
	}
	
	/*
	 * MULT 2
	 */
	
	public static BigDecimal mult(BigDecimal n1, BigDecimal n2) {
		if(n1==null) return n2;
		if(n2==null) return n1;
		return n1.multiply(n2, CONTEXT);
	}
	
	public static BigDecimal mult(BigDecimal n1, double n2) {
		return mult(n1, bg(n2));
	}
	
	public static BigDecimal mult(BigDecimal n1, int n2) {
		return mult(n1, bg(n2));
	}
	
	public static BigDecimal mult(BigDecimal n1, long n2) {
		return mult(n1, bg(n2));
	}
	
	public static BigDecimal mult(double n1, double n2) {
		return mult(bg(n1), bg(n2));
	}
	
	public static BigDecimal mult(int n1, int n2) {
		return mult(bg(n1), bg(n2));
	}
	
	public static BigDecimal mult(long n1, long n2) {
		return mult(bg(n1), bg(n2));
	}
	
	/*
	 * MULT
	 */
	
	public static BigDecimal mult(BigDecimal... array) {
		BigDecimal product = bg(1);
		if(array!=null) for(BigDecimal n : array) 
			product = mult(product, n);
		return product;
	}

	public static <U> BigDecimal mult(U[] array, T<? super U, BigDecimal> t) {
		BigDecimal product = bg(1);
		if(array!=null) for (U u : array) if(u!=null)
			product = mult(product, t.t(u));
		return product;
	}
	
	public static BigDecimal mult(List<BigDecimal> list) {
		BigDecimal product = bg(1);
		if(list!=null) for(BigDecimal n : list) 
			product = mult(product, n);
		return product;
	}
	
	public static <U> BigDecimal mult(List<U> list, T<? super U, BigDecimal> t) {
		BigDecimal product = bg(1);
		if(list!=null) for(U u : list) if(u!=null) 
			product = mult(product, t.t(u));
		return product;
	}
	
	public static BigDecimal mult(BigDecimal value1, BigDecimal[] array) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(array!=null) for(BigDecimal n : array) 
			product = mult(product, n);
		return product;
	}

	public static <U> BigDecimal mult(BigDecimal value1, U[] array, T<? super U, BigDecimal> t) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(array!=null) for (U u : array) if(u!=null)
			product = mult(product, t.t(u));
		return product;
	}
	
	public static BigDecimal mult(BigDecimal value1, List<BigDecimal> list) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(list!=null) for(BigDecimal n : list) 
			product = mult(product, n);
		return product;
	}
	
	public static <U> BigDecimal mult(BigDecimal value1, List<U> list, T<? super U, BigDecimal> t) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(list!=null) for(U u : list) if(u!=null) 
			product = mult(product, t.t(u));
		return product;
	}
	
	
	/*
	 * MULT (WHERE)
	 */
	
	public static BigDecimal mult(BigDecimal[] array, F<BigDecimal> f) {
		BigDecimal product = bg(1);
		if(array!=null) for(BigDecimal n : array) 
			if(f == null || f.f(n)) product = mult(product, n);
		return product;
	}

	public static <U> BigDecimal mult(U[] array, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal product = bg(1);
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) product = mult(product, t.t(u));
		}
		return product;
	}
	
	public static BigDecimal mult(List<BigDecimal> list, F<BigDecimal> f) {
		BigDecimal product = bg(1);
		if(list!=null) for(BigDecimal n : list) 
			if(f == null || f.f(n)) product = mult(product, n);
		return product;
	}
	
	public static <U> BigDecimal mult(List<U> list, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal product = bg(1);
		if(list!=null) for(U u : list)  if(u!=null) {
			if(f == null || f.f(u)) product = mult(product, t.t(u));
		}
		return product;
	}
	
	public static BigDecimal mult(BigDecimal value1, BigDecimal[] array, F<BigDecimal> f) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(array!=null) for(BigDecimal n : array) 
			if(f == null || f.f(n)) product = mult(product, n);
		return product;
	}

	public static <U> BigDecimal mult(BigDecimal value1, U[] array, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) product = mult(product, t.t(u));
		}
		return product;
	}
	
	public static BigDecimal mult(BigDecimal value1, List<BigDecimal> list, F<BigDecimal> f) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(list!=null) for(BigDecimal n : list) 
			if(f == null || f.f(n)) product = mult(product, n);
		return product;
	}
	
	public static <U> BigDecimal mult(BigDecimal value1, List<U> list, T<? super U, BigDecimal> t, F<? super U> f) {
		BigDecimal product = value1!=null ? value1 : bg(1);
		if(list!=null) for(U u : list) if(u!=null) {
			if(f == null || f.f(u)) product = mult(product, t.t(u));
		}
		return product;
	}
	
	/*
	 * DIV
	 */
	
	public static BigDecimal div(BigDecimal n1, BigDecimal n2) {
		return n1.divide(n2, CONTEXT);
	}
	
	public static BigDecimal div(BigDecimal n1, double n2) {
		return div(n1, bg(n2));
	}
	
	public static BigDecimal div(BigDecimal n1, int n2) {
		return div(n1, bg(n2));
	}
	
	public static BigDecimal div(BigDecimal n1, long n2) {
		return div(n1, bg(n2));
	}
	
	public static BigDecimal div(double n1, double n2) {
		return div(bg(n1), bg(n2));
	}
	
	public static BigDecimal div(int n1, int n2) {
		return div(bg(n1), bg(n2));
	}
	
	public static BigDecimal div(long n1, long n2) {
		return div(bg(n1), bg(n2));
	}
	
	/*
	 * POW
	 */
	
	public static BigDecimal pow(BigDecimal n, int exp) {
		BigDecimal product = bg(1);
		for(int i=0;i<exp;i++) product = mult(product, n);
		return product;
	}
	
	/*
	 * SQRT
	 */
	
	public static BigDecimal sqrt(BigDecimal n) {
		return n.sqrt(CONTEXT);
	}
	
	/*
	 * PERCENT
	 */
	
	public static BigDecimal toPercent(BigDecimal n) {
		return n==null ? bg(0) : mult(n, 100);
	}
	
	/*
	 * COMPARE
	 */
	
	public static boolean compareDouble(BigDecimal n1, BigDecimal n2) {
		return n1.doubleValue() == n2.doubleValue();
	}
}
