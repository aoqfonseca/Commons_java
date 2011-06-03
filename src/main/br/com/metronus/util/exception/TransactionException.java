package br.com.neoris.util.exception;

/**
 * @author wsadm
 *
 * Exceção disparada pelos TransactionObjects quando acontece algum erro no rollback ou commit da transação 
 */
public class TransactionException extends RuntimeException{

	/**
	 * valor criado pelo eclipse, para permitir que uma classes serializada possa ser deserializada apos uma mudanca em sua implementacao
	 * (ver http://www.javapractices.com/Topic45.cjp para mais detalhes)
	 */
	private static final long serialVersionUID = 4160496080807391700L;

	/**
     * 
     */
    public TransactionException() {
        super();

    }

    /**
     * @param message
     */
    public TransactionException(String message) {
        super(message);

    }

    /**
     * @param message
     * @param cause
     */
    public TransactionException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * @param cause
     */
    public TransactionException(Throwable cause) {
        super(cause);

    }

}
