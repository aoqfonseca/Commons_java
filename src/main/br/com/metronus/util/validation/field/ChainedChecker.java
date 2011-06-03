package br.com.neoris.util.validation.field;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andre Fonseca
 *
 * Classe que agrupa validações e as realiza para um campo.<br>
 * Esta recebe uma coleção de validações encadeadas (ordenadas) e quando chamado o método validate, realiza-as todas no campo
 * definido.<br>
 * Por exemplo:<br>
 * Você tem o campo nome e deseja testar se é nulo ou vazio, se é somente letrar e se não tem acentos. Ao invés de fazer uma validação
 * por vez, o desenvolvedor cria um array destas validações (Checker[]) e instancia uma ChainedChecker passando o campo(Field) e este arraya criado.<br>
 * Depois é só chamar o método validate e receber a coleção de respostas 
 */
public class ChainedChecker {
    
    public Checker[] listValidator;
    public Field field;
    
    /**
     * Metodo para validar o campo definido em todas as validaçaões encadeadas
     * @return collection de Response
     */
    public Collection validate(){
        Collection respostas = new ArrayList();
        for(int i=0;i<listValidator.length;i++){
            respostas.add(listValidator[i].validate(null));
        }
        return respostas;
    }
    /**
     * @param listValidator
     * @param field
     */
    public ChainedChecker(Checker[] listValidator) {
        super();
        this.listValidator = listValidator;
    }
    
    /**
     * @param listValidator
     * @param field
     */
    public ChainedChecker(Checker[] listValidator, Field field) {
        super();
        this.listValidator = listValidator;
        this.field = field;
    }
    /**
     * @return Returns the field.
     */
    public Field getField() {
        return field;
    }
    /**
     * @param field The field to set.
     */
    public void setField(Field field) {
        this.field = field;
    }
    /**
     * @return Returns the listValidator.
     */
    public Checker[] getListValidator() {
        return listValidator;
    }
    /**
     * @param listValidator The listValidator to set.
     */
    public void setListValidator(Checker[] listValidator) {
        this.listValidator = listValidator;
    }
}
