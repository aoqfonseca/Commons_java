package br.com.neoris.util.sql.engine.conector;

import java.sql.Connection;

import br.com.neoris.util.exception.DbConnectionException;
import br.com.neoris.util.sql.engine.ConnectionConfigure;

/**
 * Title ConnectorDB.java
 * <b> NEORIS - Brasil </b>
 * @author Andre Fonseca
 *
 * 
 */
public interface DBConnection {
	/**
	 * Metodo responsavel por retornar um conexão com o banco de dados configurado <br>
	 * no arquivo de propriedades
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws DbConnectionException;
	public void setConfigure(ConnectionConfigure configure);

}
