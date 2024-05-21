package gus.game5.core.function;

public class FunctionTan implements Function {
	
	public FunctionTan() {
	}

	public double h(double value) {
		return Math.tan(value);
	}

	public Function getDerived() {
		return new FunctionUInverse(
				new FunctionUMult(new FunctionCos(), new FunctionCos()));
	}

	public Boolean isEven() {
		return false;
	}

	public Boolean isOdd() {
		return true;
	}
	
	public boolean isDefined(double value) {
		return ((value*2/Math.PI) + 1) % 2!=0;
	}
}
