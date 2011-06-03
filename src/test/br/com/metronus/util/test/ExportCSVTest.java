/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.metronus.util.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import junit.framework.TestCase;
import br.com.metronus.util.export.ExportCSV;

/**
 * Title ExportCSVTest.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class ExportCSVTest extends TestCase {

	private ExportCSV csv;
	private File arq;
	/**
	 * Constructor for ExportCSVTest.
	 * @param arg0
	 */
	public ExportCSVTest(String arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		Pessoa pessoa = null;
		ArrayList list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			pessoa = new Pessoa();
			pessoa.setEndereco("Av America");
			pessoa.setIdade(new Double(24));
			pessoa.setNome("Andre Fonseca - teste");
			pessoa.setSexo("masculino");
			pessoa.setForte(new Boolean(true));			
			list.add(pessoa);
		}
		arq = new File("teste.csv");
		csv = new ExportCSV(list);
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(ExportCSVTest.class);
	}

	public void testExport() {
		try {
			csv.addMapping("nome");
			csv.addMapping("endereco");			
			csv.addMapping("sexo");
			csv.addMapping("idade");
			csv.addMapping("forte");
			assertNotNull(csv.export());			
			PrintWriter out = new PrintWriter(new FileOutputStream(arq));
			out.println(csv.export());
			out.flush();
			out.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
}
