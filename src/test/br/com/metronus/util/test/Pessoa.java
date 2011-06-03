package br.com.neoris.util.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Criado na data Feb 2, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */

/**
 * Title Pessoa.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class Pessoa {

	private String nome;
	private Double idade;
	private String sexo;
	private String endereco;
	private Boolean forte;
	private Collection datas = new ArrayList();
	
	public Iterator datasIterator(){
		return datas.iterator();
	}
	public void addData(Object obj){
		datas.add(obj);
	}
	
	/**
	 * @return
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @return
	 */
	public Double getIdade() {
		return idade;
	}

	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param string
	 */
	public void setEndereco(String string) {
		endereco = string;
	}

	/**
	 * @param i
	 */
	public void setIdade(Double i) {
		this.idade = i;
	}	

	/**
	 * @param string
	 */
	public void setNome(String string) {
		nome = string;
	}

	/**
	 * @param string
	 */
	public void setSexo(String string) {
		sexo = string;
	}

	/**
	 * @return
	 */
	public Boolean getForte() {
		return forte;
	}

	/**
	 * @param boolean1
	 */
	public void setForte(Boolean boolean1) {
		forte = boolean1;
	}

}
