package gus.game5.core.function;

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
}
