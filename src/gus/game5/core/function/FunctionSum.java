package gus.game5.core.function;

public class FunctionSum implements Function {
	

	private Function[] functions;
	
	public FunctionSum(Function... functions) {
		this.functions = functions;
	}
	
	

	public double h(double value) {
		double y = 0;
		for(int i=0;i<functions.length;i++) {
			y += functions[i].h(value);
		}
		return y;
	}

	public Function getDerived() {
		Function[] functions1 = new Function[functions.length];
		for(int i=0;i<functions.length;i++) {
			functions1[i] = functions[i].getDerived();
		}
		return new FunctionSum(functions1);
	}

	//TODO revoir ...
	public boolean isEven() {
		for(int i=0;i<functions.length;i++) {
			if(!functions[i].isEven()) return false;
		}
		return true;
	}

	//TODO revoir ...
	public boolean isOdd() {
		for(int i=0;i<functions.length;i++) {
			if(!functions[i].isOdd()) return false;
		}
		return true;
	}
}
