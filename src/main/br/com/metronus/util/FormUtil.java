package br.com.neoris.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.FastHashMap;

/**
 * Classse que serve para realizar o parser de um formul�rio html para um bean, atrav�s 
 * de um mapeamento.
 * A classe ir� pegar um campo do formul�rio e associa-lo a uma propriedade do bean. Quando o usuario da classe
 * executar o metodo parser, ela ir� pegar os dados do formul�rio passa-los para o bean chamando os metodos get correspondente]
 * as propriedades informadas.
 * Lembrando que para campos que retornam array a propriedade que ir� receber tamb�m dev ser um array.
 * @author Andre Fonseca
 *
 */
public class FormUtil {
	
	private FastHashMap map;
	private HttpServletRequest request;
	private Class bean;
	
	/**
	 * Construtor da classe que recebe o request com os dados do formulario e 
	 * a instancia do bean que ser� populado com os dados
	 * @param request
	 * @param bean
	 */
	public FormUtil(HttpServletRequest request,Class bean){
		this.request = request;
		this.bean    = bean;
	}
	/**
	 * M�todo para mapear um campo do formul�rio para uma propriedade do Bean
	 * @param formProperty campo do formul�rio
	 * @param beanProperty propriedade do bean (letras minusculas ex: metodo getNome, deve ser informado nome nesta argumento)
	 */
	public void mapping(String formProperty,String beanProperty){
		map.put(formProperty,beanProperty);
	}
	/**
	 * M�todo para parsear o form html e popular uma instancia do bean indicado no construtor com
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
