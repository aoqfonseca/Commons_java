package br.com.metronus.util.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Title ExportCSV.java <b>NEORIS - Brasil </b>
 * Classe que realizar a interface entre um arquivo CSV e um VO. Com esta classe o desenvolvedor
 * consegue mapear as propriedades de um BeanWrapper e passar os valores delas para um arquivo CSV. 
 * O caminho inverso também é feito, ou seja, a partir de um arquivo CSV ele popula propriedades mapeadas de um bean 
 * @author Andre Fonseca
 * @version 1.0
 */
public class ExportCSV {

    private ArrayList mapping;
    private Collection objetos;
    private String SEPARADOR = ",";

    /**
     * Metodo construtor que deve ser usado
     * 
     * @param vo  Coleção dos VO a serem exportados para CVS
     */
    public ExportCSV(Collection vo) {
        mapping = new ArrayList();
        this.objetos = vo;
    }

    /**
     * Construtor simples
     *  
     */
    public ExportCSV() {
        mapping = new ArrayList();
    }   
    /**
     * Metodo para popular os VO com os dados do arquivo csv.
     * 
     * @param file String com o caminho do arquivo CSV
     * @param bean String com a classe do VO
     * @return Collection de VO com os dados contidos no arquivo CSV
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     */
    public Collection restore(String file, String bean)
            throws IOException, InstantiationException, IllegalAccessException,
            ClassNotFoundException, InvocationTargetException {

        //Criando os objetos de acesso ao arquivo CSV
        File csv = new File(file);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(csv)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Verifica se existe um mapeamento feito
        if (mapping == null || mapping.isEmpty()) {
            throw new NullPointerException("Can't not find any column defined");
        }
        
        //Iniciando os objetos de leitura do arquivo e passagem para o VO
        Collection resposta = new ArrayList();
        String linha;
        Object obj;
        String metodo = null;
        String[] resultado;

        //Loop de leitura do arquivo - le linha a linha até o fim do arquivo
        while ((linha = reader.readLine()) != null) {
            //Para cada nova linha reinicia o indice dos metodos
            resultado = linha.split(SEPARADOR);
            obj = Class.forName(bean).newInstance();
            //loop de leitura dos dados da linha
            for (int i = 0; i < resultado.length; i++) {
                metodo = (String)mapping.get(i);
                BeanUtils.setProperty(obj, metodo, resultado[i]);
            }
            resposta.add(obj);
        }
        return resposta;
    }

    /**
     * Método para gerar string com os dados de um coleçao de VO no formato CSV
     * @return String que será persistida para o arquivo.
     * @throws NullPointerException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public String export() throws NullPointerException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        
        StringBuffer buffer = new StringBuffer();
        
        //Tratando os possiveis erros
        if (objetos.isEmpty()) {
            throw new NullPointerException("Data no found to export");
        } else if (mapping.isEmpty()) {
            throw new NullPointerException("Can´t find defined columns");
        }
        
        //Iniciando objetos para leitura dos metodos da classe
        Iterator iterator = objetos.iterator();
        Object obj = null;
        
        //Realizando loop na coleção de VO
        while (iterator.hasNext()) {
            obj = iterator.next();
            Iterator iteratorMethod = mapping.iterator();
            
            //Realizando loop nos metodos mapeados
            while (iteratorMethod.hasNext()) {
                buffer.append(BeanUtils.getProperty(obj,(String) iteratorMethod.next()));
                if (!iteratorMethod.hasNext()) {
                    buffer.append("\n");
                    continue;
                }
                buffer.append(SEPARADOR);
            }
        }
        obj = null;        
        return buffer.toString();
    }
    
    public void clearMapping(){
        mapping.clear();
    }

    /**
     * Metodo para adicionar o mapeamento de um campo do VO para exportação.
     * <br>
     * Observe que a ordem que inserir as colunas será a ordem que será
     * exportado     * 
     * @param metodo metodo get do campo do VO ( o nome sem o get antes e em letra minuscula) Ex: <br> produto.getPreco(), você irá escrever preco.
     * @return String
     */
    public void addMapping(String metodo) {
        mapping.add(metodo.toLowerCase());        
    }
    /**
     * Metodo para obter o separador a ser usada para separar os campos do CSV
     * 
     * @return
     */
    public String getSEPARADOR() {
        return SEPARADOR;
    }

    /**
     * Metodo para definir a string usada para separar os campos
     * 
     * @param string
     *            valor do separador
     */
    public void setSEPARADOR(String string) {
        SEPARADOR = string;
    }

    /**
     * Metodo para setar o mapeamento de propriedades do VO a serem exportadas.
     * <br>
     * Observe que os valores devem ser string e conter o nome do metodo sem o
     * get antes e todo em minusculo.
     * @param list Collection de String com os nomes do metodos get a serem chamados para importaçao
     */
    public void setMapping(ArrayList list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String valor = (String) iterator.next();
            mapping.add(valor.toLowerCase());
        }
    }     
}
