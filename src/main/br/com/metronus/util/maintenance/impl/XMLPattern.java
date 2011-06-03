package br.com.metronus.util.maintenance.impl;

import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

import br.com.metronus.util.maintenance.Pattern;

public class XMLPattern extends Pattern {
	
	private Log log = LogFactory.getLog(XMLPattern.class);
	public XMLPattern(String pattern){
		super(pattern);
	}
	/**
	 * Metodo para buscar dentro de um nós o XPath passado
	 */
	public void find(Object valor) {
		Node root = (Node)valor;
		try {
			if(XPathAPI.selectSingleNode(root,(String)pattern) != null){
				fireFixes();
			}
		} catch (TransformerException e) {
			log.error("Erro na leitura do no passado", e);
		}
	}

}
