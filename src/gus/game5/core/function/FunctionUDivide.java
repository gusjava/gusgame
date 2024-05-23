package gus.game5.core.function;

public class FunctionUDivide implements Function {
	
	private Function f1;
	private Function f2;
	
	public FunctionUDivide(Function f1, Function f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public double h(double value) {
		return f1.h(value)/f2.h(value);
	}

	public Function getDerived() {
		return null;
	}

	public Boolean isEven() {
		return null;
	}

	public String getExpression(String var) {
		return "("+f1.getExpression(var)+")/("+f2.getExpression(var)+")";
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return f1.isDefined(value) && f2.isDefined(value) && f2.h(value)!=0;
	}
}
