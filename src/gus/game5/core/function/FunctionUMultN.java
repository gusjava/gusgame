package gus.game5.core.function;

public class FunctionUMultN implements Function {
	
	private Function function;
	private double k;
	
	public FunctionUMultN(Function function, double k) {
		this.function = function;
		this.k = k;
	}
	
	public double h(double value) {
		return function.h(value)*k;
	}

	public Function getDerived() {
		return new FunctionUMultN(function.getDerived(), k);
	}

	public Boolean isEven() {
		return function.isEven();
	}

	public String getExpression(String var) {
		return k+"*("+function.getExpression(var)+")";
	}

	public Boolean isOdd() {
		return function.isOdd();
	}
	
	public boolean isDefined(double value) {
		return function.isDefined(value);
	}
}
