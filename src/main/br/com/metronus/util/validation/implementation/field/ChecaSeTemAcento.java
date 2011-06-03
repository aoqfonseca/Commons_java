package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;
import java.util.regex.Pattern;

import br.com.metronus.util.validation.field.Field;
import br.com.metronus.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 * Classe que valida se o campo(considerando que é uma string) contém acentos. Caso contenha o sistema o considerá não valido
 */
public class ChecaSeTemAcento extends AbstractChecker {

    private final String ACENTO_REGEXP= "[á,à,â,ã,ä,é,è,ê,ë,í,ì,î,ï,ó,ò,ô,õ,ö,ú,ù,û,ü,Á,À,Â,Ã,Ä,É,È,Ê,Ë,Í,Ì,Î,Ï,Ó,Ò,Ô,Õ,Ö,Ú,Ù,Û,Ü,ñ,Ñ,ç,Ç,\\\']";
    
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
