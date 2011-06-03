package br.com.neoris.util.validation.field;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andre Fonseca
 *
 * classe que mapea os campos de um request 
 */
public class FieldRequest extends SimpleFieldImpl {
       
    /**
     * @param request
     * @param nome
     */
    public FieldRequest(HttpServletRequest request, String nome) {
        super(nome,request.getParameter("nome"));        
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.validation.field.SimpleFieldImpl#getNome()
     */
    public String getNome() {        // 
        return super.getNome();
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.validation.field.SimpleFieldImpl#getValor()
     */
    public Object getValor() {        
        return super.getValor();
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.validation.field.SimpleFieldImpl#setNome(java.lang.String)
     */
    public void setNome(String nome) {     
        super.setNome(nome);
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.validation.field.SimpleFieldImpl#setValor(java.lang.Object)
     */
    public void setValor(Object valor) {
        super.setValor(valor);
    }
    
    
}
