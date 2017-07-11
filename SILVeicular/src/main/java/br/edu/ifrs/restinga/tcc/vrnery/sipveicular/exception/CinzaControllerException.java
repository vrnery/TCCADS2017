package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class CinzaControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CinzaControllerException() {}
	
	public CinzaControllerException(String message) {
		super(message);
	}
	
	public CinzaControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinzaControllerException(Throwable cause) {
        super(cause);
    }

    public CinzaControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
