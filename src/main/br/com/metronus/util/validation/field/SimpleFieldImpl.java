package br.com.neoris.util.validation.field;

/**
 * @author Andre Fonseca
 * Classe que representa o campo com os atributos nome e valor.
 * 
 */
public class SimpleFieldImpl implements Field {
    
    private String nome;
    private Object valor;
    
    /**
     * Método construtor vazio
     *
     */
    public SimpleFieldImpl(){
        super();
    }
    /**
     * Método construtor onde já se define as propriedades do campo
     * @param nome
     * @param valor
     */
    public SimpleFieldImpl(String nome, Object valor){
        super();
        this.nome = nome;
        this.valor = valor;
    }
    
    

    /**
     * @return Returns the nome.
     */
    public String getNome() {
        return nome;
    }
    /**
     * @param nome The nome to set.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * @return Returns the valor.
     */
    public Object getValor() {
        return valor;
    }
    /**
     * @param valor The valor to set.
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }
}
