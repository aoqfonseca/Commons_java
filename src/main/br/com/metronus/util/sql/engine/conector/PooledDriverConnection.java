/*
 * Criado na data Feb 3, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.sql.engine.conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.metronus.util.exception.DbConnectionException;
import br.com.metronus.util.sql.engine.ConnectionConfigure;

/**
 * Classe que implementa a interface de criacao de conexão com banco de dados <bR>
 * para o caso de conexões via DriveManager em Pool simples. Utiliza DBCP do jakarta.
 * <b> NEORIS - Brasil </b>
 * @author Andre Fonseca
 *
 * 
 */
public class PooledDriverConnection implements DBConnection{
    
    private ConnectionConfigure configure;    
	private String pool;
	
	
	
	/**
	 * 
	 */
	public PooledDriverConnection(String pool) {	    
		super();
		this.pool = pool;
	}

	/* (non-Javadoc)
	 * @see br.com.metronus.util.sql.conector.ConnectorDB#getConnection()
	 */
	public Connection getConnection() throws DbConnectionException {	           
        try {
			Class.forName(configure.getDriverClass());				
			return DriverManager.getConnection("jdbc:apache:commons:dbcp:"+pool,configure.getUser(),configure.getPassword());
		} catch (ClassNotFoundException e) {
			throw new DbConnectionException("Driver não encontrado. Por favor veja se classpath",e);
		} catch (SQLException e) {
			throw new DbConnectionException("Não foi possivel obter a conexão com o banco de dados informado",e);
		}
	}
  
    /* (non-Javadoc)
     * @see br.com.metronus.util.sql.conector.DBConnection#setConfigure(br.com.metronus.util.sql.engine.ConnectionConfigure)
     */
    public void setConfigure(ConnectionConfigure configure) { 
        this.configure = configure;
    }
    
    public void setPool(String valor){
        this.pool = valor;
    }
}
