package br.com.neoris.util.maintenance.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.neoris.util.maintenance.Maintenance;
import br.com.neoris.util.maintenance.Pattern;

/**
 * Classe que especializa a Maintenance para arquivos de log de texto. Ela
 * somente executa StringPattern
 * @author Andre Fonseca
 * 
 */
public class LogTxtMaintenance extends Maintenance {

	private Log logger = LogFactory.getLog(LogTxtMaintenance.class);

	private boolean flag = true;

	public LogTxtMaintenance(File file) {
		super(file);
	}

	public void start() {
		File log = (File) monitored;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(log)));
			String linha = null;
			while (flag) {
				linha = reader.readLine();
				if (linha != null  && !linha.trim().equalsIgnoreCase("")) {					
					for (int i = 0; i < patterns.size(); i++) {
						((Pattern) patterns.get(i)).find(linha);
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("Arquivo não encontrado", e);
		} catch (IOException e) {
			logger.error("Erro na tentativa de leitura da linha do arquivo", e);
		}
	}

	public void stop() {
		logger.debug("Parando a manutenção");
		this.flag = false;
	}

	/**
	 * Metodo para criar um StringPattern a ser usado nesta manutenção
	 */
	public Pattern createPattern(Object regexp) {
		Pattern pattern = new StringPattern((String) regexp);
		patterns.add(pattern);
		return pattern;
	}
}
