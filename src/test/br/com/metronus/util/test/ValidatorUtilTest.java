package br.com.metronus.util.test;

import junit.framework.TestCase;
import br.com.metronus.util.validation.field.SimpleFieldImpl;
import br.com.metronus.util.validation.field.Response;
import br.com.metronus.util.validation.implementation.field.ChecaCPF;
import br.com.metronus.util.validation.implementation.field.ChecaCampoNumerico;
import br.com.metronus.util.validation.implementation.field.ChecaEmail;
import br.com.metronus.util.validation.implementation.field.ChecaSeNaoTemRegExp;
import br.com.metronus.util.validation.implementation.field.ChecaSeTemRegExp;

/**
 * @author Andre Fonseca
 *
 * classe para teste do validatorUtil
 */
public class ValidatorUtilTest extends TestCase {  

    public void testValidaSeContem() {
        ChecaSeTemRegExp validator = new ChecaSeTemRegExp("[a-z]");        
        Response resp = validator.validate(new SimpleFieldImpl("nome","abcd123ef"));
        assertEquals(false,resp.isValid());
    }

    public void testValidaSeNaoContem() {
        ChecaSeNaoTemRegExp validator = new ChecaSeNaoTemRegExp("[a-z]");        
        Response resp = validator.validate(new SimpleFieldImpl("nome","abcd123ef"));
        assertEquals(true,resp.isValid());
        //Validando no caso de dado não valido
        validator.setRegExp("[0-9]");
        resp = validator.validate(new SimpleFieldImpl("nome","abcdef"));
        assertEquals(false,resp.isValid());
    }

    public void testValidaEmail() {
        ChecaEmail validator = new ChecaEmail();
        Response resp = validator.validate(new SimpleFieldImpl("nome","abcd123ef"));
        assertEquals(false,resp.isValid());
        resp = validator.validate(new SimpleFieldImpl("nome","metronus@metronus.com.br"));
        assertEquals(true,resp.isValid());
    }

    public void testValidaCampoNumerico() {       
        ChecaCampoNumerico validator = new ChecaCampoNumerico();
        Response resp = validator.validate(new SimpleFieldImpl("nome","abcd123ef"));
        assertEquals(false,resp.isValid());
        resp = validator.validate(new SimpleFieldImpl("nome","123456"));
        assertEquals(true,resp.isValid());
    }

    public void testValidaSeNuloOuVazio() {        
    }

    public void testValidaMinSize() {        
    }

    public void testValidaMaxSize() {       
    }
    
    public void testCPF(){
        ChecaCPF validator = new ChecaCPF();
        Response resp = validator.validate(new SimpleFieldImpl("cpf","11111111111"));
        assertTrue(resp.isValid());
    }
}
