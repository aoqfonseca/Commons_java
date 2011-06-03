package br.com.neoris.util.integration;

/**
 * @author Andre Fonseca
 *
 * interface que define o comportamento de uma classe que recebe os eventos
 * de uma fila
 */
public interface MQListener {
    
    public void onMessage(String message);

}
