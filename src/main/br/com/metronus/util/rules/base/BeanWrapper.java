package br.com.neoris.util.rules.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Andre Fonseca
 *
 * Classe que é responsavel por guardar os dados de um bean e o metodo a ser 
 * executado
 */
public class BeanWrapper {
    
    private Method method;
    private Object bean;

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
     * @return Returns the method.
     */
    public Method getMethod() {
        return method;
    }
    /**
     * @param method The method to set.
     */
    public void setMethod(Method method) {
        this.method = method;
    }
    /**
     * @param method
     * @param bean
     */
    public BeanWrapper(Method method, Object bean) {
        super();
        this.method = method;
        this.bean = bean;
    }
    
    public void execute() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        method.invoke(bean,null);
    }
}
