package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class ProcessamentoHistogramaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessamentoHistogramaException() {}
	
	public ProcessamentoHistogramaException(String message) {
		super(message);
	}
	
	public ProcessamentoHistogramaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessamentoHistogramaException(Throwable cause) {
        super(cause);
    }

    public ProcessamentoHistogramaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
