package br.com.neoris.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andre Fonseca
 *
 * Classe para ajudar na escrita de um string com formato proprietário
 * 
 */
public class StringWriterUtil {

    private StringBuffer buffer = new StringBuffer();
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    /**
     * Metodo para escrever um dado a partir de uma string
     * @param tamanho  tamanho que deve ser a palavra
     * @param valor a string com o valor
     * @param coringa string que será usada para completar o tamanho que resta
     * @param op define se o coringa será inserido a direita ou esquerda
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil append(int tamanho,String valor,char coringa,int op) throws IllegalArgumentException{
        //Verificando se o valor passado está dentro do esperado
        if(valor.length() > tamanho){
            throw new IllegalArgumentException("Valor contem tamanho maior que o estipulado para a palavra");
        }
        
        //Realizando o append
        if(op == StringWriterUtil.LEFT){
            buffer.append(StringUtil.addCharLeft(valor,coringa,(tamanho - valor.length())));
        }else if (op == StringWriterUtil.RIGHT) {
            buffer.append(StringUtil.addCharRight(valor,coringa,(tamanho - valor.length())));
        }
        return this;
    }
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor String com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,String valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,valor,coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor String com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,String valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,valor,coringa,1);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,int valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,int valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,1);
    }
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,float valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,float valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,1);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,double valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,double valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,1);
    }
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,boolean valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,boolean valor,char coringa) throws IllegalArgumentException{
         return append(tamanho,String.valueOf(valor),coringa,1);
    }
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a esquerda do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @param pattern string com o formato para a conversão da data
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithLeftComplete(int tamanho,Date valor,char coringa, String pattern) throws IllegalArgumentException{
         return append(tamanho,new SimpleDateFormat(pattern).format(valor),coringa,0);
    }
    
    /**
     * Metodo para escrever uma palavra com o tamanho dado a partir de uma string passada. Este metodo insere o coringa a direita do valor
     * @param tamanho int com o tamanho desejado para palavra
     * @param valor int com o valor
     * @param coringa char com o coringa para ser preenchido
     * @param pattern string com o formato para a conversão da data
     * @return a instancia da classe ativa
     * @throws IllegalArgumentException para o caso da string valor tenha tamanho maior que o informado no parametro tamanho
     */
    public StringWriterUtil appendWithRightComplete(int tamanho,Date valor,char coringa,String pattern) throws IllegalArgumentException{        
        return append(tamanho,new SimpleDateFormat(pattern).format(valor),coringa,1);
    }
    /**
     * metodo para adicionar uma palavra de espaço em brancos de tamanho definido
     * @param tamanho int com o tamanho da palavra
     * @return instancia ativa da classe
     */
    public StringWriterUtil appendBlankSpace(int tamanho){
        buffer.append(StringUtil.addSpaceLeft("",tamanho));
        return this;
    }
    /**
     * Metodo para indicar uma nova linha para escritor
     * @return instancia ativa da classe
     */
    public StringWriterUtil newLine(){
        buffer.append("\n");
        return this;
    }
    /**
     * Metodo para limpar o buffer da classe 
     * @return a instancia ativa da classe
     */
    public StringWriterUtil clear(){
        buffer.delete(0,buffer.length());
        return this;
    }
    
    /**
     * Méotodo para obter a string gerada
     * @return String gerada
     */
    public String getString(){
        return buffer.toString();
    }
    
    /**
     * Metodo para escrever para um arquivo o conteudo gerado pela classe
     * @param path String com o caminho completo do arquivo e seu nome
     * @throws FileNotFoundException caso não consiga achar o arquivo
     */
    public void writeToFile(String path) throws FileNotFoundException{
        File file = new File(path);
        PrintWriter writer = new PrintWriter(new FileOutputStream(file));
        writer.print(buffer.toString());
        writer.flush();
        writer.close();
    }
}
