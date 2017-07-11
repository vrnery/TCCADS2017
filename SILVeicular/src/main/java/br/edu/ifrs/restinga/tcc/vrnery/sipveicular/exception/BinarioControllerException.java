package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class BinarioControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BinarioControllerException() {}
	
	public BinarioControllerException(String message) {
		super(message);
	}
	
	public BinarioControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BinarioControllerException(Throwable cause) {
        super(cause);
    }

    public BinarioControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
