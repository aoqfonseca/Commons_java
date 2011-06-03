package br.com.neoris.util.maintenance;

import java.util.ArrayList;

/**
 * Classe respons�vel por realizar a manuten��o
 * @author Andre Fonseca
 *
 */
public abstract class Maintenance {
	
	public Object monitored;
	public ArrayList patterns;
	
	public Maintenance(Object monitored){
		this.monitored = monitored;
		this.patterns = new ArrayList();
	}
	/**
	 * Metodo para criar um pattern e associa-lo a esta manuten��o
	 * @param valor Object valor com o pattern
	 * @return Pattern
	 */
	public abstract Pattern createPattern(Object valor);
		
	
	/**
	 * Metodo para iniciar a manuten��o
	 */
	public abstract void start();
	/**
	 * Metodo para parar a manuten��o
	 */
	public abstract void stop();
}
