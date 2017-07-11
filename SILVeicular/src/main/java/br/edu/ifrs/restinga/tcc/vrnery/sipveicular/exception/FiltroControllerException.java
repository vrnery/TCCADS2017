package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class FiltroControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FiltroControllerException() {}
	
	public FiltroControllerException(String message) {
		super(message);
	}
	
	public FiltroControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FiltroControllerException(Throwable cause) {
        super(cause);
    }

    public FiltroControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
