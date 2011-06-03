package br.com.metronus.util.validation.field;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andre Fonseca
 *
 * Classe que agrupa valida��es e as realiza para um campo.<br>
 * Esta recebe uma cole��o de valida��es encadeadas (ordenadas) e quando chamado o m�todo validate, realiza-as todas no campo
 * definido.<br>
 * Por exemplo:<br>
 * Voc� tem o campo nome e deseja testar se � nulo ou vazio, se � somente letrar e se n�o tem acentos. Ao inv�s de fazer uma valida��o
 * por vez, o desenvolvedor cria um array destas valida��es (Checker[]) e instancia uma ChainedChecker passando o campo(Field) e este arraya criado.<br>
 * Depois � s� chamar o m�todo validate e receber a cole��o de respostas 
 */
public class ChainedChecker {
    
    public Checker[] listValidator;
    public Field field;
    
    /**
     * Metodo para validar o campo definido em todas as valida�a�es encadeadas
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
