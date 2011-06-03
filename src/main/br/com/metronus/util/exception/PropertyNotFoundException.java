/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title PropertyNotFoundException.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class PropertyNotFoundException extends Exception {    
	
	/**
	 * valor criado pelo eclipse, para permitir que uma classes serializada possa ser deserializada apos uma mudanca em sua implementacao
	 * (ver http://www.javapractices.com/Topic45.cjp para mais detalhes)
	 */
	private static final long serialVersionUID = 7643059836579735389L;
	
	private Log log = LogFactory.getLog(PropertyNotFoundException.class);
    /**
	 * 
	 */
	public PropertyNotFoundException() {
		super();
		log.error("Ocorreu um erro na api de obtenção das propriedades", this);
	}

	/**
	 * @param message
	 */
	public PropertyNotFoundException(String message) {
		super(message);	
		log.error(message, this);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PropertyNotFoundException(String message, Throwable cause) {
		super(message, cause);		
		log.error(message, this);
	}

	/**
	 * @param cause
	 */
	public PropertyNotFoundException(Throwable cause) {
		super(cause);		
		log.error("Ocorreu um erro na api de obtenção das propriedades", this);
	}

}
