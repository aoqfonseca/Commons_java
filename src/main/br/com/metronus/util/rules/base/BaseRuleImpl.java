package br.com.neoris.util.rules.base;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.neoris.util.rules.Rule;

/**
 * @author Andre Fonseca
 *
 * Implementação de regra para quando temos as regras mapeadas pelo xml
 */
public class BaseRuleImpl extends Rule {

    
    private Collection fail = new ArrayList();
    private Collection success= new ArrayList();
    private String nome;
    private Log log = LogFactory.getLog(BaseRuleImpl.class);
    
    
    public void addFail(BeanWrapper exe){
        log.debug("Add bean to execute when rule fail (false)");
        fail.add(exe);
    }
    public void addSuccess(BeanWrapper exe){
        log.debug("Add bean to execute when rule success (true)");
        success.add(exe);
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.Rule#fail()
     */
    public void fail() {
        log.debug("fail");
        Iterator iterator = fail.iterator();
        BeanWrapper proxy = null;
        while(iterator.hasNext()){
            proxy = (BeanWrapper)iterator.next();
            try {
                proxy.execute();
            } catch (IllegalArgumentException e) {                
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.Rule#success()
     */
    public void success() {
        log.debug("success");
        Iterator iterator = success.iterator();
        BeanWrapper proxy = null;
        while(iterator.hasNext()){
            proxy = (BeanWrapper)iterator.next();
            try {
                proxy.execute();
            } catch (IllegalArgumentException e) {                
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if(!(obj instanceof BaseRuleImpl)){
            return false;
        }
        BaseRuleImpl apoio = (BaseRuleImpl)obj;       
        return apoio.getNome().equals(this.nome);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return nome.hashCode();
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
}
