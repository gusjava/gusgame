package gus.game5.core.util;

import java.util.List;

import gus.game5.core.features.f.F;
import gus.game5.core.features.t.Tint;

public class UtilInteger {
	
	/*
	 * CHECK
	 */
	
	public static Integer check(Object obj) {
		if(obj==null) return null;
		if(obj instanceof Integer) return (Integer) obj;
		throw new RuntimeException("Invalid data type: "+obj.getClass().getName());
	}
	
	/*
	 * TESTS
	 */
	
	public static boolean between(Integer n, int min, int max) {
		if(n==null) return false;
		int v = n.intValue();
		return v>=min && v<=max;
	}
	
	public static boolean sup(Integer n, int min) {
		if(n==null) return false;
		int v = n.intValue();
		return v>=min;
	}
	
	public static boolean inf(Integer n, int max) {
		if(n==null) return false;
		int v = n.intValue();
		return v<=max;
	}
	
	/*
	 * TO INTEGER
	 */

	public static Integer objToInteger(Object obj) {
		if(obj==null) return null;
		
		if(obj instanceof Integer) return (Integer) obj;
		if(obj instanceof Number) return ((Number) obj).intValue();
		if(obj instanceof String) return stringToInteger((String) obj);
		
		throw new RuntimeException("Failed to convert data into Integer (Unsupported type: "+obj.getClass().getName()+")");
	}
	
	public static Integer stringToInteger(String s) {
		return stringToInteger(s,null);
	}
	
	public static Integer stringToInteger(String s, Integer defaultValue) {
		if(s==null) return defaultValue;
		s = s.trim();
		if(s.equals("")) return defaultValue;
		try{return Integer.parseInt(s);}
		catch(NumberFormatException e) {}
		throw new RuntimeException("Failed to convert string data into integer");
	}
	
	/*
	 * TO STRING
	 */
	
	public static String integerToString(Integer value) {
		return Integer.toString(value);
	}
	
	/*
	 * IS INTEGER
	 */
	
	public static boolean isInteger(String s) {
		try{Integer.parseInt(s);return true;}
		catch(NumberFormatException e){return false;}
	}
	
	/*
	 * DISPLAY
	 */
	
	public static String getDisplay(Integer value) {
		if(value==null) return "";
		return Integer.toString(value);
	}
	
	/*
	 * CONTAINS
	 */
	
	public static boolean contains(int[] array, int val) {
		for(int u : array) if(u==val) return true;
		return false;
	}
	
	/*
	 * SUM
	 */
	
	public static int sum(int... array) {
		int total = 0;
		if(array!=null) for(int n : array) 
			total += n;
		return total;
	}
	
	public static <U> int sum(U[] array, Tint<? super U> t) {
		int total = 0;
		if(array!=null) for(U u : array) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static int sum(List<Integer> list) {
		int total = 0;
		if(list!=null) for(Integer n : list) 
			if(n!=null) total+=n;
		return total;
	}
	
	public static <U> int sum(List<U> list, Tint<? super U> t) {
		int total = 0;
		if(list!=null) for(U u : list) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static int sum(int value0, int... array) {
		int total = value0;
		if(array!=null) for(Integer n : array) 
			if(n!=null) total += n;
		return total;
	}
	
	public static <U> int sum(Integer value0, U[] array, Tint<? super U> t) {
		int total = value0!=null ? value0 : 0;
		if(array!=null) for(U u : array) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	public static int sum(Integer value0, List<Integer> list) {
		int total =value0!=null ? value0 : 0;
		if(list!=null) for(Integer n : list) 
			if(n!=null) total+=n;
		return total;
	}
	
	public static <U> int sum(Integer value0, List<U> list, Tint<? super U> t) {
		int total = value0!=null ? value0 : 0;
		if(list!=null) for(U u : list) if(u!=null) {
			total += t.t(u);
		}
		return total;
	}
	
	/*
	 * SUM (WHERE)
	 */
	
	public static double sum(Integer[] array, F<Integer> f) {
		double total = 0;
		if(array!=null) for (Integer n : array) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}
	
	public static <U> double sum(U[] array, Tint<? super U> t, F<? super U> f) {
		double total = 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sum(List<Integer> list, F<Integer> f) {
		double total = 0;
		if(list!=null) for (Integer n : list) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}

	public static <U> double sum(List<U> list, Tint<? super U> t, F<? super U> f) {
		double total = 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sum(Integer value0, Integer[] array, F<Integer> f) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for (Integer n : array) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}
	
	public static <U> double sum(Integer value0, U[] array, Tint<? super U> t, F<? super U> f) {
		double total = value0!=null ? value0 : 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	public static double sum(Integer value0, List<Integer> list, F<Integer> f) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (Integer n : list) if(n!=null) {
			 if(f == null || f.f(n)) total += n;
		}
		return total;
	}

	public static <U> double sum(Integer value0, List<U> list, Tint<? super U> t, F<? super U> f) {
		double total = value0!=null ? value0 : 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				total += t.t(u);
			}
		}
		return total;
	}
	
	/*
	 * MULT
	 */
	
	public static int mult(Integer... array) {
		int product = 1;
		if(array!=null) for(Integer n : array) 
			if(n!=null) product *= n;
		return product;
	}
	
	public static <U> int mult(U[] array, Tint<? super U> t) {
		int product = 1;
		if(array!=null) for(U u : array) if(u!=null) {
			product *= t.t(u);
		}
		return product;
	}
	
	public static int mult(List<Integer> list) {
		int product = 1;
		if(list!=null) for(Integer n : list) 
			if(n!=null) product *= n;
		return product;
	}
	
	public static <U> int mult(List<U> list, Tint<? super U> t) {
		int product = 1;
		if(list!=null) for(U u : list) if(u!=null) {
			product *= t.t(u);
		}
		return product;
	}
	
	public static int mult(Integer value1, Integer... array) {
		int product = value1!=null ? value1 : 1;
		if(array!=null) for(Integer n : array) 
			if(n!=null) product *= n;
		return product;
	}
	
	public static <U> int mult(Integer value1, U[] array, Tint<? super U> t) {
		int product = value1!=null ? value1 : 1;
		if(array!=null) for(U u : array) if(u!=null) {
			product *= t.t(u);
		}
		return product;
	}
	
	public static int mult(Integer value1, List<Integer> list) {
		int product =value1!=null ? value1 : 1;
		if(list!=null) for(Integer n : list) 
			if(n!=null) product *= n;
		return product;
	}
	
	public static <U> int mult(Integer value1, List<U> list, Tint<? super U> t) {
		int product = value1!=null ? value1 : 1;
		if(list!=null) for(U u : list) if(u!=null) {
			product *= t.t(u);
		}
		return product;
	}
	
	/*
	 * MULT (WHERE)
	 */
	
	public static int mult(Integer[] array, F<Integer> f) {
		int product = 0;
		if(array!=null) for (Integer n : array) if(n!=null) {
			 if(f == null || f.f(n)) product *= n;
		}
		return product;
	}
	
	public static <U> int mult(U[] array, Tint<? super U> t, F<? super U> f) {
		int product = 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				product *= t.t(u);
			}
		}
		return product;
	}
	
	public static int mult(List<Integer> list, F<Integer> f) {
		int product = 0;
		if(list!=null) for (Integer n : list) if(n!=null) {
			 if(f == null || f.f(n)) product *= n;
		}
		return product;
	}

	public static <U> int mult(List<U> list, Tint<? super U> t, F<? super U> f) {
		int product = 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				product *= t.t(u);
			}
		}
		return product;
	}
	
	public static int mult(Integer value0, Integer[] array, F<Integer> f) {
		int product = value0!=null ? value0 : 0;
		if(array!=null) for (Integer n : array) if(n!=null) {
			 if(f == null || f.f(n)) product *= n;
		}
		return product;
	}
	
	public static <U> int mult(Integer value0, U[] array, Tint<? super U> t, F<? super U> f) {
		int product = value0!=null ? value0 : 0;
		if(array!=null) for (U u : array) if(u!=null) {
			if(f==null || f.f(u)) {
				product *= t.t(u);
			}
		}
		return product;
	}
	
	public static int mult(Integer value0, List<Integer> list, F<Integer> f) {
		int product = value0!=null ? value0 : 0;
		if(list!=null) for (Integer n : list) if(n!=null) {
			 if(f == null || f.f(n)) product *= n;
		}
		return product;
	}

	public static <U> int mult(Integer value0, List<U> list, Tint<? super U> t, F<? super U> f) {
		int product = value0!=null ? value0 : 0;
		if(list!=null) for (U u : list) if(u!=null) {
			if(f == null || f.f(u)) {
				product *= t.t(u);
			}
		}
		return product;
	}
	
	/*
	 * MIN
	 */
	
	public static Integer min(Integer... array) {
		if(array==null || array.length==0) return null;

		Integer min = null;
		for(Integer n : array) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Integer min(U[] array, Tint<? super U> t) {
		if (array == null || t == null) return null;

		Integer min = null;
		for (U u : array) {
			int n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	public static Integer min(List<Integer> list) {
		if(list==null || list.isEmpty()) return null;

		Integer min = null;
		for(Integer n : list) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Integer min(List<U> list, Tint<? super U> t) {
		if (list == null || t == null) return null;

		Integer min = null;
		for (U u : list) {
			int n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	/*
	 * MIN (WHERE)
	 */
	
	public static Integer min(Integer[] array, F<Integer> f) {
		if(array==null || array.length==0) return null;

		Integer min = null;
		for(Integer n : array) if (f==null || f.f(n)) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Integer min(U[] array, Tint<? super U> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Integer min = null;
		for (U u : array) if (f==null || f.f(u)) {
			int n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	public static Integer min(List<Integer> list, F<Integer> f) {
		if(list==null || list.isEmpty()) return null;

		Integer min = null;
		for(Integer n : list) if (f==null || f.f(n)) {
			if (min == null || (n != null && n<min)) min = n;
		}
		return min;
	}
	
	public static <U> Integer min(List<U> list, Tint<? super U> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Integer min = null;
		for (U u : list) if (f==null || f.f(u)) {
			int n = t.t(u);
			if (min == null || n<min) min = n;
		}
		return min;
	}
	
	/*
	 * MAX
	 */
	
	public static Integer max(Integer... array) {
		if(array==null || array.length==0) return null;

		Integer max = null;
		for(Integer n : array) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Integer max(U[] array, Tint<? super U> t) {
		if (array == null || t == null) return null;

		Integer max = null;
		for (U u : array) {
			int n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	public static Integer max(List<Integer> list) {
		if(list==null || list.isEmpty()) return null;

		Integer max = null;
		for(Integer n : list) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Integer max(List<U> list, Tint<? super U> t) {
		if (list == null || t == null) return null;

		Integer max = null;
		for (U u : list) {
			int n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	/*
	 * MAX (WHERE)
	 */
	
	public static Integer max(Integer[] array, F<Integer> f) {
		if(array==null || array.length==0) return null;

		Integer max = null;
		for(Integer n : array) if (f==null || f.f(n)) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Integer max(U[] array, Tint<? super U> t, F<? super U> f) {
		if (array == null || t == null) return null;

		Integer max = null;
		for (U u : array) if (f==null || f.f(u)) {
			int n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
	
	public static Integer max(List<Integer> list, F<Integer> f) {
		if(list==null || list.isEmpty()) return null;

		Integer max = null;
		for(Integer n : list) if (f==null || f.f(n)) {
			if (max == null || (n != null && n>max)) max = n;
		}
		return max;
	}
	
	public static <U> Integer max(List<U> list, Tint<? super U> t, F<? super U> f) {
		if (list == null || t == null) return null;

		Integer max = null;
		for (U u : list) if (f==null || f.f(u)) {
			int n = t.t(u);
			if (max == null || n>max) max = n;
		}
		return max;
	}
}
