package br.com.metronus.util.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.metronus.util.rules.base.RuleBase;

/**
 * @author Andre Fonseca
 *
 * Classe responsável por manter em mémoria as regras que podem ter
 * vindo de diversas bases (xml, banco de dados, etc)
 */
public class RuleContainer {

    private Collection ruleBases = new ArrayList();
    private HashMap rules = new HashMap();
    private Log log = LogFactory.getLog(RuleContainer.class);
    private RuleRepository  cache;

    public RuleContainer(){
        cache = new RuleRepository();
    }

    /**
     * Registra um base de regras para o container
     * @param base
     */
    public void registerBase(RuleBase base){
        log.debug("Register rule base");
        ruleBases.add(base);
    }

    public void registerRule(Rule rule){
    }

    public Rule getRule(Object key){
        log.debug("Searching for rule in register base");
        Rule rule = (Rule)cache.getElement(key);
        if(rule == null){
			Iterator iterator = ruleBases.iterator();
			RuleBase base = null;
			while(iterator.hasNext()){
				base = (RuleBase)iterator.next();
				rule = base.getRule(key);
			}
			if(rule == null){
				log.debug("rule is null. Not found");
				throw new NullPointerException("Rule not found");
			}
			cache.addElement(key,rule);
        }
        return rule;
    }
}
class RuleRepository  extends LinkedHashMap{

	/**
	 * valor criado pelo eclipse, para permitir que uma classes serializada possa ser deserializada apos uma mudanca em sua implementacao
	 * (ver http://www.javapractices.com/Topic45.cjp para mais detalhes)
	 */
	private static final long serialVersionUID = -4816905127904810724L;

	private static int maxLength = 10;

    /**
     * Metodo construtor do cache. Ele é private pois queremos implementar um estrutura de singletown
     *
     */
    protected RuleRepository(){
        super(maxLength,0.75f,true);
    }

    protected boolean removeEldestEntry(){
        return (this.size() > maxLength);
    }


    /**
     * Metodo para adicionar um novo elemento ao cache
     * @param config
     */
    protected  synchronized void addElement(Object key,Object obj){
        this.put(key,obj);
    }

    /**
     * Metodo para obter um elemento que esta registrado dentro do cache <br>
     * @param key String com a chave do elemento
     * @return Objeto dados dentro do cache
     */
    public Object getElement(Object key){
        return this.get(key);
    }

}
