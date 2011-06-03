/*
 * Criado na data Feb 3, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title UndefiniedConnectionTypeException.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class UndefiniedConnectionTypeException extends Exception {

	/**
	 * valor criado pelo eclipse, para permitir que uma classes serializada possa ser deserializada apos uma mudanca em sua implementacao
	 * (ver http://www.javapractices.com/Topic45.cjp para mais detalhes)
	 */
	private static final long serialVersionUID = 648215727760029999L;
	
	private Log log = LogFactory.getLog(UndefiniedConnectionTypeException.class);
    /**
	 * 
	 */
	public UndefiniedConnectionTypeException() {
		super();
		log.error("Tipo de conexão desconhecida para o sistema", this);
	}

	/**
	 * @param message
	 */
	public UndefiniedConnectionTypeException(String message) {
		super(message);		
		log.error(message, this);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UndefiniedConnectionTypeException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, this);
	}

	/**
	 * @param cause
	 */
	public UndefiniedConnectionTypeException(Throwable cause) {
		super(cause);
		log.error("Tipo de conexão desconhecida para o sistema", this);
	}

}
