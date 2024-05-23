package gus.game5.core.function;

public class FunctionHeaviside implements Function {
	
	public FunctionHeaviside() {
	}

	public double h(double value) {
		return value>=0 ? 1 : 0;
	}

	public Function getDerived() {
		return new FunctionConst(0); //TODO revoir... non dérivable en 0 ?
	}

	public Boolean isEven() {
		return false;
	}

	public Boolean isOdd() {
		return false;
	}
	
	public boolean isDefined(double value) {
		return true;
	}

	public String getExpression(String var) {
		return null;
	}
}
