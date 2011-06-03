package br.com.metronus.util.validation.field;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andre Fonseca
 *
 * Classe que mapeará os campos de um formulário (html), associará as validaçoes encadeadas e feito isso, ele
 * irá validar os campos
 */
public class FormChecker {
    
    private HttpServletRequest request;
    private Collection chainedValidations;
    private Collection respostas;

    /**
     * @param request
     */
    public FormChecker(HttpServletRequest request) {
        super();
        this.request = request;
    }
       
    /**
     * @return Returns the chainedValidations.
     */
    public Collection getChainedValidations() {
        return chainedValidations;
    }
    /**
     * @param chainedValidations The chainedValidations to set.
     */
    public void setChainedValidations(Collection chainedValidations) {
        this.chainedValidations = chainedValidations;
    }
    /**
     * @return Returns the respostas.
     */
    public Collection getRespostas() {
        return respostas;
    }
    /**
     * @param respostas The respostas to set.
     */
    public void setRespostas(Collection respostas) {
        this.respostas = respostas;
    }
 
    /**
     * Método para validar o bean recebendo uma coleção das mensagens de validação
     * @return Collection Response
     */
    public Collection validate(){
        respostas = new ArrayList();
        Iterator iterator = chainedValidations.iterator();
        ChainedChecker valida;
        while(iterator.hasNext()){
            valida = (ChainedChecker)iterator.next();
            respostas.addAll(valida.validate());
        }
        return respostas;
    }
    /**
     * Metodo que simplesmente verifica se o bean é valido sem retornar nenhuma mensagem
     * @return boolean indicando se é valido. True para valido e false para invalido
     */
    public boolean isValid(){
        boolean saida = false;
        Iterator iterator = chainedValidations.iterator();
        ChainedChecker valida;
        while(iterator.hasNext()){
            valida = (ChainedChecker)iterator.next();
            saida = saida&&consolida(valida.validate());
        }
        return saida;
    }

    /**
     * Metodo para registrar uma propriedade e seus validadores
     * 
     * @param nome
     *            String com o nome da propriedade a ser validada
     * @param validations
     *            ChainedChecker com os validadores a serem executados
     * @throws IntrospectionException
     */
    public void registerProperty(String nome, ChainedChecker validations)
            throws IntrospectionException {
        validations.setField(new FieldRequest(request, nome));
        chainedValidations.add(validations);
    }

    /**
     * Metodo para registrar uma propriedade e seus validadores
     * 
     * @param nome
     *            String com o nome da propriedade a ser validada
     * @param validations
     *            ChainedChecker com os validadores a serem executados
     * @throws IntrospectionException
     */
    public void registerProperty(String nome, Checker validator)
            throws IntrospectionException {
        chainedValidations
                .add(new ChainedChecker(new Checker[] { validator },
                        new FieldRequest(request, nome)));
    }
    /**
     * Metodo para consolidar os retornos de um ChainedChecker
     * @param respostas Collection de ValidaResponse
     * @return boolean
     */
    private boolean consolida(Collection respostas){
        boolean ret = true;
        Iterator iterator = respostas.iterator();
        Response resp;
        while(iterator.hasNext()){
            resp = (Response)iterator.next();
            ret = ret&&resp.isValid();
        }
        resp= null;
        iterator = null;       
        return ret;
    }
}
