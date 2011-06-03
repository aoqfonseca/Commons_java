package br.com.metronus.util.validation.implementation.field;

import java.util.Locale;
import java.util.regex.Pattern;

import br.com.metronus.util.validation.field.Field;
import br.com.metronus.util.validation.field.Response;

/**
 * @author Andre Fonseca
 * 
 * Classe que realiza a valida��o a partir de um express�o regular. A classe
 * valida se o campo n�o contem a express�o passado, sendo assim caso n�o tenha
 * a express�o n�o � valida
 */
public class ChecaSeNaoTemRegExp extends AbstractChecker {

    private String regExp;
    
    

    /**
     * @return Returns the regExp.
     */
    public String getRegExp() {
        return regExp;
    }
    /**
     * @param regExp The regExp to set.
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }
    /*
     * (non-Javadoc)
     * 
     * @see br.com.metronus.util.validation.field.Checker#validate(br.com.metronus.util.validation.field.Field)
     */
    public Response validate(Field field) {
        Response resposta = new Response(true, field, null);
        if (field== null ||field.getValor()== null ||!Pattern.compile(regExp).matcher(field.getValor().toString()).find()) {
            resposta.setValid(false);
            resposta.setMessage(bundle.getString("msg_generica"));
        }
        return resposta;
    }

    /**
     *  
     */
    public ChecaSeNaoTemRegExp(String expression) {
        super();
        this.regExp = expression;
    }

    /**
     * @param locale
     */
    public ChecaSeNaoTemRegExp(Locale locale, String expression) {
        super(locale);
        this.regExp = expression;
    }
}
