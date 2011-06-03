package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;

import br.com.metronus.util.validation.field.Field;
import br.com.metronus.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 * Classe que valida se o campo contem um email valida. Esta verificação é feita através da expressão regular
 * [a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$
 * <br>Sendo assim a classe verifica se o valor do campo contem este regExp. Caso não tenha ele diz que o campo não é valido
 */
public class ChecaEmail extends AbstractChecker {

    private final String EMAIL_REGEXP = "[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    
    /* (non-Javadoc)
     * @see br.com.metronus.util.validation.field.Checker#validate(br.com.metronus.util.validation.field.Field)
     */
    public Response validate(Field field) {
        Response response = new Response(true,field, null);
        if(field== null ||field.getValor()== null ||!field.getValor().toString().matches(EMAIL_REGEXP)){
            response.setValid(false);
            response.setMessage(bundle.getString("msg_email"));
        }
        return response;
    }
    
    /**
     * 
     */
    public ChecaEmail() {
        super();       
    }

    /**
     * @param locale
     */
    public ChecaEmail(Locale locale) {
        super(locale);
    }

}
