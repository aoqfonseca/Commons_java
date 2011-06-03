package br.com.metronus.util.sql.engine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.metronus.util.StringUtil;
import br.com.metronus.util.exception.PropertyNotFoundException;

/**
 * @author Andre Fonseca
 * <p> Classe que cont�m a logica de carregamento das
 *         propriedades de conex�o Classe respons�vel por carregar as
 *         configura��es do arquivo XML. Esta classe � o grande reposit�rio das
 *         configura��es. <br>. Atrav�s dela o desenvolvedor pode acessar as
 *         configura��es, bem como altera-las em tempo de execu��o </p>
 */
public final class Configure {

    private static Configure configure = null;

    private HashMap configurations = new HashMap();

    private static String path = "connection.cfg.xml";

    private long startTime;   
    
    private HashMap queryFiles = new HashMap();

    private static Log log = LogFactory.getLog(Configure.class);

    private Configure() {
        load(path);
    }
    
    protected QueryConfiguration findQuery(String path,String key) throws ParserConfigurationException, SAXException, IOException, TransformerException, PropertyNotFoundException{        
        File file = null;
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;
        QueryConfiguration config = null;
        
        //Acessando o arquivo mapeados
        file = new File((String)queryFiles.get(path));
        dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		doc = db.parse(file);
		Node node = XPathAPI.selectSingleNode(doc,"//query[attribute::name='"+key+"']");
		
		//Caso n�o encontre nada o sistema dispara um exce��o
		if(node == null){
		    throw new PropertyNotFoundException("Query n�o encontrada. Verifique os parametros passados. Chave["+key+"], arquivo["+path+"]");
		}
		
		Node nodeQueryTimeOut= XPathAPI.selectSingleNode(node,"attribute::timeout");
		config = new QueryConfiguration();
		config.setNome(key);
        config.setSql(StringUtil.retiraCaracteresEspeciais(node.getFirstChild().getNodeValue()));            
        if(nodeQueryTimeOut!= null){
            config.setTimeOut(Integer.parseInt(nodeQueryTimeOut.getFirstChild().getNodeValue()));
        }
        return config;        
    }    
    /**
     * Metodo para adicionar uma configura��o ao repositorio
     * 
     * @param nome
     *            String com a chave da configura��o de conex�o
     * @param config
     *            ConnectionConfigure com os dados da conex�o
     */
    public void addConnection(String nome, ConnectionConfigure config) {
        log.debug("Adicionando nova conex�o a configura��es");
        configurations.put(nome, config);
    }

    /**
     * Metodo para retornar um configura��o de conex�o default
     * 
     * @return ConnectionConfigure com os dados de configura��o
     */
    public ConnectionConfigure getConnection() {
        log.debug("Obtendo a conex�o default");
        return (ConnectionConfigure) configurations.get("default");
    }

    /**
     * Metodo para retornar um configura��o de conex�o associada a chave
     * informada
     * 
     * @param key
     *            String com o nome da chave da configura��o
     * @return ConnectionConfigure com os dados de configura��o
     */
    public ConnectionConfigure getConnection(String key) {
        log.debug("Obtendo a conex�o " + key);
        return (ConnectionConfigure) configurations.get(key);
    }

    /**
     * Metodo para iniciar as configura��es das conex�es retornando o objeto
     * 
     * @return instancia unica de Configure na VM
     */
    public static Configure buildConfigure() {
        log.debug("Iniciando a leitura do arquivo de configura��o");
        if (configure == null) {
            configure = new Configure();
            configure.startTime = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - configure.startTime) > 300000) {
            //A cada 5 minutos os sistema recarregas as propriedades
            configure.load(path);
        }
        return configure;
    }

    /**
     * Metodo para iniciar as configura��es das conex�es retornando o objeto
     * 
     * @param path
     *            String com o caminho do arquivo de configura��es
     * @return instancia unica de Configure na VM
     */
    public static Configure buildConfigure(String path) {
        Configure.path = path;
        if (configure == null) {
            configure = new Configure();
            configure.startTime = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - configure.startTime) > 600000) {
            configure.load(path);
        }
        return configure;
    }

    /**
     * Metodo para carregar as propriedades do arquivo de xml
     * 
     * @param path
     *            String informando o arquicvo xml onde est�o as configura��es
     */
    private void load(String path) {
        log.debug("Lendo as configura��es de conex�o");
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        ConnectionConfigure connectionConfigure = null;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            Element root = doc.getDocumentElement();

            //Buscando as execu�oes com o nome desejado
            NodeList connections = XPathAPI.selectNodeList(root, "//connection");            

            //Loop de leitura dos n�s encontrados
            for (int i = 0; i < connections.getLength(); i++) {
                connectionConfigure = new ConnectionConfigure();

                //Obtendo o tipo da conex�o
                int tipoConexao = getTipo(connections.item(i));
                connectionConfigure.setTipo(tipoConexao);

                switch (tipoConexao) {
                case ConnectionConfigure.CONNECTION:
                    connectionConfigure.setUrl(getURL(connections.item(i)));
                    connectionConfigure.setUser(getUser(connections.item(i)));
                    connectionConfigure.setPassword(getPassword(connections.item(i)));
                    connectionConfigure.setDriverClass(getDriverClass(connections.item(i)));
                    connectionConfigure.setShowQuery(getShowQuery(connections.item(i)));
                    break;

                case ConnectionConfigure.DATASOURCE:
                    connectionConfigure.setDataSource(getDataSource(connections
                            .item(i)));
                    connectionConfigure.setUser(getUser(connections.item(i)));
                    connectionConfigure.setPassword(getPassword(connections
                            .item(i)));
                    connectionConfigure.setShowQuery(getShowQuery(connections.item(i)));
                    break;

                case ConnectionConfigure.POOL_CONNECTION:
                    connectionConfigure.setUrl(getURL(connections.item(i)));
                    connectionConfigure.setUser(getUser(connections.item(i)));
                    connectionConfigure.setPassword(getPassword(connections
                            .item(i)));
                    connectionConfigure.setPoolSize(getPoolSize(connections
                            .item(i)));
                    connectionConfigure
                            .setDriverClass(getDriverClass(connections.item(i)));
                    connectionConfigure.setShowQuery(getShowQuery(connections.item(i)));
                    break;
                }

                //Colocando a configura��o na cole��o de mapeamento
                configurations.put(getNome(connections.item(i)),
                        connectionConfigure);
            }

            //Carregando as querys mapeadas
            loadQuery(root);

        } catch (ParserConfigurationException e) {
            log.error("Erro parseando o xml", e);            
        } catch (SAXException e) {
            log.error("Erro interpretando o xml", e);            
        } catch (IOException e) {
            log.error("Erro acessando o arquivo do xml", e);            
        } catch (TransformerException e) {
            log.error("Erro traduzindo o XPATH o xml", e);            
        } catch(NullPointerException e){
            log.error(e.getMessage(), e);  
        }
    }

    /**
     * Metodo para a carregar as propriedades de querys do arquivo xml
     * @param root No pai para realizar a busca dentro
     * @throws TransformerException
     */
    private void loadQuery(Node root) throws TransformerException,NullPointerException {
        NodeList query = XPathAPI.selectNodeList(root, "//query-mapping/query-file");
        Node cache = XPathAPI.selectSingleNode(root, "//query-mapping/property[attribute::name='cache-size']");
        Node alias = null;
        if (query == null || query.getLength() == 0) {
            return;
        }                
        //Varrendo os n�s para obter seus valores
        for (int i = 0; i < query.getLength(); i++) {
            alias = XPathAPI.selectSingleNode(query.item(i), "attribute::alias");
            if(alias == null){
                throw new NullPointerException("erro no mapeamento do arquivo de querys");
            }            
            queryFiles.put(alias.getFirstChild().getNodeValue(),StringUtil.retiraCaracteresEspeciais(query.item(i).getFirstChild().getNodeValue()));
        }
        
        if(cache == null){
            QueryRepository.buildCache(10);
            return;
        }
        int max = Integer.parseInt(cache.getFirstChild().getNodeValue());
        QueryRepository.buildCache(max);
    }

    // --------------------------------- Metodos para pegar os dados do XML --------------------------------- //
    private int getTipo(Node pai) throws TransformerException {
        Node tipoNode = XPathAPI.selectSingleNode(pai, "attribute::type");
        String tipoNome = tipoNode.getFirstChild().getNodeValue();
        if (tipoNome.equalsIgnoreCase("driver")) {
            return ConnectionConfigure.CONNECTION;
        } else if (tipoNome.equalsIgnoreCase("pool_driver")) {
            return ConnectionConfigure.POOL_CONNECTION;
        } else if (tipoNome.equalsIgnoreCase("datasource")) {
            return ConnectionConfigure.DATASOURCE;
        } else {            
            throw new NullPointerException("Nenhum tipo conhecido foi definido. Os tipos s�o: driver, pool_driver, datasource");
        }
    }

    private String getNome(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai, "attribute::name");        
        if (node == null) {           
            throw new NullPointerException("Atributo nome est� faltando");
        }
        return node.getFirstChild().getNodeValue();
    }

    private String getURL(Node pai) throws TransformerException {
        //Obtendo os dados para um conex�o via driverConnection        
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='url']");
        return node.getFirstChild().getNodeValue();
    }

    private String getUser(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='user']");
        return node.getFirstChild().getNodeValue();
    }

    private String getPassword(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='password']");
        return node.getFirstChild().getNodeValue();
    }

    private int getPoolSize(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='pool.size']");
        return Integer.parseInt(node.getFirstChild().getNodeValue());
    }

    private String getDriverClass(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='driver.class']");
        return node.getFirstChild().getNodeValue();
    }

    private String getDataSource(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,
                "//property[attribute::name='datasource']");
        return node.getFirstChild().getNodeValue();
    }
    private boolean getShowQuery(Node pai) throws TransformerException {
        Node node = XPathAPI.selectSingleNode(pai,"//property[attribute::name='show-query']");
        if(node ==null){
            return false;
        }
        return (node.getFirstChild().getNodeValue().equalsIgnoreCase("true"));
    }
}
