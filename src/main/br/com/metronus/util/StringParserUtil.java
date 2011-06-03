package br.com.neoris.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Andre Fonseca
 *
 * Classe que auxilia no parser de uma linha string. Esta classe permite que vc leia a linha e v� retirando peda�os de tamanho
 * definido no formato desejado (float, int, double, date, etc).
 */
public class StringParserUtil {

    private String linha;
    private int cursorPosicao = 0;
    /**
     * @return Returns the linha.
     */
    public String getLinha() {
        return linha;
    }
    /**
     * @param linha The linha to set.
     */
    public void setLinha(String linha) {
        this.linha = linha;
    }
    /**
     * M�todo construtor da Classe
     */
    public StringParserUtil() {
        super();
    }
    /**
     * Construtor da classe 
     * @param valor linha a ser parseada
     */
    public StringParserUtil(String valor){
        super();
        this.linha = valor;                
    }
    
    /**
     * M�todo para retornar um peda�o da linha a partir da ultima posi��o do cursor
     * @param tamanho tamanho do peda�o da linha que deve ser obtido
     * @return String com o valor do peda�o 
     */
    private String parser(int tamanho){
        cursorPosicao+=tamanho;
        if(cursorPosicao > linha.length())
            return linha.substring(cursorPosicao-tamanho, linha.length());        
        return linha.substring(cursorPosicao-tamanho, cursorPosicao);
    }
    /**
     * M�todo para retornar a substring como string
     * @param tamanho int com o tamanho da substring
     * @return String com o valor
     */
    public String asString(int tamanho){
        return parser(tamanho);
    }
    
    /**
     * M�todo para retornar a substring como int
     * @param tamanho int com o tamanho da substring
     * @return int com o valor
     */
    public int asInteger(int tamanho){
        return Integer.parseInt(parser(tamanho).trim());
    }
    
    
    /**
     * M�todo para retornar a substring como boolean
     * @param tamanho int com o tamanho da substring
     * @return boolean com o valor
     */
    public boolean asBoolean(int tamanho){
        return Boolean.getBoolean(parser(tamanho).trim());
    }
    
    
    /**
     * M�todo para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public long asLong(int tamanho){
        return Long.parseLong(parser(tamanho).trim());
    }
    /**
     * M�todo para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public double asDouble(int tamanho){
        return Double.parseDouble(parser(tamanho).trim());
    }
    /**
     * M�todo para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public float asFloat(int tamanho){
        return Float.parseFloat(parser(tamanho).trim());
    }
    /**
     * Metodo para ler o peda�o da linha e retornar como um Date
     * @param tamanho int com o tamanho da palavra para ser pega
     * @param formato String com pattern da data para a transforma��o
     * @return java.util.Date 
     * @throws ParseException caso ocorra algum erro na transforma��o de string para a data
     */
    public java.util.Date asDate(int tamanho, String formato) throws ParseException{
        return new SimpleDateFormat(formato).parse(asString(tamanho));
    }
    /**
     * M�todo para mover cursos de uma quantidade determinada
     * @param quant int com a quantidade que deve o cursos andar
     */
    public void move(int quant){
        cursorPosicao+=quant;
    }
    
    
    /**
     * Metodo para verificar o tamanho da linha
     * @return int com o tamanho (numero de caracteres) da linha
     */
    public int length(){
        return linha.length();
    }
    
    
    
    

}
