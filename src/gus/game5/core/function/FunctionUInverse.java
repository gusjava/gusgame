package gus.game5.core.function;

public class FunctionUInverse implements Function {
	
	private Function function;
	
	public FunctionUInverse(Function function) {
		this.function = function;
	}
	
	public double h(double value) {
		return 1/function.h(value);
	}

	public Function getDerived() {
		return null;
	}

	public Boolean isEven() {
		return null;
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return function.isDefined(value) && function.h(value)!=0;
	}
}
