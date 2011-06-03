package br.com.metronus.util.test;

import java.io.File;

import javax.swing.JOptionPane;

import br.com.metronus.util.maintenance.Fix;
import br.com.metronus.util.maintenance.impl.LogTxtMaintenance;

import junit.framework.TestCase;

public class LogTxtMaintenanceTest extends TestCase {
	LogTxtMaintenance maintenance;
	public static boolean f= false;

	/*
	 * Test method for 'br.com.metronus.util.maintenance.impl.LogTxtMaintenance.start()'
	 */
	public void testStart() {
		LogTxtMaintenance maintenance = new LogTxtMaintenance(new File("D:\\Dados\\Projetos\\integracaosrc\\dist\\integracao\\default_error.log"));
		maintenance.createPattern("INFO").addFix(new Fix(){
			public void fixing() {				
				JOptionPane.showMessageDialog(null,"Achei");
				System.out.println("Achei");
				LogTxtMaintenanceTest.f = true;
			}
		});
		maintenance.createPattern("DEBUG").addFix(new Fix(){
			public void fixing() {				
				JOptionPane.showMessageDialog(null,"Debug");				
				LogTxtMaintenanceTest.f = true;
			}
		});
		maintenance.start();
		assertTrue(f);
	}

	/*
	 * Test method for 'br.com.metronus.util.maintenance.impl.LogTxtMaintenance.stop()'
	 */
	public void testStop() {
		maintenance.stop();
	}

}
