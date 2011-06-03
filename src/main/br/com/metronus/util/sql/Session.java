package br.com.neoris.util.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.neoris.util.Transaction;
import br.com.neoris.util.exception.TransactionException;
import br.com.neoris.util.sql.engine.QueryConfiguration;
import br.com.neoris.util.sql.engine.QueryRepository;

/**
 * @author Andre Fonseca
 * Classe que representa a Sessao (Conexao) JDBC com o banco
 * 
 */
public class Session implements javax.transaction.Transaction{
    
    private Log log = LogFactory.getLog(Session.class);
    /**  Conexao ativa com o banco de dados */
    private Connection connection;
    /**  Lista de preparedStatement criados nesta sessao com o banco de dados */
    private ArrayList preparedStaments = new ArrayList();
    /** Repositorio de querys */
    private QueryRepository repository;
    private int status = Status.STATUS_UNKNOWN;
    private boolean show = false;
      
    
    
    /**
     * Construtor da classe para construir uma sessao com um banco de dados
     * @param connection Conexï¿½o com o banco de dados
     * @param repository Instancia do cache de query 
     * @param show booleano indicando se deve mostrar as query feitas no log
     */
    protected Session(Connection connection, QueryRepository repository, boolean show) {
        super();
        this.connection = connection;
        this.repository = repository;
        this.show = show;
    }
    /**
     * Construtor da classe para construir um sessï¿½o com o parametro show no valor defautl (false)
     * @param conn Conexï¿½o com o banco de dados
     * @param rep Instancia do cache de query
     */
    public Session(Connection conn,QueryRepository rep){
        this.connection = conn;
        this.repository = rep;
        log.info("Criando nova sessï¿½o");
    }
    /**
     * @return Returns the repository.
     */
    public QueryRepository getRepository() {
        return repository;
    }
    /**
     * @param repository The repository to set.
     */
    public void setRepository(QueryRepository repository) {
        this.repository = repository;
    }
    
    public void setShow(boolean bool){
        this.show = bool;
    }
    
    public Session(){
        log.info("Criando nova sessão");
    }    
    /**
     * Metodo para obter a conexao da sessï¿½o
     * @return Returns the connection.
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * Metodo para setar a conexï¿½o da Sessï¿½o
     * @param connection The connection to set.
     */
    public void setConnection(Connection connection) {        
        this.connection = connection;
    }
    
    /**
     * Metodo que cria um statement de execuï¿½ï¿½o de query
     * @param sql String com o sql a ser executado
     * @return PreparedStatement associado a sessï¿½o
     * @throws SQLException disparada caso ocorre algum erro na criaï¿½ï¿½o do statement
     */
    public PreparedStatement createQuery(String sql) throws SQLException {
        connection.setAutoCommit(false);
        log.info("Criando uma nova query na sessao");
        if(show){
            log.debug(sql);
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);        
        preparedStaments.add(pstmt);
        return pstmt;        
    }
    /**
     * Metodo para criar um callablestatement dentro desta sessï¿½o
     * @param sql String com o sql
     * @return CallableStatement associado a sessao
     * @throws SQLException
     */
    public CallableStatement createCallableQuery(String sql) throws SQLException{        
        connection.setAutoCommit(false);
        log.info("Criando um novo CallableStatement na sessao");
        if(show){
            log.debug(sql);
        }
        CallableStatement callStmt = connection.prepareCall(sql);
        preparedStaments.add(callStmt);
        return callStmt;
    }
    
    /**
     * Metodo para encerrar a Sessï¿½o com o banco de dados
     * @throws SQLException disparada quando ocorre algum problema na tentativa de encerrar a conexï¿½o com o banco de dados
     */
    public void close() throws SQLException{
        log.info("Encerrando a sessao e os objetos associado a ela");
        Iterator iterator = preparedStaments.iterator();
        Statement pstmt = null;        
        while(iterator.hasNext()){
            pstmt = (Statement) iterator.next();                        
            pstmt.close();
        }
        //Limpa o array de preparedStament 
        preparedStaments.clear();        
        connection.clearWarnings();
        connection.setAutoCommit(true);
        connection.close();
    }
    
    /**
     * Metodo para confirmar as aï¿½ï¿½es feitas durante  sessï¿½o
     * @throws SQLException
     */
    public void commit() throws TransactionException{            
        log.info("Comitando as alteracoes realizadas");
        status = Status.STATUS_COMMITTING;
        try {
            connection.commit();
            status = Status.STATUS_COMMITTED;
        } catch (SQLException e) {
            throw new TransactionException("Erro na tentativa de realizar commit na conexao com o banco de dados");
        }
    }
    
    /**
     * Metodo para desfazer as acoes feitas durante a sessao
     * @throws SQLException
     */
    public void rollback() throws TransactionException{            
        log.info("Realizando rollback nas alteracoes realizadas");
        status = Status.STATUS_ROLLING_BACK;
        try {
            connection.rollback();
            status = Status.STATUS_ROLLEDBACK;
        } catch (SQLException e) {
            throw new TransactionException("Erro na tentativa de realizar rollback na conexao com o banco de dados");
        }
    }
    /**
     * Metodo para iniciar uma transacao a partir desta sessao com o banco de dados
     * @return Transaction criada e iniciado com esta sessao dentro
     */
    public Transaction beginTransaction(){
            status = Status.STATUS_ACTIVE;
            return new Transaction().add(this);
    }
    
    /**
     * Metodo para criar um preparedStatement a partir de uma query mapeada no arquivo de configuracao
     * @param key String com o id da query
     * @return PreparedStatement com o sql da query do id, associado a ele
     * @throws SQLException caso ocorra algum problema na tentativa de montagem do preparedstatement
     */
    public PreparedStatement createMappingQuery(String key) throws SQLException{
        connection.setAutoCommit(false);
        log.info("Criando uma nova query na sessao");
        QueryConfiguration config = QueryRepository.getInstance().getQuery("default",key);
        if(show){
            log.debug(config.getSql());
        }
        PreparedStatement pstmt = connection.prepareStatement(config.getSql());
        pstmt.setQueryTimeout(config.getTimeOut());
        preparedStaments.add(pstmt);
        return pstmt;
    }
    /**
     * Metodo para criar um preparedStatement a partir de uma query mapeada no arquivo de configuracao
     * @param apelido apelido do arquivo onde estï¿½ a query mapeada
     * @param key nome da query mapeada
     * @return PreparedStatement com o sql da query do id, associado a ele
     * @throws SQLException
     */
    public PreparedStatement createMappingQuery(String apelido,String key) throws SQLException{
        connection.setAutoCommit(false);
        log.info("Criando uma nova query na sessao");
        QueryConfiguration config = repository.getQuery(apelido,key);
        if(show){
            log.debug(config.getSql());
        }
        PreparedStatement pstmt = connection.prepareStatement(config.getSql());
        pstmt.setQueryTimeout(config.getTimeOut());
        preparedStaments.add(pstmt);
        return pstmt;
    }
    /**
     * Método para executar uma query fazendo um bind dos resultados para uma coleção de um tipo de objeto
     * @param chave String com o nome da query
     * @param clazz Class do objeto a ser populado
     * @return Collection de objetos do tipo clazz ou uma collection vazia, caso não encontre nada
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public Collection executeMappingQuery(String chave, Class clazz) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException{
    	QueryConfiguration config = QueryRepository.getInstance().getQuery("default",chave);    	
    	return executeQuery(config.getSql(),clazz);
    }
    /**
     * Método para executar uma query fazendo um bind dos resultados para uma coleção de um tipo de objeto
     * @param chave String com o nome da query
     * @param clazz Class do objeto a ser populado
     * @return Collection de objetos do tipo clazz ou uma collection vazia, caso não encontre nada
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public Collection executeQuery(String sql, Class clazz) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException{
    	PreparedStatement stmt = createQuery(sql);
    	ResultSet result = stmt.executeQuery();
    	ResultSetMetaData meta = result.getMetaData();
    	int index = 0;
    	Object o = null;
    	ArrayList list = new ArrayList();
    	while(result.next()){
    		o = clazz.newInstance();
    		index = meta.getColumnCount();
    		for(int i=1;i<=index;i++){    			    		
    			BeanUtils.setProperty(o, meta.getColumnLabel(i).toLowerCase(), result.getObject(i));
    		}
    		list.add(o);
    	}
    	return list;
    }
    /**
     * Metodo para realizar uma query e armazenar os dados da resposta de uma forma desconectado do banco. 
     * @param pstmt PrepareStatement a ser executado, contendo a query de busca
     * @throws SQLException dispara em caso de ocorrer algum erro na execucao do statement.
     */
    public UnpluggedResultSet executeDisconnectedStatement(PreparedStatement pstmt) throws SQLException{
            ResultSet result = pstmt.executeQuery();
            UnpluggedResultSet unplugged = new UnpluggedResultSet();
            int count = 0;
            int type = Types.OTHER;
            ResultSetMetaData metaData = result.getMetaData();
            count = metaData.getColumnCount();
            while(result.next()){
                    unplugged.novaLinha();
                    for(int i=0;i<count;i++){
                            type = metaData.getColumnType(i);
                            switch(type){
                            case Types.ARRAY:
                                    unplugged.addCampo(i,metaData.getColumnName(i), result.getArray(i));
                                    break;
                            case Types.BIGINT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBigDecimal(i));
                                    break;
                            case Types.BINARY:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBytes(i));
                                    break;
                            case Types.BIT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getByte(i));
                                    break;
                            case Types.BLOB:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBlob(i));
                                    break;
                            case Types.BOOLEAN:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBoolean(i));
                                    break;
                            case Types.CHAR:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getString(i));
                                    break;
                            case Types.CLOB:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getClob(i));
                                    break;
                            case Types.DATALINK:
                                    unplugged.addCampo(i,metaData.getColumnName(i),null);
                                    break;
                            case Types.DATE:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getDate(i));
                                    break;
                            case Types.DECIMAL:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getLong(i));
                                    break;
                            case Types.DOUBLE:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getDouble(i));
                                    break;
                            case Types.FLOAT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getFloat(i));
                                    break;
                            case Types.INTEGER:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getInt(i));
                                    break;
                            case Types.JAVA_OBJECT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getObject(i));
                                    break;
                            case Types.LONGVARBINARY:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBinaryStream(i));
                                    break;
                            case Types.LONGVARCHAR:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getString(i));
                                    break;
                            case Types.NULL:                                    
                                    unplugged.addCampo(i,metaData.getColumnName(i),null);
                                    break;
                            case Types.NUMERIC:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBigDecimal(i));
                                    break;
                            case Types.REAL:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBigDecimal(i));
                                    break;
                            case Types.REF:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getRef(i));
                                    break;
                            case Types.SMALLINT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getInt(i));
                                    break;
                            case Types.STRUCT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getObject(i));
                                    break;
                            case Types.TIME:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getTime(i));
                                    break;
                            case Types.TIMESTAMP:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getTimestamp(i));
                                    break;
                            case Types.TINYINT:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getLong(i));
                                    break;
                            case Types.VARBINARY:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getBytes(i));
                                    break;
                            case Types.VARCHAR:
                                    unplugged.addCampo(i,metaData.getColumnName(i),result.getString(i));
                                    break;
                            default:
                            	 	unplugged.addCampo(i,metaData.getColumnName(i),result.getString(i));
                                    break;
                            }
                    }                    
            }
            result.close();
            return unplugged;
    }
    
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#delistResource(javax.transaction.xa.XAResource, int)
     */
    public boolean delistResource(XAResource arg0, int arg1) throws IllegalStateException, SystemException {
        return false;
    }
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#enlistResource(javax.transaction.xa.XAResource)
     */
    public boolean enlistResource(XAResource arg0) throws RollbackException, IllegalStateException, SystemException {
        return false;
    }
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#getStatus()
     */
    public int getStatus() throws SystemException {
        return status;
    }
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#registerSynchronization(javax.transaction.Synchronization)
     */
    public void registerSynchronization(Synchronization arg0) throws RollbackException, IllegalStateException, SystemException {
    }
    /* (non-Javadoc)
     * @see javax.transaction.Transaction#setRollbackOnly()
     */
    public void setRollbackOnly() throws IllegalStateException, SystemException {
    }
}
