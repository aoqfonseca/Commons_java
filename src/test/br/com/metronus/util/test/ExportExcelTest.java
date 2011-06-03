package br.com.neoris.util.test;

import java.util.Iterator;

import br.com.neoris.util.export.ExportExcel;

public class ExportExcelTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String arquivo = "C:\\eclipse\\workspace\\NeorisUtil\\teste.xls";
		ExportExcel parser = new ExportExcel(Pessoa.class, arquivo);
		parser.addMapping("nome",0);
		parser.addMapping("sexo",1);
		parser.addMapping("forte",2);
		parser.addMapping("idade",3);
		parser.setGenericMethod("addData");
		
		Iterator iterator = parser.unmarshall().iterator();
		Pessoa pessoa;
		while(iterator.hasNext()){
			pessoa = (Pessoa) iterator.next();
			System.out.println("Nome : " + pessoa.getNome());
			System.out.println("Sexo : " + pessoa.getSexo());
			System.out.println("Forte  : " + pessoa.getForte());
			System.out.println("Idade : " + pessoa.getIdade());
			Iterator dataIterator = pessoa.datasIterator();
			while(dataIterator.hasNext()){
				System.out.println("Data : " + (String)dataIterator.next());
			}
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		}		
	}

}
