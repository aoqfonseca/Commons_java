package br.com.neoris.util.rules;

import java.util.HashMap;

/**
 * @author Andre Fonseca
 *
 * Classe que representa o contexto de uma regra.
 * Funciona com um hash de atributos que serão acessados pelos itens de regras
 * quando forem executados
 */
public final class Context {
    
    private HashMap atributos = new HashMap();
    
    public void addAtribute(Object key, Object value){
        atributos.put(key,value);
    }
    
    public Object getAttribute(Object key){
        return atributos.get(key);
    }

}
