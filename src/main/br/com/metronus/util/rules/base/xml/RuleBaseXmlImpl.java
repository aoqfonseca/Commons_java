package br.com.neoris.util.rules.base.xml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.neoris.util.rules.Constraint;
import br.com.neoris.util.rules.Rule;
import br.com.neoris.util.rules.base.BeanWrapper;
import br.com.neoris.util.rules.base.RuleBase;
import br.com.neoris.util.rules.base.BaseRuleImpl;

/**
 * @author Andre Fonseca
 * 
 * Classe que representa um arquivo xml com as regras
 */
public class RuleBaseXmlImpl implements RuleBase {

    private File file;
    private Log log = LogFactory.getLog(RuleBaseXmlImpl.class);
    private HashMap rulesMap;

    private static boolean flag = true;

    public RuleBaseXmlImpl(String path) {
        super();
        file = new File(path);
        rulesMap = new HashMap();
        if(flag){
            try {
                parser();
            } catch (SecurityException e) {
                log.error(e.getMessage(),e);
            } catch (ParserConfigurationException e) {
                log.error(e.getMessage(),e);
            } catch (SAXException e) {
                log.error(e.getMessage(),e);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            } catch (TransformerException e) {
                log.error(e.getMessage(),e);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(),e);
            } catch (InvocationTargetException e) {
                log.error(e.getMessage(),e);
            } catch (InstantiationException e) {
                log.error(e.getMessage(),e);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(),e);
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    private synchronized void parser() throws ParserConfigurationException,
            SAXException, IOException, TransformerException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException, ClassNotFoundException, SecurityException,
            NoSuchMethodException {
        
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db         = null;
        Document doc               = null;

        //Acessando o arquivo mapeados
        dbf = DocumentBuilderFactory.newInstance();
        db  = dbf.newDocumentBuilder();
        doc = db.parse(file);
        log.debug("Create parser document");

        //Obtendo o elemento root para poder a partir dele percorrer o
        // documento
        Element root   = doc.getDocumentElement();
        NodeList rules = XPathAPI.selectNodeList(root, "//rule");
        BaseRuleImpl rule   = null;

        //Criando loop para a leitura dos dados da regra
        log.debug("Begin read a xml, searching for rules");
        for (int i = 0; i < rules.getLength(); i++) {
            log.debug("Find rule");
            String ruleNome = XPathAPI.selectSingleNode(rules.item(i),"attribute::name").getFirstChild().getNodeValue();
            rule = new BaseRuleImpl();
            rule.setNome(ruleNome);

            //Obtendo os grupos de or
            NodeList Or = XPathAPI.selectNodeList(rules.item(i), "or");
            for (int j = 0; j < Or.getLength(); j++) {
                log.debug("Find or group");
                NodeList And = XPathAPI.selectNodeList(Or.item(j), "and");
                Constraint obj = null;
                for (int h = 0; h < And.getLength(); h++) {
                    log.debug("Find 'and' tag");
                    //Obtendo a classe do ruleItem
                    String ruleItemClass = XPathAPI.selectSingleNode(And.item(h), "attribute::class").getFirstChild().getNodeValue();
                    log.debug("Create constraint from class :" +ruleItemClass);
                    obj = (Constraint) Class.forName(ruleItemClass).newInstance();                    
                    NodeList props = XPathAPI.selectNodeList(And.item(h),"propery");
                    for (int l = 0; l < props.getLength(); l++) {
                        log.debug("Read properties for constraint");
                        String propNome = XPathAPI.selectSingleNode(
                                props.item(l), "attribute::name").getFirstChild().getNodeValue();
                        String propValue = XPathAPI.selectSingleNode(
                                props.item(l), "attribute::value").getFirstChild().getNodeValue();
                        BeanUtils.setProperty(obj, propNome, propValue);
                    }
                    if (h == 0) {
                        rule.or(obj);
                        continue;
                    }
                    rule.and(obj);
                }
            }

            //Obtendo as consequence para fail
            NodeList fail = XPathAPI.selectNodeList(rules.item(i),"//fail/execute");
            for (int j = 0; j < fail.getLength(); j++) {
                String classe = XPathAPI.selectSingleNode(fail.item(i),"attribute::class").getFirstChild().getNodeValue();
                String metodo = XPathAPI.selectSingleNode(fail.item(i),"attribute::method").getFirstChild().getNodeValue();
                Object obj = Class.forName(classe).newInstance();
                log.debug("Execute when fail:" + classe +"."+ metodo+"()");             
                NodeList props = XPathAPI.selectNodeList(fail.item(j),"//fail/execute/property");                
                for (int h = 0; h < props.getLength(); h++) {
                    String propNome = XPathAPI.selectSingleNode(props.item(h),"attribute::name").getFirstChild().getNodeValue();
                    String propValue = XPathAPI.selectSingleNode(props.item(h),"attribute::value").getFirstChild().getNodeValue();
                    BeanUtils.setProperty(obj, propNome, propValue);
                }
                //Adicionando o execution
                rule.addFail(new BeanWrapper(obj.getClass().getDeclaredMethod(metodo, null), obj));
            }

            // Obtendo as consequence para fail
            NodeList success = XPathAPI.selectNodeList(rules.item(i),"//success/execute");
            for (int j = 0; j < success.getLength(); j++) {
                String classe = XPathAPI.selectSingleNode(success.item(j),"attribute::class").getFirstChild().getNodeValue();
                String metodo = XPathAPI.selectSingleNode(success.item(j),"attribute::method").getFirstChild().getNodeValue();
                log.debug("Execute when success :" + classe +"."+ metodo+"()");
                Object obj = Class.forName(classe).newInstance();
                NodeList props = XPathAPI.selectNodeList(fail.item(j),"//success/execute/property");                
                for (int h = 0; h < props.getLength(); h++) {
                    log.debug("Get property to set in bean instance");
                    String propNome = XPathAPI.selectSingleNode(props.item(h),"attribute::name").getFirstChild().getNodeValue();
                    String propValue = XPathAPI.selectSingleNode(props.item(h),"attribute::value").getFirstChild().getNodeValue();
                    BeanUtils.setProperty(obj, propNome, propValue);
                }
                rule.addSuccess(new BeanWrapper(obj.getClass().getDeclaredMethod(metodo, null), obj));
            }
            rulesMap.put(ruleNome, rule);
        }
        flag= false;
    }
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.base.RuleBase#getAllRules()
     */
    public Collection getAllRules() {        
        return rulesMap.values();
    }
    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.base.RuleBase#getRule(java.lang.Object)
     */
    public Rule getRule(Object key) {        
        return (Rule)rulesMap.get(key);
    }
    
}
