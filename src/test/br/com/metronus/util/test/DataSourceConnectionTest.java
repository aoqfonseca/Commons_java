/*
 * Criado na data Feb 4, 2005
 *
 * Este c�digo � de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.test;

import junit.framework.TestCase;

/**
 * Title DataSourceConnectionTest.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class DataSourceConnectionTest extends TestCase {

	/**
	 * Constructor for DataSourceConnectionTest.
	 * @param arg0
	 */
	public DataSourceConnectionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(DataSourceConnectionTest.class);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetConnection() {	    
	}

}
