/*
 * Criado na data Feb 4, 2005
 *
 * Este código é de propriedade da Michelin(NEORIS)
 * 
 */
package br.com.neoris.util.test;

import junit.framework.TestCase;

/**
 * Title PooledDriverConnectionTest.java
 * <b> NEORIS - Brasil </b>
 * @author wsadm
 *
 * 
 */
public class PooledDriverConnectionTest extends TestCase {

	/**
	 * Constructor for PooledDriverConnectionTest.
	 * @param arg0
	 */
	public PooledDriverConnectionTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.swingui.TestRunner.run(PooledDriverConnectionTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetConnection() {
	}

}
