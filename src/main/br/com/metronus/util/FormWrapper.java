package br.com.neoris.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletRequest;

/**
 * Metodo que envelopa um formulário html, contendo métodos uteis para a manipulação
 * dos dados deste formulário
 * @author Andre Fonseca
 *
 */
public class FormWrapper {
	private ServletRequest request;

	public FormWrapper(ServletRequest request) {
		this.request = request;
	}
	
	public String getParameter(String key){
		return request.getParameter(key);
	}
	
	public String [] getParameterValues(String key){
		return request.getParameterValues(key);
	}
	
	public java.util.Enumeration getParameterNames(){
		return request.getParameterNames();
	}
	
	public ServletRequest getRequest(){
		return this.request;
	}
	
	public Integer getParameterAsInteger(String key){
		return new Integer(request.getParameter(key));
	}
	public int getParameterAsInt(String key){
		return Integer.parseInt(request.getParameter(key));
	}
	public Boolean getParameterAsBoolean(String key){
		return new Boolean(request.getParameter(key));
	}
	public boolean getParameterAsBooleanPrimitivy(String key){
		return Boolean.getBoolean(request.getParameter(key));
	}
	public Long getParameterAsLong(String key){
		return new Long(request.getParameter(key));
	}
	public long getParameterAsLongPrimitivy(String key){
		return Long.parseLong(request.getParameter(key));
	}
	public java.util.Date getParameterAsDate(String key, String pattern) throws ParseException{
		return (new SimpleDateFormat(pattern).parse(request.getParameter(key)));
	}
// metodos que tratam os array
	public Integer[] getParameterValuesAsInteger(String key){
		Integer array [] = new Integer [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = new Integer(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public int[] getParameterValuesAsInt(String key){
		int array [] = new int [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = Integer.parseInt(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public Boolean[] getParameterValuesAsBoolean(String key){
		Boolean array [] = new Boolean [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = new Boolean(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public boolean[] getParameterValuesAsBooleanPrimitivy(String key){
		boolean array [] = new boolean [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = Boolean.getBoolean(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public Long[] getParameterValuesAsLong(String key){
		Long array [] = new Long [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = new Long(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public long[] getParameterValuesAsLongPrimitivy(String key){
		long array [] = new long [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = Long.parseLong(request.getParameterValues(key)[i]);
		}
		return array;
	}
	public java.util.Date[] getParameterValuesAsDate(String key, String pattern) throws ParseException{
		java.util.Date array [] = new java.util.Date [request.getParameterValues(key).length];
		for(int i=0;i<array.length;i++){
			array[i] = (new SimpleDateFormat(pattern).parse(request.getParameter(key)));
		}
		return array;		
	}
}
