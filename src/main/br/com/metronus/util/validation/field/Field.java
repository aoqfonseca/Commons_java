package br.com.metronus.util.validation.field;

/**
 * @author Andre Fonseca
 *
 * 
 */
public interface Field {
    /**
     * @return Returns the nome.
     */
    public String getNome();

    /**
     * @param nome The nome to set.
     */
    public void setNome(String nome);

    /**
     * @return Returns the valor.
     */
    public Object getValor();

    /**
     * @param valor The valor to set.
     */
    public void setValor(Object valor);
}
