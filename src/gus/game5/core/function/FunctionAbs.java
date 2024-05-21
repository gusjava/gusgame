package gus.game5.core.function;

public class FunctionAbs implements Function {
	
	public FunctionAbs() {
	}

	public double h(double value) {
		return Math.abs(value);
	}

	public Function getDerived() {
		return new FunctionSign(); //TODO revoir... non dérivable en 0 ?
	}

	public Boolean isEven() {
		return true;
	}

	public Boolean isOdd() {
		return false;
	}
	
	public boolean isDefined(double value) {
		return true;
	}
}
