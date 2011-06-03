package br.com.metronus.util.maintenance;

import java.util.ArrayList;

/**
 * Classe responsável por realizar a manutenção
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
	 * Metodo para criar um pattern e associa-lo a esta manutenção
	 * @param valor Object valor com o pattern
	 * @return Pattern
	 */
	public abstract Pattern createPattern(Object valor);
		
	
	/**
	 * Metodo para iniciar a manutenção
	 */
	public abstract void start();
	/**
	 * Metodo para parar a manutenção
	 */
	public abstract void stop();
}
