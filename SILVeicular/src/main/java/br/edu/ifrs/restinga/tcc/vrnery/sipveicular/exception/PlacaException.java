package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class PlacaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlacaException() {}
	
	public PlacaException(String message) {
		super(message);
	}
	
	public PlacaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlacaException(Throwable cause) {
        super(cause);
    }

    public PlacaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
