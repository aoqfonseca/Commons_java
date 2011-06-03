package br.com.neoris.util.sql.engine;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import br.com.neoris.util.exception.PropertyNotFoundException;

/**
 * @author Andre Fonseca
 *
 * Classe que será o repositorio de querys 
 */
public class QueryRepository  extends LinkedHashMap{
    

	private static final long serialVersionUID = 1L;
	private static int maxLength = 10;    
    private Log log = LogFactory.getLog(QueryRepository.class);
    private static QueryRepository cache = null;
    
    /**
     * Metodo construtor do cache. Ele é private pois queremos implementar um estrutura de singletown
     *
     */
    private QueryRepository(){
        super(maxLength,0.75f,true);        
        log.debug("Criando o cache de query");
    }
    
    /**
     * Metodo para obter a instancia ativa do cache dentro deste classloader da VM
     * @return a instancia ativa do cache
     */
    public static QueryRepository getInstance(){
        if(cache == null){
            cache = new QueryRepository();
        }
        return cache;
    }
    
    /**
     * Metodo para montar o cache a partir do arquivo de configuração
     * @param max int com o tamanho máximo do cache
     */
    protected static void buildCache(int max){
        if(max > 0)
            maxLength = max;
        cache = new QueryRepository();         
    }
    
    protected boolean removeEldestEntry(){
        return (this.size() > maxLength);
    }
    
    
    /**
     * Metodo para adicionar uma nova query ao cache
     * @param config
     */
    protected  synchronized void addQuery(QueryConfiguration config){
        log.debug("Adicionando uma nova query ao cache");
        this.put(config.getNome(),config);
    }
    
    /**
     * Metodo para obter um query que está mapeada no cache. Caso não encontre nada irá retornar null. Lembrar <br>
     * que a chave deve ser <arquivo>.<nome da query> 
     * @param key String com a chave da query
     * @return QueryConfiguration com os dados da query
     */
    public QueryConfiguration getQuery(String apelido,String key){
        Configure config = null;
        QueryConfiguration configQuery = null;        
        //Validando a chave passada
        if(apelido == null || key == null){
            throw new IllegalArgumentException("Argumento passado é incorreto");
        }
        
        //Obtendo o dado da chave
        if (this.get(key)== null){
            config = Configure.buildConfigure();
            try {
                configQuery =  config.findQuery(apelido,key);
                this.addQuery(configQuery);
                return configQuery;
            } catch (ParserConfigurationException e) {                
                log.error(e.getMessage(),e);
            } catch (SAXException e) {
                log.error(e.getMessage(),e);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            } catch (TransformerException e) {
                log.error(e.getMessage(),e);
            } catch (PropertyNotFoundException e) {
                log.error(e.getMessage(),e);
            }            
        }
        return (QueryConfiguration)this.get(key);
    }

}
