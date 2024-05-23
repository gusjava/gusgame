package gus.game5.core.function;

public class FunctionPow implements Function {
	
	private double n;
	
	public FunctionPow(double n) {
		this.n = n;
	}

	public double h(double value) {
		return Math.pow(value, n);
	}

	public Function getDerived() {
		return new FunctionUMultN(new FunctionPow(n-1), n);
	}

	public Boolean isEven() {
		return null;
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return true;
	}

	/*
	 * EXPRESSION
	 */
	
	public String getExpression(String var) {
		return var+"^"+n;
	}
}
