package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;
import java.util.ResourceBundle;

import br.com.metronus.util.validation.field.Checker;

/**
 * @author Andre Fonseca
 *
 * Classe abstrata que encapsula alguns passos comuns para as validações implementadas
 */
public abstract class AbstractChecker implements Checker {
    
    public ResourceBundle bundle;

    /**
     * Método construtor.Neste metodo o sistema irá usar o bundle de mensagens padrão
     *
     */
    public AbstractChecker(){
        bundle = ResourceBundle.getBundle("br.com.metronus.util.validation.implementation.field.mensagens");        
    }
    public AbstractChecker(Locale locale){
        bundle = ResourceBundle.getBundle("br.com.metronus.util.validation.implementation.field.mensagens",locale);        
    }
}
