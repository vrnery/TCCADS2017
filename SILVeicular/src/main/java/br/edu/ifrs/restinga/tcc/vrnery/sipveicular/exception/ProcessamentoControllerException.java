package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class ProcessamentoControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessamentoControllerException() {}
	
	public ProcessamentoControllerException(String message) {
		super(message);
	}
	
	public ProcessamentoControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessamentoControllerException(Throwable cause) {
        super(cause);
    }

    public ProcessamentoControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
