/*
 * Criado na data Feb 3, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.sql.engine.conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.neoris.util.exception.DbConnectionException;
import br.com.neoris.util.sql.engine.ConnectionConfigure;

/**
 * Title SimpleConnection.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class DriverConnection implements DBConnection {

	private String senha;
	private String usuario;
	private String classDriver;
	private String url;
	private ConnectionConfigure configure;


	/* (non-Javadoc)
	 * @see br.com.neoris.util.sql.conector.ConnectorDB#getConnection()
	 */
	public Connection getConnection() throws DbConnectionException {
	    this.classDriver = configure.getDriverClass();
		this.usuario = configure.getUser();
		this.senha = configure.getPassword();
		this.url = configure.getUrl();
	    try {
			Class.forName(classDriver);
			return DriverManager.getConnection(url, usuario, senha);
		} catch (ClassNotFoundException e) {
			throw new DbConnectionException("Driver não encontrado. Por favor veja se classpath",e);
		} catch (SQLException e) {
			throw new DbConnectionException("Não foi possivel obter a conexão com o banco de dados informado",e);
		}
	}
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.sql.conector.DBConnection#setConfigure(br.com.neoris.util.sql.engine.ConnectionConfigure)
     */
    public void setConfigure(ConnectionConfigure configure) { 
        this.configure = configure;
    }
}
