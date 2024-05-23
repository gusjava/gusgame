package gus.game5.core.function;

public class FunctionUScale implements Function {
	
	private Function function;
	
	private double xFactor;
	private double xOffset;
	private double fFactor;
	private double fOffset;
	
	public FunctionUScale(Function function, double xFactor, double xOffset, double fFactor, double fOffset) {
		this.function = function;
		this.xFactor = xFactor;
		this.xOffset = xOffset;
		this.fFactor = fFactor;
		this.fOffset = fOffset;
	}
	
	public double h(double value) {
		return fFactor*function.h(xFactor*value + xOffset) + fOffset;
	}

	public Function getDerived() {
		//TODO check with xFactor ... (dérivé de la composition de fonctions)
		return new FunctionUMultN(function.getDerived(), fFactor);
	}

	public Boolean isEven() {
		return null;
	}

	public Boolean isOdd() {
		return null;
	}
	
	public boolean isDefined(double value) {
		return function.isDefined(value);
	}

	public String getExpression(String var) {
		String var1 = xFactor+"*"+var+"+"+xOffset;
		return fFactor+"*"+function.getExpression(var1)+"+"+fOffset;
	}
}
