/*
 * Criado na data Feb 16, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import br.com.neoris.util.export.ExportCSV;

/**
 * Title TesteCSV.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class TesteCSV {

	public static void main(String[] args) {
		File arq = new File("teste.csv");
		ExportCSV csv = new ExportCSV();
		try {
			csv.addMapping("nome");
			csv.addMapping("endereco");
			csv.addMapping("sexo");
			csv.addMapping("idade");
			csv.addMapping("forte");
			ArrayList resposta =
				(ArrayList) csv.restore(arq.getAbsolutePath(),Pessoa.class.getName());
			System.out.println(resposta.size());
			Pessoa pessoa = (Pessoa) resposta.get(3);
			System.out.println(pessoa.getNome());
			System.out.println(pessoa.getEndereco());
			System.out.println(pessoa.getSexo());
			System.out.println(pessoa.getIdade());
			System.out.println(pessoa.getForte());			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}

