package gus.game5.core.function;

public class FunctionUCombine implements Function {
	
	private Function f1;
	private Function f2;
	
	public FunctionUCombine(Function f1, Function f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
	
	public double h(double value) {
		return f1.h(f2.h(value));
	}
	
	public Function getDerived() {
		return new FunctionUMult(
				f2.getDerived(), 
				new FunctionUCombine(f1.getDerived(), f2));
	}

	public Boolean isEven() {
		return null;
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return f2.isDefined(value) && f1.isDefined(f2.h(value));
	}
}
