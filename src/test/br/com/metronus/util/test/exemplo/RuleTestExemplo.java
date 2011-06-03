package br.com.neoris.util.test.exemplo;

import br.com.neoris.util.rules.Rule;
import br.com.neoris.util.rules.RuleContainer;
import br.com.neoris.util.rules.base.xml.RuleBaseXmlImpl;

/**
 * @author Andre Fonseca
 *
 * Classe exemplo de uso da api de regras
 */
public class RuleTestExemplo {

    public RuleTestExemplo(){
        RuleContainer container = new RuleContainer();
        container.registerBase(new RuleBaseXmlImpl("C:\\eclipse\\workspace\\NeorisUtil\\etc\\rule.xml"));
        Rule rule = container.getRule("teste");       
        rule.assertObject("nome_teste","Andre");
        rule.assertObject("sobrenome_teste","Andre");
        rule.assertObject("tipo_teste","normal");
        boolean ret = rule.fire();
        System.out.println("resultado " + ret);
        
        rule.assertObject("nome_teste","Neuber");
        rule.assertObject("sobrenome_teste","Paiva");
        rule.assertObject("tipo_teste","cara");
        ret = rule.fire();
        System.out.println("resultado " + ret);
    }
    
    public void teste(){
        
        Rule rule = new RuleTeste();
        rule.assertObject("nome_teste","Andre");
        rule.assertObject("sobrenome_teste","w");
        rule.assertObject("tipo_teste","");
        rule.and(new TestConstrain());
        rule.and(new TestConstrain2());
        rule.or(new TestConstrain3());
        boolean test = rule.fire();
        System.out.println("Retorno:"+ test);
        
    }
    
    public static void main (String args[]){
        new RuleTestExemplo();
    }
    
}
class RuleTeste extends Rule{
            
    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.Rule#fail()
     */
    public void fail() {
     System.out.println("false");

    }
    /* (non-Javadoc)
     * @see br.com.neoris.util.rules.Rule#success()
     */
    public void success() {
        System.out.println("ok");

    }
}
