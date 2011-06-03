package br.com.metronus.util.test;

import br.com.metronus.util.StringParserUtil;
import junit.framework.TestCase;

/**
 * @author wsadm
 *
 * Classe de teste da classe util de parseamento de string  
 */
public class StringParserUtilTest extends TestCase {
    
    public void testParseamentoCorreto(){
        StringParserUtil helper = new StringParserUtil("andre orlando de queiroz fonseca");
        assertEquals("andre",helper.asString(5));
    }
    
    public void testParseamentoPassandoFim(){
        StringParserUtil helper = new StringParserUtil("andre orlando de queiroz fonseca");
        assertEquals("andre",helper.asString(5));
        assertEquals(helper.asString(1)," ");
        assertEquals(helper.asString(7),"orlando");               
        assertEquals(" de queiroz fonseca",helper.asString(40));
    }

}
