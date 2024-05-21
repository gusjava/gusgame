package gus.game5.core.function;

public class FunctionSign implements Function {
	
	public FunctionSign() {
	}

	public double h(double value) {
		return value>0 ? 1 : value<0 ? -1 : 0;
	}

	public Function getDerived() {
		return new FunctionConst(0); //TODO revoir... non dérivable en 0 ?
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
