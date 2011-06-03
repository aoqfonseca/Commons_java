package br.com.metronus.util.validation.field;

/**
 * @author Andre Fonseca
 *
 * Interface que define o comportamente das classes de validação
 */
public interface Checker {
    
    public Response validate(Field field);

}
