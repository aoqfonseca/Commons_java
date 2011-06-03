package br.com.metronus.util.validation.field;

/**
 * @author Andre Fonseca
 *
 * Classe que representa a resposta de um valida��o
 */
public class Response {
    
    private boolean valid;
    private Field field;
    private String message;
        
    /**
     * @param valida
     * @param field
     * @param message
     */
    public Response(boolean valida, Field field, String message) {
        super();
        this.valid = valida;
        this.field = field;
        this.message = message;
    }
    /**
     * Metodo para retornar o campo validado
     * @return field 
     */
    public Field getField() {
        return field;
    }
    /**
     * Metodo para definir o campo validado
     * @param field
     */
    public void setField(Field field) {
        this.field = field;
    }
    /**
     * metodo para obter a mensagem da valida��o
     * @return
     */
    public String getMessage() {
        return message;
    }
    /**
     * Metodo para definir a mensagem da valida��o
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Metodo para verificar se o campo validado � v�lido ou
     * @return true caso de validado e false para caso o validador encontre algum problema
     */
    public boolean isValid() {
        return valid;
    }
    /**
     * Metodo para definir se o campo � valido ou n�o
     * @param valida
     */
    public void setValid(boolean valida) {
        this.valid = valida;
    }
}
