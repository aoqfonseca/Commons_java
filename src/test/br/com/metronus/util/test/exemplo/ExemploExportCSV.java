package br.com.metronus.util.test.exemplo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.com.metronus.util.export.ExportCSV;

/**
 * @author Andre Fonseca
 *
 * Classe exemplo de uso da classe utilitario ExportCSV
 * 
 */
public class ExemploExportCSV {
    
    public ExemploExportCSV(){
        ExportCSV csv = new ExportCSV();        
        csv.addMapping("nome");
		csv.addMapping("idade");	
		File arq = new File("");
		try {
            ArrayList resposta = (ArrayList) csv.restore( arq.getAbsolutePath(),Usuario.class.getName());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
    }

}

class Usuario{
    private String nome;
    private Integer idade;
    
    /**
     * @return Returns the idade.
     */
    public Integer getIdade() {
        return idade;
    }
    /**
     * @param idade The idade to set.
     */
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    /**
     * @return Returns the nome.
     */
    public String getNome() {
        return nome;
    }
    /**
     * @param nome The nome to set.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
