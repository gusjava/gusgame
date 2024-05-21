package gus.game5.core.function;

public class FunctionUOpposite implements Function {
	
	private Function function;
	
	public FunctionUOpposite(Function function) {
		this.function = function;
	}
	
	public double h(double value) {
		return -function.h(value);
	}

	public Function getDerived() {
		return new FunctionUOpposite(function.getDerived());
	}

	public Boolean isEven() {
		return function.isEven();
	}

	public Boolean isOdd() {
		return function.isOdd();
	}
	
	public boolean isDefined(double value) {
		return function.isDefined(value);
	}
}
