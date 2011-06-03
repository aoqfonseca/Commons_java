package br.com.metronus.util.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Andre Fonseca
 * Classe que representa os erros de metodos ou codigos  não implementados. 
 */
public class UnsupportedMethodException extends RuntimeException {

	/**
	 * valor criado pelo eclipse, para permitir que uma classes serializada possa ser deserializada apos uma mudanca em sua implementacao
	 * (ver http://www.javapractices.com/Topic45.cjp para mais detalhes)
	 */
	private static final long serialVersionUID = 7159663893425335907L;
	
	private Log log = LogFactory.getLog(UnsupportedMethodException.class);
    /**
     * 
     */
    public UnsupportedMethodException() {
        super();
        log.error("Método ou codigo não implementado",this);
    }

    /**
     * @param message
     */
    public UnsupportedMethodException(String message) {
        super(message);
        log.error(message,this);
    }

    /**
     * @param message
     * @param cause
     */
    public UnsupportedMethodException(String message, Throwable cause) {
        super(message, cause);
        log.error(message,this);
    }

    /**
     * @param cause
     */
    public UnsupportedMethodException(Throwable cause) {
        super(cause);
        log.error("Método ou codigo não implementado",this);
    }

}
