/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Title NeorisUtilTests.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class NeorisUtilTests {

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(NeorisUtilTests.class);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for br.com.neoris.util.test");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(StringUtilTest.class));
		suite.addTest(new TestSuite(ExportCSVTest.class));
		suite.addTest(new TestSuite(DateUtilTest.class));
		//$JUnit-END$
		return suite;
	}
}

