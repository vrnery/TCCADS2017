package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class EqualizadoControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EqualizadoControllerException() {}
	
	public EqualizadoControllerException(String message) {
		super(message);
	}
	
	public EqualizadoControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EqualizadoControllerException(Throwable cause) {
        super(cause);
    }

    public EqualizadoControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
