package br.com.metronus.util.test.exemplo;

/**
 * @author Andre Fonseca
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestExecutionBean {
    
    private String nome;
    private int status;

    
    /**
     * @return Returns the status.
     */
    public int getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(int status) {        
        this.status = status;
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
    
    
    public void print(){
        System.out.println("Mensagem: "+ nome);
        System.out.println("status: "+ status);
    }
}
