package gus.game5.core.exp.exception;

import gus.game5.core.exp.token.TokenSequence;

public class ExpResolveException extends ExpException {
	private static final long serialVersionUID = 1L;
	
	public ExpResolveException(TokenSequence sequence, String message) {
		super(sequence,message);
	}
	
	public ExpResolveException(int start, int end, String message) {
		super(start,end,message);
	}
	
	public ExpResolveException(TokenSequence sequence, Throwable cause) {
		super(sequence,cause);
	}
	
	public ExpResolveException(int start, int end, Throwable cause) {
		super(start,end,cause);
	}
}
