package gus.game5.core.function;

public class FunctionUSumN implements Function {
	
	private Function function;
	private double k;
	
	public FunctionUSumN(Function function, double k) {
		this.function = function;
		this.k = k;
	}
	
	public double h(double value) {
		return function.h(value)+k;
	}

	public Function getDerived() {
		return function.getDerived();
	}

	public Boolean isEven() {
		return function.isEven();
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return function.isDefined(value);
	}
}
