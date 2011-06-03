package br.com.neoris.util.validation.implementation.field;

import java.util.Locale;
import java.util.regex.Pattern;

import br.com.neoris.util.validation.field.Field;
import br.com.neoris.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 * Classe que valida atrav�s de uma express�o regular passada. O dado � valido quando o sistema n�o encontra a express�o regular passada
 */
public class ChecaSeTemRegExp extends AbstractChecker {

    private String regExp;
    
    /* (non-Javadoc)
     * @see br.com.neoris.util.validation.field.Checker#validate(br.com.neoris.util.validation.field.Field)
     */
    public Response validate(Field field) {
        Response resposta = new Response(true, field, null);
        if (field== null ||field.getValor()== null ||Pattern.compile(regExp).matcher(field.getValor().toString()).find()) {
            resposta.setValid(false);
            resposta.setMessage(bundle.getString("msg_generica"));
        }
        return resposta;
    }
    /**
     * 
     */
    public ChecaSeTemRegExp(String expression) {        
        super();
        this.regExp = expression;
    }

    /**
     * @param locale
     */
    public ChecaSeTemRegExp(Locale locale,String expression) {
        super(locale);
        this.regExp = expression;
    }

}
