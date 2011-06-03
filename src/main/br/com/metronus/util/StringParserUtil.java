package br.com.neoris.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Andre Fonseca
 *
 * Classe que auxilia no parser de uma linha string. Esta classe permite que vc leia a linha e vá retirando pedaços de tamanho
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
     * Método construtor da Classe
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
     * Método para retornar um pedaço da linha a partir da ultima posição do cursor
     * @param tamanho tamanho do pedaço da linha que deve ser obtido
     * @return String com o valor do pedaço 
     */
    private String parser(int tamanho){
        cursorPosicao+=tamanho;
        if(cursorPosicao > linha.length())
            return linha.substring(cursorPosicao-tamanho, linha.length());        
        return linha.substring(cursorPosicao-tamanho, cursorPosicao);
    }
    /**
     * Método para retornar a substring como string
     * @param tamanho int com o tamanho da substring
     * @return String com o valor
     */
    public String asString(int tamanho){
        return parser(tamanho);
    }
    
    /**
     * Método para retornar a substring como int
     * @param tamanho int com o tamanho da substring
     * @return int com o valor
     */
    public int asInteger(int tamanho){
        return Integer.parseInt(parser(tamanho).trim());
    }
    
    
    /**
     * Método para retornar a substring como boolean
     * @param tamanho int com o tamanho da substring
     * @return boolean com o valor
     */
    public boolean asBoolean(int tamanho){
        return Boolean.getBoolean(parser(tamanho).trim());
    }
    
    
    /**
     * Método para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public long asLong(int tamanho){
        return Long.parseLong(parser(tamanho).trim());
    }
    /**
     * Método para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public double asDouble(int tamanho){
        return Double.parseDouble(parser(tamanho).trim());
    }
    /**
     * Método para retornar a substring como long
     * @param tamanho int com o tamanho da substring
     * @return long com o valor
     */
    public float asFloat(int tamanho){
        return Float.parseFloat(parser(tamanho).trim());
    }
    /**
     * Metodo para ler o pedaço da linha e retornar como um Date
     * @param tamanho int com o tamanho da palavra para ser pega
     * @param formato String com pattern da data para a transformação
     * @return java.util.Date 
     * @throws ParseException caso ocorra algum erro na transformação de string para a data
     */
    public java.util.Date asDate(int tamanho, String formato) throws ParseException{
        return new SimpleDateFormat(formato).parse(asString(tamanho));
    }
    /**
     * Método para mover cursos de uma quantidade determinada
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
