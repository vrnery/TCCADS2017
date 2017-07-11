package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class FiltroException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FiltroException() {}
	
	public FiltroException(String message) {
		super(message);
	}
	
	public FiltroException(String message, Throwable cause) {
        super(message, cause);
    }

    public FiltroException(Throwable cause) {
        super(cause);
    }

    public FiltroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
