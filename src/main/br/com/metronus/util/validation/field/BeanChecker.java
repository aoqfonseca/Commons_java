package br.com.neoris.util.validation.field;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Andre Fonseca
 * 
 * Classe que valida todas as propriedades registradas de um BeanWrapper.<br>
 * Com esta classe o desenvolvedor consegue validar um bean, definindo um mapeamento propriedade/validações, e depois pedindo a validação.
 * Por Exemplo:<br>
 * O desenvolvedor tem um bem Pessoal com as propriedades nome, idade, email<br>
 *  class Pessoa{ <br>
 *    private String nome;<br>
 *    private Integer idade;<br>
 * 	  private String email;<br>
 *    //... os metodos get e set devem estar implementados<br> 
 *  }<br>
 * <br>
 * Agora vamos supor que ele queira validar o nome e email. Sendo assim ele instancia esta classe passando a instancia de Pessoa. <br>
 * Depois registra as propriedades nome e email pelos metodos registerProperty com suas respectivas validações<br>
 * Feito isso o desenvolvedor chama o método validate( caso queira as mensagens de validação) ou isValid
 */
public class BeanChecker {

    private Object bean;
    private Collection chainedValidations;
    private Collection respostas;

    
    /**
     * @return Returns the bean.
     */
    public Object getBean() {
        return bean;
    }
    /**
     * @param bean The bean to set.
     */
    public void setBean(Object bean) {
        this.bean = bean;
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
     * @param bean
     */
    public BeanChecker(Object bean) {
        super();
        this.bean = bean;
        chainedValidations = new ArrayList();
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
        validations.setField(new FieldDescriptor(bean, nome));
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
        chainedValidations.add(new ChainedChecker(new Checker[] { validator },new FieldDescriptor(bean, nome)));
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
