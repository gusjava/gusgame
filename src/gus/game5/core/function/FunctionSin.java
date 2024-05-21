package gus.game5.core.function;

public class FunctionSin implements Function {
	
	public FunctionSin() {
	}

	public double h(double value) {
		return Math.sin(value);
	}

	public Function getDerived() {
		return new FunctionCos();
	}

	public Boolean isEven() {
		return false;
	}

	public Boolean isOdd() {
		return true;
	}
	
	public boolean isDefined(double value) {
		return true;
	}
}
