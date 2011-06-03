package br.com.metronus.util.validation.field;

/**
 * @author Andre Fonseca
 *
 * Interface que define o comportamente das classes de valida��o
 */
public interface Checker {
    
    public Response validate(Field field);

}
