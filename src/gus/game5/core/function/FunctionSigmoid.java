package gus.game5.core.function;

public class FunctionSigmoid implements Function {
	
	public FunctionSigmoid() {
	}

	public double h(double value) {
		return 1 / (1 + Math.exp(-value));
	}

	public Function getDerived() {
		//TODO
		return null;
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

	/*
	 * EXPRESSION
	 */
	
	public String getExpression(String var) {
		return "1 / (1 + e(-"+var+"))";
	}
}
