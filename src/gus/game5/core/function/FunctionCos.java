package gus.game5.core.function;

public class FunctionCos implements Function {
	
	public FunctionCos() {
	}

	public double h(double value) {
		return Math.cos(value);
	}

	public Function getDerived() {
		return new FunctionUOpposite(new FunctionSin());
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

	public String getExpression(String var) {
		return "cos("+var+")";
	}
}
