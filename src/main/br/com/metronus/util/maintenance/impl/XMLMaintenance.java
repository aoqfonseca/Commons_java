package br.com.metronus.util.maintenance.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.metronus.util.maintenance.Maintenance;
import br.com.metronus.util.maintenance.Pattern;

public class XMLMaintenance extends Maintenance {

	private boolean flag = true;
	private Log logger = LogFactory.getLog(XMLMaintenance.class);	
	
	public XMLMaintenance(String path){
		super(path);  
	}
	
	public Pattern createPattern(Object valor) {
		Pattern pattern = new XMLPattern((String)valor);
		patterns.add(pattern);
		return pattern;
	}

	public void start() {
		File log = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;        
        
        //Acessando o arquivo mapeados
        log = new File((String)monitored);
        BufferedReader reader;
        String linha = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(log)));
			while (flag) {
				linha = reader.readLine();
				dbf = DocumentBuilderFactory.newInstance();
				db = dbf.newDocumentBuilder();
				doc = db.parse(linha);
				if (linha != null  && !linha.trim().equalsIgnoreCase("")) {					
					for (int i = 0; i < patterns.size(); i++) {
						((Pattern) patterns.get(i)).find(doc);
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("Arquivo não encontrado",e);
		} catch (IOException e) {
			logger.error("Erro na leitura do arquivo",e);
		} catch (ParserConfigurationException e) {
			logger.error("Erro na parser do xml",e);
		} catch (SAXException e) {
			logger.error("Erro na api de conversão do XML",e);
		}
				
		        	
	}

	public void stop() {
		logger.debug("Parando a manutenção");
		this.flag = false;
	}

}
