package gus.game5.core.function;

public class FunctionUSum implements Function {

	private Function[] functions;
	private int number;
	
	public FunctionUSum(Function... functions) {
		this.functions = functions;
		number = functions.length;
	}
	
	public double h(double value) {
		double y = 0;
		for(int i=0;i<number;i++) {
			y += functions[i].h(value);
		}
		return y;
	}
	
	public Function[] getFunctions() {
		return functions;
	}
	
	public int getNumber() {
		return number;
	}

	public Function getDerived() {
		Function[] functions1 = new Function[number];
		for(int i=0;i<number;i++) {
			functions1[i] = functions[i].getDerived();
		}
		return new FunctionUSum(functions1);
	}

	public Boolean isEven() {
		for(int i=0;i<number;i++) {
			Boolean even = functions[i].isEven();
			if(even==null || !even) return null;
		}
		return true;
	}

	public Boolean isOdd() {
		for(int i=0;i<number;i++) {
			Boolean odd = functions[i].isOdd();
			if(odd==null || !odd) return null;
		}
		return true;
	}
	
	public boolean isDefined(double value) {
		for(int i=0;i<number;i++) {
			if(!functions[i].isDefined(value)) return false;
		}
		return true;
	}
}
