package br.com.neoris.util.validation.implementation.field;

import br.com.neoris.util.validation.field.Field;
import br.com.neoris.util.validation.field.Response;

/**
 * @author Andre Fonseca
 *
 *Classe capaz de validar o CGC
 */
public class ChecaCGC extends AbstractChecker {

    
    public Response validate(Field field) {        
        Response response = new Response(true,field, null);
        if(!(field.getValor() instanceof String)){
            response.setValid(false);
            response.setMessage("CGC INVALIDO");
        }
        String valor  = (String) field.getValor();
        String numeros = valor.substring(0,12);
        String digitoVerificador = valor.substring(12);
        //Valida matematicamente
        if(!checa(numeros,digitoVerificador)){
            response.setValid(false);
            response.setMessage("CGC INVALIDO");
        }        
        return response;
    }
    
    private boolean checa(String valor, String digitoVerificador){
        char [] dig = valor.toCharArray();
        int d = 0;
        for(int i =0;i<dig.length;i++){
            d += Character.getNumericValue(dig[11-i])*(2+(i % 8));           
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
            d += Character.getNumericValue(dig[11-i])*(2+((i+1) % 8)); 
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
