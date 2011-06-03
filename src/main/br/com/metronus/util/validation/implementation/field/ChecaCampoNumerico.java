package br.com.neoris.util.validation.implementation.field;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Pattern;

import br.com.neoris.util.validation.field.Field;
import br.com.neoris.util.validation.field.Response;

/**
 * @author Andre Fonseca
 * 
 * Classe que valida se o valor do campo é numerico. Caso encontre algum
 * caracterer que não seja numérico o sistema dirá que o dado não é valido
 */
public class ChecaCampoNumerico extends AbstractChecker {

    private final String REG_EXP = "[^{0-9}]";

    /*
     * (non-Javadoc)
     * 
     * @see br.com.neoris.util.validation.field.Checker#validate(br.com.neoris.util.validation.field.Field)
     */
    public Response validate(Field field) {
        Response resposta = new Response(true, field, null);
        if (!(field.getValor() instanceof Integer)
                && !(field.getValor() instanceof Double)
                && !(field.getValor() instanceof Float)
                && !(field.getValor() instanceof Long)
                && !(field.getValor() instanceof BigDecimal)) {
            if (field == null
                    || field.getValor() == null
                    || Pattern.compile(REG_EXP).matcher(
                            field.getValor().toString()).find()) {
                resposta.setValid(false);
                resposta.setMessage(bundle.getString("msg_somente_numero"));
            }
        }
        return resposta;
    }

    /**
     *  
     */
    public ChecaCampoNumerico() {
        super();
    }

    /**
     * @param locale
     */
    public ChecaCampoNumerico(Locale locale) {
        super(locale);
    }

}
