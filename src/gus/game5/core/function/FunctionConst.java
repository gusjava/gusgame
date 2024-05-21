package gus.game5.core.function;

public class FunctionConst implements Function {
	
	private double val;
	
	public FunctionConst(double val) {
		this.val = val;
	}
	
	public double h(double value) {
		return val;
	}

	public Function getDerived() {
		return new FunctionConst(0);
	}

	public Boolean isEven() {
		return true;
	}

	public Boolean isOdd() {
		return val==0;
	}
	
	public boolean isDefined(double value) {
		return true;
	}
}
