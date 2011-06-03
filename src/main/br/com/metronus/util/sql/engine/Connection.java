package br.com.metronus.util.sql.engine;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import br.com.metronus.util.exception.DbConnectionException;
import br.com.metronus.util.exception.PropertyNotFoundException;
import br.com.metronus.util.exception.UndefiniedConnectionTypeException;
import br.com.metronus.util.sql.engine.conector.DBConnection;
import br.com.metronus.util.sql.engine.conector.DataSourceConnection;
import br.com.metronus.util.sql.engine.conector.DriverConnection;
import br.com.metronus.util.sql.engine.conector.PooledDriverConnection;

/**
 * Classe responsavel por criar um conex�o com o banco de dados, dado <br>
 * o mapeamento feito atrav�s do arquivo de propriedade do sistema. No futuro �
 * interessante que esta implementa��o seja feita <br>
 * atrav�s de um xml <b>NEORIS - Brasil </b>
 * 
 * @author Andre Fonseca
 * @version 1.0
 * 
 *  
 */
public class Connection {

    private static Log log = LogFactory.getLog(Connection.class);

    private static boolean flag = true;

    protected static boolean show = false;

    /**
     *  
     */
    private Connection() {
        super();
    }

    /**
     * Metodo para obter uma conex�o com a base de dados atrav�s.
     * 
     * @return
     */
    public static java.sql.Connection getConnection() throws UndefiniedConnectionTypeException, DbConnectionException {

        log.debug("Carregando e acessando as configura��es do arquivo xml");
        Configure configure = Configure.buildConfigure();

        log.debug("Obtendo as configura��es da conex�o default");
        ConnectionConfigure connConfigure = configure.getConnection();

        show = connConfigure.isShowQuery();

        DBConnection db = null;
        int tipo = connConfigure.getTipo();

        log
                .debug("Criando a conex�o com o banco de dados de acordo com o tipo");
        log.debug("tipo = " + tipo);
        switch (tipo) {
        case ConnectionConfigure.CONNECTION:
            db = new DriverConnection();
            db.setConfigure(connConfigure);
            break;
        case ConnectionConfigure.DATASOURCE:
            db = new DataSourceConnection();
            db.setConfigure(connConfigure);
            break;
        case ConnectionConfigure.POOL_CONNECTION:
            db = new PooledDriverConnection("default");
            db.setConfigure(connConfigure);
            Connection.createPool("default", connConfigure);
            break;
        default:
            throw new UndefiniedConnectionTypeException(
                    "Tipo de conex�o desconhecida");
        }
        return db.getConnection();
    }

    /**
     * Metodo para obter connection com banco de dados associado ao nome no
     * arquivo de configura��es
     * 
     * @param nome
     *            String com o nome no arquivo de propriedades
     * @return java.sql.Connection
     * @throws UndefiniedConnectionTypeException
     * @throws DbConnectionException
     * @throws PropertyNotFoundException
     */
    public static java.sql.Connection getConnection(String nome)
            throws UndefiniedConnectionTypeException, DbConnectionException {

        log.debug("Carregando e acessando as configura��es do arquivo xml");
        Configure configure = Configure.buildConfigure();

        log.debug("Obtendo as configura��es da conex�o " + nome);
        ConnectionConfigure connConfigure = configure.getConnection(nome);

        DBConnection db = null;
        int tipo = connConfigure.getTipo();
        show = connConfigure.isShowQuery();

        log
                .debug("Criando a conex�o com o banco de dados de acordo com o tipo");
        log.debug("tipo = " + tipo);
        switch (tipo) {
        case ConnectionConfigure.CONNECTION:
            db = new DriverConnection();
            db.setConfigure(connConfigure);
            break;
        case ConnectionConfigure.DATASOURCE:
            db = new DataSourceConnection();
            db.setConfigure(connConfigure);
            break;
        case ConnectionConfigure.POOL_CONNECTION:
            db = new PooledDriverConnection(nome);
            db.setConfigure(connConfigure);
            Connection.createPool(nome, connConfigure);
            break;
        default:
            throw new UndefiniedConnectionTypeException(
                    "Tipo de conex�o desconhecida");
        }
        return db.getConnection();
    }

    /**
     * M�todo para criar um pool de conex�es
     * 
     * @param nome
     *            nome do pool
     * @param configure
     *            ConnectionConfigure com os dados das conex�es do pool
     */
    private static void createPool(String nome, ConnectionConfigure configure) {
        if (flag) {
            init(nome, configure);
        }
    }

    private static void init(String nome, ConnectionConfigure configure) {
        String usuario = configure.getUser();
        String senha = configure.getPassword();
        String url = configure.getUrl();
        ObjectPool connectionPool = new GenericObjectPool(null);
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, usuario, senha);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
        try {
            Class.forName("org.apache.commons.dbcp.PoolingDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PoolingDriver driver = null;
        try {
            driver = (PoolingDriver) DriverManager
                    .getDriver("jdbc:apache:commons:dbcp:");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        driver.registerPool(nome, connectionPool);
        flag = false;
    }

    public static void shutdownDriver(String nome) throws Exception {
        PoolingDriver driver = (PoolingDriver) DriverManager
                .getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool(nome);
    }

}
