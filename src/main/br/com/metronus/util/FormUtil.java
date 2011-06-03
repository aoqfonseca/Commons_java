package br.com.neoris.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.FastHashMap;

/**
 * Classse que serve para realizar o parser de um formulário html para um bean, através 
 * de um mapeamento.
 * A classe irá pegar um campo do formulário e associa-lo a uma propriedade do bean. Quando o usuario da classe
 * executar o metodo parser, ela irá pegar os dados do formulário passa-los para o bean chamando os metodos get correspondente]
 * as propriedades informadas.
 * Lembrando que para campos que retornam array a propriedade que irá receber também dev ser um array.
 * @author Andre Fonseca
 *
 */
public class FormUtil {
	
	private FastHashMap map;
	private HttpServletRequest request;
	private Class bean;
	
	/**
	 * Construtor da classe que recebe o request com os dados do formulario e 
	 * a instancia do bean que será populado com os dados
	 * @param request
	 * @param bean
	 */
	public FormUtil(HttpServletRequest request,Class bean){
		this.request = request;
		this.bean    = bean;
	}
	/**
	 * Método para mapear um campo do formulário para uma propriedade do Bean
	 * @param formProperty campo do formulário
	 * @param beanProperty propriedade do bean (letras minusculas ex: metodo getNome, deve ser informado nome nesta argumento)
	 */
	public void mapping(String formProperty,String beanProperty){
		map.put(formProperty,beanProperty);
	}
	/**
	 * Método para parsear o form html e popular uma instancia do bean indicado no construtor com
	 * os dados encontrados
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public Object parser() throws IllegalAccessException, InvocationTargetException, InstantiationException{
		Iterator iterator = map.keySet().iterator();
		String campo = null;
		Object beanInstance = bean.newInstance();
		while(iterator.hasNext()){
			campo = (String)iterator.next();
			if(request.getParameterValues(campo) == null){
				BeanUtils.setProperty(beanInstance,(String) map.get(campo),null);
			}else if(request.getParameterValues(campo).length ==1){
				BeanUtils.setProperty(beanInstance,(String) map.get(campo),request.getParameterValues(campo)[0]);
			}else {
				BeanUtils.setProperty(beanInstance,(String) map.get(campo),request.getParameterValues(campo));
			}
		}
		return beanInstance;
	}

}
