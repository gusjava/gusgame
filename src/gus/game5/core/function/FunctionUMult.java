package gus.game5.core.function;

public class FunctionUMult implements Function {
	
	private Function f1;
	private Function f2;
	
	public FunctionUMult(Function f1, Function f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public double h(double value) {
		return f1.h(value)*f2.h(value);
	}

	public Function getDerived() {
		return new FunctionUSum(
				new FunctionUMult(f1, f2.getDerived()), 
				new FunctionUMult(f2, f1.getDerived()));
	}

	public Boolean isEven() {
		if(f1.isEven() && f2.isEven()) return true;
		if(f1.isOdd() && f2.isOdd()) return true;
		return null;
	}

	public Boolean isOdd() {
		if(f1.isEven() && f2.isOdd()) return true;
		if(f1.isOdd() && f2.isEven()) return true;
		return null;
	}
	
	public boolean isDefined(double value) {
		return f1.isDefined(value) && f2.isDefined(value);
	}

	public String getExpression(String var) {
		return "("+f1.getExpression(var)+")*("+f2.getExpression(var)+")";
	}
}
