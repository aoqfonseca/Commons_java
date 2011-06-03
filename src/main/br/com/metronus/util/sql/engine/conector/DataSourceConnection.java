/*
 * Criado na data Feb 3, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.sql.engine.conector;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.metronus.util.exception.DbConnectionException;
import br.com.metronus.util.sql.engine.ConnectionConfigure;

/**
 * Title DataSourceConnection.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class DataSourceConnection implements DBConnection {

	private String jndiName;
	private String usuario;
	private String senha;
	private ConnectionConfigure configure;
	

	/* (non-Javadoc)
	 * @see br.com.metronus.util.sql.conector.ConnectorDB#getConnection()
	 */
	public Connection getConnection() throws DbConnectionException {

	    this.jndiName = configure.getDataSource();
		this.usuario = configure.getUser();
		this.senha = configure.getPassword();
		InitialContext ic;
		DataSource datasource = null; 
		try {
			ic = new InitialContext();
			datasource = (DataSource) ic.lookup(jndiName);
		} catch (NamingException e) {			
			throw new DbConnectionException("Não foi possivel localizar o datasource através do nome informado",e);
		}		 
		try {
			return datasource.getConnection(usuario,senha);
		} catch (SQLException e1) {			
			throw new DbConnectionException("Não foi possivel obter a conexão com o banco de dados",e1);
		}	
	}
    

    /* (non-Javadoc)
     * @see br.com.metronus.util.sql.conector.DBConnection#setConfigure(br.com.metronus.util.sql.engine.ConnectionConfigure)
     */
    public void setConfigure(ConnectionConfigure configure) { 
        this.configure = configure;
    }

}
