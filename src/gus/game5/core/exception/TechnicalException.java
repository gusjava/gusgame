package gus.game5.core.exception;

public class TechnicalException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	
	public TechnicalException(String message) {
		super(message);
	}
	
	public TechnicalException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public TechnicalException(Throwable throwable) {
		super(throwable);
	}
}
