package br.com.metronus.util.validation.field;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Andre Fonseca
 * Classe que extende a implementaçao SimpleFieldImpl, adicionando a capacidade deste mapeamento seja feito num nivel de Beans.<br>
 * Com ela vc passa um bean e um nome da propriedade (ou o PropertyDescriptor direto) e a propria classe se encarrega de montar um Field para esta propriedade.<br>
 * A classe BeanWrapper de conter os metodos get e set !.
 */
public class FieldDescriptor extends SimpleFieldImpl {

    private PropertyDescriptor descriptor;

    private Object bean;

    public FieldDescriptor(Object bean, PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
        this.bean = bean;
    }
    
    public FieldDescriptor(Object bean, String nome) throws IntrospectionException {
        this.descriptor = new PropertyDescriptor(nome,bean.getClass());        
        this.bean = bean;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.metronus.util.validation.field.Field#getNome()
     */
    public String getNome() {
        return descriptor.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.metronus.util.validation.field.Field#getValor()
     */
    public Object getValor() {
        try {
            return descriptor.getReadMethod().invoke(bean, null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.metronus.util.validation.field.Field#setNome(java.lang.String)
     */
    public void setNome(String nome) {
        super.setNome(nome);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.metronus.util.validation.field.Field#setValor(java.lang.Object)
     */
    public void setValor(Object valor) {
        try {
            super.setValor(descriptor.getWriteMethod().invoke(bean,new Object[]{valor}));
        } catch (IllegalArgumentException e) {            
            e.printStackTrace();
            super.setValor(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            super.setValor(null);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            super.setValor(null);
        }
    }
}
