package gus.game5.core.exp.exception;

import gus.game5.core.exp.token.TokenSequence;

public class ExpException extends Exception implements TokenSequence {
	private static final long serialVersionUID = 1L;

	public ExpException(TokenSequence sequence, String message) {
		super(message);
		handleSequence(sequence);
	}
	
	public ExpException(int start, int end, String message) {
		super(message);
		this.start = start;
		this.end = end;
	}
	
	
	public ExpException(TokenSequence sequence, Throwable cause) {
		super(cause);
		handleSequence(sequence);
	}
	
	public ExpException(int start, int end, Throwable cause) {
		super(cause);
		this.start = start;
		this.end = end;
	}
	
	
	
	private void handleSequence(TokenSequence sequence) {
		if(sequence!=null) {
			start = sequence.getStart();
			end = sequence.getEnd();
		}
		else {
			start = 0;
			end = 0;
		}
	}
	
	/*
	 * START
	 */
	
	private int start;
	
	public int getStart() {
		return start;
	}
	
	/*
	 * END
	 */
	
	private int end;
	
	public int getEnd() {
		return end;
	}
}
