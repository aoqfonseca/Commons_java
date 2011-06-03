package br.com.metronus.util.regra;

import java.util.HashMap;

/**
 * @author Andre Fonseca Classe que representa o contexto de uma regra. Através
 *         dos objetos no contexto que uma regra irá realizar suas verificações
 *         de suas restrições.<br>
 *         O contexto funciona como um hashMap onde as chaves são strings que
 *         identificam os objetos lá colocados
 */
public class Contexto {

	private final HashMap atributos = new HashMap();

	public void addAtribute(Object key, Object value) {
		atributos.put(key, value);
	}

	public Object getAttribute(Object key) {
		return atributos.get(key);
	}
	
	public void remove(Object key){
		atributos.remove(key);
	}

}
