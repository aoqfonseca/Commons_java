package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.metronus.util.validation.field.Checker;

/**
 * @author Andre Fonseca
 *
 * Classe abstrata que encapsula alguns passos comuns para as valida��es implementadas
 */
public abstract class AbstractChecker implements Checker {
    
    public ResourceBundle bundle;

    /**
     * M�todo construtor.Neste metodo o sistema ir� usar o bundle de mensagens padr�o
     *
     */
    public AbstractChecker(){
        bundle = ResourceBundle.getBundle("br.com.metronus.util.validation.implementation.field.mensagens");        
    }
    public AbstractChecker(Locale locale){
        bundle = ResourceBundle.getBundle("br.com.metronus.util.validation.implementation.field.mensagens",locale);        
    }
}
