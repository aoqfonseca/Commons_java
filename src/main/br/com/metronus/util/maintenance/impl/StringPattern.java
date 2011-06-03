
package br.com.metronus.util.maintenance.impl;

import br.com.metronus.util.StringUtil;
import br.com.metronus.util.maintenance.Pattern;

/**
 * @author Andre Fonseca
 * Classe que representa um pattern a ser buscado no arquivo de log
 */
public class StringPattern  extends Pattern{

	public StringPattern(String pattern){
		super(pattern); 
	}

	/**
	 * Metodo para verificar se na string passada existe o pattern
	 * @param valor String com o valor onde será realizada a busca
	 */
	public void find(Object valor){
		if(StringUtil.find((String)valor,(String)pattern)){
			fireFixes();
		}
	}

}
