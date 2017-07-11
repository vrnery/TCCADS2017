package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception;

public class SegmentacaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SegmentacaoException() {}
	
	public SegmentacaoException(String message) {
		super(message);
	}
	
	public SegmentacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SegmentacaoException(Throwable cause) {
        super(cause);
    }

    public SegmentacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
