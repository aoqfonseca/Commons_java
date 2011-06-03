package br.com.metronus.util.rules;

/**
 * @author Andre Fonseca
 *
 * Interface que define o comportamento de um item de regra
 */
public interface Constraint {

    /**
     * Metodo que dispara a valida��o do item de regra
     * @return booleano indicando o status da valida��o
     */
    public boolean check(Context ctx);    
}
