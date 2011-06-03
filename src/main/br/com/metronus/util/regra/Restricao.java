/**
 * 
 */
package br.com.neoris.util.regra;

/**
 * @author Andre Fonseca
 * Interface que define o comportamento de uma restrição de negocio que ira 
 * compor um regra de negocio.
 *
 */
public interface Restricao {
	
	public boolean check();

}
