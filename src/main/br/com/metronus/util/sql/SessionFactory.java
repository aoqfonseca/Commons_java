package br.com.metronus.util.sql;

import br.com.metronus.util.exception.DbConnectionException;
import br.com.metronus.util.exception.UndefiniedConnectionTypeException;
import br.com.metronus.util.sql.engine.Configure;
import br.com.metronus.util.sql.engine.Connection;
import br.com.metronus.util.sql.engine.QueryRepository;

/**
 * @author Andre Fonseca Classe que controla a criação de sessões com o banco
 */
public class SessionFactory {

    /**
     * Metodo para criar uma sessão com o banco de dados
     * 
     * @return Session
     * @throws UndefiniedConnectionTypeException
     * @throws DbConnectionException
     */
    public static Session openSession()
            throws UndefiniedConnectionTypeException, DbConnectionException {
        Session session = new Session(Connection.getConnection(),QueryRepository.getInstance(), Configure.buildConfigure()
                        .getConnection().isShowQuery());
        return session;
    }

    /**
     * Metodo para criar uma sessão com o banco de dados
     * 
     * @param name
     *            Nome da conexão a ser usada
     * @return Session
     * @throws UndefiniedConnectionTypeException
     *             disparada quando o tipo de conexão não for conhecido (ver o
     *             xml)
     * @throws DbConnectionException
     *             disparada caso ocorra algum problema na conexão
     */
    public static Session openSession(String name)
            throws UndefiniedConnectionTypeException, DbConnectionException {
        Session session = new Session(Connection.getConnection(name),
                QueryRepository.getInstance(), Configure.buildConfigure()
                        .getConnection().isShowQuery());
        return session;
    }

}
