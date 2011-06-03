package br.com.metronus.util.validation.implementation.field;

import br.com.metronus.util.validation.field.Field;
import br.com.metronus.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 * Classe para validar um CPF
 */
public class ChecaCPF extends AbstractChecker {
    
    
    /* (non-Javadoc)
     * @see br.com.metronus.util.validation.field.Checker#validate(br.com.metronus.util.validation.field.Field)
     */
    public Response validate(Field field) {        
        Response response = new Response(true,field, null);
        if(!(field.getValor() instanceof String)){
            response.setValid(false);
            response.setMessage("CPF INVALIDO");
        }
        String valor  = (String) field.getValor();
        String numeros = valor.substring(0,9);
        String digitoVerificador = valor.substring(9);
        //Valida matematicamente
        if(!checa(numeros,digitoVerificador)){
            response.setValid(false);
            response.setMessage("CPF INVALIDO");
        }        
        return response;
    }
    
    private boolean checa(String valor, String digitoVerificador){
        char [] dig = valor.toCharArray();
        int d = 0;
        for(int i =0;i<dig.length;i++){
            d += Character.getNumericValue(dig[i])*(10-i);            
        }
        if(d==0){
            return false;
        }
        
        d = 11 - (d % 11);
        if (d > 9) d = 0;
        int dv = Character.getNumericValue(digitoVerificador.charAt(0));        
        if (dv != d){ 
            return false;
        }
        d *= 2;
        for (int i = 0; i < 9; i++) {
            d += Character.getNumericValue(dig[i])*(11-i); 
        }
        d = 11 - (d % 11);
        if (d > 9) d = 0;
        dv = Character.getNumericValue(digitoVerificador.charAt(1));        
        if (dv != d){ 
            return false;
        }
        return true;
    }

}
