package br.com.metronus.util.sql.engine;

/**
 * @author Andre Fonseca
 *
 * Objeto que irá encapsular os dados de configuração de uma query
 */
public class QueryConfiguration {
    private String sql;
    private int timeOut;
    private String nome;
    

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
     * @return Returns the slq.
     */
    public String getSql() {
        return sql;
    }
    /**
     * @param slq The slq to set.
     */
    public void setSql(String slq) {
        this.sql = slq;
    }
    /**
     * @return Returns the timeOut.
     */
    public int getTimeOut() {
        return timeOut;
    }
    /**
     * @param l The timeOut to set.
     */
    public void setTimeOut(int l) {
        this.timeOut = l;
    }
}
