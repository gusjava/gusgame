package gus.game5.core.function;

public class FunctionPolynom implements Function {
	

	private double[] a;
	
	public FunctionPolynom(double... a) {
		this.a = a;
	}
	
	public double[] getCoef() {
		return a;
	}
	
	public double coefAt(int i) {
		return a[i];
	}

	public double h(double value) {
		double y = 0;
		for(int i=0;i<a.length;i++) {
			y += a[i] * Math.pow(value, i);
		}
		return y;
	}

	public Function getDerived() {
		double[] a1 = new double[a.length-1];
		for(int i=0;i<a1.length;i++) {
			a1[i] = a[i+1] * (i+1);
		}
		return new FunctionPolynom(a1);
	}

	public boolean isEven() {
		for(int i=0;i<a.length;i++) {
			if(i%2==1 && a[i]!=0) return false;
		}
		return true;
	}

	public boolean isOdd() {
		for(int i=0;i<a.length;i++) {
			if(i%2==0 && a[i]!=0) return false;
		}
		return true;
	}
}
