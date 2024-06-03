package gus.game5.core.exp.exception;

import gus.game5.core.exp.token.TokenSequence;

public class ExpParseException extends ExpException {
	private static final long serialVersionUID = 1L;
	
	public ExpParseException(TokenSequence sequence, String message) {
		super(sequence,message);
	}
	
	public ExpParseException(int start, int end, String message) {
		super(start,end,message);
	}
}
