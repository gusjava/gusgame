package gus.game5.core.function;

import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilPolynom;

public class FunctionPolynom implements Function {
	

	private double[] coef;
	
	public FunctionPolynom(double... coef) {
		this.coef = coef;
	}
	
	public double[] getCoef() {
		return coef;
	}
	
	public double coefAt(int i) {
		return coef[i];
	}
	
	public double getDegree() {
		int n = coef.length;
		for(int i=0;i<n;i++) {
			if(coef[n-1-i]!=0) return n-i;
		}
		return 0;
	}

	public double h(double value) {
		double y = 0;
		for(int i=0;i<coef.length;i++) {
			y += coef[i] * Math.pow(value, i);
		}
		return y;
	}

	public Function getDerived() {
		double[] a1 = new double[coef.length-1];
		for(int i=0;i<a1.length;i++) {
			a1[i] = coef[i+1] * (i+1);
		}
		return new FunctionPolynom(a1);
	}

	public Boolean isEven() {
		for(int i=0;i<coef.length;i++) {
			if(i%2==1 && coef[i]!=0) return false;
		}
		return true;
	}

	public Boolean isOdd() {
		for(int i=0;i<coef.length;i++) {
			if(i%2==0 && coef[i]!=0) return false;
		}
		return true;
	}
	
	public boolean isDefined(double value) {
		return true;
	}
	
	public double[] findRoots() {
		return UtilPolynom.findRoots(coef);
	}
	
	/*
	 * ADD MULTIPLY
	 */
	
	public FunctionPolynom add(FunctionPolynom... ff) {
		List<double[]> coefList = UtilList.asList(coef, UtilList.collect(ff, FunctionPolynom::getCoef));
		double[] newCoef = UtilPolynom.sum(UtilList.asList(coef, coefList));
		return new FunctionPolynom(newCoef);
	}
	
	public FunctionPolynom multiply(double factor) {
		double[] newCoef = UtilPolynom.multiply(coef, factor);
		return new FunctionPolynom(newCoef);
	}

	/*
	 * EXPRESSION
	 */
	
	public String getExpression(String var) {
		StringBuffer b = new StringBuffer();
		for(int i=0;i<coef.length;i++) {
			if(coef[i]!=0) {
				if(b.length()>0) b.append("+");
				b.append(coef[i]+"*"+var+"^"+i);
			}
		}
		return b.toString();
	}
}
