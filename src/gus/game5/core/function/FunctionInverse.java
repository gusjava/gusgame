package gus.game5.core.function;

public class FunctionInverse implements Function {
	
	public FunctionInverse() {
	}
	
	public double h(double value) {
		return 1/value;
	}

	public Function getDerived() {
		//TODO
		return null;
	}

	public Boolean isEven() {
		return false;
	}

	public Boolean isOdd() {
		return true;
	}

	public String getExpression(String var) {
		return "1/"+var;
	}
	
	public boolean isDefined(double value) {
		return value!=0;
	}
}
