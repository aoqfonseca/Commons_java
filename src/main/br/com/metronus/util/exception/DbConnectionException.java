/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title DbConnectionException.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class DbConnectionException extends Exception {


	private static final long serialVersionUID = -9044558987316504194L;
	
	private Log log = LogFactory.getLog(DbConnectionException.class);
    
	
	public DbConnectionException() {
		super();		
		log.error("Ocorreu um erro na api de obtenção de conexão com o banco de dados", this);
	}

	/**
	 * @param message
	 */
	public DbConnectionException(String message) {
		super(message);		
		log.error(message, this);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DbConnectionException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, this);
	}

	/**
	 * @param cause
	 */
	public DbConnectionException(Throwable cause) {	    
		super(cause);		
		log.error("Ocorreu um erro na api de obtenção de conexão com o banco de dados", this);
	}

}
