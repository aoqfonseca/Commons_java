package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;
import java.util.regex.Pattern;

import br.com.metronus.util.validation.field.Field;
import br.com.metronus.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 * Classe que valida se o campo(considerando que � uma string) cont�m acentos. Caso contenha o sistema o consider� n�o valido
 */
public class ChecaSeTemAcento extends AbstractChecker {

    private final String ACENTO_REGEXP= "[�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,�,\\\']";
    
    /* (non-Javadoc)
     * @see br.com.metronus.util.validation.field.Checker#validate(br.com.metronus.util.validation.field.Field)
     */
    public Response validate(Field field) {
        Response resposta = new Response(true, field, null);
        if (field== null ||field.getValor()== null ||Pattern.compile(ACENTO_REGEXP).matcher(field.getValor().toString()).find()) {
            resposta.setValid(false);
            resposta.setMessage(bundle.getString("msg_contem_acento"));
        }
        return resposta;
    }
    
    /**
     * 
     */
    public ChecaSeTemAcento() {
        super();       
    }

    /**
     * @param locale
     */
    public ChecaSeTemAcento(Locale locale) {
        super(locale);
    }

}
